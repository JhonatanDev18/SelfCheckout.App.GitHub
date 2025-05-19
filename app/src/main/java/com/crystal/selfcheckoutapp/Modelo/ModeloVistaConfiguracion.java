package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConfiguracion;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseTiendas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.ResponseCajas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseConsultarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.tienda.ResponseTienda;
import com.crystal.selfcheckoutapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaConfiguracion implements IVistaConfiguracion.Modelo {
    private Context contexto;
    private IVistaConfiguracion.Presentador presentador;
    private Utilidades util;

    public ModeloVistaConfiguracion(Context contexto, IVistaConfiguracion.Presentador presentador) {
        this.contexto = contexto;
        this.presentador = presentador;
        util = new Utilidades(contexto);
    }

    @Override
    public void apiConsultarPerifericos(String tienda, String caja) {
        presentador.dialogProgressBar("Consultando Perifericos...", false);

        RequestPerifericos requestPerifericos = new RequestPerifericos(tienda, caja);
        LogFile.adjuntarLog("Request Consultar Perifericos", requestPerifericos);
        Call<ResponseConsultarPerifericos> call = Utilidades.servicioRetrofit(Constantes.API_NN).doConsultarPerifericos(
                SPM.getString(Constantes.USER_NAME)+" - "+contexto.getResources().getString(R.string.version_apk),requestPerifericos);
        call.enqueue(new Callback<ResponseConsultarPerifericos>() {
            @Override
            public void onResponse(@NonNull Call<ResponseConsultarPerifericos> call, @NonNull Response<ResponseConsultarPerifericos> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    presentador.ocultarDialogProgressBar();
                    presentador.respuestaConfigurarPerifericos(response.body());
                }else{
                    LogFile.adjuntarLog("Consultar Perifericos Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTAR_PERIFERICOS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseConsultarPerifericos> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Consultar Perifericos Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTAR_PERIFERICOS);
            }
        });
    }

    @Override
    public void apiConsultarTiendas() {
        presentador.dialogProgressBar("Consultando tiendas...", false);

        LogFile.adjuntarLog("Request Consultar Tiendas", "Consultando todas las tiendas");

        Call<ResponseTiendas> call = Utilidades.servicioRetrofit(Constantes.API_INTERMEDIA).doTiendas();
        call.enqueue(new Callback<ResponseTiendas>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTiendas> call, @NonNull Response<ResponseTiendas> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    presentador.respuestaConsultarTiendas(response.body());
                }else{
                    LogFile.adjuntarLog("Consultar Tiendas Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTAR_TIENDAS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTiendas> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Consultar Tiendas Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTAR_TIENDAS);
            }
        });
    }

    @Override
    public void apiConsultarParametrosQRBancolombia(String tienda) {
        Call<ResponseTienda> call = Utilidades.servicioRetrofit(Constantes.API_NN).doTienda(tienda);
        call.enqueue(new Callback<ResponseTienda>() {
            @Override
            public void onResponse(Call<ResponseTienda> call, Response<ResponseTienda> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getEsValida()) {
                        SPM.setString(Constantes.TIENDA_MERCHANTID, response.body().getTienda().getMerchantID());
                        SPM.setString(Constantes.TIENDA_NOMBRE, response.body().getTienda().getNombre());
                        LogFile.adjuntarLog("Extracción parámetros tienda:", response.body().toString());
                        presentador.ocultarDialogProgressBar();
                    } else {
                        LogFile.adjuntarLog("TIENDA PARÁMETROS Error", response.message());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                                Constantes.SERVICIO_CONSULTAR_TIENDA);
                        SPM.setString(Constantes.TIENDA_NOMBRE, null);
                        SPM.setString(Constantes.TIENDA_MERCHANTID, null);
                        presentador.ocultarDialogProgressBar();
                    }
                } else {
                    LogFile.adjuntarLog("TIENDA PARÁMETROS Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTAR_TIENDA);
                    SPM.setString(Constantes.TIENDA_NOMBRE, null);
                    SPM.setString(Constantes.TIENDA_MERCHANTID, null);
                }
            }

            @Override
            public void onFailure(Call<ResponseTienda> call, Throwable t) {
                Log.e("LOGCAT", "ErrorConsultaTienda: " + call + t);
                SPM.setString(Constantes.TIENDA_NOMBRE, null);
                SPM.setString(Constantes.TIENDA_MERCHANTID, null);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTAR_TIENDA);
            }
        });
    }
    @Override
    public void apiConsultarCajas(String tienda) {
        presentador.dialogProgressBar("Consultando cajas...", false);

        LogFile.adjuntarLog("Request Consultar Cajas", "Consultando todas las cajas");

        Call<ResponseCajas> cajasCall = Utilidades.servicioRetrofit(Constantes.API_NN).doCajas();

        cajasCall.enqueue(new Callback<ResponseCajas>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCajas> call, @NotNull Response<ResponseCajas> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    presentador.respuestaConsultarCajas(response.body(), tienda);
                }else {
                    LogFile.adjuntarLog("Consultar Cajas Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CAJAS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCajas> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Consultar Cajas Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CAJAS);
            }
        });
    }

    @Override
    public void apiConsultarParametros() {
        presentador.dialogProgressBar("Consultando parametros", false);

        List<RequestParametros> listaParametros = new ArrayList<>();
        listaParametros.add(new RequestParametros("ZPOSM:DF:TIMEOUT"));
        listaParametros.add(new RequestParametros("ZPOSM:DF:TIMEOUT:ES"));

        LogFile.adjuntarLog("Request Consultar Parametros", listaParametros);

        //API de parametros
        Call<ResponseParametros> parametrosCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doParametros(listaParametros);

        parametrosCall.enqueue(new Callback<ResponseParametros>() {
            @Override
            public void onResponse(@NotNull Call<ResponseParametros> call, @NotNull Response<ResponseParametros> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    presentador.respuestaParametros(response.body(), listaParametros);
                }else{
                    LogFile.adjuntarLog("Consultar Parametros Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_PARAMETROS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseParametros> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Consultar Parametros Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_PARAMETROS);
            }
        });
    }
}