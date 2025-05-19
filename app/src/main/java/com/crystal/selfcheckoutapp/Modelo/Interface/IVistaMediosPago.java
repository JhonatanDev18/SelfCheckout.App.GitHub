package com.crystal.selfcheckoutapp.Modelo.Interface;

import com.crystal.selfcheckoutapp.Modelo.clases.Descuento;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.Payment;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseExtraerInfoDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.util.List;

public interface IVistaMediosPago {
    interface Vista{
        void errorServicio(String titulo, String mensaje, int servicio);
        void respuestaCondicionesComerciales(List<Producto> productos, List<RespuestaLine> respuestaLine);
        void respuestaConsultarPago(Payment payment);
        void respuestaValidarAperturaCaja();
        void respuestaCrearDocumento(String numeroTransaccion);
        void respuestaCerrarDocumento(String textoFiscal);
        void respuestaRfidVenta();
        void respuestaDetalleDescuentos();
        void respuestaDocumentoDian();
        void respuestaDocumentoTef();
        void llenarDescuentosReferido(String descuento);
        void respuestaConsultaCupoEmpleado(double cupo, String empresaTemporal);
        void respuestaActualizarCupoEmpleado();
        void respuestaExpirarCodigoReferido();
        void respuestaDatafono(RespuestaDatafono datafono);
        void respuestaGuardarResTef();
        void respuestaGuardarTrazaFE();
    }

    interface Presentador{
        void generarCompraDatafono(Factura factura);
        double calcularTotal(List<Producto> productoList);
        void errorServicio(String titulo, String mensaje, int servicio);
        void mostrarQrGenerado(String qr);
        void generarQrBancolombia(Cliente cliente);
        void respuestaConsultarPago(Payment payment);
        void consultarPagoQrBancolombia();
        void consultarConsecutivoFiscal(Factura factura);
        void respuestaValidarAperturaCaja();
        void crearDocumento(Factura factura);
        void cerrarDocumento(Factura factura);
        void crearRfidVenta(Factura factura);
        void enviarDescuentos(Factura factura);
        void enviarDocumentoDian(Factura factura);
        void enviarDocumentoTef(Factura factura);
        void respuestaCrearDocumento(String numeroTransaccion);
        void respuestaCerrarDocumento(String textoFiscal);
        void reenviarCodigoOTP(Cliente cliente);
        void respuestaReenvioCodigo(String nuevoCodigo);
        void respuestaRfidVenta();
        void respuestaDetalleDescuentos();
        void respuestaDocumentoDian();
        void respuestaDocumentoTef();
        void verificacionOTP(Cliente cliente, Descuento descuento);
        void generarCodigoOTP(Cliente cliente, Descuento descuento);
        void validacionCuponReferido(String codigo);
        void mostrarDescuentoReferido(Cliente cliente, String descuentos,boolean habilitarAtras);
        void mostrarDescuentoMedioPago(Cliente cliente);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void consultarProductos(List<String> eanes);
        void consultarCupoEmpleado(String cedula);
        void procesarPago();
        void actualizarCupoEmpleado(Cliente cliente);
        void respuestaActualizarCupoEmpleado();
        void respuestaConsultaCupoEmpleado(double cupo, String empresaTemporal);
        void respuestaCondicionesComerciales(List<Producto> productos, List<RespuestaLine> respuestaLine);
        void respuestaDatafono(RespuestaDatafono datafono);
        void consultarDocumento(Factura factura);
        void guardarRespuestaTef(Factura factura);
        void respuestaGuardarResTef();
        void expirarCodigoReferido();
        void respuestaExpirarCodigoReferido();
        void extraerInfoDocumento(Factura factura);
        void facturaElectronicaQR(Factura factura);
        void guardarTrazaFE(Factura factura);

        void validarRespuestaDatafono();
        void respuestaGuardarTrazaFE();
        void ocultarDialogProgressBar();
        void ocultarDescuentos();
        void ocultarDescuentosReferido();
        boolean estadoProgress();
    }

    interface Modelo{
        void apiConsultarProductos(List<String> eanes);
        void apiCondicionesComerciales(List<Producto> productos);
        void apiGenerarQrBancolombia(Cliente cliente);
        void apiConsultarPagoQrBancolombia();
        void apiGenerarCodigoOTP(Cliente cliente, Descuento descuento, boolean reenvio);
        void apiMediosPagoCaja();
        void apiConsecutivoFiscal(Factura factura);
        void apiValidarAperturaCaja(Factura factura);
        void apiCrearDocumento(Factura factura);
        void apiConsultarDocumento(Factura factura);
        void apiCerrarDocumento(Factura factura);
        void apiCrearRfidVenta(Factura factura);
        void apiEnviarDescuentos(Factura factura);
        void apiEnviarDocumentoDian(Factura factura);
        void apiEnviarDocumentoTef(Factura factura);
        void apiValidacionCodigoReferido(String codigo);
        void apiExpirarCodigoReferido();
        void apiValidarEmpleado(String cedula);
        void apiConsultarCupoEmpleado(String cedula);
        void apiActualizarCupoEmpleado(Cliente cliente);
        void apiCompraDatafono(Factura factura);
        void apiGuardarRespuestaTef(Factura factura);
        void apiExtraerInfoDocumento(Factura factura);
        void apiFacturaElectronicaQR(Factura factura);
        void apiGuardarTrazaFE(Factura factura);

        void validarRespuestaDatafono();
    }
}
