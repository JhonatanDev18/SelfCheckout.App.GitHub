package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaLogin;
import com.crystal.selfcheckoutapp.Modelo.clases.Seguridad;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestLogin;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.ResponseAperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.ResponseCajas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.consecutivofiscal.ConsecutivoFiscal;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.consecutivofiscal.ResponseConsecutivoFiscal;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.login.Login;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.login.ResponseLogin;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.Parametro;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaLogin implements IVistaLogin.Modelo {
    private Context contexto;
    private IVistaLogin.Presentador presentador;
    private String grupo;
    private String usuarioLogin;
    private String contrasenaLogin;

    public ModeloVistaLogin(Context contexto, IVistaLogin.Presentador presentador) {
        this.contexto = contexto;
        this.presentador = presentador;
    }

    @Override
    public void apiLogin(String usuario, String contrasena, boolean seguridad)  {
        //Consultar la Api de login
        RequestLogin requestLogin = new RequestLogin(new Seguridad(usuario, contrasena));
        Call<ResponseLogin> loginCall = Utilidades.servicioRetrofit(Constantes.API_NN).doLogin(requestLogin);
        LogFile.adjuntarLog("Request Login", usuario);

        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NotNull Call<ResponseLogin> call, @NotNull Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Login Response", response.body().getLogin());
                        Login login = response.body().getLogin();

                        //Obtener el grupo al que pertenece el usuario
                        if(seguridad){
                            usuarioLogin = usuario;
                            contrasenaLogin = contrasena;

                            grupo = login.getGrupo();
                            apiParametros("", "", true, null);
                        }else{
                            SPM.setString(Constantes.USER_NAME, login.getUsuario());
                            SPM.setString(Constantes.USER_UTIL, login.getUsuarioUtil());
                            SPM.setString(Constantes.TIENDA_DEFECTO, login.getTiendaPorDefecto());

                            apiCajas(login.getTiendaPorDefecto());
                        }
                    }else{
                        LogFile.adjuntarLog("Login Response Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_LOGIN);
                    }
                }else{
                    LogFile.adjuntarLog("Login Response Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_LOGIN);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseLogin> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Login Response Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_LOGIN);
            }
        });
    }

    @Override
    public void apiCajas(String tiendaDefecto) {
        //Consultar api de cajas
        LogFile.adjuntarLog("Request Cajas", tiendaDefecto);
        Call<ResponseCajas> cajasCall = Utilidades.servicioRetrofit(Constantes.API_NN).doCajas();

        cajasCall.enqueue(new Callback<ResponseCajas>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCajas> call, @NotNull Response<ResponseCajas> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        List<Caja> cajasApi = response.body().getListado();
                        List<Caja> cajas = new ArrayList<>();
                        if(!cajasApi.isEmpty()){
                            for(Caja caja: cajasApi){
                                if(tiendaDefecto.equals(caja.getCodigoTienda())){
                                    cajas.add(caja);
                                }
                            }

                            if(!cajas.isEmpty()){
                                LogFile.adjuntarLog("Cajas Response ["+tiendaDefecto+"]", cajas);
                                presentador.ocultarDialogProgressBar();
                                presentador.seleccionarCaja(cajas);
                            }else{
                                LogFile.adjuntarLog("Cajas Response Error", String.format(contexto.getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto));
                                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                        String.format(contexto.getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto),
                                        Constantes.SERVICIO_LOGIN);
                            }
                        }else {
                            LogFile.adjuntarLog("Cajas Response Error", String.format(contexto.getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto));
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    String.format(contexto.getResources().getString(R.string.sin_cajas_disponible), tiendaDefecto),
                                    Constantes.SERVICIO_LOGIN);
                        }
                    }else{
                        LogFile.adjuntarLog("Cajas Response Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_LOGIN);
                    }
                }else {
                    LogFile.adjuntarLog("Cajas Response Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CAJAS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCajas> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Cajas Response Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CAJAS);
            }
        });
    }

    @Override
    public void apiConsecutivoFiscal(String codigoCaja) {
        //API para consultar el consecutivo fiscal
        RequestCaja requestCaja = new RequestCaja(codigoCaja);
        LogFile.adjuntarLog("Request Consecutivo Fiscal", requestCaja);

        Call<ResponseConsecutivoFiscal> consecutivoFiscalCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsecutivoFiscal(requestCaja);

        consecutivoFiscalCall.enqueue(new Callback<ResponseConsecutivoFiscal>() {
            @Override
            public void onResponse(@NotNull Call<ResponseConsecutivoFiscal> call,
                                   @NotNull Response<ResponseConsecutivoFiscal> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        ConsecutivoFiscal consecutivoFiscal = response.body().getConsecutivoFiscal();

                        int numeroInicial = consecutivoFiscal.getNumeroInicial();
                        int numeroFinal = consecutivoFiscal.getNumeroFinal();
                        int numeroAlerta = consecutivoFiscal.getNumeroAlerta();

                        int consecutivo = consecutivoFiscal.getConsecutivo();

                        boolean esValida = true;
                        StringBuilder mensaje = new StringBuilder();
                        mensaje.append("");

                        if (response.body().getMensaje() != null) {
                            mensaje.append(response.body().getMensaje()).append("\n");
                        }

                        if(numeroInicial <= consecutivo && numeroFinal >= consecutivo){
                            if(consecutivo >= (numeroFinal - numeroAlerta)){
                                mensaje.append(consecutivoFiscal.getMensajeAlertaLimiteConsesecutivo())
                                        .append("\n");
                            }
                        }else{
                            esValida = false;
                            mensaje.append(consecutivoFiscal.getMensajeAlertaLimiteFiscal()).append("\n");
                        }

                        Date fechaFinal = Utilidades.parceFecha(consecutivoFiscal.getFechaFinal(),
                                Constantes.FORMATO_LARGO);
                        Date fechaAutorizacion = Utilidades.parceFecha(consecutivoFiscal.
                                getFechaAutorizacion(), Constantes.FORMATO_LARGO);
                        Date hoy = new Date();
                        int diaAlerta = consecutivoFiscal.getDiaAlerta();

                        if(fechaAutorizacion.getTime() <= hoy.getTime() && fechaFinal.getTime() >= hoy.getTime()){
                            fechaFinal.setDate(fechaFinal.getDate() - diaAlerta);
                            if(hoy.getTime() >= (fechaFinal.getTime())){
                                mensaje.append(consecutivoFiscal.getMensajeAlertaFechaResolucion())
                                        .append("\n");
                            }
                        }else{
                            esValida = false;
                            mensaje.append(consecutivoFiscal.getMensajeAlertaVencimientoResolucion())
                                    .append("\n");
                        }

                        if(esValida){
                            LogFile.adjuntarLog("Consecutivo Fiscal Response", response.body().getConsecutivoFiscal());
                            SPM.setString(Constantes.MSG_ERROR_CONSECUTIVO, !mensaje.toString().isEmpty() ?
                                    mensaje.toString() : null);
                            apiAperturaCaja(codigoCaja);
                        }else{
                            LogFile.adjuntarLog("Consecutivo Fiscal Response Error", mensaje.toString());
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    mensaje.toString(), Constantes.SERVICIO_CONSECUTIVO_FISCAL);
                        }
                    }else{
                        LogFile.adjuntarLog("Consecutivo Fiscal Response Error", response.body().getMensaje() != null ? response.body().getMensaje() : "");
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje() != null ? response.body().getMensaje() : "",
                                Constantes.SERVICIO_CONSECUTIVO_FISCAL);
                    }
                }else{
                    LogFile.adjuntarLog("Consecutivo Fiscal Response Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSECUTIVO_FISCAL);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseConsecutivoFiscal> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Consecutivo Fiscal Response Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSECUTIVO_FISCAL);
            }
        });
    }

    @Override
    public void apiAperturaCaja(String codigoCaja) {
        //API para consultar si la caja esta abierta o no
        RequestCaja requestCaja = new RequestCaja(codigoCaja);
        LogFile.adjuntarLog("Request Validar Apertura Caja", requestCaja);
        Call<ResponseAperturaCaja> aperturaCajaCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doAperturaCaja(requestCaja);

        aperturaCajaCall.enqueue(new Callback<ResponseAperturaCaja>() {
            @Override
            public void onResponse(@NotNull Call<ResponseAperturaCaja> call,
                                   @NotNull Response<ResponseAperturaCaja> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        AperturaCaja aperturaCaja = response.body().getAperturaCaja();

                        if(aperturaCaja.getEstadoCaja().equals("ABIERTA")){
                            LogFile.adjuntarLog("Validar Apertura Caja Response",
                                    aperturaCaja);
                            String tienda = SPM.getString(Constantes.TIENDA_DEFECTO);
                            SPM.setString(Constantes.ID_CAJERO, aperturaCaja.getCajero());
                            apiParametros(codigoCaja, tienda, false, aperturaCaja);
                        }else{
                            LogFile.adjuntarLog("Validar Apertura Caja Response Error",
                                    String.format(contexto.getResources().getString(R.string.caja_cerrada), codigoCaja));
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    String.format(contexto.getResources().getString(R.string.caja_cerrada), codigoCaja),
                                    Constantes.SERVICIO_APERTURA_CAJA);
                        }
                    }else{
                        LogFile.adjuntarLog("Validar Apertura Caja Response Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_APERTURA_CAJA);
                    }
                }else{
                    LogFile.adjuntarLog("Validar Apertura Caja Response Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_APERTURA_CAJA);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseAperturaCaja> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Validar Apertura Caja Response Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_APERTURA_CAJA);
            }
        });
    }

    @Override
    public void apiParametros(String codigoCaja, String tienda, boolean seguridad, AperturaCaja aperturaCaja) {
        List<RequestParametros> listaParametros = new ArrayList<>();
        if(seguridad){
            listaParametros.add(new RequestParametros("ZPOSM:GRUPOSROL:CONFIGURADOR"));
        }else{
            listaParametros.add(new RequestParametros("SELFCHK:COL:CERTREGALO"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:BOLSAS:CANTIDAD"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:READTIME"));
            listaParametros.add(new RequestParametros("SELFCHK:CONFI:OPTION"));
            listaParametros.add(new RequestParametros("WCUPO:TEMPORALES"));
            listaParametros.add(new RequestParametros("ZPOSM:CLIENTE:GENERICO"));
            listaParametros.add(new RequestParametros("ZPOSM:OTP:MEDIOPAGO"));
            listaParametros.add(new RequestParametros("ZPOSM:MEDIOPAGO:TEF"));
            listaParametros.add(new RequestParametros("ZPOSM:MEDIOPAGO:CREDITO1"));
            listaParametros.add(new RequestParametros("ZPOSM:MEDIOPAGO:CREDITO10"));
            listaParametros.add(new RequestParametros("ZPOSM:MEDIOSPAGO:CREDITOS"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:MEDIOPAGO:CLI"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:REF:A:EMPLEADO"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:REF:B:EMPLEADO"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:REF:E:EMPLEADO"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:REF:F:EMPLEADO"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:REF:J:EMPLEADO"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:REF:L:EMPLEADO"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:REF:S:EMPLEADO"));
            listaParametros.add(new RequestParametros("REF:ACT:CLI"));
            listaParametros.add(new RequestParametros("SELFCHK:BOLSA:GEF"));
            listaParametros.add(new RequestParametros("SELFCHK:BOLSA:PB"));
            listaParametros.add(new RequestParametros("SELFCHK:BOLSA:BF"));
            listaParametros.add(new RequestParametros("SELFCHK:BOLSA:GALAX"));
            listaParametros.add(new RequestParametros("SELFCHK:BOLSA:MULTI"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:CONTRIBUYENTE"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:PLAZOCAMBIO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:POLITICASCAMBIO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:LINEAATENCION"));
            listaParametros.add(new RequestParametros("ZPOSM:FACELEC:XML"));
            listaParametros.add(new RequestParametros("VAL:ACT:EMP"));
            listaParametros.add(new RequestParametros("VAL:ACT:CLI"));
            listaParametros.add(new RequestParametros("ZPOSM:CLIENTE:CANAL"));
            listaParametros.add(new RequestParametros("ZPOSM:MEDIOPAGO:QRBANCOLOMBIA"));
            listaParametros.add(new RequestParametros("ZPOSM:MSGARTICULODESCONTABLE"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:MSJDIA:ACTIVO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:MSJDIA:DESC"));
            listaParametros.add(new RequestParametros("ZPOSM:FORMATO:TIENDA:" + codigoCaja));
            listaParametros.add(new RequestParametros("ZPOSM:RFID:TIPORFID:" + tienda));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:TEXTOIVA"));
            listaParametros.add(new RequestParametros("ZPOSM:DONACIONESUNICEF"));
            listaParametros.add(new RequestParametros("ZPOSM:CAJAHABEAS:" + codigoCaja));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:ACTIVO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:CLGEN"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:FORMATO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:MARCA"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:MONTO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:MULTIPLO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:PALABRACLAVE"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:TEXT1"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M1:TEXT2"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:ACTIVO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:CLGEN"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:FORMATO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:MARCA"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:MONTO"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:PALABRACLAVE"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:TEXT1"));
            listaParametros.add(new RequestParametros("ZPOSM:TIQUETE:M2:TEXT2"));
            listaParametros.add(new RequestParametros("SELFCHK:COL:DESC:EMPLEADO"));
            listaParametros.add(new RequestParametros("FACT:ELECTRO:AMBIENTE"));
            listaParametros.add(new RequestParametros("ZPOSM:FE:URLBASE:QR"));
            listaParametros.add(new RequestParametros("ZPOSM:FE:PROVEEDORTEC"));
            listaParametros.add(new RequestParametros("ZPOSM:DF:TIMEOUT"));
            listaParametros.add(new RequestParametros("ZPOSM:DF:TIMEOUT:ES"));
            listaParametros.add(new RequestParametros("SELFCHK:CEDULA:OTP:FIJO"));
            listaParametros.add(new RequestParametros("SELFCHK:CODIGO:OTP:FIJO"));
        }
        LogFile.adjuntarLog("Request Parametros", listaParametros);

        //API de parametros
        Call<ResponseParametros> parametrosCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doParametros(listaParametros);

        parametrosCall.enqueue(new Callback<ResponseParametros>() {
            @Override
            public void onResponse(@NotNull Call<ResponseParametros> call, @NotNull Response<ResponseParametros> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        List<Parametro> parametros = response.body().getListado();
                        LogFile.adjuntarLog("Parametros Response", response.body().getListado());
                        if(!parametros.isEmpty()){
                            if(listaParametros.size() == parametros.size()){
                                for(Parametro parametro : parametros){
                                    /**
                                     * Cedula que esta sujeta al código OTP fijo para pruebas de automatización
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:CEDULA:OTP:FIJO")){
                                        SPM.setString(Constantes.CEDULA_OTP_FIJO, parametro.getValor());
                                    }

                                    /**
                                     * Código OTP fijo para pruebas de automatización
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:CODIGO:OTP:FIJO")){
                                        SPM.setString(Constantes.CODIGO_OTP_FIJO, parametro.getValor());
                                    }

                                    /**
                                     * Timeout de espera de datafono para consultar la respuesta
                                     * mayor o igual a 3 segundos
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:DF:TIMEOUT")){
                                        SPM.setString(Constantes.TIMEOUT_DATAFONO_RB, parametro.getValor());
                                        SPM.setString(Constantes.TIMEOUT_DATAFONO, parametro.getValor());
                                    }

                                    /**
                                     * Timeout de espera de datafono mayor o igual a 3 minutos
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:DF:TIMEOUT:ES")){
                                        SPM.setString(Constantes.TIMEOUT_ESPERA_DATAFONO_RB, parametro.getValor());
                                        SPM.setString(Constantes.TIMEOUT_ESPERA_DATAFONO, parametro.getValor());
                                    }

                                    /**
                                     * Proveedor tecnologico de facturación electrónica
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:FE:PROVEEDORTEC")){
                                        SPM.setString(Constantes.PARAM_PROVEEDOR_TECNOLOGICO_FE, parametro.getValor());
                                    }

                                    /**
                                     * Url base de la facturación electrónica
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:FE:URLBASE:QR")){
                                        SPM.setString(Constantes.PARAM_URL_BASE_FE_QR, parametro.getValor());
                                    }

                                    /**
                                     * Ambiente de la facturación electrónica 1-productivo  2-pruebas
                                     */
                                    if(parametro.getCodigo().equals("FACT:ELECTRO:AMBIENTE")){
                                        String ambiente = parametro.getValor();

                                        if(ambiente.equalsIgnoreCase("productivo")){
                                            SPM.setString(Constantes.PARAM_AMBIENTE_FE_QR, "1");
                                        }else{
                                            SPM.setString(Constantes.PARAM_AMBIENTE_FE_QR, "2");
                                        }
                                    }

                                    /**
                                     * Activar la generación de certificado regalo automático 1-activo  2-desactivado
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:CERTREGALO")){
                                        SPM.setBoolean(Constantes.PARAM_GENERAR_CERTIFICADO_AUTOMATICO,
                                                parametro.getValor().equals("1"));
                                    }

                                    /**
                                     * LIMITE DE BOLSAS FACTURABLES
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:BOLSAS:CANTIDAD")){
                                        SPM.setString(Constantes.PARAM_CANTIDAD_BOLSAS_FACTURABLES,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Tiempo de escucha lector y mensaje cajón
                                     *
                                     * Milisegundos en los cuales el lector escucha para ver que prendas y
                                     * volver a mostrar el msj del cajón.
                                     *
                                     * Ejemplo:
                                     * 3000;5000
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:READTIME")){
                                        SPM.setString(Constantes.PARAM_TIEMPO_ESCUCHA_LECTOR_AND_MSJ_CAJON,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Opciones de configuración para el autopago
                                     *
                                     * Palabras clave para las opciones de configuración especiales del autopago
                                     *
                                     * Ejemplo:
                                     * Consulta última Transacción TEF|CF01|TRUE;
                                     * Anular Transacción TEF|CF02|TRUE;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:CONFI:OPTION")){
                                        SPM.setString(Constantes.PARAM_OPCIONES_CONFIGURACION,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Empresas temporales
                                     *
                                     * Agencias Temporales que se deben excluir de la validación
                                     * del cupo empleado igual a cero
                                     *
                                     * Ejemplo:
                                     * NICOLE - VINCULAMOS;
                                     * CRYSTAL - VINCULAMOS;
                                     * COLHILADOS - VINCULAMOS;
                                     * CRYSTAL - TIEMPOS;
                                     * NICOLE - TIEMPOS;
                                     * PRINTEX - EMPLEAMOS TEMPORALES;
                                     * CRYSTAL - EMPLEAMOS TEMPORALES;
                                     * NICOLE - UNIVERSAL
                                     */
                                    if(parametro.getCodigo().equals("WCUPO:TEMPORALES")){
                                        SPM.setString(Constantes.PARAM_EMPRESA_TEMPORALES,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Cliente generico
                                     *
                                     * Ejemplo:
                                     * 999999999999
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:CLIENTE:GENERICO")){
                                        SPM.setString(Constantes.PARAM_CLIENTE_GENERICO,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Mensaje OTP que se le envía al cliente
                                     *
                                     * CUERPO DEL MENSAJE DE TEXO ENVIADO AL CLIENTE, PARA LA VALIDACIÓN DE IDENTIDAD MEDIANTE OTP
                                     *
                                     * Ejemplo:
                                     * %1$s es tu código de verificación.
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:OTP:MEDIOPAGO")){
                                        SPM.setString(Constantes.PARAM_MENSAJE_OTP,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Códigos medios de pago TEF
                                     *
                                     * Código medio de pago TEF
                                     *
                                     * Ejemplo:
                                     * C03
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:MEDIOPAGO:TEF")){
                                        SPM.setString(Constantes.PARAM_CODIGO_MEDIOPAGO_TEF,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Códigos medios de pago credito 1 quincena
                                     *
                                     * Código medio de pago crédito empleado 1 quincena.
                                     *
                                     * Ejemplo:
                                     * C17
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:MEDIOPAGO:CREDITO1")){
                                        SPM.setString(Constantes.PARAM_CODIGO_MEDIOPAGO_CREDITO1,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Códigos medios de pago credito 10 quincena
                                     *
                                     * Código medio de pago crédito empleado 10 quincena.
                                     *
                                     * Ejemplo:
                                     * C18
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:MEDIOPAGO:CREDITO10")){
                                        SPM.setString(Constantes.PARAM_CODIGO_MEDIOPAGO_CREDITO10,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Códigos medios de pago credito
                                     *
                                     * Medios de pago creditos.
                                     *
                                     * Ejemplo:
                                     * C17;C18
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:MEDIOSPAGO:CREDITOS")){
                                        SPM.setString(Constantes.PARAM_MEDIOS_PAGOS_CREDITO,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Medios de pago clientes
                                     *
                                     * Medios de pago selfcheckout cliente.
                                     *
                                     * Ejemplo:
                                     * tarjetas|TARJETA;
                                     * QR Bancolombia|QR;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:MEDIOPAGO:CLI")){
                                        SPM.setString(Constantes.PARAM_COL_MEDIOPAGO_CLI,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Descuento referido tipo A
                                     *
                                     * Parametro palabras clave para descuento de referido tipo A Colombia.
                                     *
                                     * Ejemplo:
                                     * 20% tarjetas|REFERIDOS20EMPLEADOS;
                                     * 25% QR Bancolombia|REFERIDOS25EMPLEADOS;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:REF:A:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_A,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Descuento referido tipo B
                                     *
                                     * Parametro palabras clave para descuento de referido tipo B Colombia.
                                     *
                                     * Ejemplo:
                                     * 20% tarjetas|REFERIDOS20EMPLEADOS;
                                     * 25% QR Bancolombia|REFERIDOS25EMPLEADOS;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:REF:B:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_B,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Descuento referido tipo E
                                     *
                                     * Parametro palabras clave para descuento de referido tipo E Colombia.
                                     *
                                     * Ejemplo:
                                     * 20% tarjetas|REFERIDOS20EMPLEADOS;
                                     * 25% QR Bancolombia|REFERIDOS25EMPLEADOS;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:REF:E:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_E,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Descuento referido tipo F
                                     *
                                     * Parametro palabras clave para descuento de referido tipo F Colombia.
                                     *
                                     * Ejemplo:
                                     * 20% tarjetas|REFERIDOS20EMPLEADOS;
                                     * 25% QR Bancolombia|REFERIDOS25EMPLEADOS;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:REF:F:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_F,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Descuento referido tipo J
                                     *
                                     * Parametro palabras clave para descuento de referido tipo J Colombia.
                                     *
                                     * Ejemplo:
                                     * 20% tarjetas|REFERIDOS20EMPLEADOS;
                                     * 25% QR Bancolombia|REFERIDOS25EMPLEADOS;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:REF:J:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_J,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Descuento referido tipo L
                                     *
                                     * Parametro palabras clave para descuento de referido tipo L Colombia.
                                     *
                                     * Ejemplo:
                                     * 20% tarjetas|REFERIDOS20EMPLEADOS;
                                     * 25% QR Bancolombia|REFERIDOS25EMPLEADOS;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:REF:L:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_L,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Descuento referido tipo S
                                     *
                                     * Parametro palabras clave para descuento de referido tipo S Colombia.
                                     *
                                     * Ejemplo:
                                     * 20% tarjetas|REFERIDOS20EMPLEADOS;
                                     * 25% QR Bancolombia|REFERIDOS25EMPLEADOS;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:REF:S:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_S,
                                                parametro.getValor());
                                    }

                                    /**
                                     * Tipos de cliente para referido
                                     *
                                     * Tipos de cliente para el descuento referido ejemplo:
                                     * (\"A\",\"E\",\"F\",\"J\",\"L\",\"O\",\"S\",\"B\")
                                     */
                                    if(parametro.getCodigo().equals("REF:ACT:CLI")){
                                        SPM.setString(Constantes.PARAM_TIPOS_CLIENTE_DESCUENTO_REF,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Ean bolsa GEF
                                     *
                                     * Registro de Bolsa para la venta
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:BOLSA:GEF")){
                                        SPM.setString(Constantes.PARAM_EAN_BOLSA_GEF,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Ean bolsa PUNTO BLANCO
                                     *
                                     * Registro de Bolsa para la venta
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:BOLSA:PB")){
                                        SPM.setString(Constantes.PARAM_EAN_BOLSA_PB,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Ean bolsa BABY FRESH
                                     *
                                     * Registro de Bolsa para la venta
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:BOLSA:BF")){
                                        SPM.setString(Constantes.PARAM_EAN_BOLSA_BF,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Ean bolsa BABY FRESH
                                     *
                                     * Registro de Bolsa para la venta
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:BOLSA:GALAX")){
                                        SPM.setString(Constantes.PARAM_EAN_BOLSA_GALAX,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Ean bolsa BABY FRESH
                                     *
                                     * Registro de Bolsa para la venta
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:BOLSA:MULTI")){
                                        SPM.setString(Constantes.PARAM_EAN_BOLSA_MULTI,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Texto contribuyentes (tirilla)
                                     *
                                     * SOMOS GRANDES CONTRIBUYENTES
                                     * RESOLUCION No. 9061 DEL 10/12/2020
                                     * AUTORRETENEDORES
                                     * RESOLUCION 104 ENERO /31 /1986
                                     * IVA REGIMEN COMUN
                                     * AGENTE RETENEDOR DE IVA
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:CONTRIBUYENTE")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_CONTRIBUYENTES,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Texto plazo (tirilla)
                                     *
                                     * Texto en tiquete impreso de plazo para cambios. Salto de línea \\n
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:PLAZOCAMBIO")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_PLAZO_CAMBIO,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Politica para el cambio (tirilla)
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:POLITICASCAMBIO")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_POLITICA_CAMBIO,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Lineas de atención tirilla
                                     *
                                     * mensaje lineas de atencion la cliente tirilla.
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:LINEAATENCION")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_LINEAS_ATENCION,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Grupo configurador
                                     *
                                     * MSS;OPE;TCD;ADM;ADT;GDA;AD2
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:GRUPOSROL:CONFIGURADOR")){
                                        SPM.setString(Constantes.PARAM_GRUPO_CONFIGURADOR,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Tipos de empresa descuento
                                     *
                                     * INSERTA TRANSACCIONES DE POS MOVIL EN TABLA ZPIECEXPORT_FE
                                     * TRUE (INSERTA)
                                     * FALSE (NO INSERTA)
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:FACELEC:XML")){
                                        SPM.setBoolean(Constantes.PARAM_TIPOS_EMPRESA_DESCUENTO,
                                                Boolean.parseBoolean(parametro.getValor()));
                                        continue;
                                    }

                                    /**
                                     * Tipos de empresa descuento
                                     *
                                     * Tipos de empresa para el descuento empleado ejemplo:
                                     * (\"003\",\"004\",\"006\")
                                     */
                                    if(parametro.getCodigo().equals("VAL:ACT:EMP")){
                                        SPM.setString(Constantes.PARAM_TIPOS_EMPRESA_DESCUENTO,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Tipos de cliente descuento
                                     *
                                     * Tipos de cliente para el descuento empleado ejemplo:
                                     * (\"A\",\"E\",\"F\",\"J\",\"L\",\"O\",\"S\",\"B\")
                                     */
                                    if(parametro.getCodigo().equals("VAL:ACT:CLI")){
                                        SPM.setString(Constantes.PARAM_TIPOS_CLIENTE_DESCUENTO,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Canal donde el cliente se registro
                                     *
                                     * Parametro que trae el canal donde el cliente se esta registrando
                                     * en caso del POS MOBILE es "17", se usa este mientras tanto.
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:CLIENTE:CANAL")){
                                        SPM.setString(Constantes.PARAM_CANAL_CLIENTE,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Código QR Bancolombia
                                     *
                                     * Parametro que trae el código del medio de pago QR Bancolombia
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:MEDIOPAGO:QRBANCOLOMBIA")){
                                        SPM.setString(Constantes.PARAM_CODIGO_MEDIOPAGO_QRBANCOLOMBIA,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Mensaje articulo descontable
                                     *
                                     * Parametro que trae el mensaje de error en articulo descontable
                                     * con descuento
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:MSGARTICULODESCONTABLE")){
                                        SPM.setString(Constantes.PARAM_MSG_ARTICULO_DESCONTABLE,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Mensaje día vaucher activador
                                     *
                                     * Parametro que activa o desactiva el Msj Día Desc.
                                     *
                                     * Ejemplo:
                                     * GRAN OFERTA DE REAPERTURA
                                     * DEL 1 AL 20 DE OCTUBRE
                                     * GRANDES DESCUENTOS
                                     * ---TE ESPERAMOS---
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:MSJDIA:ACTIVO")){
                                        SPM.setBoolean(Constantes.PARAM_TIQUETE_MSJ_DIA_ACTIVO,
                                                parametro.getValor().equals("true"));
                                        continue;
                                    }

                                    /**
                                     * Mensaje día vaucher
                                     *
                                     * Parametro que tiene un msj del día para descuento, solo se muestra
                                     * si el msj dia esta activo y esto va en el vaucher.
                                     *
                                     * Ejemplo:
                                     * GRAN OFERTA DE REAPERTURA
                                     * DEL 1 AL 20 DE OCTUBRE
                                     * GRANDES DESCUENTOS
                                     * ---TE ESPERAMOS---
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:MSJDIA:DESC")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_MSJ_DIA_DESC,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Formato Tienda
                                     *
                                     * Parametro para saber el formato de la tienda, este permite
                                     * cambiar el logo de la marca en cada una de las pantallas.
                                     *
                                     * 003: Tienda GEF
                                     * 004: Tienda Punto Blanco
                                     * 006: Tienda Baby Fresh
                                     * 010: Tienda Galax
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:FORMATO:TIENDA:" + codigoCaja)){
                                        SPM.setString(Constantes.PARAM_FORMATO_TIENDA, parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Tienda RFID
                                     *
                                     * Parametro para saber si la tienda es RFID, si llega
                                     * RFIDDE : TRUE, vacio : FALSE.
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:RFID:TIPORFID:" + tienda)){
                                        SPM.setBoolean(Constantes.PARAM_TIENDA_RFID,
                                                parametro.getValor().equals("RFIDDE"));
                                        continue;
                                    }

                                    /**
                                     * Texto IVA vaucher
                                     *
                                     * Parametro que trae el IVA, esto va en el vaucher.
                                     *
                                     * Ejemplo:
                                     * 19%
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:TEXTOIVA")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_TEXTO_IVA,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Ean danacion unicef
                                     *
                                     * Parametro que trae el ean para las donaciones unicef.
                                     *
                                     * Ejemplo:
                                     * 7701584461654
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:DONACIONESUNICEF")){
                                        SPM.setString(Constantes.PARAM_EAN_DONACION_UNICEF,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Caja habeasdata
                                     *
                                     * Parametro que trae un TRUE si la caja permite habeas data.
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:CAJAHABEAS:" + codigoCaja)){
                                        SPM.setBoolean(Constantes.PARAM_CAJA_HABEAS,
                                                parametro.getValor().equals("TRUE"));
                                        continue;
                                    }

                                    /**
                                     * Modelo 1 tiquete - Activo
                                     *
                                     * Parametro que Activo/Inactivo modelo 1 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:ACTIVO")){
                                        SPM.setBoolean(Constantes.PARAM_TIQUETE_M1_ACTIVO,
                                                parametro.getValor().equals("TRUE"));
                                        continue;
                                    }

                                    /**
                                     * Cliente generico - Modelo 1
                                     *
                                     * Parametro aplica para cliente generico del modelo 1 de
                                     * desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:CLGEN")){
                                        SPM.setBoolean(Constantes.PARAM_TIQUETE_M1_CLGEN,
                                                parametro.getValor().equals("TRUE"));
                                        continue;
                                    }

                                    /**
                                     * Código Formato - Modelo 1
                                     *
                                     * Parametro que trae el Código formato de tiendas que imprimen
                                     * modelo 1 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:FORMATO")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M1_FORMATO,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 1 - marca
                                     *
                                     * Parametro que tree el código marca de los articulo que suman
                                     * al monto modelo 1 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:MARCA")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M1_MARCA,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 1 - monto
                                     *
                                     * Parametro que tree el Monto/valor de los articulos minimo para
                                     * la impresión del modelo 1 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:MONTO")){
                                        SPM.setFloat(Constantes.PARAM_TIQUETE_M1_MONTO,
                                                Float.parseFloat(parametro.getValor()));
                                        continue;
                                    }

                                    /**
                                     * Modelo 1 - multiplo
                                     *
                                     * Parametro que trae un boleano Por cada=TRUE A partir=FALSE
                                     * Multiplo segun el monto para la impresión del modelo 1 de
                                     * desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:MULTIPLO")){
                                        SPM.setBoolean(Constantes.PARAM_TIQUETE_M1_MULTIPLO,
                                                parametro.getValor().equals("TRUE"));
                                        continue;
                                    }

                                    /**
                                     * Modelo 1 - palabra clave
                                     *
                                     * Parametro que tree la palabra clave que va en el codigo de
                                     * barras modelo 1 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:PALABRACLAVE")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M1_PALABRA_CLAVE,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 1 - texto 1
                                     *
                                     * Parametro que tree el texto encabezado modelo 1 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:TEXT1")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M1_TEXTO1,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 1 - texto 2
                                     *
                                     * Texto encabezado modelo 1 de desprendible no usar salto de linea
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M1:TEXT2")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M1_TEXTO2,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 2 tiquete - Activo
                                     *
                                     * Parametro que Activo/Inactivo modelo 2 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:ACTIVO")){
                                        SPM.setBoolean(Constantes.PARAM_TIQUETE_M2_ACTIVO,
                                                parametro.getValor().equals("TRUE"));
                                        continue;
                                    }

                                    /**
                                     * Cliente generico - Modelo 2
                                     *
                                     * Parametro aplica para cliente generico del modelo 2 de
                                     * desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:CLGEN")){
                                        SPM.setBoolean(Constantes.PARAM_TIQUETE_M2_CLGEN,
                                                parametro.getValor().equals("TRUE"));
                                        continue;
                                    }

                                    /**
                                     * Código Formato - Modelo 2
                                     *
                                     * Parametro que trae el Código formato de tiendas que imprimen
                                     * modelo 2 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:FORMATO")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M2_FORMATO,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 2 - marca
                                     *
                                     * Parametro que tree el código marca de los articulo que suman
                                     * al monto modelo 2 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:MARCA")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M2_MARCA,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 2 - monto
                                     *
                                     * Parametro que tree el Monto/valor de los articulos minimo para
                                     * la impresión del modelo 2 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:MONTO")){
                                        SPM.setFloat(Constantes.PARAM_TIQUETE_M2_MONTO,
                                                Float.parseFloat(parametro.getValor()));
                                        continue;
                                    }

                                    /**
                                     * Modelo 2 - palabra clave
                                     *
                                     * Parametro que tree la palabra clave que va en el codigo de
                                     * barras modelo 2 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:PALABRACLAVE")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M2_PALABRA_CLAVE,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 2 - texto 1
                                     *
                                     * Parametro que tree el texto encabezado modelo 2 de desprendible
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:TEXT1")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M2_TEXTO1,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Modelo 2 - texto 2
                                     *
                                     * Texto encabezado modelo 2 de desprendible no usar salto de linea
                                     */
                                    if(parametro.getCodigo().equals("ZPOSM:TIQUETE:M2:TEXT2")){
                                        SPM.setString(Constantes.PARAM_TIQUETE_M2_TEXTO2,
                                                parametro.getValor());
                                        continue;
                                    }

                                    /**
                                     * Descuento empleado
                                     *
                                     * Parametro palabras clave para descuento de empleados Colombia.
                                     *
                                     * Ejemplo:
                                     * 15% Crédito 10 quincenas|EMPLEADOS15;
                                     * 20% tarjetas|EMPLEADOS20;
                                     * 25% QR Bancolombia|EMPLEADOS25;
                                     * 25% Crédito 1 quincena|EMPLEADOS25;
                                     */
                                    if(parametro.getCodigo().equals("SELFCHK:COL:DESC:EMPLEADO")){
                                        SPM.setString(Constantes.PARAM_COL_DESCUENTO_EMPLEADO,
                                                parametro.getValor());
                                    }
                                }


                                if(!seguridad){
                                    Date fechaAperturaDate = Utilidades.parceFecha(aperturaCaja.getFechaApertura(),
                                            Constantes.FORMATO_CORTO);

                                    // Convertir Date a LocalDate
                                    LocalDate fechaApertura = fechaAperturaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    LocalDate fechaActual = LocalDate.now();
                                    String fechaAperturaString = fechaApertura.getDayOfMonth()+"/"+fechaApertura.getMonthValue()+"/"+fechaApertura.getYear();
                                    String fechaHoyString = fechaActual.getDayOfMonth()+"/"+fechaActual.getMonthValue()+"/"+fechaActual.getYear();

                                    SPM.setString(Constantes.MSG_ALERTA_APERTURA, !fechaApertura.toString().equals(fechaActual.toString()) ?
                                            String.format(contexto.getResources().getString(R.string.msj_alerta_apertura),
                                                    fechaAperturaString,
                                                    codigoCaja,
                                                    fechaHoyString) : null);

                                    SPM.setBoolean(Constantes.MSGS_PRIMERA_VEZ, true);
                                    presentador.vistaConsultaCliente();
                                }else{
                                    validarInicioSesion(grupo);
                                }
                            }else{
                                StringBuilder nombreParametros = new StringBuilder();

                                for(RequestParametros param: listaParametros){
                                    boolean encontrado = false;
                                    for(Parametro parametro : parametros){
                                        if (param.getCodigo().equals(parametro.getCodigo())) {
                                            encontrado = true;
                                            break;
                                        }
                                    }
                                    if(!encontrado){
                                        nombreParametros.append(param.getCodigo()).append(",");
                                    }
                                }

                                LogFile.adjuntarLog("Parametros Response Error", String.format(contexto.getResources().getString(R.string.error_lectura_parametros),
                                        nombreParametros.toString()));
                                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                        String.format(contexto.getResources().getString(R.string.error_lectura_parametros),
                                                nombreParametros.toString()),
                                        Constantes.SERVICIO_PARAMETROS);
                            }
                        }else {
                            if(response.body().getMensaje() == null){
                                LogFile.adjuntarLog("Parametros Response Error",
                                        contexto.getResources().getString(R.string.parametros_no_encontrados));
                                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                        contexto.getResources().getString(R.string.parametros_no_encontrados),
                                        Constantes.SERVICIO_PARAMETROS);
                            }else{
                                LogFile.adjuntarLog("Parametros Response Error", response.body().getMensaje());
                                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                        response.body().getMensaje(), Constantes.SERVICIO_PARAMETROS);
                            }
                        }
                    }else{
                        LogFile.adjuntarLog("Parametros Response Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_PARAMETROS);
                    }
                }else{
                    LogFile.adjuntarLog("Parametros Response Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_PARAMETROS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseParametros> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Parametros Response Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_PARAMETROS);
            }
        });
    }

    private void validarInicioSesion(String grupo){
        String usuarioConf = SPM.getString(Constantes.PARAM_GRUPO_CONFIGURADOR);

        assert usuarioConf != null;
        if(usuarioConf.contains(grupo)){
            presentador.vistaConfiguracion();
        }else{
            apiLogin(usuarioLogin, contrasenaLogin, false);
        }
    }
}
