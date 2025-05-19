package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaMediosPago;
import com.crystal.selfcheckoutapp.Modelo.clases.Descuento;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.Header;
import com.crystal.selfcheckoutapp.Modelo.clases.KeyWord;
import com.crystal.selfcheckoutapp.Modelo.clases.Line;
import com.crystal.selfcheckoutapp.Modelo.clases.MediosPago;
import com.crystal.selfcheckoutapp.Modelo.clases.PagoTEF;
import com.crystal.selfcheckoutapp.Modelo.clases.Payment;
import com.crystal.selfcheckoutapp.Modelo.clases.PaymentCD;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestConsultarDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCrearDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestExpirarCodigoWsp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestExtraerInfoDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGenerarQRFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGuardarRespuestaTef;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGuardarTrazaFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseExtraerInfoDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.ResponseGuardarRespuestaTef;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.SeguridadDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseConsultaDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.facturaeletronica.ResponseGenerarQRFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.facturaeletronica.ResponseGuardarTrazaFE;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.facturaeletronica.UploadResponse;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestActualizarCupoEmpleado;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestBancolombiaQr;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCajaSimple;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCondicionesComerciales;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestConsultarCupoEmpleado;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestDetalleDescuento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestGenerarCodigoOtp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestProductos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestRFIDVentas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestReferidoWsp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestXmlDian;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBaseIntermedias;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.ResponseAperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia.ResponseBancolombiaQr;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.bancolombia.ResponseConsultaPagoQr;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.ResponseCliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.codigootp.ResponseGenerarCodigoOtp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.Condiciones;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.ResponseCondicionesComerciales;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.consecutivofiscal.ConsecutivoFiscal;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.consecutivofiscal.ResponseConsecutivoFiscal;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cupoempleado.ResponseConsultarCupoEmpleado;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseCerrarDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseCrearDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento.ResponseBaseDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.mediospago.ResponseMediosPago;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.ResponseProductos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.referido.ReferidoWsp;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.referido.ResponseReferidoWsp;

import org.jetbrains.annotations.NotNull;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaMediosPago implements IVistaMediosPago.Modelo {
    private IVistaMediosPago.Presentador presentador;
    private Context contexto;
    private Utilidades util;
    private String caja;
    private String divisa;
    private String tienda;
    private String pais;
    private Factura factura;
    private Timer tiempo;
    private TimerTask tarea;
    private int tiempoEsperaDatafono;
    private CuerpoRespuestaDatafono cuerpoRespuestaDatafono;
    private long tiempoEsperaCierreDetafono;
    private CountDownTimer countDownTimer;
    private boolean unaBusqueda;

    public ModeloVistaMediosPago(IVistaMediosPago.Presentador presentador, Context contexto) {
        this.presentador = presentador;
        this.contexto = contexto;
        util = new Utilidades(contexto);
        caja = SPM.getString(Constantes.CAJA_CODIGO);
        divisa = SPM.getString(Constantes.CAJA_DIVISA);
        tienda = SPM.getString(Constantes.CAJA_CODIGO_TIENDA);
        pais = SPM.getString(Constantes.CAJA_PAIS);
    }

    @Override
    public void apiConsultarProductos(List<String> eanes) {
        //API para consultar productos
        RequestProductos requestProductos = new RequestProductos(eanes, pais, tienda);
        LogFile.adjuntarLog("Request Consultar Productos", requestProductos);
        Call<ResponseProductos> responseProductosCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsultarProductos(requestProductos);
        responseProductosCall.enqueue(new Callback<ResponseProductos>() {
            @Override
            public void onResponse(@NotNull Call<ResponseProductos> call, @NotNull Response<ResponseProductos> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        List<Producto> listaProductos = response.body().getListaProductos();
                        if(listaProductos.isEmpty()){
                            LogFile.adjuntarLog("Consultar Productos Error", "Producto no encontrado");
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    contexto.getResources().getString(R.string.error_producto_no_encontrado),
                                    Constantes.SERVICIO_CONSULTA_PRODUCTO);
                        }else{
                            List<Producto> listaProductosAgregados = new ArrayList<>();
                            LogFile.adjuntarLog("Consultar Productos", listaProductos);
                            String articuloDescontable = SPM.getString(Constantes.PARAM_MSG_ARTICULO_DESCONTABLE);
                            StringBuilder mensaje;
                            boolean addEan;

                            for(Producto producto : listaProductos){
                                mensaje = new StringBuilder();
                                addEan = true;
                                assert articuloDescontable != null;
                                if(!articuloDescontable.isEmpty()){
                                    if(producto.getDescontable() != null){
                                        if(!producto.getDescontable()){
                                            if(!producto.getPrecio().equals(producto.getPrecioOriginal())){
                                                mensaje.append(producto.getEan()).append(" - ").append(producto.getNombre()).append(". ").append(articuloDescontable);
                                                addEan = false;
                                            }
                                        }
                                    }
                                }

                                if(addEan){
                                    listaProductosAgregados.add(producto);
                                }

                                if(mensaje.length() > 0){
                                    Utilidades.mjsToast(mensaje.toString(), Constantes.TOAST_TYPE_INFO,
                                            Toast.LENGTH_LONG, contexto);
                                }
                            }

                            if(!listaProductosAgregados.isEmpty()){
                                List<Producto> listaProductosFront = new ArrayList<>();

                                for(int i=0; i<listaProductosAgregados.size(); i++){
                                    Producto producto = listaProductosAgregados.get(i);

                                    for(int j=0; j<eanes.size(); j++){
                                        String ean = eanes.get(j);
                                        if(ean.equals(producto.getEan())){
                                            listaProductosFront.add(producto);
                                        }
                                    }
                                }

                                for(int i=0; i<listaProductosFront.size(); i++){
                                    listaProductosFront.get(i).setLine(i);
                                    listaProductosFront.get(i).setCantidad(1);
                                    listaProductosFront.get(i).setPrecioBase(listaProductosFront.get(i).getPrecio());
                                }

                                apiCondicionesComerciales(listaProductosFront);
                            }else{
                                presentador.ocultarDialogProgressBar();
                            }
                        }
                    }else{
                        LogFile.adjuntarLog("Consultar Productos Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_PRODUCTO);
                    }
                }else{
                    LogFile.adjuntarLog("Consultar Productos Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_PRODUCTO);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseProductos> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Consultar Productos Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_PRODUCTO);
            }
        });
    }

    @Override
    public void apiCondicionesComerciales(List<Producto> productos) {
        String customerId = SPM.getString(Constantes.CUSTOMER_ID);
        List<String> respuestaID = new ArrayList<>();
        List<Line> linesProductos = util.construirLinesCondicion(productos);

        //Creando las lineas de palabra claves para cosumir el servicio.
        List<KeyWord> keyWordList = util.construirKeyWordCondicion();

        Header header = new Header(divisa, customerId, util.getThisDateSimple(), keyWordList,
                tienda, false);

        //API para las condiciones comerciales
        RequestCondicionesComerciales requestCondicionesComerciales = new RequestCondicionesComerciales(header, linesProductos);
        LogFile.adjuntarLog("Request Condiciones Comerciales", requestCondicionesComerciales);
        Call<ResponseCondicionesComerciales> responseCondicionesComercialesCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsultaCondicionesComerciales(requestCondicionesComerciales);
        responseCondicionesComercialesCall.enqueue(new Callback<ResponseCondicionesComerciales>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCondicionesComerciales> call, @NotNull Response<ResponseCondicionesComerciales> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        Condiciones condiciones = response.body().getCondiciones();
                        LogFile.adjuntarLog("Condiciones Comerciales", condiciones);
                        SPM.setString(Constantes.TIQUETE_VAUCHER_TEXT, condiciones.getVaucherText());
                        SPM.setString(Constantes.TIQUETE_VAUCHER_PALABRA, condiciones.getVaucherPalabra());

                        respuestaID.addAll(condiciones.getRespuestaID());

                        if(!condiciones.getRespuestaLine().isEmpty()){
                            List<Producto> productoList = util.aplicarDescuentosLines(condiciones.getRespuestaLine(),
                                    productos);

                            presentador.respuestaCondicionesComerciales(productoList, condiciones.getRespuestaLine());
                        }else{
                            for(Producto producto:productos){
                                producto.setValorFinal("Valor Final: "+ Utilidades.formatearPrecio(producto.getPrecio()));
                            }

                            presentador.respuestaCondicionesComerciales(productos, condiciones.getRespuestaLine());
                        }
                        presentador.ocultarDialogProgressBar();
                    }else {
                        LogFile.adjuntarLog("Condiciones Comerciales Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_CONDICIONES);
                    }
                }else {
                    LogFile.adjuntarLog("Condiciones Comerciales Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_CONDICIONES);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCondicionesComerciales> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Condiciones Comerciales Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_CONDICIONES);
            }
        });
    }

    @Override
    public void apiGenerarQrBancolombia(Cliente cliente) {
        //API de generación del QR bancolombia
        double totalVenta = Double.parseDouble(Objects.requireNonNull(SPM.getString(Constantes.TOTAL_A_PAGAR)));
        StringBuilder referencia = new StringBuilder();
        String tipoDocumentoLetra = cliente.getTipoDocumentoLetra();
        String numeroDocumento = cliente.getCedula(false);
        String nombreCompleto = cliente.getNombreCompleto();

        referencia.append(caja).append(util.getThisDateSimplePegado());
        SPM.setString(Constantes.REFERENCIA_QRBANCOLOMBIA, referencia.toString());

        RequestBancolombiaQr requestBancolombiaQr = new RequestBancolombiaQr(referencia.toString(),Double.toString(totalVenta),tipoDocumentoLetra,numeroDocumento,nombreCompleto,
                Constantes.PROPOSITO_BANCOLOMBIA_QR_COMPRA,SPM.getString(Constantes.TIENDA_MERCHANTID), SPM.getString(Constantes.TIENDA_NOMBRE));
        LogFile.adjuntarLog("Request Generar QR Bancolombia", requestBancolombiaQr);
        Call<ResponseBancolombiaQr> bancolombiaQrCall = Utilidades.servicioRetrofit(Constantes.API_INTERMEDIA)
                .doGenerarQrBancolombia(caja, requestBancolombiaQr);
        bancolombiaQrCall.enqueue(new Callback<ResponseBancolombiaQr>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBancolombiaQr> call, @NotNull Response<ResponseBancolombiaQr> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getError()){
                        if(response.body().getMensaje() != null){
                            LogFile.adjuntarLog("Generar QR Bancolombia Error", response.body().getMensaje());
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    response.body().getMensaje(), Constantes.SERVICIO_GENERAR_QRBANCOLOMBIA);
                        }else{
                            LogFile.adjuntarLog("Generar QR Bancolombia Error", "Error con el QR, revisar el request de la petición");
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    "Error con el QR, comuniquese con el administrador.", Constantes.SERVICIO_GENERAR_QRBANCOLOMBIA);
                        }
                    }else{
                        LogFile.adjuntarLog("Generar QR Bancolombia Response", "Respuesta por Bancolombia exitosa");
                        presentador.ocultarDialogProgressBar();
                        presentador.mostrarQrGenerado(response.body().getQrBancolombia());
                    }
                }else{
                    LogFile.adjuntarLog("Generar QR Bancolombia Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_GENERAR_QRBANCOLOMBIA);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBancolombiaQr> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Generar QR Bancolombia Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_GENERAR_QRBANCOLOMBIA);
            }
        });
    }

    @Override
    public void apiConsultarPagoQrBancolombia() {
        String referencia = SPM.getString(Constantes.REFERENCIA_QRBANCOLOMBIA);
        //API para consultar si el cliente ya pago el Qr generado
        Call<ResponseConsultaPagoQr> consultaPagoQrCall = Utilidades.servicioRetrofit(Constantes.API_INTERMEDIA)
                .doConsultarPagoBancolombiaQr(caja, referencia);
        LogFile.adjuntarLog("Request Consultar Pago QR Bancolombia", "Referencia: "+ referencia);
        consultaPagoQrCall.enqueue(new Callback<ResponseConsultaPagoQr>() {
            @Override
            public void onResponse(@NotNull Call<ResponseConsultaPagoQr> call, @NotNull Response<ResponseConsultaPagoQr> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getError()){
                        LogFile.adjuntarLog("Consultar Pago QR Bancolombia Error", "No se ha encontrado el pago");
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                contexto.getResources().getString(R.string.pago_no_encontrado_qr_bancolombia),
                                Constantes.SERVICIO_CONSULTAR_PAGO_QRBANCOLOMBIA);
                    }else{
                        LogFile.adjuntarLog("Consultar Pago QR Bancolombia", "Pago correcto");
                        apiMediosPagoCaja();
                    }
                }else{
                    LogFile.adjuntarLog("Consultar Pago QR Bancolombia Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTAR_PAGO_QRBANCOLOMBIA);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseConsultaPagoQr> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Consultar Pago QR Bancolombia Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTAR_PAGO_QRBANCOLOMBIA);
            }
        });
    }

    @Override
    public void apiGenerarCodigoOTP(Cliente cliente, Descuento descuento, boolean reenvio) {
        //API para generar el código OTP de verificación de identidad credito 1 y 10 quincenas.
        RequestGenerarCodigoOtp requestGenerarCodigoOtp = new RequestGenerarCodigoOtp(cliente.getCedula(false),
                cliente.getCelular(), tienda, caja, cliente.getCorreo(), true, true,
                SPM.getString(Constantes.PARAM_MENSAJE_OTP), "Código de verificación",
                contexto.getResources().getString(R.string.app_name));
        LogFile.adjuntarLog("Request Generar Código OTP", requestGenerarCodigoOtp);
        Call<ResponseGenerarCodigoOtp> generarCodigoOtpCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doEnviarCodigoOtp(requestGenerarCodigoOtp);
        generarCodigoOtpCall.enqueue(new Callback<ResponseGenerarCodigoOtp>() {
            @Override
            public void onResponse(@NotNull Call<ResponseGenerarCodigoOtp> call, @NotNull Response<ResponseGenerarCodigoOtp> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getEsValida()) {
                        presentador.ocultarDialogProgressBar();
                        LogFile.adjuntarLog("Generar Código OTP [Reenvio: "+reenvio+"]", response.body().getDato());
                        if(reenvio){
                            Utilidades.mjsToast("Código reenviado",
                                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG,
                                    contexto);
                            presentador.respuestaReenvioCodigo(response.body().getDato().getCodigoGenerado());
                        }else{
                            cliente.setCodigoOTP(response.body().getDato().getCodigoGenerado());
                            if(Utilidades.verificarClienteOTPFijo(cliente.getCedula(false))){
                                cliente.setCodigoOTP(SPM.getString(Constantes.CODIGO_OTP_FIJO));
                            }
                            presentador.verificacionOTP(cliente, descuento);
                        }
                    }else{
                        LogFile.adjuntarLog("Generar Código OTP Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_GENERAR_CODIGO_OTP);
                    }
                }else{
                    LogFile.adjuntarLog("Generar Código OTP Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_GENERAR_CODIGO_OTP);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseGenerarCodigoOtp> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Generar Código OTP Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_GENERAR_CODIGO_OTP);
            }
        });
    }

    @Override
    public void apiMediosPagoCaja() {
        RequestCajaSimple requestCaja = new RequestCajaSimple(caja);
        LogFile.adjuntarLog("Request Medios de pago caja", requestCaja);
        Call<ResponseMediosPago> mediosPagoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doMediosPagoCaja(requestCaja);
        mediosPagoCall.enqueue(new Callback<ResponseMediosPago>() {
            @Override
            public void onResponse(@NotNull Call<ResponseMediosPago> call, @NotNull Response<ResponseMediosPago> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getEsValida()) {
                        String codigo = SPM.getString(Constantes.MEDIO_PAGO_CODIGO);
                        List<MediosPago> mediosPagos = response.body().getMediosPagoList();
                        Payment payment = new Payment();
                        double totalVenta = Double.parseDouble(Objects.requireNonNull(SPM.getString(Constantes.TOTAL_A_PAGAR)));

                        for(MediosPago mediosPago: mediosPagos){
                            assert codigo != null;
                            if(codigo.equals(mediosPago.getCodigo())){
                                payment.setName(mediosPago.getNombre());
                                payment.setAmount(totalVenta);
                                payment.setCurrencyId(mediosPago.getDivisa());
                                payment.setDueDate("");
                                payment.setId(1);
                                payment.setIsReceivedPayment(1);
                                payment.setMethodId(mediosPago.getCodigo());
                            }
                        }

                        LogFile.adjuntarLog("Medios de pago caja", payment);

                        presentador.respuestaConsultarPago(payment);
                    }else{
                        LogFile.adjuntarLog("Medios de pago caja Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTAR_MEDIOS_PAGO_CAJA);
                    }
                }else{
                    LogFile.adjuntarLog("Medios de pago caja Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTAR_MEDIOS_PAGO_CAJA);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseMediosPago> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Medios de pago caja Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTAR_MEDIOS_PAGO_CAJA);
            }
        });
    }

    @Override
    public void apiConsecutivoFiscal(Factura factura) {
        //API para consultar el consecutivo fiscal
        RequestCaja requestCaja = new RequestCaja(caja);
        LogFile.adjuntarLog("Request Consecutivo Fiscal", requestCaja);

        Call<ResponseConsecutivoFiscal> consecutivoFiscalCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsecutivoFiscal(requestCaja);
        consecutivoFiscalCall.enqueue(new Callback<ResponseConsecutivoFiscal>() {
            @Override
            public void onResponse(@NotNull Call<ResponseConsecutivoFiscal> call, @NotNull Response<ResponseConsecutivoFiscal> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getEsValida()) {
                        ConsecutivoFiscal consecutivoFiscal = response.body().getConsecutivoFiscal();
                        LogFile.adjuntarLog("Consecutivo Fiscal", consecutivoFiscal);

                        factura.setPrefijo(consecutivoFiscal.getPrefijo());
                        factura.setConsecutivo(consecutivoFiscal.getConsecutivo().toString());
                        factura.setClaveFacturaElectronica(consecutivoFiscal.getClaveFacturaElectronica());
                        factura.setTipoResolucion(consecutivoFiscal.getTipoResolucion());

                        factura.construirReferenciaInterna();

                        apiValidarAperturaCaja(factura);
                    }else{
                        LogFile.adjuntarLog("Consecutivo Fiscal Error",  response.body().getMensaje() != null ? response.body().getMensaje() : "");
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje() != null ? response.body().getMensaje() : "",
                                Constantes.SERVICIO_CONSECUTIVO_FISCAL);
                    }
                }else{
                    LogFile.adjuntarLog("Consecutivo Fiscal Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSECUTIVO_FISCAL);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseConsecutivoFiscal> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Consecutivo Fiscal Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSECUTIVO_FISCAL);
            }
        });
    }

    @Override
    public void apiValidarAperturaCaja(Factura factura) {
        //API para consultar si la caja esta abierta o no
        RequestCaja requestCaja = new RequestCaja(caja);
        LogFile.adjuntarLog("Request Validar Apertura Caja", requestCaja);
        Call<ResponseAperturaCaja> aperturaCajaCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doAperturaCaja(requestCaja);
        aperturaCajaCall.enqueue(new Callback<ResponseAperturaCaja>() {
            @Override
            public void onResponse(@NotNull Call<ResponseAperturaCaja> call, @NotNull Response<ResponseAperturaCaja> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        AperturaCaja aperturaCaja = response.body().getAperturaCaja();

                        if(aperturaCaja.getEstadoCaja().equals("ABIERTA")){
                            LogFile.adjuntarLog("Validar Apertura Caja", aperturaCaja);
                            factura.setAperturaCaja(aperturaCaja);
                            presentador.respuestaValidarAperturaCaja();
                        }else{
                            LogFile.adjuntarLog("Validar Apertura Caja Error", String.format(contexto.getResources().getString(R.string.caja_cerrada), caja));
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    String.format(contexto.getResources().getString(R.string.caja_cerrada), caja),
                                    Constantes.SERVICIO_APERTURA_CAJA);
                        }
                    }else{
                        LogFile.adjuntarLog("Validar Apertura Caja Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_APERTURA_CAJA);
                    }
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

    @Override
    public void apiCrearDocumento(Factura factura) {
        //API para crear el documento de venta en CEGID
        RequestCrearDocumento crearDocumento = factura.crearDocumento();
        double pagar = Double.parseDouble(SPM.getString(Constantes.TOTAL_A_PAGAR));
        List<PaymentCD> listaPayment = new ArrayList<>();
        for(PaymentCD payment: crearDocumento.getPayments()){
            if(payment.getAmount() != pagar){
                PaymentCD paymentCD = new PaymentCD(pagar, payment.getCurrencyId(),
                        util.getThisDateSimple(), payment.getId(), true, payment.getNombre(),
                        payment.getMethodId());

                listaPayment.add(paymentCD);
            }
        }

        if(!listaPayment.isEmpty()){
            crearDocumento.getPayments().clear();
            crearDocumento.getPayments().addAll(listaPayment);
        }

        LogFile.adjuntarLog("Request Crear Documento", crearDocumento);

        Call<ResponseCrearDocumento> crearDocumentoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doCrearDocumento(crearDocumento);
        crearDocumentoCall.enqueue(new Callback<ResponseCrearDocumento>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCrearDocumento> call, @NotNull Response<ResponseCrearDocumento> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Crear Documento Response", response.body().getDocumento());
                        presentador.respuestaCrearDocumento(response.body().getDocumento().getNumeroTransaccion());
                    }else{
                        LogFile.adjuntarLog("Crear Documento Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CREAR_DOCUMENTO);
                    }
                }else{
                    LogFile.adjuntarLog("Crear Documento Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CREAR_DOCUMENTO);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCrearDocumento> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Crear Documento Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CREAR_DOCUMENTO);
            }
        });
    }

    @Override
    public void apiConsultarDocumento(Factura factura) {
        //API para consultar el documento en CEGID y revisar si ya existe
        RequestConsultarDocumento requestConsultarDocumento = new RequestConsultarDocumento(factura.getReferenciaInterna());
        LogFile.adjuntarLog("Request Consultar Documento", requestConsultarDocumento);
        Call<ResponseConsultaDocumento> documentoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsultaDocumento(requestConsultarDocumento);
        documentoCall.enqueue(new Callback<ResponseConsultaDocumento>() {
            @Override
            public void onResponse(@NonNull Call<ResponseConsultaDocumento> call, @NonNull Response<ResponseConsultaDocumento> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Consultar Documento Response", response.body().getDocumento());
                        if(response.body().getDocumento() != null){
                            presentador.respuestaCrearDocumento(Integer.toString(response.body().getDocumento().getNumeroTransaccion()));
                        }else{
                            apiCrearDocumento(factura);
                        }
                    }else{
                        LogFile.adjuntarLog("Consultar Documento Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTAR_DOCUMENTO);
                    }
                }else{
                    LogFile.adjuntarLog("Consultar Documento Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTAR_DOCUMENTO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseConsultaDocumento> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Consultar Documento Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTAR_DOCUMENTO);
            }
        });
    }

    @Override
    public void apiCerrarDocumento(Factura factura) {
        //API para finalizar la compra.
        LogFile.adjuntarLog("Request Cerrar Documento", factura.cerrarDocumento());
        Call<ResponseCerrarDocumento> cerrarDocumentoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doCerrarDocumento(factura.cerrarDocumento());
        cerrarDocumentoCall.enqueue(new Callback<ResponseCerrarDocumento>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCerrarDocumento> call, @NotNull Response<ResponseCerrarDocumento> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Cerrar Documento Response", response.body().getDocumento());
                        presentador.respuestaCerrarDocumento(response.body().getDocumento().getReferenciaFiscalFirma());
                    }else{
                        LogFile.adjuntarLog("Cerrar Documento Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CERRAR_DOCUMENTO);
                    }
                }else{
                    LogFile.adjuntarLog("Cerrar Documento Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CERRAR_DOCUMENTO);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCerrarDocumento> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Cerrar Documento Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CERRAR_DOCUMENTO);
            }
        });
    }

    @Override
    public void apiCrearRfidVenta(Factura factura) {
        //API para insertar si hay venta RFID.
        RequestRFIDVentas requestRFIDVentas = new RequestRFIDVentas(factura.rfidVenta());
        LogFile.adjuntarLog("Request Crear RFID Venta", requestRFIDVentas);
        Call<ResponseBaseIntermedias> intermediasCall = Utilidades.servicioRetrofit(Constantes.API_INTERMEDIA)
                .doRequestRfidVentas("SelfCheckout: " + contexto.getResources().getString(R.string.version_apk), requestRFIDVentas);
        intermediasCall.enqueue(new Callback<ResponseBaseIntermedias>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBaseIntermedias> call, @NotNull Response<ResponseBaseIntermedias> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()) {
                        LogFile.adjuntarLog("Crear RFID Venta Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CREAR_RFID_VENTA);
                    }else{
                        LogFile.adjuntarLog("Crear RFID Venta Response", response.body());
                        presentador.respuestaRfidVenta();
                    }
                } else {
                    LogFile.adjuntarLog("Crear RFID Venta Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CREAR_RFID_VENTA);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBaseIntermedias> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Crear RFID Venta Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CREAR_RFID_VENTA);
            }
        });
    }

    @Override
    public void apiEnviarDescuentos(Factura factura) {
        //API para enviar los descuentos que tiene la venta
        RequestDetalleDescuento requestDetalleDescuento = new RequestDetalleDescuento(factura.getTienda(),
                Integer.parseInt(factura.getNumeroTransaccion()), factura.getCliente().getCustomerId(),
                factura.getDetalleDescuentos(), factura.getUsuarioUtil());
        LogFile.adjuntarLog("Request Enviar Descuentos", requestDetalleDescuento);
        Call<ResponseBaseDocumento> detalleDescuentoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doDetalleDescuentos(requestDetalleDescuento);
        detalleDescuentoCall.enqueue(new Callback<ResponseBaseDocumento>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBaseDocumento> call, @NotNull Response<ResponseBaseDocumento> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Enviar Descuentos Response", response.body());
                        presentador.respuestaDetalleDescuentos();
                    }else {
                        LogFile.adjuntarLog("Enviar Descuentos Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_DETALLE_DESCUENTOS);
                    }
                }else{
                    LogFile.adjuntarLog("Enviar Descuentos Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_DETALLE_DESCUENTOS);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBaseDocumento> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Enviar Descuentos Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_DETALLE_DESCUENTOS);
            }
        });
    }

    @Override
    public void apiEnviarDocumentoDian(Factura factura) {
        //API para guardar el documento en un tabla Z, se utiliza para la DIAN
        RequestXmlDian requestXmlDian = new RequestXmlDian(Integer.parseInt(factura.getNumeroTransaccion()),
                factura.getTienda(), Constantes.CLASE_DOCUMENTO, Constantes.ESTADO_DOCUMENTO);
        LogFile.adjuntarLog("Request Enviar Documento Dian", requestXmlDian);
        Call<ResponseBaseDocumento> documentoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doEnviarXmlDian(requestXmlDian);
        documentoCall.enqueue(new Callback<ResponseBaseDocumento>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBaseDocumento> call, @NotNull Response<ResponseBaseDocumento> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Enviar Documento Dian Response", response.body());
                        presentador.respuestaDocumentoDian();
                    }else {
                        LogFile.adjuntarLog("Enviar Documento Dian Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_DOCUMENTO_DIAN);
                    }
                }else{
                    LogFile.adjuntarLog("Enviar Documento Dian Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_DOCUMENTO_DIAN);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBaseDocumento> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Enviar Documento Dian Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_DOCUMENTO_DIAN);
            }
        });
    }

    @Override
    public void apiEnviarDocumentoTef(Factura factura) {
        List<PagoTEF> pagoTEFList = new ArrayList<>();
        RespuestaDatafono datafono = factura.getRespuestaDatafono();
        String respuestaTEF = "Autorizador=Credibanco|Franquicia=" + datafono.getFranquicia() + "|TipoCuenta="
                + datafono.getTipoCuenta() + "|Iva=" + datafono.getIvaCompra() + "|Cuotas=" + datafono.getNumeroCuotas()
                + "|CodTerminal=" + Utilidades.codigoDatafono() + "|RRN=" + "" + "|BIN=" + datafono.getBinTarjeta()
                + "|CodRta=" + datafono.getCodigoRespuesta() + "|CodigoUnico=" + datafono.getNumeroConfirmacion()
                + "|VlrBaseDevolu= |DirEstablecimiento=" + datafono.getDireccionEstablecimiento()
                + "|FechaVenTarjeta=" + datafono.getFechaVencimiento() + "|";

        PagoTEF pagoTEF = new PagoTEF(factura.getCaja(), SPM.getString(Constantes.PARAM_CODIGO_MEDIOPAGO_TEF), datafono.getUltimosDigitosTarjeta(),
                datafono.getFechaVencimiento(), datafono.getNumeroConfirmacion(), Integer.parseInt(datafono.getNumeroCuotas()),
                1, Integer.parseInt(factura.getNumeroTransaccion()), datafono.getNumeroRecibo(), respuestaTEF,
                "");

        pagoTEFList.add(pagoTEF);
        LogFile.adjuntarLog("Request Enviar Documento Tef", pagoTEFList);

        //API para enviar a una tabla si el cliente pago con tef, si no.. la API no se consume.
        Call<ResponseBaseDocumento> documentoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doEnviarPagoTef(pagoTEFList);
        documentoCall.enqueue(new Callback<ResponseBaseDocumento>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBaseDocumento> call, @NotNull Response<ResponseBaseDocumento> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Enviar Documento Tef Response", response.body());
                        presentador.respuestaDocumentoTef();
                    }else {
                        LogFile.adjuntarLog("Enviar Documento Tef Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_DOCUMENTO_TEF);
                    }
                }else{
                    LogFile.adjuntarLog("Enviar Documento Tef Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_DOCUMENTO_TEF);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBaseDocumento> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Enviar Documento Tef Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_DOCUMENTO_TEF);
            }
        });
    }

    @Override
    public void apiValidacionCodigoReferido(String codigo) {
        //API para validar el código de referido
        RequestReferidoWsp requestReferidoWsp = new RequestReferidoWsp(codigo);
        LogFile.adjuntarLog("Request Validar Codigo Referido", requestReferidoWsp);
        Call<ResponseReferidoWsp> referidoWspCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doVerificarCodigoReferido(requestReferidoWsp);
        referidoWspCall.enqueue(new Callback<ResponseReferidoWsp>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReferidoWsp> call, @NonNull Response<ResponseReferidoWsp> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Validar Codigo Referido Response", response.body().getDato());
                        ReferidoWsp referidoWsp = response.body().getDato();
                        SPM.setString(Constantes.CODIGO_REFERIDO_WSP, referidoWsp.getCodigo());
                        apiValidarEmpleado(referidoWsp.getNumeroDocumento());
                    }else{
                        LogFile.adjuntarLog("Validar Codigo Referido Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_VALIDAR_CODIGO_REFERIDO_WSP);
                    }
                }else{
                    LogFile.adjuntarLog("Validar Codigo Referido Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_VALIDAR_CODIGO_REFERIDO_WSP);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseReferidoWsp> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Validar Codigo Referido Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_VALIDAR_CODIGO_REFERIDO_WSP);
            }
        });
    }

    @Override
    public void apiExpirarCodigoReferido() {
        //Api para expirar el código usado por el cliente referido
        RequestExpirarCodigoWsp requestExpirarCodigoWsp =
                new RequestExpirarCodigoWsp(SPM.getString(Constantes.CODIGO_REFERIDO_WSP));
        LogFile.adjuntarLog("Request Expirar Código Referido", requestExpirarCodigoWsp);
        Call<ResponseBase> baseCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doExpirarCodigoReferido(requestExpirarCodigoWsp);
        baseCall.enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBase> call, @NonNull Response<ResponseBase> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Expirar Código Referido Response", response.body());
                        presentador.respuestaExpirarCodigoReferido();
                    }else{
                        LogFile.adjuntarLog("Expirar Código Referido Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_EXPIRAR_CODIGO_REFERIDO_WSP);
                    }
                }else{
                    LogFile.adjuntarLog("Expirar Código Referido Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_EXPIRAR_CODIGO_REFERIDO_WSP);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBase> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Expirar Código Referido Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_EXPIRAR_CODIGO_REFERIDO_WSP);
            }
        });
    }

    @Override
    public void apiValidarEmpleado(String cedula) {
        LogFile.adjuntarLog("Request Validar Empleado", "Cédula: " + cedula);
        Call<ResponseCliente> clienteCall = Utilidades.servicioRetrofit(Constantes.API_NN).doConsultarCliente(cedula);
        clienteCall.enqueue(new Callback<ResponseCliente>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCliente> call, @NonNull Response<ResponseCliente> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Validar Empleado Response", response.body().getCliente());
                        Cliente cliente = response.body().getCliente();
                        String gruposReferidos = SPM.getString(Constantes.PARAM_TIPOS_CLIENTE_DESCUENTO_REF);
                        assert gruposReferidos != null;
                        String descuento = "";
                        if(gruposReferidos.contains(cliente.getTipo())){
                            switch(cliente.getTipo()) {
                                case "A":
                                    descuento = SPM.getString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_A);
                                    break;
                                case "B":
                                    descuento = SPM.getString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_B);
                                    break;
                                case "E":
                                    descuento = SPM.getString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_E);
                                    break;
                                case "F":
                                    descuento = SPM.getString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_F);
                                    break;
                                case "J":
                                    descuento = SPM.getString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_J);
                                    break;
                                case "L":
                                    descuento = SPM.getString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_L);
                                    break;
                                case "S":
                                    descuento = SPM.getString(Constantes.PARAM_COL_DESCUENTO_REFERIDO_S);
                                    break;
                            }
                        }

                        assert descuento != null;
                        if(!descuento.isEmpty()){
                            presentador.mostrarDescuentoReferido(null, descuento, false);
                        }
                        presentador.ocultarDialogProgressBar();
                    }else{
                        LogFile.adjuntarLog("Validar Empleado Error", "Código invalido");
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                "Código invalido", Constantes.SERVICIO_CONSULTA_CLIENTE);
                    }
                }else{
                    LogFile.adjuntarLog("Validar Empleado Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_CLIENTE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCliente> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Validar Empleado Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_CLIENTE);
            }
        });
    }

    @Override
    public void apiConsultarCupoEmpleado(String cedula) {
        //Api para consultar el cupo del empleado
        RequestConsultarCupoEmpleado requestConsultarCupoEmpleado = new RequestConsultarCupoEmpleado(cedula);
        LogFile.adjuntarLog("Request Consultar Cupo Empleado", requestConsultarCupoEmpleado);
        Call<ResponseConsultarCupoEmpleado> cupoEmpleadoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsultarCupoEmpleado(requestConsultarCupoEmpleado);
        cupoEmpleadoCall.enqueue(new Callback<ResponseConsultarCupoEmpleado>() {
            @Override
            public void onResponse(@NonNull Call<ResponseConsultarCupoEmpleado> call, @NonNull Response<ResponseConsultarCupoEmpleado> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Consultar Cupo Empleado Response", response.body().getDato());
                        presentador.ocultarDialogProgressBar();
                        presentador.respuestaConsultaCupoEmpleado(response.body().getDato().getCupo(),
                                response.body().getDato().getEmpresa());
                    }else{
                        LogFile.adjuntarLog("Consultar Cupo Empleado Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTAR_CUPO_EMPLEADO);
                    }
                }else{
                    LogFile.adjuntarLog("Consultar Cupo Empleado Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTAR_CUPO_EMPLEADO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseConsultarCupoEmpleado> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Consultar Cupo Empleado Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTAR_CUPO_EMPLEADO);
            }
        });
    }

    @Override
    public void apiActualizarCupoEmpleado(Cliente cliente) {
        //API para actualizar el cupo del empleado por compra de 1 o 10 quincenas
        double totalVenta = Double.parseDouble(Objects.requireNonNull(SPM.getString(Constantes.TOTAL_A_PAGAR)));
        int actualizarCupo = (int) (cliente.getCupoEmpleado() - totalVenta);
        RequestActualizarCupoEmpleado requestActualizarCupoEmpleado
                = new RequestActualizarCupoEmpleado(cliente.getCedula(false), actualizarCupo,
                cliente.getEmpresa(), cliente.getNombreCompleto());
        LogFile.adjuntarLog("Request Actualizar Cupo Empleado", requestActualizarCupoEmpleado);
        Call<String> callActualizarCupo = Utilidades.servicioRetrofit(Constantes.API_INTERMEDIA)
                .doActualizarCupoEmpleado("SelfCheckout", requestActualizarCupoEmpleado);

        callActualizarCupo.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().equals("true")) {
                        LogFile.adjuntarLog("Actualizar Cupo Empleado Response", response.body());
                        presentador.respuestaActualizarCupoEmpleado();
                    }else{
                        LogFile.adjuntarLog("Actualizar Cupo Empleado Error", "Error al actualizar el cupo del empleado");
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                "Error al actualizar el cupo del empleado", Constantes.SERVICIO_CONSULTAR_CUPO_EMPLEADO);
                    }
                }else{
                    LogFile.adjuntarLog("Actualizar Cupo Empleado Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_ACTUALIZAR_CUPO_EMPLEADO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Actualizar Cupo Empleado Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_ACTUALIZAR_CUPO_EMPLEADO);
            }
        });
    }

    @Override
    public void apiCompraDatafono(Factura factura) {
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


        this.factura = factura;
        //API para probar la comunicación con el datafono
        Call<ResponseCompraDatafono> datafonoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doCompraDatafono(Utilidades.peticionCompraDatafono(factura));
        datafonoCall.enqueue(new Callback<ResponseCompraDatafono>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCompraDatafono> call, @NonNull Response<ResponseCompraDatafono> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Compra Datafono Response", "Oprima el botón verde del datáfono y sigue las instrucciones");
                        presentador.ocultarDialogProgressBar();

                        util.vozAndroid(contexto.getString(R.string.voz_pago_tef));
                        SPM.setBoolean(Constantes.BOOL_ANIMACION_DATAFONO, true);
                        presentador.dialogProgressBar(contexto.getString(R.string.progress_seguir_pasos_datafono_cliente),
                                false);

                        countDownTimer.start();
                        iniciarTareaRespuestaDatafono();
                    }else{
                        LogFile.adjuntarLog("Compra Datafono Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO);
                    }
                }else{
                    LogFile.adjuntarLog("Compra Datafono Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCompraDatafono> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Compra Datafono Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO);
            }
        });
    }

    public void validarRespuestaDatafono(){
        unaBusqueda = true;
        apiRespuestaDatafono();
    }

    @Override
    public void apiGuardarRespuestaTef(Factura factura) {
        RespuestaDatafono datafono = factura.getRespuestaDatafono();
        //Api para guardar la respuesta del datafono
        RequestGuardarRespuestaTef requestGuardarRespuestaTef = new RequestGuardarRespuestaTef(
                datafono.isErrorHoraMinutos() ? "": datafono.getHora(),
                datafono.isErrorHoraMinutos() ? "": datafono.getMinuto(),
                factura.getNumeroTransaccion(), tienda, caja, factura.getReferenciaInterna(),
                cuerpoRespuestaDatafono);
        LogFile.adjuntarLog("Request Guardar Respuestas Tef", requestGuardarRespuestaTef);
        Call<ResponseGuardarRespuestaTef> tefCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doGuardarRespuestaTef(requestGuardarRespuestaTef);
        tefCall.enqueue(new Callback<ResponseGuardarRespuestaTef>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGuardarRespuestaTef> call, @NonNull Response<ResponseGuardarRespuestaTef> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        LogFile.adjuntarLog("Guardar Respuestas Tef Response", "Procesado Correctamente");
                        presentador.respuestaGuardarResTef();
                    }else{
                        LogFile.adjuntarLog("Guardar Respuestas Tef Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                Constantes.SERVICIO_GUARDAR_RESPUESTA_TEF);
                    }
                }else{
                    LogFile.adjuntarLog("Guardar Respuestas Tef Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_GUARDAR_RESPUESTA_TEF);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGuardarRespuestaTef> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Guardar Respuestas Tef Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_GUARDAR_RESPUESTA_TEF);
            }
        });
    }

    @Override
    public void apiExtraerInfoDocumento(Factura factura) {
        RequestExtraerInfoDocumento requestExtraerInfoDocumento = new RequestExtraerInfoDocumento(
                Integer.parseInt(factura.getNumeroTransaccion()),
                factura.getTienda(),
                "FFO");
        LogFile.adjuntarLog("Request Extraer Info Documento", requestExtraerInfoDocumento);
        Call<ResponseExtraerInfoDocumento> call = Utilidades.servicioRetrofit(Constantes.API_NN).doExtraerInfoDocumento(
                factura.getUsuarioTienda()+" - "+contexto.getResources().getString(R.string.version_apk),
                requestExtraerInfoDocumento);

        call.enqueue(new Callback<ResponseExtraerInfoDocumento>() {
            @Override
            public void onResponse(@NonNull Call<ResponseExtraerInfoDocumento> call, @NonNull Response<ResponseExtraerInfoDocumento> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isEsValida()) {
                        LogFile.adjuntarLog("Extraer Info Documento Response", response.body().getRespuestaInfo());
                        try {
                            factura.setErrorExtraerInfoDocumento(false);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                            factura.setFechaCreacionFE(dateFormat.parse(response.body().getRespuestaInfo().getFechaCreacion()));
                            apiFacturaElectronicaQR(factura);
                        } catch (ParseException e) {
                            LogFile.adjuntarLog("Extraer Info Documento Error", e.fillInStackTrace());
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    "Extraer info api: "+ e.getMessage(),
                                    Constantes.SERVICIO_EXTRAER_INFO_DOCUMENTO);
                            throw new RuntimeException(e);
                        }
                    }else{
                        LogFile.adjuntarLog("Extraer Info Documento Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(),
                                Constantes.SERVICIO_EXTRAER_INFO_DOCUMENTO);
                    }
                }else{
                    LogFile.adjuntarLog("Extraer Info Documento Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_EXTRAER_INFO_DOCUMENTO);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseExtraerInfoDocumento> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Extraer Info Documento Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_EXTRAER_INFO_DOCUMENTO);
            }
        });
    }

    @Override
    public void apiFacturaElectronicaQR(Factura factura) {
        RequestGenerarQRFE requestGenerarQRFE = new RequestGenerarQRFE(Integer.parseInt(factura.getNumeroTransaccion()),
                factura.getTienda(), "FFO", "FV", factura.getReferenciaInternaFE(),
                factura.getTextoFiscal());
        LogFile.adjuntarLog("Request Factura Electronica QR", requestGenerarQRFE);
        Call<ResponseGenerarQRFE> call = Utilidades.servicioRetrofit(Constantes.API_NN).doFacturaElectronicaQR(
                requestGenerarQRFE
        );
        call.enqueue(new Callback<ResponseGenerarQRFE>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGenerarQRFE> call, @NonNull Response<ResponseGenerarQRFE> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isEsValida()) {
                        LogFile.adjuntarLog("Factura Electronica QR Response", response.body().getRespuesta());
                        UploadResponse upload = response.body().getRespuesta().getUploadResponse();

                        factura.setCufeHash(upload.getCufe());
                        factura.setCufeCalculado(false);
                        apiGuardarTrazaFE(factura);
                    }else{
                        LogFile.adjuntarLog("Factura Electronica QR Error", response.body().getMensaje());
                        factura.setErrorFacturaElectronicaQr(false);
                        if(response.body().getMensaje().equals("No es posible hacer mas reintentos.")
                                || response.body().getMensaje().contains("ERROR CARVAJAL:")){
                            factura.calcularCufe();
                            factura.setCufeCalculado(true);
                            apiGuardarTrazaFE(factura);
                        }else if(response.body().getMensaje().contains("ERROR:")){
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    response.body().getMensaje(),
                                    Constantes.SERVICIO_FACTURA_ELECTRONICA_QR);
                        }else {
                            apiFacturaElectronicaQR(factura);
                        }
                    }
                }else{
                    LogFile.adjuntarLog("Factura Electronica QR Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_FACTURA_ELECTRONICA_QR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGenerarQRFE> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Factura Electronica QR Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_FACTURA_ELECTRONICA_QR);
            }
        });
    }

    @Override
    public void apiGuardarTrazaFE(Factura factura) {
        RequestGuardarTrazaFE requestGuardarTrazaFE = new RequestGuardarTrazaFE(
                Integer.parseInt(factura.getNumeroTransaccion()),
                factura.getTienda(), "FFO", factura.getTipoResolucion(),
                factura.getReferenciaInternaFE(), Integer.parseInt(factura.getConsecutivo()),
                factura.getPrefijo(), factura.isCufeCalculado() ? factura.getCufeHash() : "");
        LogFile.adjuntarLog("Request Guardar Traza Factura Electronica", requestGuardarTrazaFE);
        Call<ResponseGuardarTrazaFE> call = Utilidades.servicioRetrofit(Constantes.API_NN).doFacturaElectronicaGuardarTraza(
                requestGuardarTrazaFE);
        call.enqueue(new Callback<ResponseGuardarTrazaFE>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGuardarTrazaFE> call, @NonNull Response<ResponseGuardarTrazaFE> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isEsValida()) {
                        LogFile.adjuntarLog("Guardar Traza Factura Electronica Response", response.body());
                        presentador.respuestaGuardarTrazaFE();
                    }else{
                        LogFile.adjuntarLog("Guardar Traza Factura Electronica Error", response.body().getMensaje());
                        if(response.body().getMensaje().equals("La referencia ya se encuentra insertada")){
                            presentador.respuestaGuardarTrazaFE();
                        }else{
                            presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                    Constantes.SERVICIO_FACTURA_ELECTRONICA_GUARDAR_TRAZA);
                        }
                    }
                }else{
                    LogFile.adjuntarLog("Guardar Traza Factura Electronica Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_FACTURA_ELECTRONICA_GUARDAR_TRAZA);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGuardarTrazaFE> call, @NonNull Throwable t) {
                LogFile.adjuntarLog("Guardar Traza Factura Electronica Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_FACTURA_ELECTRONICA_GUARDAR_TRAZA);
            }
        });
    }

    public void iniciarTareaRespuestaDatafono() {
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

    private void apiRespuestaDatafono() {
        try{
            //API para consultar el pago del datafono
            Call<ResponseDatafono> datafonoCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                    .doRespuestaDatafono(Utilidades.peticionRespuestaDatafono());
            datafonoCall.enqueue(new Callback<ResponseDatafono>() {
                @Override
                public void onResponse(@NonNull Call<ResponseDatafono> call, @NonNull Response<ResponseDatafono> response) {
                    if(response.isSuccessful()) {
                        assert response.body() != null;
                        if(response.body().getEsValida()){
                            presentador.ocultarDialogProgressBar();
                            cuerpoRespuestaDatafono = response.body().getRespuestaDatafono();
                            LogFile.adjuntarLog("Respuesta Datafono Response", "Número de recibo: " + cuerpoRespuestaDatafono.getRecibo() +
                                    "\nAprobación: " + cuerpoRespuestaDatafono.getAprobacion() +
                                    "\nTipo Cuenta: " + cuerpoRespuestaDatafono.getTipoCuentaMedioPago() +
                                    "\nFecha y Hora: " + cuerpoRespuestaDatafono.getFechaTransaccion() + " | " + cuerpoRespuestaDatafono.getHoraTransaccion() +
                                    "\nValor Total: " + cuerpoRespuestaDatafono.getValorTotal());
                            RespuestaDatafono datafono = new RespuestaDatafono(1,
                                    "00", cuerpoRespuestaDatafono.getRecibo(), cuerpoRespuestaDatafono.getAprobacion(),
                                    cuerpoRespuestaDatafono.getUltimosDigitosTarjeta(), cuerpoRespuestaDatafono.getFranquicia(),
                                    cuerpoRespuestaDatafono.getTipoCuentaMedioPago(), cuerpoRespuestaDatafono.getNumeroCuotas(),
                                    cuerpoRespuestaDatafono.getFechaTransaccion()+cuerpoRespuestaDatafono.getHoraTransaccion(),
                                    cuerpoRespuestaDatafono.getBinTarjeta(), cuerpoRespuestaDatafono.getValorIva(),
                                    cuerpoRespuestaDatafono.getFechaVencimiento(), cuerpoRespuestaDatafono.getDireccionComercio(),"");

                            if(cuerpoRespuestaDatafono.getHoraTransaccion().trim().length() == 4){
                                datafono.setHora(cuerpoRespuestaDatafono.getHoraTransaccion().substring(0, 2));
                                datafono.setMinuto(cuerpoRespuestaDatafono.getHoraTransaccion().substring(2, 4));
                                datafono.setErrorHoraMinutos(false);
                            }else{
                                datafono.setErrorHoraMinutos(true);
                            }

                            presentador.respuestaDatafono(datafono);
                        }else{
                            LogFile.adjuntarLog("Respuesta Datafono Error", response.body().getMensaje());

                            if(response.body().getMensaje().equals("No se encuentra el pago")){
                                if(unaBusqueda){
                                    unaBusqueda = false;
                                    presentador.ocultarDialogProgressBar();
                                    presentador.errorServicio(contexto.getResources().getString(R.string.error), response.body().getMensaje(),
                                            Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO);
                                }else{
                                    iniciarTareaRespuestaDatafono();
                                }
                            }else{
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
        }catch (Exception ex){
            LogFile.adjuntarLog("Respuesta Datafono Error (Exception): "+ ex.getMessage(), ex.fillInStackTrace());
            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                    contexto.getResources().getString(R.string.error_conexion) + ex.getMessage() + " (TryCatch)",
                    Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO_EXCEPTION);
        }
    }
}