package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaImpresionTirilla;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestInsertarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestNps;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.npss.ResponseNps;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;
import com.crystal.selfcheckoutapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaImpresionTirilla implements IVistaImpresionTirilla.Modelo{
    private Context contexto;
    View view;
    private IVistaImpresionTirilla.Presentador presentador;

    public ModeloVistaImpresionTirilla(Context contexto, IVistaImpresionTirilla.Presentador presentador) {
        this.contexto = contexto;
        this.presentador = presentador;
    }

    @Override
    public void apiPromotorPuntuacion(Factura factura) {
        //API para insertar la calificación que dio el cliente al autopago.
        RequestNps requestNps = new RequestNps(Integer.parseInt(factura.getNumeroTransaccion()),
                factura.getTienda(), Constantes.CLASE_DOCUMENTO, factura.getCliente().getCustomerId(),
                Integer.toString(factura.getCliente().getCalificacion()), factura.getCedulaCajero(),
                factura.getCliente().getCedula(false));
        LogFile.adjuntarLog("Request Puntuación Experiencia", requestNps);
        Call<ResponseNps> npsCall = Utilidades.servicioRetrofit(Constantes.API_NN).doCalificacionNps(requestNps);
        npsCall.enqueue(new Callback<ResponseNps>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNps> call, @NonNull Response<ResponseNps> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Puntuación Experiencia Response", "Puntuación Guardada Exitosamente");
                        presentador.ocultarDialogProgressBar();
                        presentador.respuestaCalificacion();
                    }else{
                        LogFile.adjuntarLog("Puntuación Experiencia Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CALIFICACION_NPS);
                    }
                }else{
                    LogFile.adjuntarLog("Puntuación Experiencia Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CALIFICACION_NPS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNps> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Puntuación Experiencia Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CALIFICACION_NPS);
            }
        });
    }

    @Override
    public void apiGuardarPerifericos() {
        presentador.dialogProgressBar("Guardando perifericos...", false);
        List<RequestInsertarPerifericos> perifericos = new ArrayList<>();

        perifericos.add(new RequestInsertarPerifericos(SPM.getString(Constantes.CAJA_CODIGO),
                SPM.getString(Constantes.CAJA_CODIGO_TIENDA), "", "",
                Constantes.CONST_IMPRESORA_EPSON_BLUETOOTH_AUTOPAGO,
                SPM.getString(Constantes.IMPRESORA_MAC) != null ? SPM.getString(Constantes.IMPRESORA_MAC) : "",
                "", "", "", "",
                SPM.getString(Constantes.USER_NAME), ""));

        perifericos.add(new RequestInsertarPerifericos(SPM.getString(Constantes.CAJA_CODIGO),
                SPM.getString(Constantes.CAJA_CODIGO_TIENDA),
                SPM.getString(Constantes.IMPRESORA_IP) != null ? SPM.getString(Constantes.IMPRESORA_IP) : "",
                SPM.getString(Constantes.IMPRESORA_PUERTO) != null ? SPM.getString(Constantes.IMPRESORA_PUERTO) : "",
                Constantes.CONST_IMPRESORA_EPSON_AUTOPAGO, "", "", "",
                "", "", SPM.getString(Constantes.USER_NAME), ""));

        LogFile.adjuntarLog("Request Guardar Perifericos", perifericos);

        Call<ResponseGuardarPerifericos> callGuardarPerifericos = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doGuardarPerifericos(SPM.getString(Constantes.USER_NAME) + " - " + contexto.getResources().getString(R.string.version_apk),perifericos);

        callGuardarPerifericos.enqueue(new Callback<ResponseGuardarPerifericos>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGuardarPerifericos> call, @NonNull Response<ResponseGuardarPerifericos> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog("Guardar Perifericos Response", "Perifericos Guardados Exitosamente");
                    presentador.ocultarDialogProgressBar();
                    presentador.respuestaGuardarPerifericos(response.body());
                }else{
                    LogFile.adjuntarLog("Guardar Perifericos Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_GUARDAR_PERIFERICOS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGuardarPerifericos> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Guardar Perifericos Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_GUARDAR_PERIFERICOS);
            }
        });
    }
}