package com.crystal.selfcheckoutapp.Modelo.retrofit;
import android.util.Log;

import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRetrofit {
    private static ClienteRetrofit instancia = null;
    private Retrofit retrofit;
    private ServiceRetrofit servicios;

    public ClienteRetrofit(String urlBase){
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .client(ok)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        servicios = retrofit.create(ServiceRetrofit.class);
    }

    public static ClienteRetrofit obtenerInstancia(int api) {
        String andURL;

        if(api == Constantes.API_INTERMEDIA){
            andURL = SPM.getString(Constantes.API_POSSERVICE_URL);
        }else{
            andURL = SPM.getString(Constantes.API_POSSERVICE_URL_NN);
        }

        if (instancia == null){
            instancia = new ClienteRetrofit(andURL);
        }else{
            String insURL = instancia.retrofit.baseUrl().toString();
            if(!insURL.equals(andURL)){
                instancia = new ClienteRetrofit(andURL);
                Log.i("logcat", "new: "+instancia.retrofit.baseUrl()+ " - " + SPM.getString(Constantes.API_POSSERVICE_URL));
            }
        }

        return instancia;
    }

    public ServiceRetrofit obtenerServicios(){
        return servicios;
    }
}


