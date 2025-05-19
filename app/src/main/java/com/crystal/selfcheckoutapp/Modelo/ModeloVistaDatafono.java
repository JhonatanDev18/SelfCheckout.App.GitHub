package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaDatafono;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestInsertarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.Parametro;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;
import com.crystal.selfcheckoutapp.R;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaDatafono implements IVistaDatafono.Modelo {
    private Context contexto;
    private IVistaDatafono.Presentador presentador;
    private Timer tiempo;
    private TimerTask tarea;
    private int tiempoEsperaDatafono;
    private long tiempoEsperaCierreDetafono;
    private CountDownTimer countDownTimer;

    public ModeloVistaDatafono(Context contexto, IVistaDatafono.Presentador presentador) {
        this.contexto = contexto;
        this.presentador = presentador;
    }

    @Override
    public void apiCompraDatafono() {
        tiempoEsperaDatafono = Utilidades.tiempoDatafono();
        tiempoEsperaCierreDetafono = Utilidades.tiempoEsperaDatafono();

        countDownTimer = new CountDownTimer(tiempoEsperaCierreDetafono, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Actualizar el texto del temporizador en cada tick (cada segundo)
            }

            @Override
            public void onFinish() {
                // Acción a realizar al finalizar el tiempo (3 minutos)
                presentador.ocultarDialogProgressBar();
            }
        };

        //API para probar la comunicación con el datafono
        Call<ResponseCompraDatafono> datafonoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doCompraDatafono(Utilidades.peticionCompraDatafono("400101"));
        datafonoCall.enqueue(new Callback<ResponseCompraDatafono>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCompraDatafono> call, @NonNull Response<ResponseCompraDatafono> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.ocultarDialogProgressBar();
                        SPM.setBoolean(Constantes.BOOL_ANIMACION_DATAFONO, true);
                        presentador.dialogProgressBar(contexto.getString(R.string.progress_seguir_pasos_datafono_cliente), true);

                        //Iniciar el temporizador
                        countDownTimer.start();
                        iniciarTareaRespuestaDatafono();
                    }else{
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO);
                    }
                }else{
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCompraDatafono> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("ErrorApiCompraDatafono", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO);
            }
        });
    }

    @Override
    public void apiGuardarPerifericos() {
        presentador.dialogProgressBar("Guardando perifericos...", false);

        List<RequestInsertarPerifericos> perifericos = new ArrayList<>();

        perifericos.add(new RequestInsertarPerifericos(SPM.getString(Constantes.CAJA_CODIGO),
                SPM.getString(Constantes.CAJA_CODIGO_TIENDA), "", "",
                Constantes.CONST_DATAFONO_CREDIBANCO_AUTOPAGO, "",
                SPM.getString(Constantes.USUARIO_DATAFONO) != null ? SPM.getString(Constantes.USUARIO_DATAFONO) : "",
                SPM.getString(Constantes.PASS_DATAFONO) != null ? SPM.getString(Constantes.PASS_DATAFONO) : "",
                SPM.getString(Constantes.CODIGO_DATAFONO) != null ? SPM.getString(Constantes.CODIGO_DATAFONO) : "",
                SPM.getString(Constantes.CODIGO_UNICO_DATAFONO) != null ? SPM.getString(Constantes.CODIGO_UNICO_DATAFONO) : "",
                SPM.getString(Constantes.USER_NAME), ""));

        perifericos.add(new RequestInsertarPerifericos(SPM.getString(Constantes.CAJA_CODIGO),
                SPM.getString(Constantes.CAJA_CODIGO_TIENDA), "", "",
                Constantes.CONST_DATAFONO_REDEBAN_AUTOPAGO, "",
                SPM.getString(Constantes.USUARIO_DATAFONO_RB) != null ? SPM.getString(Constantes.USUARIO_DATAFONO_RB) : "",
                SPM.getString(Constantes.PASS_DATAFONO_RB) != null ? SPM.getString(Constantes.PASS_DATAFONO_RB) : "",
                SPM.getString(Constantes.CODIGO_DATAFONO_RB) != null ? SPM.getString(Constantes.CODIGO_DATAFONO_RB) : "",
                SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB) != null ? SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB) : "",
                SPM.getString(Constantes.USER_NAME),
                SPM.getString(Constantes.PASS_SUPERVISOR_RB) != null ? SPM.getString(Constantes.PASS_SUPERVISOR_RB) : ""));

        Call<ResponseGuardarPerifericos> callGuardarPerifericos = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doGuardarPerifericos(SPM.getString(Constantes.USER_NAME) + " - " + contexto.getResources().getString(R.string.version_apk),perifericos);

        callGuardarPerifericos.enqueue(new Callback<ResponseGuardarPerifericos>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGuardarPerifericos> call, @NonNull Response<ResponseGuardarPerifericos> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    presentador.ocultarDialogProgressBar();
                    presentador.respuestaGuardarPerifericos(response.body());
                }else{
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_GUARDAR_PERIFERICOS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGuardarPerifericos> call, @NonNull Throwable t) {
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_GUARDAR_PERIFERICOS);
            }
        });
    }

    private void iniciarTareaRespuestaDatafono() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                if(presentador.estadoProgress()){
                    if(tarea != null){
                        tarea.cancel();
                    }

                    if(tiempo != null){
                        tiempo.cancel();
                    }
                }else{
                    apiRespuestaDatafono();
                }
            }
        };

        tiempo = new Timer();
        tiempo.schedule(tarea, tiempoEsperaDatafono);
    }

    private void apiRespuestaDatafono(){
        //API para consultar el pago del datafono
        RequestRespuestaDatafono respuestaDatafono = Utilidades.peticionRespuestaDatafono();
        Call<ResponseDatafono> datafonoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doRespuestaDatafono(respuestaDatafono);
        datafonoCall.enqueue(new Callback<ResponseDatafono>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDatafono> call, @NonNull Response<ResponseDatafono> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.ocultarDialogProgressBar();
                        Utilidades.sweetAlert(contexto.getResources().getString(R.string.pago_exitososo),
                                response.body().getRespuestaDatafono().toString(), SweetAlertDialog.SUCCESS_TYPE,
                                contexto);
                    }else{
                        if(response.body().getMensaje().equals("No se encuentra el pago")){
                            iniciarTareaRespuestaDatafono();
                        }else{
                            presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                    Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
                        }
                    }
                }else{
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDatafono> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("ErrorApiRespuestaDatafono", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
            }
        });
    }
}
