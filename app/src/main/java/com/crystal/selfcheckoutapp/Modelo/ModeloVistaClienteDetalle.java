package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaDetalleCliente;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestActualizarCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCrearCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestInsertarHabeasData;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.ResponseActualizarCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.ResponseCrearCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.habeasdata.ResponseConsultaHabeas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.habeasdata.ResponseInsertarHabeas;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaClienteDetalle implements IVistaDetalleCliente.Modelo {
    private IVistaDetalleCliente.Presentador presentador;
    private Context contexto;
    private String tienda;
    private String caja;
    private String marca;

    public ModeloVistaClienteDetalle(IVistaDetalleCliente.Presentador presentador, Context contexto) {
        this.presentador = presentador;
        this.contexto = contexto;
        tienda = SPM.getString(Constantes.CAJA_CODIGO_TIENDA);
        caja = SPM.getString(Constantes.CAJA_CODIGO);
        marca = Utilidades.marcaTienda();
    }

    @Override
    public void apiConsultarHabeasData(String cedula, String tipoDocumento) {
        //Consultar API para verificar si ya tiene habeas data o no.
        Call<ResponseConsultaHabeas> consultaHabeasCall = Utilidades.servicioRetrofit(Constantes.API_NN).
                doConsultaHabeas(cedula, tipoDocumento);
        consultaHabeasCall.enqueue(new Callback<ResponseConsultaHabeas>() {
            @Override
            public void onResponse(@NotNull Call<ResponseConsultaHabeas> call, @NotNull Response<ResponseConsultaHabeas> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.ocultarDialogProgressBar();
                        presentador.respuestaConsultaHabeasData(response.body().isSolicitarHabeas());
                    }else{
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_HABEAS);
                    }
                }else{
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_HABEAS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseConsultaHabeas> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("ErrorApiConsultaHabeasData", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_HABEAS);
            }
        });
    }

    @Override
    public void apiRegistrarCliente(Cliente cliente) {
        String canal = SPM.getString(Constantes.PARAM_CANAL_CLIENTE);

        //Api que consume servicio nativo de creación de nuevo usuario
        RequestCrearCliente requestCrearCliente = new RequestCrearCliente(cliente.getCedula(false),
                cliente.getTipoDocumento(), cliente.getNombre(), cliente.getApellido(),
                cliente.getSexo(), cliente.getCorreo(), cliente.getCelular(), cliente.getDiaCumpleanos(),
                cliente.getMesCumpleanos(), cliente.getAnoCumpleanos(), tienda, cliente.getDireccion(),
                "", cliente.getCiudad(), cliente.getPaisId(), cliente.getRegionId(),
                cliente.getCodigoPostal(), canal);

        Call<ResponseCrearCliente> crearClienteCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doCrearCliente(requestCrearCliente);
        crearClienteCall.enqueue(new Callback<ResponseCrearCliente>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCrearCliente> call, @NotNull Response<ResponseCrearCliente> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.respuestaCrearCliente();
                    }else{
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CREAR_CLIENTE);
                    }
                }else{
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CREAR_CLIENTE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCrearCliente> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("ErrorApiCrearCliente", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CREAR_CLIENTE);
            }
        });
    }

    @Override
    public void apiActualizarCliente(Cliente cliente) {
        //Api que consume servicio nativo de actualización de cliente
        RequestActualizarCliente requestActualizarCliente = new RequestActualizarCliente(cliente.getCedula(false),
                cliente.getCustomerId(), cliente.getNombre(), cliente.getApellido(),
                cliente.getSexo(), cliente.getCorreo(), cliente.getCelular(), cliente.getDiaCumpleanos(),
                cliente.getMesCumpleanos(), cliente.getAnoCumpleanos(), tienda, cliente.getDireccion(),
                cliente.getDireccion2(), cliente.getCiudad(), cliente.getPaisId(), cliente.getRegionId(),
                cliente.getCodigoPostal(), true);

        Call<ResponseActualizarCliente> actualizarClienteCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doActualizarCliente(requestActualizarCliente);
        actualizarClienteCall.enqueue(new Callback<ResponseActualizarCliente>() {
            @Override
            public void onResponse(@NotNull Call<ResponseActualizarCliente> call, @NotNull Response<ResponseActualizarCliente> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.ocultarDialogProgressBar();
                        presentador.respuestaActualizarCliente(cliente);
                    }else{
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_HABEAS);
                    }
                }else{
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_ACTUALIZAR_CLIENTE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseActualizarCliente> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("ErrorApiActualizarCliente", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_ACTUALIZAR_CLIENTE);
            }
        });
    }

    @Override
    public void apiInsertarHabeasData(Cliente cliente) {
        //Api que consume servicio nativo para insertarle habeas data al cliente
        RequestInsertarHabeasData requestInsertarHabeasData = new RequestInsertarHabeasData(caja,
                cliente.getCorreo(), Constantes.ESTADO_CONSENTIMIENTO_ACEPTADO,
                cliente.getCedula(false), Integer.parseInt(cliente.getTipoDocumento()), marca);
        Call<ResponseInsertarHabeas> insertarHabeasCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doInsertarHabeas(requestInsertarHabeasData);

        insertarHabeasCall.enqueue(new Callback<ResponseInsertarHabeas>() {
            @Override
            public void onResponse(@NotNull Call<ResponseInsertarHabeas> call,
                                   @NotNull Response<ResponseInsertarHabeas> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        presentador.ocultarDialogProgressBar();
                        presentador.respuestaInsertarHabeas(cliente);
                    }else{
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_INSERTAR_HABEAS);
                    }
                }else{
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_INSERTAR_HABEAS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseInsertarHabeas> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("ErrorApiInsertarHabeasData", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_INSERTAR_HABEAS);
            }
        });
    }
}