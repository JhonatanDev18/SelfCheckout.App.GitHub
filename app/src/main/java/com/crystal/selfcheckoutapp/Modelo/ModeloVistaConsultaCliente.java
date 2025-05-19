package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConsultaCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestActualizarTransaccion;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseActualizarTransaccion;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseRespuestasDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.SeguridadDatafono;
import com.crystal.selfcheckoutapp.Presentador.PresentadorBase;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.ResponseCliente;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaConsultaCliente implements IVistaConsultaCliente.Modelo {
    private Context contexto;
    private IVistaConsultaCliente.Presentador presentador;
    private Utilidades util;
    private String tienda;
    private String caja;
    private Timer tiempo;
    private TimerTask tarea;
    private RespuestaCompletaTef respuestaCompletaTef;
    private int tiempoEsperaDatafono;
    private long tiempoEsperaCierreDetafono;
    private CountDownTimer countDownTimer;

    public ModeloVistaConsultaCliente(Context contexto, IVistaConsultaCliente.Presentador presentador) {
        this.contexto = contexto;
        this.presentador = presentador;
        util = new Utilidades(contexto);
        caja = SPM.getString(Constantes.CAJA_CODIGO);
        tienda = SPM.getString(Constantes.CAJA_CODIGO_TIENDA);
    }

    @Override
    public void apiConsultaCedula(String cedula) {
        //Api para consultar si la cédula existe en CEGID o si es un cliente nuevo.
        LogFile.adjuntarLog("PASO 1: Request Consulta Cedula", cedula);
        Call<ResponseCliente> clienteCall = Utilidades.servicioRetrofit(Constantes.API_NN).doConsultarCliente(cedula);

        clienteCall.enqueue(new Callback<ResponseCliente>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCliente> call, @NotNull Response<ResponseCliente> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        Cliente cliente = response.body().getCliente();

                        if(Utilidades.containsSpecialCharacters(cliente.getNombreCompleto())){
                            LogFile.adjuntarLog("PASO 1: Response Consulta Cedula Error (Caracteres Especiales)", cliente);

                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    contexto.getResources().getString(R.string.error_caracter_especial_nombre), Constantes.SERVICIO_CONSULTA_CLIENTE_CARACTER_ESPECIAL);
                        }else{
                            LogFile.adjuntarLog("PASO 1: Response Consulta Cedula", cliente);
                            presentador.respuestaConsultaCliente(cliente);
                        }
                        presentador.ocultarDialogProgressBar();
                    }else{
                        LogFile.adjuntarLog("PASO 1: Response Consulta Cedula Error", response.body().getMensaje());
                        if(response.body().getMensaje().equals(contexto.getResources().
                                getString(R.string.error_cliente_no_existe))){
                            if(SPM.getBoolean(Constantes.PARAM_CAJA_HABEAS)){
                                presentador.clienteGenerico(Utilidades.clienteGenerico(contexto));
                            }
                            presentador.ocultarDialogProgressBar();
                        }else{
                            LogFile.adjuntarLog("PASO 1: Response Consulta Cedula Error", response.body().getMensaje());
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_CLIENTE);
                        }
                    }
                }else{
                    LogFile.adjuntarLog("PASO 1: Response Consulta Cedula Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_CLIENTE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCliente> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("PASO 1: Response Consulta Cedula Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_CLIENTE);
            }
        });
    }

    @Override
    public void apiRespuestaDatafono(boolean anulacion) {
        //API para consultar el pago del datafono
        Call<ResponseDatafono> datafonoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doRespuestaDatafono(Utilidades.peticionRespuestaDatafono());
        datafonoCall.enqueue(new Callback<ResponseDatafono>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDatafono> call, @NonNull Response<ResponseDatafono> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Respuesta Datafono [Anulación: " + anulacion +"]" , response.body().getRespuestaDatafono());
                        presentador.ocultarDialogProgressBar();
                        if(anulacion){
                            apiActualizarTransaccion(respuestaCompletaTef);
                        }else{
                            presentador.respuestaDatafono(response.body().getRespuestaDatafono());
                        }
                    }else{
                        if(anulacion){
                            if(response.body().getMensaje().equals("No se encuentra el pago")){
                                iniciarTareaRespuestaDatafono();
                            }else{
                                LogFile.adjuntarLog("Respuesta Datafono Error", "Anulación: "+ response.body().getMensaje());
                                presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                        Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
                            }
                        }else{
                            LogFile.adjuntarLog("Respuesta Datafono Error", response.body().getMensaje());
                            presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                    Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
                        }
                    }
                }else{
                    LogFile.adjuntarLog("Respuesta Datafono Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDatafono> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Respuesta Datafono Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
            }
        });
    }

    @Override
    public void apiActualizarTransaccion(RespuestaCompletaTef respuestaCompletaTef){
        //API para actualizar en la base de datos la anulación de la transacción
        RequestActualizarTransaccion requestActualizarTransaccion =
                new RequestActualizarTransaccion(respuestaCompletaTef.getHeader().getNumeroTransaccion(),
                        tienda, caja, respuestaCompletaTef.getHeader().getReferenciaInterna());
        LogFile.adjuntarLog("Request Actualizar Respuesta Tef", requestActualizarTransaccion);
        Call<ResponseActualizarTransaccion> transaccionCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doActualizarTransacion(requestActualizarTransaccion);
        transaccionCall.enqueue(new Callback<ResponseActualizarTransaccion>() {
            @Override
            public void onResponse(@NonNull Call<ResponseActualizarTransaccion> call, @NonNull Response<ResponseActualizarTransaccion> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.respuestaActualizarAnulacion();
                        LogFile.adjuntarLog("Respuesta Actualizar Respuesta Tef", "Transacción Anulada ("+respuestaCompletaTef.getRespuestaTef().getRecibo()+")");
                        Utilidades.sweetAlert("Transacción Anulada ("+respuestaCompletaTef.getRespuestaTef().getRecibo()+")",
                                "La transacción se anuló correctamente",
                                SweetAlertDialog.SUCCESS_TYPE, contexto);
                    }else{
                        LogFile.adjuntarLog("Respuesta Actualizar Respuesta Tef Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                Constantes.SERVICIO_ACTUALIZAR_TRANSACCION_TEF);
                    }
                }else{
                    LogFile.adjuntarLog("Respuesta Actualizar Respuesta Tef Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_ACTUALIZAR_TRANSACCION_TEF);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseActualizarTransaccion> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Respuesta Actualizar Respuesta Tef Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_ACTUALIZAR_TRANSACCION_TEF);
            }
        });
    }

    @Override
    public void apiConsultarRespuestasTef() {
        //API consulta respuestas tef
        Call<ResponseRespuestasDatafono> datafonoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doRespuestasDatafono(util.getThisDateSimple(), tienda, caja);
        datafonoCall.enqueue(new Callback<ResponseRespuestasDatafono>() {
            @Override
            public void onResponse(@NonNull Call<ResponseRespuestasDatafono> call, @NonNull Response<ResponseRespuestasDatafono> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getEsValida()) {
                        presentador.ocultarDialogProgressBar();
                        response.body().setListado(eliminarNulos(response.body().getListado()));
                        LogFile.adjuntarLog("Respuestas Datafonos", response.body().getListado());
                        presentador.mostrarAnulacionesTef(response.body().getListado());
                    } else {
                        if (response.body().getMensaje().equals("No se encontraron datos con esos filtros")) {
                            presentador.ocultarDialogProgressBar();
                            LogFile.adjuntarLog("Respuestas Datafonos Error", "No se encontraron registros tef de hoy");
                            Utilidades.mjsToast("No se encontraron registros tef de hoy",
                                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                        } else {
                            LogFile.adjuntarLog("Respuestas Datafonos Error", response.body().getMensaje());
                            presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                    Constantes.SERVICIO_RESPUESTAS_TEF);
                        }
                    }
                } else {
                    LogFile.adjuntarLog("Respuestas Datafonos Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_RESPUESTAS_TEF);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseRespuestasDatafono> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Respuestas Datafonos Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_RESPUESTAS_TEF);
            }
        });
    }

    @Override
    public void apiAnularTransaccionTef(RespuestaCompletaTef respuestaCompleta) {
        //API para anular un transacion tef
        respuestaCompletaTef = respuestaCompleta;
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

        Call<ResponseCompraDatafono> datafonoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doAnularDatafono(Utilidades.peticionAnulacionDatafono(respuestaCompleta));
        datafonoCall.enqueue(new Callback<ResponseCompraDatafono>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCompraDatafono> call, @NonNull Response<ResponseCompraDatafono> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.ocultarDialogProgressBar();

                        LogFile.adjuntarLog("Anular Compra Datafono", "IsRedeban: "+Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN))
                                +"\nCódigo Datafono: "+response.body().getCompraDatafono().getCodigoTerminal()+
                                "\nNúmero Recibo: "+ response.body().getCompraDatafono().getRecibo());

                        LogFile.adjuntarLog("Anular Compra Datafono", response.body().getCompraDatafono());

                        if(Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN))){
                            presentador.dialogProgressBar(contexto.getString(R.string.progress_seguir_pasos_datafono_cliente_redeban),
                                    false);
                        }else{
                            presentador.dialogProgressBar(contexto.getString(R.string.progress_seguir_pasos_datafono_cliente_anulacion),
                                    false);
                        }

                        countDownTimer.start();
                        iniciarTareaRespuestaDatafono();
                    }else{
                        LogFile.adjuntarLog("Anular Compra Datafono Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                Constantes.SERVICIO_ANULAR_TEF);
                    }
                }else{
                    LogFile.adjuntarLog("Anular Compra Datafono Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_ANULAR_TEF);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCompraDatafono> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Anular Compra Datafono Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_ANULAR_TEF);
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
                    apiRespuestaDatafono(true);
                }
            }
        };

        tiempo = new Timer();
        tiempo.schedule(tarea, tiempoEsperaDatafono);
    }

    private List<RespuestaCompletaTef> eliminarNulos(List<RespuestaCompletaTef> listado){
        for(RespuestaCompletaTef respuesta : listado){
            if (respuesta.getRespuestaTef() == null){
                listado.remove(respuesta);
            }
        }
        return listado;
    }
}