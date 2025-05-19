package com.crystal.selfcheckoutapp.Vista;

import androidx.appcompat.app.AppCompatActivity;

import com.crystal.selfcheckoutapp.Modelo.Interface.IBase;
import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConsultaCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.Configuraciones;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.KeyboardUtils;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.ResponseAperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaConsultaCliente;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaAnularTefDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaAutorizacionSimpleFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaClienteGenericoDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaDialogFragmentMostrarInfo;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaLanguageDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomDosAccionesDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomUnaAccionDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaSeleccionConfiguracionDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VistaConsultaCliente extends AppCompatActivity implements IBase.Vista, IVistaConsultaCliente.Vista,
        VistaAutorizacionSimpleFragment.OnInputListener, VistaSeleccionConfiguracionDialogFragment.OnInputListener,
        VistaAnularTefDialogFragment.OnInputListener {

    private Button btnConsultarCedula, btnContinuarSinDatos;
    private Context contexto;
    private EditText etCedula;
    private PresentadorVistaConsultaCliente presentador;
    private Utilidades util;
    private TextInputLayout tliCedula;
    private ImageView ivConfiguracionVC;
    private Button btnCambiarIdiomaVC;
    private Configuraciones configuraciones;
    private VistaMsjCustomDosAccionesDialogFragment msjCustomDosAccionesDialogFragment;
    private RespuestaCompletaTef respuestaCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPM.setInt(Constantes.PROCESO_ACTIVITY, Constantes.VISTA_CONSULTA_CLIENTE);
        setContentView(R.layout.vista_consulta_cliente);

        LogFile.adjuntarLogTitulo("Abriendo [Pantalla Consulta Cliente]");

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

        incializar();
        eventos();
    }

    private void cargarIntents() {
        //Intent info cliente
        try {
            if(getIntent().getExtras() != null){
                if(Objects.requireNonNull(getIntent().getExtras()).containsKey(getResources().getString(R.string.key_intent_ultima_tef))){
                    VistaSeleccionConfiguracionDialogFragment configuracionDialogFragment =
                            new VistaSeleccionConfiguracionDialogFragment(configuracionesMostrar());
                    configuracionDialogFragment.show(getSupportFragmentManager(), "ConfiguracionDialogFragment");
                }
            }
        }catch (Exception e){
            Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void incializar() {
        contexto = VistaConsultaCliente.this;
        util = new Utilidades(contexto);
        presentador = new PresentadorVistaConsultaCliente(contexto, getSupportFragmentManager(), this, this);
        tliCedula = findViewById(R.id.tliCedula);

        etCedula = findViewById(R.id.etCedula);
        btnConsultarCedula = findViewById(R.id.btnConsultarCedula);
        ivConfiguracionVC = findViewById(R.id.ivConfiguracionVC);
        btnCambiarIdiomaVC = findViewById(R.id.btnCambiarIdiomaVC);
        btnContinuarSinDatos = findViewById(R.id.btnContinuarSinDatos);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void eventos() {
        btnConsultarCedula.setOnClickListener(this::validarAperturaCaja);

        /*Detecta si se aprento el ENTER*/
        etCedula.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                btnConsultarCedula.callOnClick();
                return true;
            }
            return false;
        });

        etCedula.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                btnConsultarCedula.callOnClick();
            }
            return false;
        });

        etCedula.setOnTouchListener((v, event) -> {
            // Se ha tocado el EditText con el dedo
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                util.vozAndroid(getResources().getString(R.string.ingrese_su_numero_cedula));
                comprobarTextoCedula();
            }
            return false;
        });

        etCedula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                comprobarTextoCedula();
            }
        });

        ivConfiguracionVC.setOnClickListener(v -> presentador.autorizar(true));
        btnCambiarIdiomaVC.setOnClickListener(this::cambiarIdioma);
        btnContinuarSinDatos.setOnClickListener(this::continuarSinDatos);
    }

    public void continuarSinDatos(View view){
        vistaClienteDetalle(Utilidades.clienteGenerico(contexto));
    }

    private void cambiarIdioma(View view){
        VistaLanguageDialogFragment languageDialogFragment =
                new VistaLanguageDialogFragment(Arrays.asList(getResources().getStringArray(R.array.language_options)));
        languageDialogFragment.show(getSupportFragmentManager(), "CambiarIdiomaDialogFragment");
    }

    private void comprobarTextoCedula(){
        String texto = etCedula.getText().toString();
        if(texto.isEmpty()){
            etCedula.setHint(getResources().getString(R.string.ejm_id));
        }else{
            etCedula.setHint("");
        }
    }
    private void mensajesApertura() {
        try{
            boolean primeraVez = SPM.getBoolean(Constantes.MSGS_PRIMERA_VEZ);
            String mensaje;
            if(primeraVez){
                SPM.setBoolean(Constantes.MSGS_PRIMERA_VEZ, false);
                if(SPM.getString(Constantes.MSG_ERROR_CONSECUTIVO) != null){
                    mensaje = SPM.getString(Constantes.MSG_ERROR_CONSECUTIVO);
                    VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment =
                            new VistaMsjCustomUnaAccionDialogFragment(R.drawable.error,
                                    getResources().getString(R.string.ups_algo_mal),
                                    mensaje, mensaje,
                                    getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
                    msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
                }

                if(SPM.getString(Constantes.MSG_ALERTA_APERTURA) != null){
                    mensaje = SPM.getString(Constantes.MSG_ALERTA_APERTURA);
                    VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment =
                            new VistaMsjCustomUnaAccionDialogFragment(R.drawable.alerta,
                                    getResources().getString(R.string.atencion),
                                    mensaje, mensaje,
                                    getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
                    msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
                }
            }
        }catch (Exception e){
            Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void validarAperturaCaja(View view){
        if(presentador.estadoProgress()){
            presentador.validarAperturaCaja(SPM.getString(Constantes.CAJA_CODIGO));
        }
    }

    private void consultarCedula(){
        String cedula = etCedula.getText().toString();

        if(!cedula.isEmpty()){
            if(cedula.length() > 58){
                StringBuilder cedulaBuilder = new StringBuilder();
                for (int i = 24; i < cedula.length(); i++) {
                    char character = cedula.charAt(i);

                    if (Character.isDigit(character)) {
                        cedulaBuilder.append(character);
                    } else {
                        break;
                    }
                }
                etCedula.setText(cedulaBuilder.toString());

                consultarDocumento(cedulaBuilder.toString());
            }else{
                consultarDocumento(cedula);
            }
        }
    }

    private void consultarDocumento(String doc) {
        if(presentador.estadoProgress()){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etCedula.getWindowToken(), 0);

            if(doc.length() < 4 || doc.length() > 18){
                Utilidades.mjsToast(getResources().getString(R.string.identificacion_invalida),
                        Constantes.TOAST_TYPE_INFO, Toast.LENGTH_SHORT, contexto);
                etCedula.setText("");
                etCedula.requestFocus();
            }else{
                presentador.dialogProgressBar(getResources().getString(R.string.progress_consultando_cedula),
                        false);
                presentador.consultarCedula(doc);
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        if(servicio == Constantes.SERVICIO_ACTUALIZAR_TRANSACCION_TEF){
            VistaMsjCustomUnaAccionDialogFragment msjCustomUnaAccionDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                    getResources().getString(R.string.ups_algo_mal),
                    String.format(getResources().getString(R.string.ups_algo_mal_msj_actualizar), servicio), mensaje,
                    getResources().getString(R.string.reintentar), Constantes.ACCION_ACTUALIZAR_TRANSACCION_TEF);
            msjCustomUnaAccionDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
            msjCustomUnaAccionDialogFragment.setVistaConsultaCliente(this);
        }else if(servicio == Constantes.SERVICIO_PETICION_RESPUESTA_DATAFONO &&
                mensaje.equals(getResources().getString(R.string.texto_no_encuentra_pago_tef))){
            Utilidades.mjsToast(getResources().getString(R.string.texto_no_hay_ultimo_pago_tef),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
        }else if(servicio == Constantes.SERVICIO_CONSULTA_CLIENTE_CARACTER_ESPECIAL) {
            VistaMsjCustomDosAccionesDialogFragment msjCustomDosAcciones = new VistaMsjCustomDosAccionesDialogFragment(
                   R.drawable.advertencia,
                    getResources().getString(R.string.ups_algo_mal),
                    mensaje,
                    "",
                    getResources().getString(R.string.generico),
                    getResources().getString(R.string.entendido),
                    Constantes.ACCION_CLIENTE_CARACTER_ESPECIAL,
                    true
            );
            msjCustomDosAcciones.show(getSupportFragmentManager(), "msjCustomDosAcciones");
            msjCustomDosAcciones.setVistaConsultaCliente(this);
        }else{
            VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                    getResources().getString(R.string.ups_algo_mal),
                    String.format(getResources().getString(R.string.ups_algo_mal_msj), servicio), mensaje,
                    getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
        }
        presentador.ocultarDialogProgressBar();
        etCedula.setText("");
        etCedula.requestFocus();
        KeyboardUtils.hideKeyboard(this);
    }

    @Override
    public void respuestaValidarAperturaCaja(ResponseAperturaCaja respuesta) {
        if(respuesta.getEsValida()){
            AperturaCaja aperturaCaja = respuesta.getAperturaCaja();

            if(aperturaCaja.getEstadoCaja().equals("ABIERTA")){
                Date fechaAperturaDate = Utilidades.parceFecha(aperturaCaja.getFechaApertura(),
                        Constantes.FORMATO_CORTO);

                // Convertir Date a LocalDate
                LocalDate fechaApertura = fechaAperturaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaActual = LocalDate.now();
                String fechaAperturaString = fechaApertura.getDayOfMonth()+"/"+fechaApertura.getMonthValue()+"/"+fechaApertura.getYear();
                String fechaHoyString = fechaActual.getDayOfMonth()+"/"+fechaActual.getMonthValue()+"/"+fechaActual.getYear();


                if(!fechaApertura.toString().equals(fechaActual.toString())){
                    VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment =
                            new VistaMsjCustomUnaAccionDialogFragment(R.drawable.alerta,
                                    getResources().getString(R.string.atencion),
                                    String.format(contexto.getResources().getString(R.string.msj_alerta_apertura),
                                            fechaAperturaString,
                                            SPM.getString(Constantes.CAJA_CODIGO),
                                            fechaHoyString), "",
                                    getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
                    msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
                    etCedula.setText("");
                }else {
                    consultarCedula();
                }
            }else{
                errorServicio(contexto.getResources().getString(R.string.error),
                        String.format(contexto.getResources().getString(R.string.caja_cerrada), SPM.getString(Constantes.CAJA_CODIGO)),
                        Constantes.SERVICIO_APERTURA_CAJA);
            }
        }else{
            errorServicio(contexto.getResources().getString(R.string.error),
                    respuesta.getMensaje(), Constantes.SERVICIO_APERTURA_CAJA);
        }
    }

    @Override
    public void vistaClienteDetalle(Cliente cliente){
        Intent intent = new Intent(contexto, VistaCompraCliente.class);
        String grupoEmpleados = SPM.getString(Constantes.PARAM_TIPOS_CLIENTE_DESCUENTO);
        assert grupoEmpleados != null;

        if(grupoEmpleados.contains(cliente.getTipo())){
            cliente.setEmpleado(true);
        }

        intent.putExtra(getResources().getString(R.string.key_intent_cliente), cliente);
        startActivity(intent);
        finish();
    }

    @Override
    public void clienteGenerico(Cliente cliente) {
        VistaClienteGenericoDialogFragment vistaClienteGenericoDialogFragment =
                new VistaClienteGenericoDialogFragment(cliente, this);
        vistaClienteGenericoDialogFragment.show(getSupportFragmentManager(), "ClienteGenericoDialogFragment");
    }

    @Override
    public void onBackPressed() {
        presentador.autorizar(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Validar los mensajes de apertura y errores*/
        try {
            cargarIntents();
            mensajesApertura();
        }catch (Exception ex){
            Toast.makeText(contexto, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void vistaLogin() {
        Intent intent = new Intent(contexto, VistaLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void sendInputItemAutorizacionSimpleFrament(boolean isConfiguracion) {
        if(isConfiguracion){
            VistaSeleccionConfiguracionDialogFragment configuracionDialogFragment =
                    new VistaSeleccionConfiguracionDialogFragment(configuracionesMostrar());
            configuracionDialogFragment.show(getSupportFragmentManager(), "ConfiguracionDialogFragment");
        }else{
            vistaLogin();
        }
    }

    private List<Configuraciones> configuracionesMostrar() {
        List<Configuraciones> configuraciones = new ArrayList<>();
        String[] configuracionesItem = SPM.getString(Constantes.PARAM_OPCIONES_CONFIGURACION).split(";");

        for (String descuento : configuracionesItem) {
            if (!descuento.trim().isEmpty()) {
                List<String> configuracionDetalle = Arrays.asList(descuento.split("\\|"));
                if (!configuracionDetalle.isEmpty()) {
                    configuraciones.add(new Configuraciones(configuracionDetalle.get(0), configuracionDetalle.get(1),
                            Boolean.parseBoolean(configuracionDetalle.get(2))));
                }
            }
        }

        return configuraciones;
    }

    @Override
    public void sendInputItemConfiSelectDialogFragment(Configuraciones configuracion) {
        configuraciones = configuracion;
        switch (configuracion.getCodigo()){
            case Constantes.CONFIGURACION_CONSULTA_ULTIMA_TEF:
                if(presentador.estadoProgress()){
                    presentador.dialogProgressBar(getResources().getString(R.string.progress_consultando_ultima_tef),
                            false);
                    presentador.consultarRespuestaDatafono(false);
                }
                break;
            case Constantes.CONFIGURACION_ANULAR_TRANSACION_TEF:
                if(presentador.estadoProgress()){
                    presentador.dialogProgressBar(getResources().getString(R.string.progress_consultando_ultima_tef),
                            false);
                    presentador.consultarRespuestasTef();
                }
                break;
        }
    }

    @Override
    public void respuestaDatafono(CuerpoRespuestaDatafono respuestaDatafono) {
        VistaDialogFragmentMostrarInfo vistaDialogFragmentMostrarInfo =
                new VistaDialogFragmentMostrarInfo(R.drawable.ultima_tef_dos, configuraciones.getNombre(),
                        respuestaDatafono.toStringClaveValor(), Constantes.ACCION_TEF);
        vistaDialogFragmentMostrarInfo.show(getSupportFragmentManager(), "MostrarInfoClaveValor");
    }

    @Override
    public void respuestaActualizarAnulacion() {
        msjCustomDosAccionesDialogFragment.dismiss();
        presentador.cerrarDialogAnularTef();
    }

    @Override
    public void sendInputItemAnularSelectDialogFragment(RespuestaCompletaTef respuestaDatafono) {
        msjCustomDosAccionesDialogFragment = new VistaMsjCustomDosAccionesDialogFragment(R.drawable.anular_tef_dos,
                String.format(getResources().getString(R.string.seleccione_opcion_tef), respuestaDatafono.getRespuestaTef().getRecibo()),
                getResources().getString(R.string.texto_seleccione_opcion_tef), "",
                getResources().getString(R.string.positivo_anulacion),
                getResources().getString(R.string.negativo_anulacion), Constantes.ACCION_ANULAR_TEF, true);
        msjCustomDosAccionesDialogFragment.show(getSupportFragmentManager(), "MsjCustomDosDialogFragment");
        msjCustomDosAccionesDialogFragment.setVistaConsultaCliente(this);
        msjCustomDosAccionesDialogFragment.setCuerpoRespuestaDatafono(respuestaDatafono.getRespuestaTef());
        msjCustomDosAccionesDialogFragment.setRespuestaCompletaTef(respuestaDatafono);
        respuestaCompleta = respuestaDatafono;
    }

    public void mostrarInfoTef(CuerpoRespuestaDatafono respuestaDatafono){
        VistaDialogFragmentMostrarInfo vistaDialogFragmentMostrarInfo =
                new VistaDialogFragmentMostrarInfo(R.drawable.anular_tef_dos, "Información Tef",
                        respuestaDatafono.toStringClaveValor(), Constantes.ACCION_TEF);
        vistaDialogFragmentMostrarInfo.show(getSupportFragmentManager(), "MostrarInfoClaveValor");
    }

    public void anularTransaccionTef(RespuestaCompletaTef respuestaDatafono){
        if(presentador.estadoProgress()){
            presentador.dialogProgressBar(getResources().getString(R.string.progress_anulando), false);
            presentador.anularTransaccionTef(respuestaDatafono);
        }
    }

    public void reintentarActualizacionTransaccion(){
        if(presentador.estadoProgress()){
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.actualizarTransaccion(respuestaCompleta);
        }
    }
}