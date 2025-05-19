package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;

import com.crystal.selfcheckoutapp.Modelo.Interface.IBase;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.ResponseAperturaCaja;
import com.crystal.selfcheckoutapp.R;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloBase implements IBase.Modelo {
    private Context contexto;
    private IBase.Presentador presentador;
    private Utilidades util;
    public ModeloBase(Context contexto, Utilidades util, IBase.Presentador presentador) {
        this.contexto = contexto;
        this.presentador = presentador;
        this.util = util;
    }

    @Override
    public void apiAperturaCaja(String caja) {
        presentador.dialogProgressBar(contexto.getResources().getString(R.string.progress_cargando),
                false);
        RequestCaja requestCaja = new RequestCaja(caja);
        LogFile.adjuntarLog("Request Validar Apertura Caja", requestCaja);
        Call<ResponseAperturaCaja> aperturaCajaCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doAperturaCaja(requestCaja);
        aperturaCajaCall.enqueue(new Callback<ResponseAperturaCaja>() {
            @Override
            public void onResponse(@NotNull Call<ResponseAperturaCaja> call, @NotNull Response<ResponseAperturaCaja> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog("Validar Apertura Caja Response", response.body());
                    presentador.ocultarDialogProgressBar();
                    presentador.respuestaValidarAperturaCaja(response.body());
                }else{
                    LogFile.adjuntarLog("Validar Apertura Caja Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_APERTURA_CAJA);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseAperturaCaja> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Validar Apertura Caja Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_APERTURA_CAJA);
            }
        });
    }
}
