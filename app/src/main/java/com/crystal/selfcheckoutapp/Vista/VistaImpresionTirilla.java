package com.crystal.selfcheckoutapp.Vista;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentContainerView;

import com.bixolon.commonlib.connectivity.searcher.BXLUsbDevice;
import com.bixolon.mpos.MPosControllerConfig;
import com.bxl.config.editor.BXLConfigLoader;
import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaImpresionTirilla;
import com.crystal.selfcheckoutapp.Modelo.clases.ImpresoraDevice;
import com.crystal.selfcheckoutapp.Modelo.clases.ClaveValor;
import com.crystal.selfcheckoutapp.Modelo.clases.DescuentoSimple;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.common.BluetoothImpresora;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.impresora.ExcepcionImpresion;
import com.crystal.selfcheckoutapp.Modelo.impresora.IManejadorImpresora;
import com.crystal.selfcheckoutapp.Modelo.impresora.ManejadorImpresoraBixolon;
import com.crystal.selfcheckoutapp.Modelo.impresora.ManejadorImpresoraPredeterminado;
import com.crystal.selfcheckoutapp.Modelo.impresora.SettingsHelper;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;
import com.crystal.selfcheckoutapp.Presentador.PresentadorImpresionTirilla;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaDeviceBluetoothSelectDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaImpresionTirilla extends AppCompatActivity implements VistaDeviceBluetoothSelectDialogFragment.OnInputListener,
        IVistaImpresionTirilla.Vista,
        AdapterView.OnItemClickListener,
        View.OnTouchListener{

    //ZEBRA Impresora
    private static final String bluetoothAddressKey = "ZEBRA_DEMO_BLUETOOTH_ADDRESS";
    private static final String tcpAddressKey = "ZEBRA_DEMO_TCP_ADDRESS";
    private static final String tcpPortKey = "ZEBRA_DEMO_TCP_PORT";
    private static final String PREFS_NAME = "OurSavedAddress";
    private static final String ACTION_USB_PERMISSION = "com.example.USB_PERMISSION";
    private final ArrayList<CharSequence> bondedDevices = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;
    private UsbManager usbManager;
    private UsbDevice device;

    private ListView listaBixolon;
    private Factura factura;
    private Context contexto;
    private String  mac, ip, puerto;
    private Utilidades util;
    private ImageView laLogoCompraRealizada, laEnojadoCalificacion, laNeutralCalificacion, laFelizCalificacion;
    private TextView tvTituloGraciasCompra, tvEstatusImp;
    private FragmentContainerView fragmentLogoMarca, fragmentProcesoImpresion;
    private RadioGroup rgOpcionesImpresion;
    private RadioButton rbBluetoothConeccion, rbIpDnsConeccion;
    private EditText etDireccionIp, etPuerto, etMac;
    private TextInputLayout tliDireccionIp, tliPuerto, tliMac;
    private IManejadorImpresora impresora;
    private BluetoothImpresora coneccionGlobal;
    private BluetoothAdapter mBtAdapter;
    private boolean cambioImp, reImprimir, errorBixolon, calificacionEnojado, calificacionNeutral, calificacionFeliz;
    private Button btnSalirImpresion, btnImprimir;
    private Cliente cliente;
    private AperturaCaja aperturaCaja;
    private Timer tiempo, tiempo2;
    private TimerTask tarea, tarea2;
    private MPosControllerConfig mPosControllerConfig;
    private CardView cvTirillaImpresion;
    private Button btnVolverInicio, btnCertificadoRegalo;
    private ImageView ivEnojadoCalificacion, ivNeutralCalificacion, ivFelizCalificacion;
    private TextView tvTituloGraciasCalificacion;
    private IVistaImpresionTirilla.Presentador presentador;
    private ClaveValor claveValor;
    private boolean generarCertificadoAuto;
    private TextView tvTituloSelecionBixolon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPM.setInt(Constantes.PROCESO_ACTIVITY, Constantes.VISTA_IMPRESION);
        setContentView(R.layout.vista_impresion_tirilla);

        LogFile.adjuntarLogTitulo("Abriendo [Pantalla Impresión Tirilla]");

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

        if(!SPM.getBoolean(Constantes.CONFIG_IMPRESORA_BIXOLON)){
            ImpresoraDevice impresoraDevice = new ImpresoraDevice(getResources().getString(R.string.bixolon),
                    "", "", "", 0,false);
            SPM.setObject(Constantes.OBJECT_BIXOLON_DEVICE, impresoraDevice);
        }

        if(!SPM.getBoolean(Constantes.CONFIG_IMPRESORA_EPSON)){
            ImpresoraDevice impresoraDevice = new ImpresoraDevice(getResources().getString(R.string.epson),
                    "", "", "", 0,false);
            SPM.setObject(Constantes.OBJECT_EPSON_DEVICE, impresoraDevice);
        }

        if(factura.getImpresora().equals(getResources().getString(R.string.bixolon))){
            configuracionBixolon();
        }else{
            configuracionEpson();
        }
    }

    private void iniciarTarea() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                volverInicio(null);
            }
        };
    }

    private void volverInicio(View view){
        if(calificacionEnojado){
            ingresarCalificacion(Constantes.CALIFICACION_ENOJADO);
        }else if(calificacionNeutral){
            ingresarCalificacion(Constantes.CALIFICACION_NEUTRAL);
        }else if(calificacionFeliz){
            ingresarCalificacion(Constantes.CALIFICACION_FELIZ);
        }else{
            inicio();
        }
    }

    private void ingresarCalificacion(int calificacion){
        factura.getCliente().setCalificacion(calificacion);
        presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                false);
        presentador.ingresarCalificacion(factura);
    }

    private void inicio(){
        Intent intent = new Intent(contexto, VistaConsultaCliente.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tarea != null){
            tarea.cancel();
        }

        if(tiempo != null){
            tiempo.cancel();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void inicializar() {
        contexto = VistaImpresionTirilla.this;
        factura = new Factura();
        claveValor = new ClaveValor("Anulacion", "");
        util = new Utilidades(contexto);

        presentador = new PresentadorImpresionTirilla(this, getSupportFragmentManager(), contexto);
        generarCertificadoAuto = SPM.getBoolean(Constantes.PARAM_GENERAR_CERTIFICADO_AUTOMATICO);

        laLogoCompraRealizada = findViewById(R.id.laLogoCompraRealizada);
        cvTirillaImpresion = findViewById(R.id.cvTirillaImpresion);
        tvTituloGraciasCompra = findViewById(R.id.tvTituloGraciasCompra);
        fragmentLogoMarca = findViewById(R.id.fragmentLogoMarca);
        btnVolverInicio = findViewById(R.id.btnVolverInicio);
        btnCertificadoRegalo = findViewById(R.id.btnCertificadoRegalo);

        rgOpcionesImpresion = findViewById(R.id.rgOpcionesImpresion);
        rbBluetoothConeccion = findViewById(R.id.rbBluetoothConeccion);
        rbIpDnsConeccion = findViewById(R.id.rbIpDnsConeccion);
        etDireccionIp = findViewById(R.id.etDireccionIp);
        etPuerto = findViewById(R.id.etPuerto);
        etMac = findViewById(R.id.etMac);
        tliDireccionIp = findViewById(R.id.tliDireccionIp);
        tliPuerto = findViewById(R.id.tliPuerto);
        tliMac = findViewById(R.id.tliMac);
        tvEstatusImp = findViewById(R.id.tvEstatusImp);
        btnSalirImpresion = findViewById(R.id.btnSalirImpresion);
        btnImprimir = findViewById(R.id.btnImprimir);
        fragmentProcesoImpresion = findViewById(R.id.fragmentProcesoImpresion);

        //Referencia calificación
        laEnojadoCalificacion = findViewById(R.id.laEnojadoCalificacion);
        laNeutralCalificacion = findViewById(R.id.laNeutralCalificacion);
        laFelizCalificacion = findViewById(R.id.laFelizCalificacion);

        ivEnojadoCalificacion = findViewById(R.id.ivEnojadoCalificacion);
        ivNeutralCalificacion = findViewById(R.id.ivNeutralCalificacion);
        ivFelizCalificacion = findViewById(R.id.ivFelizCalificacion);

        tvTituloGraciasCalificacion = findViewById(R.id.tvTituloGraciasCalificacion);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, bondedDevices);
        listaBixolon = findViewById(R.id.listViewPairedDevices);
        listaBixolon.setAdapter(arrayAdapter);

        listaBixolon.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listaBixolon.setOnItemClickListener(this);
        listaBixolon.setOnTouchListener(this);

        tvTituloSelecionBixolon = findViewById(R.id.tvTituloSelecionBixolon);

        if(SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE, ImpresoraDevice.class) == null ){
            SPM.setBoolean(Constantes.CONFIG_IMPRESORA_BIXOLON, false);
        }

        if(SPM.getObject(Constantes.OBJECT_EPSON_DEVICE, ImpresoraDevice.class) == null ){
            SPM.setBoolean(Constantes.CONFIG_IMPRESORA_EPSON, false);
        }
    }

    private void eventos() {
        etMac.setOnClickListener(this::seleccionarMac);
        btnSalirImpresion.setOnClickListener(this::regresarConfiguracion);
        btnImprimir.setOnClickListener(this::imprimir);

        rgOpcionesImpresion.setOnCheckedChangeListener((group, checkedId) -> {
            if(isRbBluetooth()){
                etDireccionIp.setEnabled(false);
                etPuerto.setEnabled(false);
                etMac.setEnabled(true);
            }else{
                etDireccionIp.setEnabled(true);
                etPuerto.setEnabled(true);
                etMac.setEnabled(false);
            }

            disconnect();
        });

        etDireccionIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    SPM.setString(Constantes.IMPRESORA_IP, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPuerto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    SPM.setString(Constantes.IMPRESORA_PUERTO, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    SPM.setString(Constantes.IMPRESORA_MAC, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMac.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                seleccionarMac(view);
            }
        });

        ivEnojadoCalificacion.setOnClickListener(v -> marcarCalificacion(Constantes.CALIFICACION_ENOJADO));
        ivNeutralCalificacion.setOnClickListener(v -> marcarCalificacion(Constantes.CALIFICACION_NEUTRAL));
        ivFelizCalificacion.setOnClickListener(v -> marcarCalificacion(Constantes.CALIFICACION_FELIZ));

        btnVolverInicio.setOnClickListener(this::volverInicio);
        btnCertificadoRegalo.setOnClickListener(this::certificadoRegalo);
    }

    private void marcarCalificacion(int marcar){
        // Establece la visibilidad predeterminada
        int laEnojadoVisibility = View.INVISIBLE;
        int laNeutralVisibility = View.INVISIBLE;
        int laFelizVisibility = View.INVISIBLE;

        int ivEnojadoVisibility = View.VISIBLE;
        int ivNeutralVisibility = View.VISIBLE;
        int ivFelizVisibility = View.VISIBLE;

        calificacionEnojado = false;
        calificacionNeutral = false;
        calificacionFeliz = false;

        switch (marcar) {
            case Constantes.CALIFICACION_ENOJADO:
                calificacionEnojado = true;
                laEnojadoVisibility = View.VISIBLE;
                ivEnojadoVisibility = View.INVISIBLE;
                break;
            case Constantes.CALIFICACION_NEUTRAL:
                calificacionNeutral = true;
                laNeutralVisibility = View.VISIBLE;
                ivNeutralVisibility = View.INVISIBLE;
                break;
            case Constantes.CALIFICACION_FELIZ:
                calificacionFeliz = true;
                laFelizVisibility = View.VISIBLE;
                ivFelizVisibility = View.INVISIBLE;
                break;
        }

        // Establece la visibilidad para todas las vistas
        laEnojadoCalificacion.setVisibility(laEnojadoVisibility);
        laNeutralCalificacion.setVisibility(laNeutralVisibility);
        laFelizCalificacion.setVisibility(laFelizVisibility);

        ivEnojadoCalificacion.setVisibility(ivEnojadoVisibility);
        ivNeutralCalificacion.setVisibility(ivNeutralVisibility);
        ivFelizCalificacion.setVisibility(ivFelizVisibility);

        // Resto del código
        tvTituloGraciasCalificacion.setVisibility(View.VISIBLE);
    }

    private void seleccionarMac(View view){
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Utilidades.mjsToast(getResources().getString(R.string.bluetooth_error_adapter),
                    Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, contexto);
        } else {
            if (mBtAdapter.isEnabled()) {
                VistaDeviceBluetoothSelectDialogFragment dialogSelectDeviceBluetooth = new VistaDeviceBluetoothSelectDialogFragment();
                dialogSelectDeviceBluetooth.show(getSupportFragmentManager(), "SelectDeviceBluetooth");
            } else {
                Intent enableBtIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,
                        Constantes.RESP_ACT_BLUE_SELECT);
            }
        }
    }

    private void disconnect() {
        reImprimir = true;
        try {
            if(impresora != null){
                setEstatus(getResources().getString(R.string.desconectando), Color.MAGENTA);
                impresora.desconectar();
                setEstatus(getResources().getString(R.string.no_conectado), Color.RED);
            }
        } catch (ExcepcionImpresion e) {
            setEstatus(getResources().getString(R.string.error_desconectada), Color.RED);
        }
    }

    private boolean isRbBluetooth() {
        return rbBluetoothConeccion.isChecked();
    }

    private void cargarIntents() {
        //Intent info cliente
        factura = (Factura) getIntent().getSerializableExtra(getResources().getString(R.string.key_intent_factura));

        assert factura != null;
        if(factura.isConfiguracion()){
            habilitarConfiguracion();
        }else if(!factura.getLabelImprimir().isEmpty()){
            deshabilitarConfiguracion();
        }else{
            cliente = factura.getCliente();
            aperturaCaja = factura.getAperturaCaja();
        }
    }

    private void deshabilitarConfiguracion() {
        laLogoCompraRealizada.setVisibility(View.GONE);
        cvTirillaImpresion.setVisibility(View.GONE);
        tvTituloGraciasCompra.setVisibility(View.GONE);
        fragmentLogoMarca.setVisibility(View.GONE);
        fragmentProcesoImpresion.setVisibility(View.GONE);
        tliMac.setVisibility(View.GONE);

        tvEstatusImp.setVisibility(View.VISIBLE);
        btnSalirImpresion.setVisibility(View.VISIBLE);
        btnImprimir.setVisibility(View.VISIBLE);
    }

    private void habilitarConfiguracion() {
        laLogoCompraRealizada.setVisibility(View.GONE);
        cvTirillaImpresion.setVisibility(View.GONE);
        tvTituloGraciasCompra.setVisibility(View.GONE);
        fragmentLogoMarca.setVisibility(View.GONE);
        fragmentProcesoImpresion.setVisibility(View.GONE);

        if(factura.getImpresora().equals(getResources().getString(R.string.bixolon))){
            vistaBixolon();
        }else{
            vistaEpson();
        }

        tvEstatusImp.setVisibility(View.VISIBLE);
        btnSalirImpresion.setVisibility(View.VISIBLE);
        btnImprimir.setVisibility(View.VISIBLE);
    }

    private void vistaBixolon(){

    }

    private void vistaEpson(){
        rgOpcionesImpresion.setVisibility(View.VISIBLE);

        if(factura.isBluetooth()){
            rbBluetoothConeccion.setChecked(true);
        }else{
            rbIpDnsConeccion.setChecked(true);
        }

        tliDireccionIp.setVisibility(View.VISIBLE);
        tliPuerto.setVisibility(View.VISIBLE);
        tliMac.setVisibility(View.VISIBLE);
    }

    private void configuracionBixolon() {
        try {
            if(factura.isConfiguracion()){
                tliMac.setVisibility(View.GONE);
                tvTituloSelecionBixolon.setVisibility(View.VISIBLE);
                listaBixolon.setVisibility(View.VISIBLE);

                setUSBDevice();
            }else{
                inicializarBixolon();
            }
        } catch (Exception ex) {
            errorBixolon = true;
            setEstatus("Device open fail exception: " + ex.getMessage(), Color.RED);
        }
    }

    private void inicializarBixolon(){
        try {
            ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE, ImpresoraDevice.class);

            if(impresoraDevice != null){
                impresora = new ManejadorImpresoraBixolon(contexto);
                boolean abrir = impresora.abrirConexion(BXLConfigLoader.DEVICE_BUS_USB, impresoraDevice.getLogicalName(),
                        impresoraDevice.getAddress(), true);

                if(abrir){
                    errorBixolon = false;
                    setEstatus("Impresora conectada", Color.GREEN);
                    SPM.setBoolean(Constantes.CONFIG_IMPRESORA_BIXOLON, true);
                    cambiarBanderaImpresora(getResources().getString(R.string.bixolon), false);

                    if(factura.isConfiguracion()){
                        imprimirPruebaBixolon();
                        setBtnImprimir(getResources().getString(R.string.re_imprimir));
                    }else if(!factura.getLabelImprimir().isEmpty()){
                        switch (factura.getLabelImprimir()){
                            case Constantes.LABEL_CLAVE_VALOR:
                                imprimirClaveValorBixolon();
                                setBtnImprimir(getResources().getString(R.string.re_imprimir));
                                break;
                            case Constantes.LABEL_CERTIFICADO_REGALO:
                                imprimirCertificadoRegaloBixolon();
                                break;
                        }
                    }else{
                        imprimirFacturaBixolon();
                    }
                }else{
                    errorBixolon = true;
                    setEstatus("Device open fail", Color.RED);
                    cambiarBanderaImpresora(getResources().getString(R.string.bixolon), true);
                }
            }
        } catch (Exception ex) {
            errorBixolon = true;
            setEstatus("Device open fail exception: " + ex.getMessage(), Color.RED);
        }
    }

    private void setUSBDevice() {
        String[] items;
        int index = 0;
        bondedDevices.clear();

        Set<UsbDevice> usbDevices = BXLUsbDevice.refreshUsbDevicesList(this, false);
        items = new String[usbDevices.size()];
        if (usbDevices != null && !usbDevices.isEmpty()) {
            for (UsbDevice device : usbDevices) {
                items[index] = device.getProductName() + " (" + device.getDeviceName() + ")";
                bondedDevices.add(items[index++]);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Can't found USB devices. ", Toast.LENGTH_SHORT).show();
        }

        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }

        ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE, ImpresoraDevice.class);

        if(!bondedDevices.isEmpty() && impresoraDevice != null){
            for(CharSequence item : bondedDevices){
                if(item.toString().equals(impresoraDevice.getDevice())){
                    listaBixolon.post(() -> listaBixolon.performItemClick(
                            listaBixolon.getAdapter().getView(impresoraDevice.getPosition(), null, null),
                            impresoraDevice.getPosition(),
                            listaBixolon.getAdapter().getItemId(impresoraDevice.getPosition())
                    ));
                }
            }
        }

        mHandler.obtainMessage(1).sendToTarget();
    }

    public final Handler mHandler = new Handler(msg -> {
        switch (msg.what) {
            case 0:
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                break;
            case 1:
                String data = (String) msg.obj;
                if (data != null && !data.isEmpty()) {
                    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                }
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                break;
        }
        return false;
    });

    private void configuracionEpson(){
        //Obteniendo las variable a usar
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        mac = settings.getString(bluetoothAddressKey, "");
        ip = settings.getString(tcpAddressKey, "10.144."+util.sacarOcteno(factura.getTienda())+".150");
        puerto = settings.getString(tcpPortKey, "9100");
        cambioImp = false;

        String ipImpresora = SPM.getString(Constantes.IMPRESORA_IP);
        String puertoImpresora = SPM.getString(Constantes.IMPRESORA_PUERTO);
        String macImpresora = SPM.getString(Constantes.IMPRESORA_MAC);

        if(ipImpresora != null){
            etDireccionIp.setText(ipImpresora);
        }else{
            etDireccionIp.setText(ip);
        }

        if(puertoImpresora != null){
            etPuerto.setText(puertoImpresora);
        }else{
            etPuerto.setText(puerto);
        }

        if(macImpresora != null){
            etMac.setText(macImpresora);
        }else{
            etMac.setText(mac);
        }

        //Obtener la conexion global para bluetooth
        coneccionGlobal = BluetoothImpresora.getInstance(getMac());

        validarTipoConexion();
    }

    private void validarTipoConexion() {
        if(factura.isBluetooth()){
            try {
                if(impresora != null){
                    impresora.getConnection();
                }
            } catch (ExcepcionImpresion excepcionImpresion) {
                excepcionImpresion.printStackTrace();
            }
            //Validar que tenga una mac previamente selecionada para realizar la impresion
            if (mac.length() > 0) {
                conectarImpresora();
            }
            etPuerto.setEnabled(false);
            etDireccionIp.setEnabled(false);
        }else{
            rbIpDnsConeccion.setChecked(true);
            rbBluetoothConeccion.setChecked(false);
            etMac.setEnabled(false);
            if (etDireccionIp.getText().length() > 0 && etPuerto.getText().length() > 0) {
                conectarImpresora();
            }
        }
    }

    private void conectarImpresora() {
        new Thread(() -> {
            Looper.prepare();
            conectar();
            Looper.loop();
            Looper.myLooper().quit();
        }).start();
    }

    private void conectar() {
        if (isRbBluetooth()) {
            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBtAdapter == null) {
                Utilidades.mjsToast(getResources().getString(R.string.bluetooth_error_adapter),
                        Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, contexto);
            } else {
                if (mBtAdapter.isEnabled()) {
                    if (impresora == null) {
                        impresora = new ManejadorImpresoraPredeterminado(getMac(), "", 0, true);
                        impresora.inicializar();
                        SettingsHelper.saveBluetoothAddress(this, getMac());
                    } else {
                        if (cambioImp) {
                            setEstatus(getResources().getString(R.string.desconectando), Color.MAGENTA);
                            try {
                                impresora.desconectar();
                            } catch (ExcepcionImpresion e) {
                                setEstatus(getResources().getString(R.string.error_desconectada), Color.RED);
                            }
                            impresora = new ManejadorImpresoraPredeterminado(getMac(), "", 0, true);
                            impresora.inicializar();
                            SettingsHelper.saveBluetoothAddress(this, getMac());
                        }
                    }
                    abrirConexion();
                } else {
                    setEstatus(getResources().getString(R.string.bluetooth_apagado), Color.RED);
                    Intent enableBtIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent,
                            Constantes.RESP_ACT_BLUE_PRINT);
                }
            }
        } else {
            try {
                int port = Integer.parseInt(getPuerto());
                impresora = new ManejadorImpresoraPredeterminado("", getIp(), port, false);
                impresora.inicializar();

                SettingsHelper.saveIp(this, getIp());
                SettingsHelper.savePort(this, getPuerto());
                abrirConexion();
            } catch (NumberFormatException e) {
                setEstatus(getResources().getString(R.string.puerto_invalido), Color.RED);
            }
        }
    }

    public void abrirConexion() {
        setEstatus(getResources().getString(R.string.conectado___), Color.YELLOW);
        try {
            impresora.abrirConexion();

            setEstatus(getResources().getString(R.string.imprimiendo___), Color.YELLOW);
            if (impresora != null) {
                if (impresora.isConnected()) {
                    cambiarBanderaImpresora(getResources().getString(R.string.epson), false);
                    SPM.setBoolean(Constantes.CONFIG_IMPRESORA_EPSON, true);

                    runOnUiThread(() -> btnSalirImpresion.setEnabled(false));

                    if(factura.isConfiguracion()){
                        imprimirPruebaEpson();
                        setBtnImprimir(getResources().getString(R.string.re_imprimir));
                    }else if(!factura.getLabelImprimir().isEmpty()){
                        switch (factura.getLabelImprimir()){
                            case Constantes.LABEL_CLAVE_VALOR:
                                imprimirClaveValor();
                                setBtnImprimir(getResources().getString(R.string.re_imprimir));
                                break;
                            case Constantes.LABEL_CERTIFICADO_REGALO:
                                imprimirCertificadoRegalo();
                                break;
                        }
                    }else{
                        imprimirFactura();
                    }

                    runOnUiThread(() -> btnSalirImpresion.setEnabled(true));
                    reImprimir = true;

                    if (isRbBluetooth()) {
                        if (impresora != null) {
                            setEstatus(getResources().getString(R.string.impresion_realizada), Color.GREEN);
                        }
                    } else {
                        if (impresora != null) {
                            reImprimir = true;
                            impresora.desconectar();
                            setEstatus(getResources().getString(R.string.impresion_realizada), Color.GREEN);
                        }
                    }
                } else {
                    cambiarBanderaImpresora(getResources().getString(R.string.epson), true);
                    setEstatus(getResources().getString(R.string.error_desconectada), Color.RED);
                    impresora.desconectar();
                    disconnect();
                }
            } else {
                cambiarBanderaImpresora(getResources().getString(R.string.epson), true);
                setEstatus(getResources().getString(R.string.error_desconectada), Color.RED);
                disconnect();
            }
        } catch (Exception e) {
            cambiarBanderaImpresora(getResources().getString(R.string.epson), true);

            if (isRbBluetooth()) {
                Utilidades.mjsToast(getResources().getString(R.string.rivise_dispositivo_bluetooth),
                        Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            } else {
                Utilidades.mjsToast(getResources().getString(R.string.revise_ip_puerto),
                        Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            }
            disconnect();
        }
    }

    //Mostrar un en barra inferior el estatus de la conexion al dispositivo
    private void setEstatus(final String statusMessage, final int color) {
        if (color == Color.RED) {
            reImprimir = true;
        }

        runOnUiThread(() -> {
            tvEstatusImp.setBackgroundColor(color);
            tvEstatusImp.setText(statusMessage);
        });
    }

    @Override
    public void sendInputListSelectDeviceBluetoothDialogFragment(String mac) {
        etMac.setText(mac);
        cambioImp = true;
        reImprimir = true;
        setEstatus(getResources().getString(R.string.pulse_impresion), Color.RED);
    }

    @Override
    public void respuestaCalificacion() {
        inicio();
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        presentador.ocultarDialogProgressBar();
        SweetAlertDialog pDialog = new SweetAlertDialog(contexto, SweetAlertDialog.ERROR_TYPE);

        pDialog.setTitleText(titulo)
                .setContentText(mensaje)
                .setConfirmButton(contexto.getResources().getString(R.string.entiendo), sweetAlertDialog -> {
                    if(servicio == Constantes.SERVICIO_CALIFICACION_NPS){
                        inicio();
                    }
                })
                .setCancelable(false);

        pDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String device = ((TextView) view).getText().toString();
        String DEVICE_ADDRESS_START = " (";
        String DEVICE_ADDRESS_END = ")";
        String logicalName = device.substring(0, device.indexOf(' '));
        String address = device.substring(device.indexOf(DEVICE_ADDRESS_START)
                + DEVICE_ADDRESS_START.length(), device.indexOf(DEVICE_ADDRESS_END));

        ImpresoraDevice impresoraDevice = new ImpresoraDevice(getResources().getString(R.string.bixolon),
                logicalName, address, device, i,false);
        SPM.setObject(Constantes.OBJECT_BIXOLON_DEVICE, impresoraDevice);

        if(!logicalName.equals(Constantes.LOGICAL_NAME_SRPQ302)){
            Utilidades.sweetAlert(getResources().getString(R.string.atencion),
                    "No se puede imprimir con un dispositivo diferente al "+Constantes.LOGICAL_NAME_SRPQ302
                    + ", Contacte al administrador.",
                    SweetAlertDialog.WARNING_TYPE, contexto);
        }else{
            inicializarBixolon();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        listaBixolon.requestDisallowInterceptTouchEvent(motionEvent.getAction() != MotionEvent.ACTION_UP);
        return false;
    }

    public static class ByteHelper {
        public static byte commandByteRepresentation(final int command) {
            final byte[] bytes = ByteBuffer
                    .allocate(4)
                    .putInt(command)
                    .array();
            return bytes[3];
        }
    }

    public void cambiarBanderaImpresora(String impresora, boolean error){
        if(error){
            if(impresora.equals(getResources().getString(R.string.bixolon))){
                ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE, ImpresoraDevice.class);
                impresoraDevice.setConnected(false);
                SPM.setObject(Constantes.OBJECT_BIXOLON_DEVICE, impresoraDevice);
            }else{
                ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_EPSON_DEVICE, ImpresoraDevice.class);
                impresoraDevice.setConnected(false);
                SPM.setObject(Constantes.OBJECT_EPSON_DEVICE, impresoraDevice);
            }
        }else{
            if(impresora.equals(getResources().getString(R.string.bixolon))){
                ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE, ImpresoraDevice.class);
                impresoraDevice.setConnected(true);
                SPM.setObject(Constantes.OBJECT_BIXOLON_DEVICE, impresoraDevice);

                impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_EPSON_DEVICE, ImpresoraDevice.class);
                impresoraDevice.setConnected(false);
                SPM.setObject(Constantes.OBJECT_EPSON_DEVICE, impresoraDevice);
            }else{
                ImpresoraDevice impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_EPSON_DEVICE, ImpresoraDevice.class);
                impresoraDevice.setConnected(true);
                SPM.setObject(Constantes.OBJECT_EPSON_DEVICE, impresoraDevice);

                impresoraDevice = (ImpresoraDevice) SPM.getObject(Constantes.OBJECT_BIXOLON_DEVICE, ImpresoraDevice.class);
                impresoraDevice.setConnected(false);
                SPM.setObject(Constantes.OBJECT_BIXOLON_DEVICE, impresoraDevice);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Utilidades.mjsToast("Bluetooth activado", Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);

            if (requestCode == Constantes.RESP_ACT_BLUE_PRINT) {
                cambioImp = true;
                conectarImpresora();
            }else{
                VistaDeviceBluetoothSelectDialogFragment dialogSelectDeviceBluetooth = new VistaDeviceBluetoothSelectDialogFragment();
                dialogSelectDeviceBluetooth.show(getSupportFragmentManager(), "SelectDeviceBluetooth");
            }
        } else {
            if(factura.isConfiguracion()){
                regresarConfiguracion(null);
                if(factura.getImpresora().equals(getResources().getString(R.string.epson))){
                    Utilidades.mjsToast(getResources().getString(R.string.activat_bluetooth_impr),
                            Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                }
            }
        }
    }

    private void regresarConfiguracion(View view){
        presentador.guardarPerifericos();
    }

    @Override
    public void respuestaGuardarPerifericos(ResponseGuardarPerifericos respuesta) {
        if(respuesta.isEsValida()){
            regresar();
        }else{
            Utilidades.mjsToast(respuesta.getMensaje(), Constantes.TOAST_TYPE_ERROR,
                    Toast.LENGTH_LONG, contexto);
            regresar();
        }
    }

    public void regresar(){
        if(!factura.getLabelImprimir().isEmpty()){
            if (factura.getLabelImprimir().equals(Constantes.LABEL_CLAVE_VALOR)) {
                if (factura.getActivity() == Constantes.VISTA_CONSULTA_CLIENTE) {
                    Intent intent = new Intent(contexto, VistaConsultaCliente.class);
                    intent.putExtra(getResources().getString(R.string.key_intent_ultima_tef), claveValor);
                    startActivity(intent);
                    finish();
                }
            }
        }else{
            finish();
        }
    }

    //Obtener texto dMac
    private String getMac() {
        return etMac.getText().toString();
    }

    //Obtener texto Puerto
    private String getPuerto() {
        return etPuerto.getText().toString();
    }

    //Obtener texto IP
    private String getIp() {
        return etDireccionIp.getText().toString();
    }

    public void setBtnImprimir(String btntitulo) {
        runOnUiThread(() -> btnImprimir.setText(btntitulo));
    }

    private void imprimir(View view){
        if(factura.getImpresora().equals(getResources().getString(R.string.bixolon))){
            if(errorBixolon){
                configuracionBixolon();
            }else{
                imprimirPruebaBixolon();
            }
        }else{
            if (isRbBluetooth()) {
                if (etMac.getText().length() > 0) {
                    if (reImprimir) {
                        //Ir a la conexion e impresion
                        conectarImpresora();
                    } else {
                        Utilidades.mjsToast(getResources().getString(R.string.proceso_curso),
                                Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                    }
                } else {
                    reImprimir = true;
                    Utilidades.mjsToast(getResources().getString(R.string.selec_impresora),
                            Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                }
            } else {
                if (etDireccionIp.getText().length() > 0 && etPuerto.getText().length() > 0) {
                    if (reImprimir) {
                        reImprimir = false;
                        //Ir a la conexion e impresion
                        conectarImpresora();
                    } else {
                        Utilidades.mjsToast(getResources().getString(R.string.proceso_curso),
                                Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                    }
                } else {
                    reImprimir = true;
                    Utilidades.mjsToast(getResources().getString(R.string.introducir_puerto_ip),
                            Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                }
            }
        }
    }

    private void imprimirPruebaBixolon(){
        try{
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto("Crystal S.A.S", 1, true, false);
            impresora.saltarLinea();
            impresora.imprimirCodigoBarras("PRUEBA-OK", 3, 100, IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto("PRUEBA-OK", 1, true, false);
            impresora.saltarLinea();
            impresora.imprimirQR("*Prueba Correcta*", 4, 120, IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto("*Prueba Correcta*", 1, false, false);
            impresora.saltarLinea();

            impresora.cortarPapel();

            LogFile.adjuntarLog("imprimirPruebaBixolon", "Impresión realizada");
        }catch (Exception ex){
            errorBixolon = true;
            LogFile.adjuntarLog("imprimirPruebaBixolon Error", ex.fillInStackTrace());
            setEstatus("Device not opened: "+ex.getMessage(), Color.RED);
        }
    }

    private void imprimirPruebaEpson() {
        try {
            impresora.abrirCajon();
            impresora.iniciarImpresion();

            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getNombreEmpresa(), 0, false, false);
            impresora.saltarLinea();

            //CODIGO DE BARRAS
            impresora.imprimirCodigoBarras(getResources().getString(R.string.texto_pruebasCodigoBarras), IManejadorImpresora.SET_WIDTH_ANCHO);
            impresora.imprimirTexto(getResources().getString(R.string.texto_pruebasCodigoBarras), 0, false, false);
            impresora.saltarLinea();

            //Alican codiciones y restricciones
            impresora.imprimirTexto(getResources().getString(R.string.texto_pruebas), 0, false, false);
            impresora.saltarLinea();
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            LogFile.adjuntarLog("imprimirPruebaEpson", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("imprimirPruebaEpson Error", e.fillInStackTrace());
            Utilidades.mjsToast(getResources().getString(R.string.error_impresion_intentar),
                    Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, contexto);
            disconnect();
        }
    }

    private void imprimirFacturaBixolon() {
        try {
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getNombreEmpresa()+"\n" +
                    factura.getNitEmpresa()+"\n", 0 , false, false);
            impresora.imprimirTexto("TIENDA " + factura.getNombreTienda(), 1, true, false);
            impresora.saltarLinea();

            impresora.imprimirTexto("Numero de Factura\n" +
                    factura.getReferenciaInterna() + "\n\n",1 , false, false);

            //Información
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto("Emitida: " + util.getThisDate(), 1 , false, false);
            impresora.saltarLinea();
            impresora.imprimirColumnas(
                    new Object[]{
                            "Tienda: " + factura.getTienda(),
                            "",
                            "Caja No: " + factura.getCaja(),
                            ""
                    },
                    new int[]{
                            8,
                            8,
                            7,
                            9
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX
                    },
                    null, false
            );

            impresora.imprimirTexto("Num. Transaccion: " + factura.getNumeroTransaccion() + "\n" +
                    "Cedula Cliente: " + cliente.getCedula(true) + "\n" +
                    "Nombre Cliente: " + cliente.getNombreCompleto(),1 , false, false);
            impresora.saltarLinea();

            //Encabezado de los productos
            impresora.imprimirSeparador();
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

            impresora.imprimirColumnas(
                    new Object[]{
                            "Cant",
                            "Descripcion",
                            "Valor",
                            "Imp"
                    },
                    new int[]{
                            5,
                            24,
                            12,
                            1
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX
                    },
                    null, false
            );

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirSeparador();
            impresora.saltarLinea();

            //Los productos
            for (Producto p : factura.getProductos()) {

                String pNombre = p.construirNombreProducto(22);

                String pCodigoTasaImpuesto = p.codigoTasaImpuesto();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

                impresora.imprimirColumnas(
                        new Object[]{
                                "",
                                p.getEan(),
                                "",
                                ""
                        },
                        new int[]{
                                5,
                                24,
                                11,
                                2
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX
                        },
                        null, false
                );

                impresora.saltarLinea();

                if (p.getPrecioOriginal().intValue() == 0) {
                    impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                    impresora.imprimirColumnas(
                            new Object[]{
                                    "1x",
                                    pNombre,
                                    Utilidades.formatearPrecio(p.getPrecio()),
                                    "  " + pCodigoTasaImpuesto
                            },
                            new int[]{
                                    5,
                                    24,
                                    11,
                                    2
                            },
                            new int[]{
                                    IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                    IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                    IManejadorImpresora.ALINEACION_DERECHA_BX,
                                    IManejadorImpresora.ALINEACION_DERECHA_BX
                            },
                            null, false
                    );
                } else if (p.getDescuento() != null) {
                    if (p.getPrecioOriginal().equals(p.getPrecioBase())) {
                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "1x",
                                        pNombre,
                                        Utilidades.formatearPrecio(p.getPrecioBase()),
                                        "  " + pCodigoTasaImpuesto
                                },
                                new int[]{
                                        5,
                                        24,
                                        11,
                                        2
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX
                                },
                                null, false
                        );
                    } else {
                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "1x",
                                        pNombre,
                                        Utilidades.formatearPrecio(p.getPrecioOriginal()),
                                        "  " + pCodigoTasaImpuesto
                                },
                                new int[]{
                                        5,
                                        24,
                                        11,
                                        2
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX
                                },
                                null, false
                        );
                        impresora.saltarLinea();

                        factura.calcularPorDescAndTarifaDesc(p.getPrecioOriginal(), p.getPrecioBase());

                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "",
                                        "Dcto. T "  + Utilidades.formatearPrecio(factura.getTarifaDtoUni())
                                                + " -" + factura.getPorcDtoUni().intValue()
                                                + "%: Valor final: "+ Utilidades.formatearPrecio(p.getPrecioBase()),
                                        ""
                                },
                                new int[]{
                                        1,
                                        49,
                                        1
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX
                                },
                                null, false
                        );
                    }
                } else {
                    if (p.getPrecioOriginal().equals(p.getPrecioBase())) {
                        if (!p.getPrecio().equals(p.getPrecioBase())) {
                            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                            impresora.imprimirColumnas(
                                    new Object[]{
                                            "1x",
                                            pNombre,
                                            Utilidades.formatearPrecio(p.getPrecioBase()),
                                            "  " + pCodigoTasaImpuesto
                                    },
                                    new int[]{
                                            5,
                                            24,
                                            11,
                                            2
                                    },
                                    new int[]{
                                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                                            IManejadorImpresora.ALINEACION_DERECHA_BX
                                    },
                                    null, false
                            );
                        } else {
                            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                            impresora.imprimirColumnas(
                                    new Object[]{
                                            "1x",
                                            pNombre,
                                            Utilidades.formatearPrecio(p.getPrecio()),
                                            "  " + pCodigoTasaImpuesto
                                    },
                                    new int[]{
                                            5,
                                            24,
                                            11,
                                            2
                                    },
                                    new int[]{
                                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                                            IManejadorImpresora.ALINEACION_DERECHA_BX
                                    },
                                    null, false
                            );
                        }
                    } else {
                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "1x",
                                        pNombre,
                                        Utilidades.formatearPrecio(p.getPrecioOriginal()),
                                        "  " + pCodigoTasaImpuesto
                                },
                                new int[]{
                                        5,
                                        24,
                                        11,
                                        2
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX
                                },
                                null, false
                        );
                        impresora.saltarLinea();

                        factura.calcularPorDescAndTarifaDesc(p.getPrecioOriginal(), p.getPrecioBase());

                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "",
                                        "Dcto. T "  + Utilidades.formatearPrecio(factura.getTarifaDtoUni() * -1)
                                                + " -" + factura.getPorcDtoUni().intValue()
                                                + "%: Valor final: "+ Utilidades.formatearPrecio(p.getPrecioBase()),
                                        ""
                                },
                                new int[]{
                                        1,
                                        49,
                                        1
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX,
                                        IManejadorImpresora.ALINEACION_DERECHA_BX
                                },
                                null, false
                        );
                    }
                }
            }

            impresora.imprimirSeparador();

            //Totales
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirColumnas(
                    new Object[]{
                            "Subtotal",
                            "",
                            "",
                            Utilidades.formatearPrecio(factura.getSubtotal())
                    },
                    new int[]{
                            21,
                            2,
                            1,
                            20
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX
                    },
                    null, false
            );

            factura.calcularDescuentosSimples();

            //Descuentos
            if(!factura.getDescuentoSimples().isEmpty()){
                for (DescuentoSimple descuentoSimple : factura.getDescuentoSimples()) {
                    impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                    impresora.imprimirColumnas(
                            new Object[]{
                                    descuentoSimple.getNombre(),
                                    "-"+Utilidades.formatearPrecio(descuentoSimple.getValor()),
                                    ""
                            },
                            new int[]{
                                    28,
                                    14,
                                    2
                            },
                            new int[]{
                                    IManejadorImpresora.ALINEACION_DERECHA_BX,
                                    IManejadorImpresora.ALINEACION_DERECHA_BX,
                                    IManejadorImpresora.ALINEACION_DERECHA_BX
                            },
                            null, false
                    );
                    impresora.saltarLinea();
                }

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

                impresora.imprimirColumnas(
                        new Object[]{
                                "Ahorro total",
                                "-"+Utilidades.formatearPrecio(factura.getDescuentoTotal()),
                                ""
                        },
                        new int[]{
                                28,
                                14,
                                2
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX
                        },
                        null, true
                );
                impresora.saltarLinea();

                impresora.imprimirSeparador();
                impresora.saltarLinea();
            }

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.setTamano(2);
            impresora.imprimirColumnas(
                    new Object[]{
                            "TOTAL",
                            "",
                            "",
                            Utilidades.formatearPrecio(factura.getTotalCompra())
                    },
                    new int[]{
                            21,
                            2,
                            1,
                            20
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX
                    },
                    null, true
            );
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

            //medios de pagos
            if (factura.getTotalCompra().intValue() != 0) {
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirColumnas(
                        new Object[]{
                                factura.getMedioPago().getMethodId().equals("C45") ?
                                        factura.getMedioPago().getName().substring(factura.getMedioPago().getName().indexOf(" ")+1) : factura.getMedioPago().getName(),
                                "",
                                "",
                                Utilidades.formatearPrecio(factura.getMedioPago().getAmount())
                        },
                        new int[]{
                                21,
                                2,
                                1,
                                20
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX
                        },
                        null, false
                );

                impresora.saltarLinea();
            }

            if (factura.getCantidadArticulos() != 0) {
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirTexto("TOTAL ARTICULOS: " + factura.getCantidadArticulos(), 1,true ,false );
                impresora.saltarLinea();
            }

            //DISCRIMINACION TARIFAS IVA
            if (factura.getBaseLmp().intValue() != 0) {
                impresora.saltarLinea();
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
                impresora.imprimirTexto("DISCRIMINACION TARIFAS IVA", 1,true , false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirColumnas(
                        new Object[]{
                                "Tarifa",
                                "Compra",
                                "Base/Imp",
                                "Iva"
                        },
                        new int[]{
                                10,
                                10,
                                12,
                                12
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX
                        },
                        null, false
                );
                impresora.imprimirSeparador();
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirColumnas(
                        new Object[]{
                                factura.getTarifa() + "%",
                                Utilidades.formatearPrecio(factura.getTotalCompraIva()),
                                Utilidades.formatearPrecio(factura.getBaseLmp()),
                                Utilidades.formatearPrecio(factura.getIvaCompra())
                        },
                        new int[]{
                                10,
                                10,
                                12,
                                12
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX
                        },
                        null, false
                );
                impresora.imprimirSeparador();
                impresora.saltarLinea();
            }

            //DISCRIMINACION IMPUESTO BOLSA.
            if (factura.getCantidadBolsas() != 0) {
                impresora.saltarLinea();
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
                impresora.imprimirTexto("DISCRIMINACION IMPUESTO BOLSA", 1, true, false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirColumnas(
                        new Object[]{
                                "Cant",
                                "Valor unitario",
                                "",
                                "Total"
                        },
                        new int[]{
                                12,
                                12,
                                6,
                                12
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX
                        },
                        null, false
                );
                impresora.imprimirSeparador();
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
                impresora.imprimirColumnas(
                        new Object[]{
                                factura.getCantidadBolsas(),
                                Utilidades.formatearPrecio(factura.getPrecioBolsa()),
                                "",
                                Utilidades.formatearPrecio(factura.getPrecioBolsaTotal())
                        },
                        new int[]{
                                12,
                                12,
                                6,
                                12
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX,
                                IManejadorImpresora.ALINEACION_DERECHA_BX
                        },
                        null, false
                );
                impresora.saltarLinea();
                impresora.imprimirSeparador();
                impresora.saltarLinea();
            }

            //TEXTO FACTURA ELECTRONICA
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto("REPRESENTACION GRAFICA DE SU FACTURA \n ELECTRONICA DE VENTA", 1, true, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS CONTRIBUYENTES
            impresora.imprimirTexto(factura.getTextoContribuyentes(), 1, false, false);
            impresora.saltarLinea();

            //TEXTO FISCAL
            impresora.saltarLinea();
            impresora.imprimirTexto(factura.convertirTextoFiscal(56), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto("CUFE: " + factura.getCufeHash(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            impresora.imprimirTexto("Emitida: " + util.getThisDate(), 1, false, false);
            impresora.saltarLinea();

            impresora.imprimirQR(factura.getUrlBaseFacturaElectronicaQR()+factura.getCufeHash(),
                    4, 120, IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.saltarLinea();

            //TEXTO PROVEEDOR TECNOLOGICO
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto(factura.getProveedorTecnologicoFE(true), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS PLAZOS
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getPlazos(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS POLITICAS CAMBIOS
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getPolitaCambios(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS LINEAS ATENCION
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getLineasAtencion(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //CODIGO DE BARRAS
            impresora.imprimirCodigoBarras(factura.getReferenciaInterna(), 3, 100, IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getReferenciaInterna(), 1, false, false);
            impresora.saltarLinea();
            impresora.cortarPapel();

            if (factura.isTirillaMensajeDia()) {
                tirillaMSJdia_ESCPOSBixolon();
            }

            if (factura.getVaucherText() != null && factura.getVaucherPalabra() != null) {
                if (!factura.getVaucherText().isEmpty() && !factura.getVaucherPalabra().isEmpty()) {
                    vaucherESCPOSBixolon();
                }
            }

            if (factura.getTirillaM2Formato().contains(factura.getFormatoTienda()))
                if (factura.getTotalM2() > factura.getTirillaM2Monto() && factura.isTirillaM2() && factura.isTirillaM2Gen()) {
                    tirillaModelo2_ESCPOSBixolon();
                }

            if (factura.getTirillaM1Formato().contains(factura.getFormatoTienda()))
                if (factura.getTotalM1() > factura.getTirillaM1Monto() && factura.isTirillaM1() && factura.isTirillaM1Gen()) {
                    if (factura.isTirillaM1Multiplo()) {
                        double totalM1num = Math.floor(factura.getTotalM1() / factura.getTirillaM1Monto());
                        for (int i = 0; i < (int) totalM1num; i++) {
                            tirillaModelo1_ESCPOSBixolon();
                        }
                    } else {
                        tirillaModelo1_ESCPOSBixolon();
                    }
                }

            iniciarTarea();
            tiempo = new Timer();
            tiempo.schedule(tarea, 60000);

            iniciarTarea2();
            tiempo2 = new Timer();
            tiempo2.schedule(tarea2, 200);

            if(generarCertificadoAuto){
                imprimirCertificadoRegaloBixolon();
            }

            LogFile.adjuntarLog("imprimirFacturaBixolon", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("imprimirFacturaBixolon Error", e.fillInStackTrace());
            LogFile.adjuntarLog("Error en Impresión: factura", e);
        }
    }

    private void imprimirFactura() {
        try {
            //Encabezado
            impresora.iniciarImpresion();
            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getNombreEmpresa()+"\n" +
                    factura.getNitEmpresa()+"\n", 0 , false, false);
            impresora.imprimirTexto("TIENDA " + factura.getNombreTienda(), 0 , true, false);
            impresora.saltarLinea();

            impresora.imprimirTexto("Numero de Factura\n" +
                    factura.getReferenciaInterna() + "\n\n",0 , false, false);

            //Información
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto("Emitida: " + util.getThisDate(), 0 , false, false);
            impresora.saltarLinea();
            impresora.imprimirColumnas(
                    new Object[]{
                            "Tienda: " + factura.getTienda(),
                            "",
                            "Caja No: " + factura.getCaja(),
                            ""
                    },
                    new int[]{
                            10,
                            10,
                            10,
                            10
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_DERECHA,
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_DERECHA
                    },
                    null, false
            );
            impresora.saltarLinea();

            impresora.imprimirTexto("Num. Transaccion: " + factura.getNumeroTransaccion() + "\n" +
                    "Cedula Cliente: " + cliente.getCedula(true) + "\n" +
                    "Nombre Cliente: " + cliente.getNombreCompleto(),0 , false, false);
            impresora.saltarLinea();

            //Encabezado de los productos
            impresora.imprimirSeparador();
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

            impresora.imprimirColumnas(
                    new Object[]{
                            "Cant",
                            "Descripcion",
                            "Valor",
                            "Imp"
                    },
                    new int[]{
                            5,
                            33,
                            12,
                            1
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_DERECHA,
                            IManejadorImpresora.ALINEACION_DERECHA
                    },
                    null, false
            );

            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirSeparador();
            impresora.saltarLinea();

            //Los productos
            for (Producto p : factura.getProductos()) {

                String pNombre = p.construirNombreProducto(32);

                String pCodigoTasaImpuesto = p.codigoTasaImpuesto();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

                impresora.imprimirColumnas(
                        new Object[]{
                                "",
                                p.getEan(),
                                "",
                                ""
                        },
                        new int[]{
                                5,
                                33,
                                12,
                                1
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_DERECHA,
                                IManejadorImpresora.ALINEACION_DERECHA
                        },
                        null, false
                );

                impresora.saltarLinea();

                if (p.getPrecioOriginal().intValue() == 0) {
                    impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                    impresora.imprimirColumnas(
                            new Object[]{
                                    "1x",
                                    pNombre,
                                    Utilidades.formatearPrecio(p.getPrecio()),
                                    "  " + pCodigoTasaImpuesto
                            },
                            new int[]{
                                    5,
                                    33,
                                    12,
                                    1
                            },
                            new int[]{
                                    IManejadorImpresora.ALINEACION_IZQUIERDA,
                                    IManejadorImpresora.ALINEACION_IZQUIERDA,
                                    IManejadorImpresora.ALINEACION_DERECHA,
                                    IManejadorImpresora.ALINEACION_DERECHA
                            },
                            null, false
                    );
                    impresora.saltarLinea();
                } else if (p.getDescuento() != null) {
                    if (p.getPrecioOriginal().equals(p.getPrecioBase())) {
                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "1x",
                                        pNombre,
                                        Utilidades.formatearPrecio(p.getPrecioBase()),
                                        "  " + pCodigoTasaImpuesto
                                },
                                new int[]{
                                        5,
                                        33,
                                        12,
                                        1
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_DERECHA,
                                        IManejadorImpresora.ALINEACION_DERECHA
                                },
                                null, false
                        );
                        impresora.saltarLinea();
                    } else {
                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "1x",
                                        pNombre,
                                        Utilidades.formatearPrecio(p.getPrecioOriginal()),
                                        "  " + pCodigoTasaImpuesto
                                },
                                new int[]{
                                        5,
                                        33,
                                        12,
                                        1
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_DERECHA,
                                        IManejadorImpresora.ALINEACION_DERECHA
                                },
                                null, false
                        );
                        impresora.saltarLinea();

                        factura.calcularPorDescAndTarifaDesc(p.getPrecioOriginal(), p.getPrecioBase());

                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "",
                                        "Dcto. Tarifa "  + Utilidades.formatearPrecio(factura.getTarifaDtoUni())
                                                + " -" + factura.getPorcDtoUni().intValue()
                                                + "%: Valor final: "+ Utilidades.formatearPrecio(p.getPrecioBase()),
                                        ""
                                },
                                new int[]{
                                        1,
                                        49,
                                        1
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_DERECHA,
                                        IManejadorImpresora.ALINEACION_DERECHA
                                },
                                null, false
                        );
                        impresora.saltarLinea();
                    }
                } else {
                    if (p.getPrecioOriginal().equals(p.getPrecioBase())) {
                        if (!p.getPrecio().equals(p.getPrecioBase())) {
                            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                            impresora.imprimirColumnas(
                                    new Object[]{
                                            "1x",
                                            pNombre,
                                            Utilidades.formatearPrecio(p.getPrecioBase()),
                                            "  " + pCodigoTasaImpuesto
                                    },
                                    new int[]{
                                            5,
                                            33,
                                            12,
                                            1
                                    },
                                    new int[]{
                                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                                            IManejadorImpresora.ALINEACION_DERECHA,
                                            IManejadorImpresora.ALINEACION_DERECHA
                                    },
                                    null, false
                            );
                        } else {
                            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                            impresora.imprimirColumnas(
                                    new Object[]{
                                            "1x",
                                            pNombre,
                                            Utilidades.formatearPrecio(p.getPrecio()),
                                            "  " + pCodigoTasaImpuesto
                                    },
                                    new int[]{
                                            5,
                                            33,
                                            12,
                                            1
                                    },
                                    new int[]{
                                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                                            IManejadorImpresora.ALINEACION_DERECHA,
                                            IManejadorImpresora.ALINEACION_DERECHA
                                    },
                                    null, false
                            );
                            impresora.saltarLinea();
                        }
                    } else {
                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "1x",
                                        pNombre,
                                        Utilidades.formatearPrecio(p.getPrecioOriginal()),
                                        "  " + pCodigoTasaImpuesto
                                },
                                new int[]{
                                        5,
                                        33,
                                        12,
                                        1
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_DERECHA,
                                        IManejadorImpresora.ALINEACION_DERECHA
                                },
                                null, false
                        );
                        impresora.saltarLinea();

                        factura.calcularPorDescAndTarifaDesc(p.getPrecioOriginal(), p.getPrecioBase());

                        impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                        impresora.imprimirColumnas(
                                new Object[]{
                                        "",
                                        "Dcto. Tarifa "  + Utilidades.formatearPrecio(factura.getTarifaDtoUni() * -1)
                                                + " -" + factura.getPorcDtoUni().intValue()
                                                + "%: Valor final: "+ Utilidades.formatearPrecio(p.getPrecioBase()),
                                        ""
                                },
                                new int[]{
                                        1,
                                        49,
                                        1
                                },
                                new int[]{
                                        IManejadorImpresora.ALINEACION_IZQUIERDA,
                                        IManejadorImpresora.ALINEACION_DERECHA,
                                        IManejadorImpresora.ALINEACION_DERECHA
                                },
                                null, false
                        );
                        impresora.saltarLinea();
                    }
                }
            }

            impresora.imprimirSeparador();
            impresora.saltarLinea();

            //Totales
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirColumnas(
                    new Object[]{
                            "Subtotal",
                            "",
                            "",
                            Utilidades.formatearPrecio(factura.getSubtotal())
                    },
                    new int[]{
                            25,
                            2,
                            1,
                            24
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_DERECHA,
                            IManejadorImpresora.ALINEACION_DERECHA
                    },
                    null, false
            );
            impresora.saltarLinea();

            factura.calcularDescuentosSimples();

            //Descuentos
            if(!factura.getDescuentoSimples().isEmpty()){
                for (DescuentoSimple descuentoSimple : factura.getDescuentoSimples()) {
                    impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                    impresora.imprimirColumnas(
                            new Object[]{
                                    descuentoSimple.getNombre(),
                                    "-"+Utilidades.formatearPrecio(descuentoSimple.getValor()),
                                    ""
                            },
                            new int[]{
                                    36,
                                    14,
                                    2
                            },
                            new int[]{
                                    IManejadorImpresora.ALINEACION_DERECHA,
                                    IManejadorImpresora.ALINEACION_DERECHA,
                                    IManejadorImpresora.ALINEACION_DERECHA
                            },
                            null, false
                    );
                    impresora.saltarLinea();
                }

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

                impresora.imprimirColumnas(
                        new Object[]{
                                "Ahorro total",
                                "-"+Utilidades.formatearPrecio(factura.getDescuentoTotal()),
                                ""
                        },
                        new int[]{
                                36,
                                14,
                                2
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_DERECHA,
                                IManejadorImpresora.ALINEACION_DERECHA,
                                IManejadorImpresora.ALINEACION_DERECHA
                        },
                        null, true
                );
                impresora.saltarLinea();

                impresora.imprimirSeparador();
                impresora.saltarLinea();
            }

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_TXT_DOBLE_ALTURA);
            impresora.imprimirColumnas(
                    new Object[]{
                            "TOTAL",
                            "",
                            "",
                            Utilidades.formatearPrecio(factura.getTotalCompra())
                    },
                    new int[]{
                            18,
                            1,
                            1,
                            23
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_DERECHA,
                            IManejadorImpresora.ALINEACION_DERECHA
                    },
                    null, true
            );

            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_TXT_NORMAL);
            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

            //medios de pagos
            if (factura.getTotalCompra().intValue() != 0) {
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirColumnas(
                        new Object[]{
                                factura.getMedioPago().getMethodId().equals("C45") ?
                                        factura.getMedioPago().getName().substring(factura.getMedioPago().getName().indexOf(" ")+1) : factura.getMedioPago().getName(),
                                "",
                                "",
                                Utilidades.formatearPrecio(factura.getMedioPago().getAmount())
                        },
                        new int[]{
                                25,
                                2,
                                1,
                                24
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_DERECHA,
                                IManejadorImpresora.ALINEACION_DERECHA
                        },
                        null, false
                );

                impresora.saltarLinea();
            }

            if (factura.getCantidadArticulos() != 0) {
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirTexto("TOTAL ARTICULOS: " + factura.getCantidadArticulos(), 0,true ,false );
                impresora.saltarLinea();
            }

            //DISCRIMINACION TARIFAS IVA
            if (factura.getBaseLmp().intValue() != 0) {
                impresora.saltarLinea();
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
                impresora.imprimirTexto("DISCRIMINACION TARIFAS IVA", 0,true , false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirColumnas(
                        new Object[]{
                                "Tarifa",
                                "Compra",
                                "Base/Imp",
                                "Iva"
                        },
                        new int[]{
                                10,
                                14,
                                14,
                                14
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_DERECHA,
                                IManejadorImpresora.ALINEACION_DERECHA
                        },
                        null, false
                );
                impresora.saltarLinea();
                impresora.imprimirSeparador();
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirColumnas(
                        new Object[]{
                                factura.getTarifa() + "%",
                                Utilidades.formatearPrecio(factura.getTotalCompraIva()),
                                Utilidades.formatearPrecio(factura.getBaseLmp()),
                                Utilidades.formatearPrecio(factura.getIvaCompra())
                        },
                        new int[]{
                                10,
                                14,
                                14,
                                14
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_DERECHA,
                                IManejadorImpresora.ALINEACION_DERECHA
                        },
                        null, false
                );
                impresora.saltarLinea();
                impresora.imprimirSeparador();
                impresora.saltarLinea();
            }

            //DISCRIMINACION IMPUESTO BOLSA.
            if (factura.getCantidadBolsas() != 0) {
                impresora.saltarLinea();
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
                impresora.imprimirTexto("DISCRIMINACION IMPUESTO BOLSA", 0, true, false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
                impresora.imprimirColumnas(
                        new Object[]{
                                "Cant",
                                "Valor unitario"
                        },
                        new int[]{
                                20,
                                20
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_IZQUIERDA
                        },
                        null, false
                );
                impresora.imprimirTexto("Valor total", 0, false, false);
                impresora.saltarLinea();
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirSeparador();
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
                impresora.imprimirColumnas(
                        new Object[]{
                                factura.getCantidadBolsas(),
                                Utilidades.formatearPrecio(factura.getPrecioBolsa())
                        },
                        new int[]{
                                20,
                                20
                        },
                        new int[]{
                                IManejadorImpresora.ALINEACION_IZQUIERDA,
                                IManejadorImpresora.ALINEACION_IZQUIERDA
                        },
                        null, false
                );
                impresora.imprimirTexto(Utilidades.formatearPrecio(factura.getPrecioBolsaTotal()),0 , false, false);
                impresora.saltarLinea();
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirSeparador();
                impresora.saltarLinea();
            }

            //TEXTO FACTURA ELECTRONICA
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto("REPRESENTACION GRAFICA DE SU FACTURA \n ELECTRONICA DE VENTA", 0, true, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS CONTRIBUYENTES
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getTextoContribuyentes(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTO FISCAL
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.saltarLinea();
            impresora.imprimirTexto(factura.convertirTextoFiscal(56), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto("CUFE: " + factura.getCufeHash(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            impresora.imprimirTexto("Emitida: " + util.getThisDate(), 0 , false, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirQR(factura.getUrlBaseFacturaElectronicaQR()+factura.getCufeHash(), 40);
            impresora.saltarLinea();

            //TEXTO PROVEEDOR TECNOLOGICO
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto(factura.getProveedorTecnologicoFE(false), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS PLAZOS
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getPlazos(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS POLITICAS CAMBIOS
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getPolitaCambios(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS LINEAS ATENCION
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getLineasAtencion(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //CODIGO DE BARRAS
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);

            impresora.imprimirCodigoBarras(factura.getReferenciaInterna(), IManejadorImpresora.SET_WIDTH_NORMAL);
            impresora.imprimirTexto(factura.getReferenciaInterna(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();
            impresora.saltarLinea();
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            if (factura.isTirillaMensajeDia()) {
                tirillaMSJdia_ESCPOS();
            }

            if (factura.getVaucherText() != null && factura.getVaucherPalabra() != null) {
                if (!factura.getVaucherText().isEmpty() && !factura.getVaucherPalabra().isEmpty()) {
                    vaucherESCPOS();
                }
            }

            if (factura.getTirillaM2Formato().contains(factura.getFormatoTienda()))
                if (factura.getTotalM2() > factura.getTirillaM2Monto() && factura.isTirillaM2() && factura.isTirillaM2Gen()) {
                    tirillaModelo2_ESCPOS();
                }

            if (factura.getTirillaM1Formato().contains(factura.getFormatoTienda()))
                if (factura.getTotalM1() > factura.getTirillaM1Monto() && factura.isTirillaM1() && factura.isTirillaM1Gen()) {
                    if (factura.isTirillaM1Multiplo()) {
                        double totalM1num = Math.floor(factura.getTotalM1() / factura.getTirillaM1Monto());
                        for (int i = 0; i < (int) totalM1num; i++) {
                            tirillaModelo1_ESCPOS();
                        }
                    } else {
                        tirillaModelo1_ESCPOS();
                    }
                }

            iniciarTarea();
            tiempo = new Timer();
            tiempo.schedule(tarea, 60000);

            iniciarTarea2();
            tiempo2 = new Timer();
            tiempo2.schedule(tarea2, 200);

            if(generarCertificadoAuto){
                imprimirCertificadoRegalo();
            }

            LogFile.adjuntarLog("imprimirFactura", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("imprimirFactura Error", e.fillInStackTrace());
            disconnect();
        }
    }

    private void iniciarTarea2() {
        tarea2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->{
                    util.vozAndroid(getResources().getString(R.string.voz_agradecimiento_compra));
                });
            }
        };
    }

    private void tirillaMSJdia_ESCPOSBixolon() {
        try {
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getTiriMensajeDia(), 1, false, false);
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.cortarPapel();
            LogFile.adjuntarLog("tirillaMSJdia_ESCPOSBixolon", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("tirillaMSJdia_ESCPOSBixolon Error", e.fillInStackTrace());
        }
    }

    private void tirillaMSJdia_ESCPOS() {
        try {
            impresora.iniciarImpresion();
            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getTiriMensajeDia(), 0, false, false);
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            LogFile.adjuntarLog("tirillaMSJdia_ESCPOS", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("tirillaMSJdia_ESCPOS Error", e.fillInStackTrace());
            disconnect();
        }
    }

    private void tirillaModelo1_ESCPOSBixolon() {
        try {
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getNombreEmpresa(), 1, false, false);
            impresora.saltarLinea();

            impresora.imprimirTexto(util.getTexto(R.string.texto_nombreTienda) + " "
                    + factura.getNombreTienda(), 1, true, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroDeIdentificacion) + " "
                    + cliente.getCedula(true), 1, false, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto(cliente.getNombreCompleto(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //Texto Header
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(util.convertirTexto(50, factura.getTextMillasHeaderM1()), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //Codigo de barras
            if (!factura.getTirillaM1PalabraClave().isEmpty()) {
                impresora.imprimirCodigoBarras(factura.getTirillaM1PalabraClave(), 3, 100, IManejadorImpresora.ALINEACION_CENTRO_BX);
                impresora.saltarLinea();
            }

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroCaja)+ " "
                    + factura.getCaja(), 1, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

            impresora.imprimirTexto(util.getTexto(R.string.texto_fecha)+ " "
                    + util.getThisDateSimple(), 1, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroTransacionSencillo)+ " "
                    + factura.getNumeroTransaccion(), 1, false, false);
            impresora.saltarLinea();

            //Texto Footer
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(util.convertirTexto(50, factura.getTextMillasFooterM1()), 1, false, false);
            impresora.saltarLinea();
            impresora.cortarPapel();

            LogFile.adjuntarLog("tirillaModelo1_ESCPOSBixolon", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("tirillaModelo1_ESCPOSBixolon Error", e.fillInStackTrace());
        }
    }

    private void tirillaModelo1_ESCPOS() {
        try {
            impresora.iniciarImpresion();

            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getNombreEmpresa(), 0, false, false);
            impresora.saltarLinea();

            impresora.imprimirTexto(util.getTexto(R.string.texto_nombreTienda) + " "
                    + factura.getNombreTienda(), 0, true, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroDeIdentificacion) + " "
                    + cliente.getCedula(true), 0, false, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto(cliente.getNombreCompleto(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //Texto Header
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(util.convertirTexto(50, factura.getTextMillasHeaderM1()), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //Codigo de barras
            if (!factura.getTirillaM1PalabraClave().isEmpty()) {
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);

                impresora.imprimirCodigoBarras(factura.getTirillaM1PalabraClave(), IManejadorImpresora.SET_WIDTH_NORMAL);
                impresora.saltarLinea();
            }

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroCaja)+ " "
                    + factura.getCaja(), 0, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

            impresora.imprimirTexto(util.getTexto(R.string.texto_fecha)+ " "
                    + util.getThisDateSimple(), 0, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroTransacionSencillo)+ " "
                    + factura.getNumeroTransaccion(), 0, false, false);
            impresora.saltarLinea();

            //Texto Footer
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(util.convertirTexto(50, factura.getTextMillasFooterM1()), 0, false, false);
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            LogFile.adjuntarLog("tirillaModelo1_ESCPOS", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("tirillaModelo1_ESCPOS Error", e.fillInStackTrace());
            disconnect();
        }
    }

    private void tirillaModelo2_ESCPOSBixolon() {
        try {
            if (!factura.getTirillaM2PalabraClave().isEmpty()) {
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
                impresora.imprimirTexto(factura.getNombreEmpresa(), 1, false, false);
                impresora.saltarLinea();

                impresora.imprimirTexto(util.getTexto(R.string.texto_nombreTienda) + " " + factura.getNombreTienda(), 1, true, false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirTexto(util.getTexto(R.string.texto_numeroDeIdentificacion) + " "
                        + cliente.getCedula(true), 1, false, false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirTexto(cliente.getNombreCompleto(), 1, false, false);
                impresora.saltarLinea();
                impresora.saltarLinea();
            }

            //Texto Header
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(util.convertirTexto(50, factura.getTextMillasHeaderM2()), 1, false, false);
            impresora.saltarLinea();


            if (!factura.getTirillaM2PalabraClave().isEmpty()) {
                //CODIGO DE BARRAS
                impresora.imprimirCodigoBarras(factura.getTirillaM2PalabraClave(), 3, 100, IManejadorImpresora.ALINEACION_CENTRO_BX);
                impresora.saltarLinea();
            }

            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.convertirTextFooter(50), 1, false, false);
            impresora.saltarLinea();
            impresora.cortarPapel();

            LogFile.adjuntarLog("tirillaModelo2_ESCPOSBixolon", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("tirillaModelo2_ESCPOSBixolon Error", e.fillInStackTrace());
        }
    }
    private void tirillaModelo2_ESCPOS() {
        try {
            impresora.iniciarImpresion();
            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);

            if (!factura.getTirillaM2PalabraClave().isEmpty()) {
                impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
                impresora.imprimirTexto(factura.getNombreEmpresa(), 0, false, false);
                impresora.saltarLinea();

                impresora.imprimirTexto(util.getTexto(R.string.texto_nombreTienda) + " " + factura.getNombreTienda(), 0, true, false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirTexto(util.getTexto(R.string.texto_numeroDeIdentificacion) + " "
                        + cliente.getCedula(true), 0, false, false);
                impresora.saltarLinea();

                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirTexto(cliente.getNombreCompleto(), 0, false, false);
                impresora.saltarLinea();
                impresora.saltarLinea();
            }

            //Texto Header
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(util.convertirTexto(50, factura.getTextMillasHeaderM2()), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();


            if (!factura.getTirillaM2PalabraClave().isEmpty()) {
                //CODIGO DE BARRAS
                impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);

                impresora.imprimirCodigoBarras(factura.getTirillaM2PalabraClave(), IManejadorImpresora.SET_WIDTH_NORMAL);
                impresora.saltarLinea();
            }

            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.convertirTextFooter(50), 0, false, false);
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            LogFile.adjuntarLog("tirillaModelo2_ESCPOS", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("tirillaModelo2_ESCPOS Error", e.fillInStackTrace());
            disconnect();
        }
    }

    private void vaucherESCPOSBixolon() {
        try {
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getNombreEmpresa(), 1, false, false);
            impresora.saltarLinea();

            impresora.imprimirTexto(util.getTexto(R.string.texto_nombreTienda)+ " "
                    + factura.getNombreTienda(), 1, true, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroDeIdentificacion) + " " +
                    cliente.getCedula(true), 1, false, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto(cliente.getNombreCompleto(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //CODIGO DE BARRAS
            impresora.imprimirCodigoBarras(factura.getVaucherPalabra(), 3, 100, IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroCaja) + " "
                    + factura.getCaja(), 1, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

            impresora.imprimirTexto(util.getTexto(R.string.texto_fecha)+ " "
                    + util.getThisDateSimple(), 1, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);

            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroTransacionSencillo) + " "
                    + factura.getNumeroTransaccion(), 1, false, false);
            impresora.saltarLinea();

            //Vale texto
            impresora.saltarLinea();
            impresora.imprimirTexto(factura.convertirVaucherText(40), 1, false, false);
            impresora.saltarLinea();
            impresora.cortarPapel();

            LogFile.adjuntarLog("vaucherESCPOSBixolon", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("vaucherESCPOSBixolon Error", e.fillInStackTrace());
        }
    }

    private void vaucherESCPOS() {
        try {

            impresora.iniciarImpresion();

            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getNombreEmpresa(), 0, false, false);
            impresora.saltarLinea();

            impresora.imprimirTexto(util.getTexto(R.string.texto_nombreTienda)+ " "
                    + factura.getNombreTienda(), 0, true, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroDeIdentificacion) + " " +
                    cliente.getCedula(true), 0, false, false);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto(cliente.getNombreCompleto(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //CODIGO DE BARRAS
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);

            impresora.imprimirCodigoBarras(factura.getVaucherPalabra(), IManejadorImpresora.SET_WIDTH_NORMAL);
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroCaja) + " "
                    + factura.getCaja(), 0, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

            impresora.imprimirTexto(util.getTexto(R.string.texto_fecha)+ " "
                    + util.getThisDateSimple(), 0, false, false);
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);

            impresora.imprimirTexto(util.getTexto(R.string.texto_numeroTransacionSencillo) + " "
                    + factura.getNumeroTransaccion(), 0, false, false);
            impresora.saltarLinea();

            //Vale texto
            impresora.saltarLinea();
            impresora.imprimirTexto(factura.convertirVaucherText(40), 0, false, false);
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            LogFile.adjuntarLog("vaucherESCPOS", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("vaucherESCPOS Error", e.fillInStackTrace());
            disconnect();
        }
    }

    private void imprimirClaveValorBixolon(){
        try {
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getTituloClaveValor(), 1, true, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            for(ClaveValor claveValor : factura.getListaClaveValor()){
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
                impresora.imprimirTexto(claveValor.getClave() +" "+ claveValor.getValor(), 1, false, false);
                impresora.saltarLinea();
            }

            impresora.cortarPapel();

            LogFile.adjuntarLog("imprimirClaveValorBixolon", "Impresión realizada");
        }catch (Exception e){
            LogFile.adjuntarLog("imprimirClaveValorBixolon", e.fillInStackTrace());
        }
    }

    private void imprimirClaveValor() {
        try {
            impresora.iniciarImpresion();
            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getTituloClaveValor(), 0, true, false);
            impresora.saltarLinea();
            impresora.saltarLinea();
            
            for(ClaveValor claveValor : factura.getListaClaveValor()){
                impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
                impresora.imprimirTexto(claveValor.getClave() +" "+ claveValor.getValor(), 0, false, false);
                impresora.saltarLinea();
            }
            
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            LogFile.adjuntarLog("imprimirClaveValor", "Impresión realizada");
        }catch (Exception e){
            LogFile.adjuntarLog("imprimirClaveValor Error", e.fillInStackTrace());
            disconnect();
        }
    }

    private void imprimirCertificadoRegaloBixolon(){
        try {
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getNombreEmpresa(), 1, false, false);
            impresora.saltarLinea();

            impresora.imprimirTexto(getTexto(R.string.texto_nombreTienda) + " " + factura.getNombreTienda(),
                    1, true, false);
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA_BX);
            impresora.imprimirColumnas(
                    new Object[]{
                            "Numero",
                            "Fecha y Hora",
                            "Tienda",
                            "Caja"
                    },
                    new int[]{
                            10,
                            18,
                            8,
                            8
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX
                    },
                    null, false
            );

            impresora.imprimirSeparador();

            impresora.imprimirColumnas(
                    new Object[]{
                            factura.getNumeroTransaccion(),
                            Utilidades.formateaFecha(new Date(), Constantes.FORMATO_MEDIO),
                            factura.getTienda(),
                            factura.getCaja()
                    },
                    new int[]{
                            8,
                            18,
                            8,
                            8
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_IZQUIERDA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX,
                            IManejadorImpresora.ALINEACION_DERECHA_BX
                    },
                    null, false
            );
            impresora.saltarLinea();
            impresora.imprimirSeparador();
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.saltarLinea();
            impresora.imprimirTexto(getTexto(R.string.texto_tituloCertificadoRegalo),2, true, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //CODIGO DE BARRAS
            impresora.imprimirCodigoBarras(factura.getReferenciaInterna(), 3, 100, IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getReferenciaInterna(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //VENDEDOR
            impresora.imprimirTexto("AUTOPAGO\n" + "*Aplican codiciones y restricciones*",1, false, false);
            impresora.saltarLinea();

            //TEXTOS PLAZOS
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getPlazos(), 1, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS LINEAS ATENCION
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO_BX);
            impresora.imprimirTexto(factura.getLineasAtencion(), 1, false, false);
            impresora.saltarLinea();
            impresora.cortarPapel();

            LogFile.adjuntarLog("imprimirCertificadoRegaloBixolon", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("imprimirCertificadoRegaloBixolon Error", e.fillInStackTrace());
            Utilidades.mjsToast(getResources().getString(R.string.error_impresion_intentar),
                    Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, contexto);
        }
    }

    private void imprimirCertificadoRegalo(){
        try {
            impresora.iniciarImpresion();

            impresora.comandoFormatoTexto(IManejadorImpresora.COMANDO_FUENTE_PEQUEÑA);
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getNombreEmpresa(), 0, false, false);
            impresora.saltarLinea();

            impresora.imprimirTexto(getTexto(R.string.texto_nombreTienda) + " " + factura.getNombreTienda(),
                    0, true, false);
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_IZQUIERDA);
            impresora.imprimirColumnas(
                    new Object[]{
                            "Numero",
                            "Fecha y Hora",
                            "Tienda",
                            "Caja"
                    },
                    new int[]{
                            10,
                            22,
                            10,
                            10
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_DERECHA,
                            IManejadorImpresora.ALINEACION_DERECHA
                    },
                    null, false
            );
            impresora.saltarLinea();
            impresora.imprimirSeparador();
            impresora.saltarLinea();

            impresora.imprimirColumnas(
                    new Object[]{
                            factura.getNumeroTransaccion(),
                            Utilidades.formateaFecha(new Date(), Constantes.FORMATO_MEDIO),
                            factura.getTienda(),
                            factura.getCaja()
                    },
                    new int[]{
                            10,
                            22,
                            10,
                            10
                    },
                    new int[]{
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_IZQUIERDA,
                            IManejadorImpresora.ALINEACION_DERECHA,
                            IManejadorImpresora.ALINEACION_DERECHA
                    },
                    null, false
            );
            impresora.saltarLinea();
            impresora.imprimirSeparador();
            impresora.saltarLinea();

            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.saltarLinea();
            impresora.imprimirTexto(getTexto(R.string.texto_tituloCertificadoRegalo),0, true, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //CODIGO DE BARRAS
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);

            impresora.imprimirCodigoBarras(factura.getReferenciaInterna(), IManejadorImpresora.SET_WIDTH_NORMAL);
            impresora.imprimirTexto(factura.getReferenciaInterna(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //VENDEDOR
            impresora.imprimirTexto("AUTOPAGO\n" + "*Aplican codiciones y restricciones*",0, false, false);
            impresora.saltarLinea();

            //TEXTOS PLAZOS
            impresora.saltarLinea();
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getPlazos(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            //TEXTOS LINEAS ATENCION
            impresora.alinear(IManejadorImpresora.ALINEACION_CENTRO);
            impresora.imprimirTexto(factura.getLineasAtencion(), 0, false, false);
            impresora.saltarLinea();
            impresora.saltarLinea();

            impresora.saltarLinea();
            impresora.confirmarComando();
            impresora.alimentaPapel();
            impresora.cortarPapel();

            LogFile.adjuntarLog("imprimirCertificadoRegalo", "Impresión realizada");
        } catch (Exception e) {
            LogFile.adjuntarLog("imprimirCertificadoRegalo", e.fillInStackTrace());
            Utilidades.mjsToast(getResources().getString(R.string.error_impresion_intentar),
                    Constantes.TOAST_TYPE_ERROR, Toast.LENGTH_LONG, contexto);
            disconnect();
        }
    }

    public String getTexto(int texto){
        return getResources().getString(texto);
    }

    private void certificadoRegalo(View view){
        factura.setLabelImprimir(Constantes.LABEL_CERTIFICADO_REGALO);
        if(factura.getImpresora().equals(getResources().getString(R.string.bixolon))){
            configuracionBixolon();
        }else{
            configuracionEpson();
        }
    }
}