package com.crystal.selfcheckoutapp.Vista;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaMediosPago;
import com.crystal.selfcheckoutapp.Modelo.clases.Descuento;
import com.crystal.selfcheckoutapp.Modelo.clases.DetalleDescuento;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.ImpresoraDevice;
import com.crystal.selfcheckoutapp.Modelo.clases.Payment;
import com.crystal.selfcheckoutapp.Modelo.clases.PaymentCD;
import com.crystal.selfcheckoutapp.Modelo.clases.RFIDVenta;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.clases.SerialEan;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.KeyboardUtils;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseExtraerInfoDocumento;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaMediosPago;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ProductosMpDescripRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCodigoVerificacionDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaDescuentosClienteDialogFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomDosAccionesDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomUnaAccionDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaMediosPago extends AppCompatActivity implements IVistaMediosPago.Vista,
        VistaDescuentosClienteDialogFragment.OnInputListener, VistaCodigoVerificacionDialogFragment.OnInputListener{

    private ProductosMpDescripRecyclerViewAdapter adaptador;
    private RecyclerView rvProductosMP;
    private List<Producto> listaProductos;
    private Context contexto;
    private Button btnAtrasMedioPago, btnPagar, btnEditarMedioPago;
    private Factura factura;
    private Cliente cliente;
    private PresentadorVistaMediosPago presentador;
    private RespuestaDatafono respuestaDatafono;
    private boolean xmlDian, seleccionoMp, isReferido;
    private Descuento medioPago;
    private EditText etMedioPago;
    private TextView tvNumeroArticulosMP, tvSubtotalMP, tvTotalDescuentoMP, tvTotalPagarMP;
    private Utilidades util;
    private Timer tiempo;
    private TimerTask tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPM.setInt(Constantes.PROCESO_ACTIVITY, Constantes.VISTA_MEDIO_PAGO);
        setContentView(R.layout.vista_medios_pago);

        LogFile.adjuntarLogTitulo("Abriendo [Pantalla Medios de pago]");
        
        /*Esta acción permite que la pantalla no se apague sin importar que el dispositivo
        tenga una suspencion minima ejemplo (15 segundos), la pantalla seguira activa.
        Tener en cuenta que en el manifest debe existir el siguiente permiso
        (<uses-permission android:name="android.permission.WAKE_LOCK"/>)*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Ocultar el Action bar del aplicativo
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Ocultar la barra de estado donde se ve lo siguiente(Wifi, Notificaciones, batería etc...)
        Utilidades.ocultarBarraEstado(getWindow());

        //Ocultar la barra de navegación, que son los 3 botones (Atras, Inicio y Recientes)
        Utilidades.ocultarBarraNavegacion(getWindow());

        //Inicializar variables de interfaz o otras variables.
        inicializar();

        /*En este metodo estan los eventos de los botones o si se usa otro evento en general
        ejemplo (OnClick).*/
        eventos();

        cargarIntents();

        iniciarTarea();
        tiempo = new Timer();
        tiempo.schedule(tarea, 500);
    }

    @SuppressLint("SetTextI18n")
    private void cargarIntents() {
        //Intent info cliente
        factura = (Factura) getIntent().getSerializableExtra(getResources().getString(R.string.key_intent_factura));

        cliente = factura.getCliente();
        listaProductos = factura.getProductos();

        List<Producto> listaProductosCopia = new ArrayList<>();
        for(int i=0;i<listaProductos.size(); i++){
            Producto productoInsert = listaProductos.get(i);
            Producto productoCopia = null;
            try{
                productoCopia = (Producto) productoInsert.clone();
            }catch (Exception ex){
                Log.e("LOGCAT", "Error en la copia");
            }

            listaProductosCopia.add(i, productoCopia);
        }
        factura.setProductosRFID(listaProductosCopia);

        isReferido = cliente.isReferido();

        if(isReferido){
            LogFile.adjuntarLog("PASO 3: ABRIENDO POP-UP Descuentos/Medio de pago", "Clientes - Referidos");
            presentador.mostrarDescuentoReferido(cliente, cliente.getDescuentoReferido(), true);
        }else{
            LogFile.adjuntarLog("PASO 3: ABRIENDO POP-UP Descuentos/Medio de pago", "Empleado tipo: "+cliente.getTipo());
            presentador.mostrarDescuentoMedioPago(cliente);
        }

        productosFront();

        totalizar();
    }

    private void productosFront() {
        List<Producto> productosFront = new ArrayList<>();

        for(int i=0; i<listaProductos.size();i++){
            try{
                Producto productoCopia = (Producto) listaProductos.get(i).clone();
                int contador = 0;
                boolean agregar = true;

                for(Producto producto:listaProductos){
                    if(producto.getEan().equals(productoCopia.getEan())){
                        contador++;
                    }
                }

                productoCopia.setCantidad(contador);

                for(Producto producto: productosFront){
                    if (producto.getEan().equals(productoCopia.getEan())) {
                        agregar = false;
                        break;
                    }
                }

                if(agregar){
                    productosFront.add(productoCopia);
                }
            }catch (Exception ex){
                Log.e("LOGCAT", "Error en la copia");
            }
        }

        adaptador = new ProductosMpDescripRecyclerViewAdapter(productosFront);
        rvProductosMP.setAdapter(adaptador);
    }

    private void iniciarTarea() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->{
                    util.vozAndroid(getResources().getString(R.string.voz_medio_de_pago));
                });
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private void totalizar() {
        double descuentoTotal = util.calcularDescuentoTotal(listaProductos);
        tvNumeroArticulosMP.setText(Integer.toString(listaProductos.size()));
        tvSubtotalMP.setText(Utilidades.formatearPrecio(util.calcularSubtotal(listaProductos)));
        tvTotalDescuentoMP.setText(Utilidades.formatearPrecio(descuentoTotal*-1));
        tvTotalPagarMP.setText(Utilidades.formatearPrecio(presentador.calcularTotal(listaProductos)));
        SPM.setString(Constantes.TOTAL_A_PAGAR, Integer.toString((int) presentador.calcularTotal(listaProductos)));
        btnPagar.setText(String.format(getResources().getString(R.string.btn_pagar_precio),
                Utilidades.formatearPrecio(Double.parseDouble(SPM.getString(Constantes.TOTAL_A_PAGAR)))));
    }

    private void inicializar() {
        factura = new Factura();
        contexto = VistaMediosPago.this;
        util = new Utilidades(contexto);
        xmlDian = SPM.getBoolean(Constantes.PARAM_INSERTAR_DIAN);
        presentador = new PresentadorVistaMediosPago(this, contexto, getSupportFragmentManager());

        btnAtrasMedioPago = findViewById(R.id.btnAtrasMedioPago);
        btnEditarMedioPago = findViewById(R.id.btnEditarMedioPago);
        etMedioPago = findViewById(R.id.etMedioPago);
        btnPagar = findViewById(R.id.btnPagar);

        rvProductosMP = findViewById(R.id.rvProductosMP);
        rvProductosMP.setLayoutManager(new LinearLayoutManager(this));

        tvNumeroArticulosMP = findViewById(R.id.tvNumeroArticulosMP);
        tvSubtotalMP = findViewById(R.id.tvSubtotalMP);
        tvTotalDescuentoMP = findViewById(R.id.tvTotalDescuentoMP);
        tvTotalPagarMP = findViewById(R.id.tvTotalPagarMP);
    }

    private void eventos() {
        btnAtrasMedioPago.setOnClickListener(this::atrasMedioPago);
        btnEditarMedioPago.setOnClickListener(this::seleccionarOtroMedioPago);
        btnPagar.setOnClickListener(this::pagar);
    }

    private void seleccionarOtroMedioPago(View view){
        LogFile.adjuntarLog("Botón", "Cambiar medio de pago");
        util.vozAndroid(getResources().getString(R.string.voz_medio_de_pago_otro));
        if(isReferido){
            presentador.mostrarDescuentoReferido(cliente, cliente.getDescuentoReferido(), false);
        }else{
            presentador.mostrarDescuentoMedioPago(cliente);
        }
    }

    private void actualizarErrores() {
        factura.setErrorConsecutivoFiscal(false);
        factura.setErrorCompraDatafono(false);
        factura.setErrorCrearDocumento(false);
        factura.setErrorRfidVenta(false);
        factura.setErrorEnviarDescuentos(false);
        factura.setErrorDocumentoDian(false);
        factura.setErrorDocumentoTef(false);
        factura.setErrorActualizarCupoEmpleado(false);
        factura.setErrorGuardarRespuestaTef(false);
    }

    @SuppressLint("DefaultLocale")
    private void pagar(View view){
        LogFile.adjuntarLog("Botón", "Pagar "+
                Utilidades.formatearPrecio(Double.parseDouble(SPM.getString(Constantes.TOTAL_A_PAGAR))));
        String mpSeleccionado = medioPago.getCodigoMP();

        if(Objects.requireNonNull(SPM.getString(Constantes.PARAM_MEDIOS_PAGOS_CREDITO)).contains(mpSeleccionado)){
            if(!factura.validarErrores()){
                if(presentador.estadoProgress()){
                    factura.setPagoTef(false);
                    presentador.dialogProgressBar(getResources().getString(R.string.progress_procesando_compra),
                            false);
                    presentador.procesarPago();
                }
            }else{
                validarErrores();
            }
        }else if(mpSeleccionado.equals(SPM.getString(Constantes.PARAM_CODIGO_MEDIOPAGO_QRBANCOLOMBIA))){
            try{
                if(!factura.validarErrores()){
                    if(presentador.estadoProgress()){
                        factura.setPagoTef(false);
                        presentador.dialogProgressBar(getResources().getString(R.string.progress_generando_qr),
                                false);
                        presentador.generarQrBancolombia(cliente);
                    }
                }else{
                    validarErrores();
                }
            }catch (Exception e){
                @SuppressLint("StringFormatMatches") VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                        getResources().getString(R.string.ups_algo_mal),
                        String.format(getResources().getString(R.string.ups_algo_mal_msj), Constantes.SERVICIO_GENERAR_QRBANCOLOMBIA), e.getMessage(),
                        getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
                msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");

                StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                int lineNumber = stackTraceElement.getLineNumber();

                // Registra el log con detalles
                LogFile.adjuntarLog(
                        String.format("Generar QR Bancolombia Error [%s - %s:%d]", className, methodName, lineNumber),
                        e.fillInStackTrace()
                );
            }
        }else if(mpSeleccionado.equals(SPM.getString(Constantes.PARAM_CODIGO_MEDIOPAGO_TEF))){
            if(!factura.validarErrores()){
                if(presentador.estadoProgress()){
                    factura.setPagoTef(true);
                    presentador.dialogProgressBar(getResources().getString(R.string.progress_procesando_compra),
                            false);
                    presentador.procesarPago();
                }
            }else{
                validarErrores();
            }
        }
    }

    private void validarErrores(){
        if(factura.isErrorConsecutivoFiscal()){
            factura.setErrorConsecutivoFiscal(false);
            presentador.consultarConsecutivoFiscal(factura);
        }else if(factura.isErrorCompraDatafono()){
            factura.setErrorCompraDatafono(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_procesando_compra_datafono),
                    false);
            presentador.generarCompraDatafono(factura);
        }else if(factura.isErrorRespuestaDatafono()){
            factura.setErrorRespuestaDatafono(false);
            presentador.dialogProgressBar("Validando pago datafono...",
                    false);
            presentador.validarRespuestaDatafono();
        }else if(factura.isErrorCrearDocumento()){
            factura.setErrorCrearDocumento(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.consultarDocumento(factura);
        }else if(factura.isErrorRfidVenta()){
            factura.setErrorRfidVenta(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.crearRfidVenta(factura);
        }else if(factura.isErrorEnviarDescuentos()){
            factura.setErrorEnviarDescuentos(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.enviarDescuentos(factura);
        }else if(factura.isErrorDocumentoDian()){
            factura.setErrorDocumentoDian(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.enviarDocumentoDian(factura);
        }else if(factura.isErrorDocumentoTef()){
            factura.setErrorDocumentoTef(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.enviarDocumentoTef(factura);
        }else if(factura.isErrorActualizarCupoEmpleado()){
            factura.setErrorActualizarCupoEmpleado(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.actualizarCupoEmpleado(cliente);
        }else if(factura.isErrorGuardarRespuestaTef()){
            factura.setErrorGuardarRespuestaTef(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.guardarRespuestaTef(factura);
        }else if(factura.isErrorExpirarCodigoReferido()){
            factura.setErrorExpirarCodigoReferido(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.expirarCodigoReferido();
        }else if(factura.isErrorExtraerInfoDocumento()){
            factura.setErrorExtraerInfoDocumento(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.extraerInfoDocumento(factura);
        }else if(factura.isErrorFacturaElectronicaQr()){
            factura.setErrorFacturaElectronicaQr(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.facturaElectronicaQR(factura);
        }else if(factura.isErrorGuardarTrazaFE()){
            factura.setErrorGuardarTrazaFE(false);
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.guardarTrazaFE(factura);
        }else{
            imprimirTirilla();
        }
    }

    public void atrasMedioPago(View view){
        LogFile.adjuntarLog("Botón", "Regresar");
        factura.setCliente(cliente);
        Intent intent = new Intent(contexto, VistaCompraCliente.class);
        intent.putExtra(getResources().getString(R.string.key_intent_factura), factura);
        startActivity(intent);
        finish();
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        presentador.ocultarDialogProgressBar();

        if(mensaje.equals(String.format(getResources().getString(R.string.caja_cerrada), SPM.getString(Constantes.CAJA_CODIGO)))
            && servicio == Constantes.SERVICIO_APERTURA_CAJA){
            regresarLogin();
        }else if(servicio == Constantes.SERVICIO_CONSULTAR_CUPO_EMPLEADO){
            volverPreguntarMedioPago(false);
        }else if(servicio == Constantes.SERVICIO_CONSULTAR_PAGO_QRBANCOLOMBIA
                && mensaje.equals(getResources().getString(R.string.pago_no_encontrado_qr_bancolombia))){
            Utilidades.mjsToast(getResources().getString(R.string.pago_no_encontrado_qr_bancolombia_idioma),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
        }else if(servicio == Constantes.SERVICIO_VALIDAR_CODIGO_REFERIDO_WSP
                && mensaje.equals(getResources().getString(R.string.codigo_ya_redimido))){
            Utilidades.mjsToast(getResources().getString(R.string.codigo_ya_redimido_idioma),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
        }else if(servicio == Constantes.SERVICIO_VALIDAR_CODIGO_REFERIDO_WSP
                && mensaje.equals(getResources().getString(R.string.codigo_ya_expiro))){
            Utilidades.mjsToast(getResources().getString(R.string.codigo_ya_expiro_idioma),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
        }else if(servicio == Constantes.SERVICIO_VALIDAR_CODIGO_REFERIDO_WSP
                && mensaje.equals(getResources().getString(R.string.codigo_no_existe))){
            Utilidades.mjsToast(getResources().getString(R.string.codigo_no_existe_idioma),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
        }else if(servicio == Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO_EXCEPTION){
            VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                    getResources().getString(R.string.ups_algo_mal),
                    String.format(getResources().getString(R.string.ups_algo_mal_msj_tef), servicio), mensaje,
                    getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
        }else{
            VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                    getResources().getString(R.string.ups_algo_mal),
                    String.format(getResources().getString(R.string.ups_algo_mal_msj), servicio), mensaje,
                    getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
        }

        if(servicio == Constantes.SERVICIO_CONSECUTIVO_FISCAL || servicio == Constantes.SERVICIO_APERTURA_CAJA){
            factura.setErrorConsecutivoFiscal(true);
        }

        if(servicio == Constantes.SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO){
            factura.setErrorCompraDatafono(true);
        }

        if(servicio == Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO_EXCEPTION){
            factura.setErrorRespuestaDatafono(true);
        }

        if(servicio == Constantes.SERVICIO_CREAR_DOCUMENTO || servicio == Constantes.SERVICIO_CONSULTAR_DOCUMENTO ||
                servicio == Constantes.SERVICIO_CERRAR_DOCUMENTO){
            factura.setErrorCrearDocumento(true);
        }

        if(servicio == Constantes.SERVICIO_CREAR_RFID_VENTA){
            factura.setErrorRfidVenta(true);
        }

        if(servicio == Constantes.SERVICIO_DETALLE_DESCUENTOS){
            factura.setErrorEnviarDescuentos(true);
        }

        if(servicio == Constantes.SERVICIO_DOCUMENTO_DIAN){
            factura.setErrorDocumentoDian(true);
        }

        if(servicio == Constantes.SERVICIO_DOCUMENTO_TEF){
            factura.setErrorDocumentoTef(true);
        }

        if(servicio == Constantes.SERVICIO_ACTUALIZAR_CUPO_EMPLEADO){
            factura.setErrorActualizarCupoEmpleado(true);
        }

        if(servicio == Constantes.SERVICIO_GUARDAR_RESPUESTA_TEF){
            factura.setErrorGuardarRespuestaTef(true);
        }

        if(servicio == Constantes.SERVICIO_EXPIRAR_CODIGO_REFERIDO_WSP){
            factura.setErrorExpirarCodigoReferido(true);
        }

        if(servicio == Constantes.SERVICIO_EXTRAER_INFO_DOCUMENTO){
            factura.setErrorExtraerInfoDocumento(true);
        }

        if(servicio == Constantes.SERVICIO_FACTURA_ELECTRONICA_QR){
            factura.setErrorFacturaElectronicaQr(true);
        }

        if(servicio == Constantes.SERVICIO_FACTURA_ELECTRONICA_GUARDAR_TRAZA){
            factura.setErrorGuardarTrazaFE(true);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void volverPreguntarMedioPago(boolean errorCupo){
        medioPago = null;
        if(errorCupo){
            double totalPagar = presentador.calcularTotal(listaProductos);
            sweetAlertCupoEmpleado(getResources().getString(R.string.informacion),
                    String.format(getResources().getString(R.string.error_cupo_empleado),
                            Utilidades.formatearPrecio(cliente.getCupoEmpleado()), Utilidades.formatearPrecio(totalPagar)),
                    SweetAlertDialog.WARNING_TYPE, contexto);
        }else{
            presentador.mostrarDescuentoMedioPago(cliente);
        }
    }

    @Override
    public void respuestaCondicionesComerciales(List<Producto> productos, List<RespuestaLine> respuestaLine) {
        factura.setDescuentos(respuestaLine);
        factura.setProductos(productos);

        listaProductos.clear();
        listaProductos.addAll(productos);

        if(isReferido){
            presentador.ocultarDescuentos();
            cliente.setReferido(true);
        }

        totalizar();

        productosFront();

        validarCupoEmpleado();
    }

    private void validarCupoEmpleado() {
        if(cliente.isEmpleado()){
            if(Objects.requireNonNull(SPM.getString(Constantes.PARAM_MEDIOS_PAGOS_CREDITO))
                    .contains(medioPago.getCodigoMP())){
                if(!presentador.estadoProgress()){
                    presentador.ocultarDialogProgressBar();
                }
                presentador.dialogProgressBar(getResources().getString(R.string.progress_consultando_cupo_empleado),
                        false);
                presentador.consultarCupoEmpleado(cliente.getCedula(false));
            }
        }else{
            cliente.setCupoEmpleado(0.0);
        }
    }

    @Override
    public void respuestaConsultarPago(Payment payment) {
        factura.setMedioPago(payment);
        presentador.consultarConsecutivoFiscal(factura);
    }

    @Override
    public void respuestaValidarAperturaCaja() {
        if(factura.isPagoTef()){
            presentador.ocultarDialogProgressBar();
            if(presentador.estadoProgress()){
                presentador.dialogProgressBar(getResources().getString(R.string.progress_procesando_compra_datafono),
                        false);
                presentador.generarCompraDatafono(factura);
            }
        }else{
            presentador.crearDocumento(factura);
        }
    }

    @Override
    public void respuestaCrearDocumento(String numeroTransaccion) {
        factura.setNumeroTransaccion(numeroTransaccion);
        presentador.cerrarDocumento(factura);
    }

    @Override
    public void respuestaCerrarDocumento(String textoFiscal) {
        factura.setTextoFiscal(textoFiscal);

        List<RFIDVenta> ventaRfidList = factura.rfidVenta();
        if(!ventaRfidList.isEmpty()){
            presentador.crearRfidVenta(factura);
        }else{
            enviarDescuentos();
        }
    }

    @Override
    public void respuestaRfidVenta() {
        enviarDescuentos();
    }

    @Override
    public void respuestaDetalleDescuentos() {
        documentoDian();
    }

    @Override
    public void respuestaDocumentoDian() {
        documentoTef();
    }

    @Override
    public void respuestaDocumentoTef() {
        descontarCupoEmpleado();
    }

    @Override
    public void llenarDescuentosReferido(String descuento) {
        cliente.setDescuentoReferido(descuento);
    }

    @Override
    public void respuestaConsultaCupoEmpleado(double cupo, String empresaTemporal) {
        double totalPagar = Double.parseDouble(Objects.requireNonNull(SPM.getString(Constantes.TOTAL_A_PAGAR)));
        cliente.setCupoEmpleado(cupo);
        cliente.setEmpresaTemporal(empresaTemporal);
        if(totalPagar > cupo && !validarEmpresaTemporales(empresaTemporal)){
            volverPreguntarMedioPago(true);
        }
    }

    private void enviarDescuentos(){
        factura.setDetalleDescuentos(factura.construirDescuentos());
        if(!factura.getDetalleDescuentos().isEmpty()){
            presentador.enviarDescuentos(factura);
        }else{
            documentoDian();
        }
    }

    public void sweetAlertCupoEmpleado(String titulo, String mensaje, int icono, Context contexto){
        VistaMsjCustomDosAccionesDialogFragment msjCustom =
                new VistaMsjCustomDosAccionesDialogFragment(R.drawable.informacion_cupo,
                        titulo,
                        mensaje,
                        "",
                        getResources().getString(R.string.flecha_continuar),
                        getResources().getString(R.string.flecha_salir),
                        Constantes.ACCION_CUPO_EMPLEADO,
                        false
                        );
        msjCustom.show(getSupportFragmentManager(), "MsjCustomTwoDialogFragment");
        msjCustom.setVistaMediosPago(this);
    }

    public void regresarConsultaCliente(){
        Intent intent = new Intent(contexto, VistaConsultaCliente.class);
        startActivity(intent);
        finish();
    }

    public void mostrarDescuentosMp(){
        presentador.mostrarDescuentoMedioPago(cliente);
    }

    private void documentoDian(){
        if(xmlDian){
            presentador.enviarDocumentoDian(factura);
        }else{
            documentoTef();
        }
    }

    private void documentoTef(){
        factura.setConfiguracion(false);
        factura.setTotalCompra(presentador.calcularTotal(listaProductos));
        factura.setSubtotal(util.calcularSubtotal(listaProductos));
        factura.setDescuentoTotal(util.calcularDescuentoTotal(listaProductos));
        factura.calcularTotalCompraIva();

        if(factura.isPagoTef()){
            presentador.enviarDocumentoTef(factura);
        }else{
            descontarCupoEmpleado();
        }
    }

    private void descontarCupoEmpleado(){
        if(cliente.getCupoEmpleado() != 0.0){
            if(validarEmpresaTemporales(cliente.getEmpresaTemporal())){
                guardarRespuestaTef();
            }else{
                presentador.actualizarCupoEmpleado(cliente);
            }
        }else{
            guardarRespuestaTef();
        }
    }

    private void guardarRespuestaTef(){
        if(presentador.estadoProgress()){
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
        }
        if(factura.isPagoTef() && factura.getRespuestaDatafono() != null){
            presentador.guardarRespuestaTef(factura);
        }else{
            expirarCodigoReferido();
        }
    }

    private void expirarCodigoReferido(){
        if(isReferido){
            presentador.expirarCodigoReferido();
        }else{
            extraerInfoDocumento();
        }
    }

    private boolean validarEmpresaTemporales(String empresaCliente){
        boolean temporal = false;
        String[] empresas = SPM.getString(Constantes.PARAM_EMPRESA_TEMPORALES).split(";");
        for(String empresa: empresas){
            if(empresa.equals(empresaCliente)){
                temporal = true;
                break;
            }
        }

        return temporal;
    }

    @Override
    public void respuestaActualizarCupoEmpleado() {
        guardarRespuestaTef();
    }

    @Override
    public void respuestaExpirarCodigoReferido() {
        extraerInfoDocumento();
    }

    @Override
    public void respuestaDatafono(RespuestaDatafono datafono) {
        factura.setRespuestaDatafono(datafono);

        presentador.dialogProgressBar(getResources().getString(R.string.progress_procesando_compra_exitosa),
                false);
        presentador.crearDocumento(factura);
    }

    @Override
    public void respuestaGuardarResTef() {
        expirarCodigoReferido();
    }

    private void extraerInfoDocumento(){
        if(presentador.estadoProgress()){
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
        }

        presentador.extraerInfoDocumento(factura);
    }

    @Override
    public void respuestaGuardarTrazaFE() {
        imprimirTirilla();
    }

    private void imprimirTirilla() {
        presentador.ocultarDialogProgressBar();
        factura.setCliente(cliente);
        Intent intent = new Intent(contexto, VistaImpresionTirilla.class);
        intent.putExtra(getResources().getString(R.string.key_intent_factura), factura);

        ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE,
                ImpresoraDevice.class);

        if(impresoraDevice.isConnected()){
            factura.setImpresora(getResources().getString(R.string.bixolon));
        }else{
            factura.setImpresora(getResources().getString(R.string.epson));
        }

        startActivity(intent);
        finish();
    }

    private void regresarLogin(){
        Intent intent = new Intent(contexto, VistaLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void sendInputListDescuentoTipoDialogFragment(Descuento descuento, boolean referido) {
        medioPago(descuento, referido);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void medioPago(Descuento descuento, boolean referido){
        if(descuento != null){
            if(medioPago != null){
                if(!medioPago.getCodigoMP().equals(descuento.getCodigoMP())){
                    actualizarErrores();
                    seleccionoMedioPago(descuento, referido);
                }
            }else{
                seleccionoMedioPago(descuento, referido);
            }
        }else{
            if(medioPago == null){
                btnAtrasMedioPago.callOnClick();
            }
        }
    }

    private void seleccionoMedioPago(Descuento descuento, boolean referido){
        isReferido = referido;

        seleccionoMp = true;
        medioPago = descuento;
        String palabraClave = medioPago.getCodigo();

        if(cliente.isEmpleado()){
            cliente.setDescuentoPalabraClave(palabraClave);
            cliente.setSeleccionoDescuento(true);
            SPM.setString(Constantes.PALABRA_CLAVE_CLIENTE, palabraClave);
        }else{
            String mensaje = medioPago.getMensaje();
            if(mensaje.contains(Constantes.REFERENCIA_PORCENTAJE)){
                SPM.setString(Constantes.PALABRA_CLAVE_CLIENTE, palabraClave);
            }else{
                SPM.setString(Constantes.PALABRA_CLAVE_CLIENTE, null);
            }
        }

        SPM.setString(Constantes.MEDIO_PAGO_CODIGO, medioPago.getCodigoMP());

        KeyboardUtils.hideKeyboard(this);
        etMedioPago.setText(medioPago.getMensaje());

        if(presentador.estadoProgress()){
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                    false);
            presentador.consultarProductos(Utilidades.eanesConsultar(listaProductos));
        }
    }

    @Override
    public void sendInputVerificacionCodigoDialogFragment(Descuento descuento) {
        medioPago(descuento, false);
        presentador.ocultarDescuentos();
    }
}