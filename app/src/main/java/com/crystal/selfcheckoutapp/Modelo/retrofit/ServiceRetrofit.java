package com.crystal.selfcheckoutapp.Modelo.retrofit;

import com.crystal.selfcheckoutapp.Modelo.clases.PagoTEF;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestActualizarCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestActualizarTransaccion;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestBancolombiaQr;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCajaSimple;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCerrarDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCondicionesComerciales;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestConsultarCupoEmpleado;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestConsultarDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCrearCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestExpirarCodigoWsp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestExtraerInfoDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGenerarCodigoOtp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGenerarQRFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGuardarRespuestaTef;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGuardarTrazaFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestInsertarHabeasData;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestInsertarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestLogin;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestNps;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestProductos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestRFIDVentas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestReferidoWsp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestXmlDian;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBaseIntermedias;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseExtraerInfoDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseTiendas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.ResponseAperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia.ResponseBancolombiaQr;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia.ResponseConsultaPagoQr;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.ResponseActualizarCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.ResponseCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.ResponseCrearCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.codigootp.ResponseGenerarCodigoOtp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.ResponseCondicionesComerciales;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.consecutivofiscal.ResponseConsecutivoFiscal;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cupoempleado.ResponseConsultarCupoEmpleado;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseActualizarTransaccion;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseGuardarRespuestaTef;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseRespuestasDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseConsultaDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.facturaeletronica.ResponseGenerarQRFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.facturaeletronica.ResponseGuardarTrazaFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.habeasdata.ResponseConsultaHabeas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.habeasdata.ResponseInsertarHabeas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.mediospago.ResponseMediosPago;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.npss.ResponseNps;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseConsultarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.ResponseProductos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.referido.ResponseReferidoWsp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestActualizarCupoEmpleado;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCrearDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestDetalleDescuento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.ResponseCajas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseCerrarDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseCrearDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseBaseDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.login.ResponseLogin;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.tienda.ResponseTienda;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceRetrofit {
    @POST("Usuario")
    Call<ResponseLogin> doLogin(@Body RequestLogin requestLogin);

    @POST("Caja")
    Call<ResponseCajas> doCajas();
    @GET("tienda")
    Call<ResponseTiendas> doTiendas();
    @GET("tienda")
    Call<ResponseTienda> doTienda(@Query("tienda") String codigo);

    @POST("ConsecutivoFiscal/Consultar")
    Call<ResponseConsecutivoFiscal> doConsecutivoFiscal(@Body RequestCaja requestCaja);

    @POST("AperturaCaja/ValidarAperturaCaja")
    Call<ResponseAperturaCaja> doAperturaCaja(@Body RequestCaja requestCaja);

    @POST("Parametro")
    Call<ResponseParametros> doParametros(@Body List<RequestParametros> requestParametros);

    @GET("Cliente")
    Call<ResponseCliente> doConsultarCliente(@Query("Cedula") String cedula);

    @POST("Cliente/Crear")
    Call<ResponseCrearCliente> doCrearCliente(@Body RequestCrearCliente requestCrearCliente);

    @POST("Cliente/Actualizar")
    Call<ResponseActualizarCliente> doActualizarCliente(@Body RequestActualizarCliente requestActualizarCliente);

    @GET("HabeasData/Consultar")
    Call<ResponseConsultaHabeas> doConsultaHabeas(@Query("Cedula") String cedula, @Query("TipoDocumento") String tipoDocumento);

    @POST("HabeasData/Insertar")
    Call<ResponseInsertarHabeas> doInsertarHabeas(@Body RequestInsertarHabeasData requestInsertarHabeasData);

    @POST("Articulo")
    Call<ResponseProductos> doConsultarProductos(@Body RequestProductos requestProductos);

    @POST("CondicionComercial/CondicionesComerciales")
    Call<ResponseCondicionesComerciales> doConsultaCondicionesComerciales(@Body RequestCondicionesComerciales requestCondicionesComerciales);

    @POST("bancolombia/qr-codes")
    Call<ResponseBancolombiaQr> doGenerarQrBancolombia(@Header("usuario") String usuario, @Body RequestBancolombiaQr requestBancolombiaQr);

    @GET("bancolombia/pagoqr/{referencia}")
    Call<ResponseConsultaPagoQr> doConsultarPagoBancolombiaQr(@Header("usuario") String usuario, @Path("referencia") String referencia);

    @POST("MedioPago")
    Call<ResponseMediosPago> doMediosPagoCaja(@Body RequestCajaSimple requestCajaSimple);

    @POST("documento/crear")
    Call<ResponseCrearDocumento> doCrearDocumento(@Body RequestCrearDocumento requestCrearDocumento);

    @POST("documento/consulta")
    Call<ResponseConsultaDocumento> doConsultaDocumento(@Body RequestConsultarDocumento requestConsultarDocumento);

    @POST("Documento")
    Call<ResponseCerrarDocumento> doCerrarDocumento(@Body RequestCerrarDocumento requestCerrarDocumento);

    @POST("rfid/ventas")
    Call<ResponseBaseIntermedias> doRequestRfidVentas(@Header("usuario") String usuario, @Body RequestRFIDVentas rRFIDVentas);

    @POST("DetalleDescuento")
    Call<ResponseBaseDocumento> doDetalleDescuentos(@Body RequestDetalleDescuento requestDetalleDescuento);

    @POST("XmlDian")
    Call<ResponseBaseDocumento> doEnviarXmlDian(@Body RequestXmlDian requestXmlDian);

    @POST("TEF/Pago")
    Call<ResponseBaseDocumento> doEnviarPagoTef(@Body List<PagoTEF> pagos);

    @POST("Cliente/GenerarCodigo")
    Call<ResponseGenerarCodigoOtp> doEnviarCodigoOtp(@Body RequestGenerarCodigoOtp requestGenerarCodigoOtp);

    @POST("Referidos/ValidarReferidoWsp")
    Call<ResponseReferidoWsp> doVerificarCodigoReferido(@Body RequestReferidoWsp requestReferidoWsp);

    @POST("Referidos/ExpirarCodigoWsp")
    Call<ResponseBase> doExpirarCodigoReferido(@Body RequestExpirarCodigoWsp requestExpirarCodigoWsp);

    @POST("CupoEmpleado/Consultar")
    Call<ResponseConsultarCupoEmpleado> doConsultarCupoEmpleado(@Body RequestConsultarCupoEmpleado requestConsultarCupoEmpleado);

    @POST("cupoempleado/actualizar")
    Call<String> doActualizarCupoEmpleado(@Header("usuario") String usuario, @Body RequestActualizarCupoEmpleado requestActualizarCupoEmpleado);

    @POST("TEF/Compra")
    Call<ResponseCompraDatafono> doCompraDatafono(@Body RequestCompraDatafono requestCompraDatafono);

    @POST("TEF/Respuesta")
    Call<ResponseDatafono> doRespuestaDatafono(@Body RequestRespuestaDatafono requestRespuestaDatafono);

    @POST("TEF/Anular")
    Call<ResponseCompraDatafono> doAnularDatafono(@Body RequestCompraDatafono requestCompraDatafono);

    @POST("Cliente/CalificacionNps")
    Call<ResponseNps> doCalificacionNps(@Body RequestNps requestNps);

    @POST("TEF/GuardarRespuesta")
    Call<ResponseGuardarRespuestaTef> doGuardarRespuestaTef(@Body RequestGuardarRespuestaTef requestGuardarRespuestaTef);

    @GET("TEF/Respuestas")
    Call<ResponseRespuestasDatafono> doRespuestasDatafono(@Query("Fecha") String fecha,
                                                          @Query("Tienda") String tienda,
                                                          @Query("Caja") String caja);

    @POST("TEF/ActualizarRespuesta")
    Call<ResponseActualizarTransaccion> doActualizarTransacion(@Body RequestActualizarTransaccion requestActualizarTransaccion);

    @POST("FacturaElectronica/QR")
    Call<ResponseGenerarQRFE> doFacturaElectronicaQR(@Body RequestGenerarQRFE requestGenerarQRFE);

    @POST("FacturaElectronica/GuardarTraza")
    Call<ResponseGuardarTrazaFE> doFacturaElectronicaGuardarTraza(@Body RequestGuardarTrazaFE requestGuardarTrazaFE);

    @POST("Caja/GetPerifericos")
    Call<ResponseConsultarPerifericos> doConsultarPerifericos(@Header("usuario") String usuario, @Body RequestPerifericos requestPerifericos);

    @POST("Caja/Insertar/Perifericos")
    Call<ResponseGuardarPerifericos> doGuardarPerifericos(@Header("usuario") String usuario, @Body List<RequestInsertarPerifericos> requestInsertarPerifericos);

    @POST("documento/extraerinfo")
    Call<ResponseExtraerInfoDocumento> doExtraerInfoDocumento(@Header("usuario") String usuario, @Body RequestExtraerInfoDocumento requestExtraerInfoDocumento);
}