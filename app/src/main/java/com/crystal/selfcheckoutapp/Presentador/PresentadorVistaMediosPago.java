package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaMediosPago;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaMediosPago;
import com.crystal.selfcheckoutapp.Modelo.clases.Descuento;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.Payment;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseExtraerInfoDocumento;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCodigoVerificacionDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaDescuentosClienteDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaPantallaCargaDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaQrBancolombiaDialogFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PresentadorVistaMediosPago implements IVistaMediosPago.Presentador {
    private IVistaMediosPago.Vista vista;
    private IVistaMediosPago.Modelo modelo;
    private Context contexto;
    private Utilidades util;
    private FragmentManager fragmentManager;
    private VistaCodigoVerificacionDialogFragment verificacionDialogFragment;
    private VistaDescuentosClienteDialogFragment vistaDescuentosClienteDialogFragment, vistaDescuentoReferidoDialogFragment;
    private Cliente clienteReal;
    private VistaPantallaCargaDialogFragment pantallaCarga;

    public PresentadorVistaMediosPago(IVistaMediosPago.Vista vista, Context contexto, FragmentManager fragmentManager) {
        this.vista = vista;
        this.contexto = contexto;
        this.fragmentManager = fragmentManager;

        modelo = new ModeloVistaMediosPago(this, contexto);
        util = new Utilidades();
    }

    @Override
    public void generarCompraDatafono(Factura factura) {
        modelo.apiCompraDatafono(factura);
    }

    @Override
    public double calcularTotal(List<Producto> productoList) {
        return util.calcularTotal(productoList);
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        vista.errorServicio(titulo, mensaje, servicio);
    }

    @Override
    public void mostrarQrGenerado(String qr) {
        VistaQrBancolombiaDialogFragment vistaQrBancolombiaDialogFragment =
                new VistaQrBancolombiaDialogFragment(qr, vista, this);
        vistaQrBancolombiaDialogFragment.show(fragmentManager, "DialogFragmentQrBancolombia");
    }

    @Override
    public void generarQrBancolombia(Cliente cliente) {
        modelo.apiGenerarQrBancolombia(cliente);
    }

    @Override
    public void respuestaConsultarPago(Payment payment) {
        vista.respuestaConsultarPago(payment);
    }

    @Override
    public void consultarPagoQrBancolombia() {
        modelo.apiConsultarPagoQrBancolombia();
    }

    @Override
    public void consultarConsecutivoFiscal(Factura factura) {
        modelo.apiConsecutivoFiscal(factura);
    }

    @Override
    public void respuestaValidarAperturaCaja() {
        vista.respuestaValidarAperturaCaja();
    }

    @Override
    public void crearDocumento(Factura factura) {
        modelo.apiCrearDocumento(factura);
    }

    @Override
    public void cerrarDocumento(Factura factura) {
        modelo.apiCerrarDocumento(factura);
    }

    @Override
    public void crearRfidVenta(Factura factura) {
        modelo.apiCrearRfidVenta(factura);
    }

    @Override
    public void enviarDescuentos(Factura factura) {
        modelo.apiEnviarDescuentos(factura);
    }

    @Override
    public void enviarDocumentoDian(Factura factura) {
        modelo.apiEnviarDocumentoDian(factura);
    }

    @Override
    public void enviarDocumentoTef(Factura factura) {
        modelo.apiEnviarDocumentoTef(factura);
    }

    @Override
    public void respuestaCrearDocumento(String numeroTransaccion) {
        vista.respuestaCrearDocumento(numeroTransaccion);
    }

    @Override
    public void respuestaCerrarDocumento(String textoFiscal) {
        vista.respuestaCerrarDocumento(textoFiscal);
    }

    @Override
    public void reenviarCodigoOTP(Cliente cliente) {
        modelo.apiGenerarCodigoOTP(cliente, null, true);
    }

    @Override
    public void respuestaReenvioCodigo(String nuevoCodigo) {
        if(verificacionDialogFragment != null){
            verificacionDialogFragment.insertarNuevoCodigo(nuevoCodigo);
        }
    }

    @Override
    public void validarRespuestaDatafono(){
        modelo.validarRespuestaDatafono();
    }

    @Override
    public void respuestaRfidVenta() {
        vista.respuestaRfidVenta();
    }

    @Override
    public void respuestaDetalleDescuentos() {
        vista.respuestaDetalleDescuentos();
    }

    @Override
    public void respuestaDocumentoDian() {
        vista.respuestaDocumentoDian();
    }

    @Override
    public void respuestaDocumentoTef() {
        vista.respuestaDocumentoTef();
    }

    @Override
    public void verificacionOTP(Cliente cliente, Descuento descuento) {
        verificacionDialogFragment = new VistaCodigoVerificacionDialogFragment(cliente, descuento, this);
        verificacionDialogFragment.show(fragmentManager, "VerificacionOtpDialogFragment");
    }

    @Override
    public void generarCodigoOTP(Cliente cliente, Descuento descuento) {
        modelo.apiGenerarCodigoOTP(cliente, descuento, false);
    }

    @Override
    public void validacionCuponReferido(String codigo) {
        modelo.apiValidacionCodigoReferido(codigo);
    }

    @Override
    public void mostrarDescuentoReferido(Cliente cliente, String descuentos, boolean habilitarAtras) {
        if(!descuentos.isEmpty()){
            vista.llenarDescuentosReferido(descuentos);
            vistaDescuentoReferidoDialogFragment = new VistaDescuentosClienteDialogFragment(descuentosMostrar(descuentos),
                    cliente==null ? clienteReal: cliente, true, this, vista, habilitarAtras);
            vistaDescuentoReferidoDialogFragment.show(fragmentManager, "DescuentosReferido");
        }
    }

    @Override
    public void mostrarDescuentoMedioPago(Cliente cliente) {
        clienteReal = cliente;
        String descuentosText;

        if(cliente.isEmpleado()){
            descuentosText = SPM.getString(Constantes.PARAM_COL_DESCUENTO_EMPLEADO);
        }else{
            descuentosText = SPM.getString(Constantes.PARAM_COL_MEDIOPAGO_CLI);
        }

        assert descuentosText != null;
        if(!descuentosText.isEmpty()){
            vistaDescuentosClienteDialogFragment =
                    new VistaDescuentosClienteDialogFragment(descuentosMostrar(descuentosText), cliente,
                            false, this, vista, true);
            vistaDescuentosClienteDialogFragment.show(fragmentManager, "DescuentosEmpleado");
        }
    }

    private List<Descuento> descuentosMostrar(String descuentosText) {
        List<Descuento> descuentos = new ArrayList<>();
        String[] descuentoItem = descuentosText.split(";");

        for (String descuento : descuentoItem) {
            if (!descuento.trim().isEmpty()) {
                List<String> descuentoDetalle = Arrays.asList(descuento.split("\\|"));
                if (descuentoDetalle.size() > 0) {
                    descuentos.add(new Descuento(descuentoDetalle.get(1), descuentoDetalle.get(0), descuentoDetalle.get(2)));
                }
            }
        }

        return descuentos;
    }

    @Override
    public void dialogProgressBar(String mensaje, boolean cancelable) {
        pantallaCarga = new VistaPantallaCargaDialogFragment(mensaje, cancelable);
        pantallaCarga.show(fragmentManager, "PantallaCargaDialogFragment");
        pantallaCarga.setPresentadorVistaMediosPago(this);
    }

    @Override
    public void consultarProductos(List<String> eanes) {
        modelo.apiConsultarProductos(eanes);
    }

    @Override
    public void consultarCupoEmpleado(String cedula) {
        modelo.apiConsultarCupoEmpleado(cedula);
    }

    @Override
    public void procesarPago() {
        modelo.apiMediosPagoCaja();
    }

    @Override
    public void actualizarCupoEmpleado(Cliente cliente) {
        modelo.apiActualizarCupoEmpleado(cliente);
    }

    @Override
    public void respuestaActualizarCupoEmpleado() {
        vista.respuestaActualizarCupoEmpleado();
    }

    @Override
    public void respuestaConsultaCupoEmpleado(double cupo, String empresaTemporal) {
        vista.respuestaConsultaCupoEmpleado(cupo, empresaTemporal);
    }

    @Override
    public void respuestaCondicionesComerciales(List<Producto> productos, List<RespuestaLine> respuestaLine) {
        vista.respuestaCondicionesComerciales(productos, respuestaLine);
    }

    @Override
    public void respuestaDatafono(RespuestaDatafono datafono) {
        vista.respuestaDatafono(datafono);
    }

    @Override
    public void consultarDocumento(Factura factura) {
        modelo.apiConsultarDocumento(factura);
    }

    @Override
    public void guardarRespuestaTef(Factura factura) {
        modelo.apiGuardarRespuestaTef(factura);
    }

    @Override
    public void respuestaGuardarResTef() {
        vista.respuestaGuardarResTef();
    }

    @Override
    public void expirarCodigoReferido() {
        modelo.apiExpirarCodigoReferido();
    }

    @Override
    public void respuestaExpirarCodigoReferido() {
        vista.respuestaExpirarCodigoReferido();
    }

    @Override
    public void extraerInfoDocumento(Factura factura) {
        modelo.apiExtraerInfoDocumento(factura);
    }

    @Override
    public void facturaElectronicaQR(Factura factura) {
        modelo.apiFacturaElectronicaQR(factura);
    }

    @Override
    public void guardarTrazaFE(Factura factura) {
        modelo.apiGuardarTrazaFE(factura);
    }

    @Override
    public void respuestaGuardarTrazaFE() {
        vista.respuestaGuardarTrazaFE();
    }

    @Override
    public void ocultarDialogProgressBar() {
        if(pantallaCarga != null){
            pantallaCarga.dismiss();
            pantallaCarga = null;
        }
    }

    @Override
    public void ocultarDescuentos() {
        if(vistaDescuentosClienteDialogFragment != null){
            vistaDescuentosClienteDialogFragment.dismiss();
        }
    }

    @Override
    public void ocultarDescuentosReferido() {
        if(vistaDescuentoReferidoDialogFragment != null){
            vistaDescuentoReferidoDialogFragment.dismiss();
        }
    }

    @Override
    public boolean estadoProgress(){
        return pantallaCarga == null;
    }
}
