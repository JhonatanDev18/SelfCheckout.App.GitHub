package com.crystal.selfcheckoutapp.Vista;

import androidx.appcompat.app.AppCompatActivity;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaDetalleCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.Departamento;
import com.crystal.selfcheckoutapp.Modelo.clases.DepartamentoLista;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaClienteDetalle;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaDepartamentosDialogFrangment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCiudadesDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaInsertarEditTextDialogFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.google.android.material.textfield.TextInputLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaDetalleCliente extends AppCompatActivity implements IVistaDetalleCliente.Vista,
        VistaDepartamentosDialogFrangment.OnInputListener, VistaCiudadesDialogFragment.OnInputListener {

    private TextView tvTituloDetalleCliente;
    private int diaCumple = 0, mesCumple= 0, anoCumple = 0;
    private boolean clienteNuevo;
    private Cliente cliente;
    private Cliente clienteN;
    private EditText etCedulaDetalle, etNombreDetalle, etApellidoDetalle, etFechaNacimientoDetalle;
    private TextInputLayout tliNombreDetalle,tliApellidoDetalle, tliCorreoDetalle, tliCelularDetalle,
            tliDireccionDetalle;
    private EditText etCorreoDetalle, etCelularDetalle, etDireccionDetalle, etDepartamentoDetalle;
    private EditText etCiudadDetalle;
    private ImageView ivCalendarioFechaNacimientoDetalle;
    private Button btnAtrasDetalleCliente, btnContinuar;
    private Context contexto;
    private CheckBox cbAutorizacion;
    private Spinner spinnerTipoDocumentoDetalle;
    private List<String> listTiposDocumento;
    private RadioButton rbMasculinoDetalle, rbFemeninoDetalle;
    private RadioGroup rgGeneroDetalle;
    private PresentadorVistaClienteDetalle presentador;
    private List<String> departamentosList;
    private DepartamentoLista departamentos;
    private boolean isTipoDocumento, insertarHabeasData;
    private ImageView ivModificarNombre, ivModificarApellido, ivModificarDepartamento, ivModificarCiudad, ivModificarCorreo, ivModificarCelular,
            ivModificarDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPM.setInt(Constantes.PROCESO_ACTIVITY, Constantes.VISTA_DETALLE_CLIENTE);
        setContentView(R.layout.vista_cliente_detalle);

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

        //Cargar departamentos
        cargarDepartamentos();

        //Cargar tipos de documentos
        cargarTiposDocumentos();

        //Intents
        cargarIntents();
    }

    private void cargarDepartamentos() {
        departamentosList = new ArrayList<>();

        for(Departamento d:departamentos.getListDepartamentos()){
            departamentosList.add(d.getDepartamento());
        }

        Set<String> set = new HashSet<>(departamentosList);
        departamentosList.clear();
        departamentosList.addAll(set);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void incializar() {
        clienteN = new Cliente();
        contexto = VistaDetalleCliente.this;
        departamentos = new DepartamentoLista();
        presentador = new PresentadorVistaClienteDetalle(this, contexto, getSupportFragmentManager());

        spinnerTipoDocumentoDetalle = findViewById(R.id.spinnerTipoDocumentoDetalle);
        btnAtrasDetalleCliente = findViewById(R.id.btnAtrasDetalleCliente);
        tvTituloDetalleCliente = findViewById(R.id.tvTituloDetalleCliente);
        etCedulaDetalle = findViewById(R.id.etCedulaDetalle);
        etNombreDetalle = findViewById(R.id.etNombreDetalle);
        etApellidoDetalle = findViewById(R.id.etApellidoDetalle);
        rgGeneroDetalle = findViewById(R.id.rgGeneroDetalle);
        rbMasculinoDetalle = findViewById(R.id.rbMasculinoDetalle);
        rbFemeninoDetalle = findViewById(R.id.rbFemeninoDetalle);
        etFechaNacimientoDetalle = findViewById(R.id.etFechaNacimientoDetalle);
        etCorreoDetalle = findViewById(R.id.etCorreoDetalle);
        etCelularDetalle = findViewById(R.id.etCelularDetalle);
        etDireccionDetalle = findViewById(R.id.etDireccionDetalle);
        etDepartamentoDetalle = findViewById(R.id.etDepartamentoDetalle);
        etCiudadDetalle = findViewById(R.id.etCiudadDetalle);
        ivCalendarioFechaNacimientoDetalle = findViewById(R.id.ivCalendarioFechaNacimientoDetalle);
        ivModificarCorreo = findViewById(R.id.ivModificarCorreo);
        tliCorreoDetalle = findViewById(R.id.tliCorreoDetalle);
        ivModificarCelular = findViewById(R.id.ivModificarCelular);
        tliCelularDetalle = findViewById(R.id.tliCelularDetalle);
        ivModificarDireccion = findViewById(R.id.ivModificarDireccion);
        tliDireccionDetalle = findViewById(R.id.tliDireccionDetalle);
        ivModificarNombre = findViewById(R.id.ivModificarNombre);
        ivModificarApellido = findViewById(R.id.ivModificarApellido);

        tliNombreDetalle = findViewById(R.id.tliNombreDetalle);
        tliApellidoDetalle = findViewById(R.id.tliApellidoDetalle);

        ivModificarDepartamento = findViewById(R.id.ivModificarDepartamento);
        ivModificarCiudad = findViewById(R.id.ivModificarCiudad);

        cbAutorizacion = findViewById(R.id.cbAutorizacion);
        btnContinuar = findViewById(R.id.btnContinuar);
    }

    private void cargarTiposDocumentos() {
        listTiposDocumento = new ArrayList<>();
        listTiposDocumento.add(getResources().getString(R.string.texto_tipo_documento));
        listTiposDocumento.addAll(Arrays.asList(getResources().getStringArray(R.array.documentos_identidad)));

        ArrayAdapter<String> arrayAdapterTiposDocumentos =
                new ArrayAdapter(contexto, R.layout.spinner_items, listTiposDocumento);
        spinnerTipoDocumentoDetalle.setAdapter(arrayAdapterTiposDocumentos);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void eventos() {
        btnAtrasDetalleCliente.setOnClickListener(this::vistaConsultaCliente);
        btnContinuar.setOnClickListener(this::continuar);
        ivCalendarioFechaNacimientoDetalle.setOnClickListener(this::calendarioFechaNacimiento);

        ivModificarDepartamento.setOnClickListener(this::mostrarDepartamentos);
        ivModificarCiudad.setOnClickListener(this::mostrarCiudades);
        ivModificarNombre.setOnClickListener(v -> {
            modificarEditText(tliNombreDetalle, etNombreDetalle, false);
        });
        ivModificarApellido.setOnClickListener(v ->{
            modificarEditText(tliApellidoDetalle, etApellidoDetalle, false);
        });
        ivModificarCorreo.setOnClickListener(v -> {
            modificarEditText(tliCorreoDetalle, etCorreoDetalle, false);
        });
        ivModificarCelular.setOnClickListener(v -> {
            modificarEditText(tliCelularDetalle, etCelularDetalle, true);
        });
        ivModificarDireccion.setOnClickListener(v -> {
            modificarEditText(tliDireccionDetalle, etDireccionDetalle, false);
        });

        etNombreDetalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clienteN.setNombre(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etApellidoDetalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clienteN.setApellido(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rbMasculinoDetalle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                clienteN.setSexo("M");
            }
        });

        rbFemeninoDetalle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                clienteN.setSexo("F");
            }
        });

        etFechaNacimientoDetalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clienteN.setDiaCumpleanos(Integer.toString(diaCumple));
                clienteN.setMesCumpleanos(Integer.toString(mesCumple));
                clienteN.setAnoCumpleanos(Integer.toString(anoCumple));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCorreoDetalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clienteN.setCorreo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCelularDetalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clienteN.setCelular(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etDireccionDetalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clienteN.setDireccion(s.toString());
                clienteN.setDireccion2("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinnerTipoDocumentoDetalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isTipoDocumento = position != 0;
                switch (position){
                    case 1:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_CC);
                        break;
                    case 2:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_NIT);
                        break;
                    case 3:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_PASAPORTE);
                        break;
                    case 4:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_CICR);
                        break;
                    case 5:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_CJ);
                        break;
                    case 6:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_DIMEX);
                        break;
                    case 7:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_NITE);
                        break;
                    case 8:
                        clienteN.setTipoDocumento(Constantes.DOCUMENTO_EXTRANJERO);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void modificarEditText(TextInputLayout textInputLayout, EditText editText, boolean isNumerico){
        VistaInsertarEditTextDialogFragment bottomSheetFragment = new VistaInsertarEditTextDialogFragment(textInputLayout,
                editText, isNumerico);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void cargarIntents() {
        if(getIntent().getExtras().containsKey(getResources().getString(R.string.key_intent_cliente_nuevo))){
            insertarHabeasData = true;
            tvTituloDetalleCliente.setText(R.string.cliente_nuevo);
            clienteNuevo = true;
            etCedulaDetalle.setText(getIntent().getStringExtra(getResources().getString(R.string.key_intent_cliente_nuevo)));
            clienteN.setCedula(getIntent().getStringExtra(getResources().getString(R.string.key_intent_cliente_nuevo)));
        }else if(getIntent().getExtras().containsKey(getResources().getString(R.string.key_intent_cliente))){
            //Lógica para clientes antiguos
            cliente = (Cliente) getIntent().getSerializableExtra(getResources().getString(R.string.key_intent_cliente));
            clienteNuevo = false;

            if(cliente != null){
                cargarClienteAntiguo();
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void cargarClienteAntiguo() {
        switch (cliente.getTipoDocumento()){
            case Constantes.DOCUMENTO_CC:
                spinnerTipoDocumentoDetalle.setSelection(1);
                break;
            case Constantes.DOCUMENTO_NIT:
                spinnerTipoDocumentoDetalle.setSelection(2);
                break;
            case Constantes.DOCUMENTO_PASAPORTE:
                spinnerTipoDocumentoDetalle.setSelection(3);
                break;
            case Constantes.DOCUMENTO_CICR:
                spinnerTipoDocumentoDetalle.setSelection(4);
                break;
            case Constantes.DOCUMENTO_CJ:
                spinnerTipoDocumentoDetalle.setSelection(5);
                break;
            case Constantes.DOCUMENTO_DIMEX:
                spinnerTipoDocumentoDetalle.setSelection(6);
                break;
            case Constantes.DOCUMENTO_NITE:
                spinnerTipoDocumentoDetalle.setSelection(7);
                break;
            case Constantes.DOCUMENTO_EXTRANJERO:
                spinnerTipoDocumentoDetalle.setSelection(8);
                break;
        }
        spinnerTipoDocumentoDetalle.setEnabled(false);

        etCedulaDetalle.setText(cliente.getCedula(false));

        etNombreDetalle.setText(cliente.getNombre());
        etNombreDetalle.setEnabled(false);
        etNombreDetalle.setTextColor(getResources().getColor(R.color.negro_traslusido));
        tliNombreDetalle.setBackground(getResources().getDrawable(R.drawable.edittext_disabled_background));
        tliNombreDetalle.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.negro_traslusido)));

        etApellidoDetalle.setText(cliente.getApellido());
        etApellidoDetalle.setEnabled(false);
        etApellidoDetalle.setTextColor(getResources().getColor(R.color.negro_traslusido));
        tliApellidoDetalle.setBackground(getResources().getDrawable(R.drawable.edittext_disabled_background));
        tliApellidoDetalle.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.negro_traslusido)));

        switch (cliente.getSexo()){
            case "M":
                rbMasculinoDetalle.setChecked(true);
                break;
            case "F":
                rbFemeninoDetalle.setChecked(true);
                break;
        }

        rgGeneroDetalle.setEnabled(false);
        rbMasculinoDetalle.setEnabled(false);
        rbFemeninoDetalle.setEnabled(false);

        String fechaNacimiento = cliente.getDiaCumpleanos()+" de "+
                Utilidades.convertirNumeroMes(Integer.parseInt(cliente.getMesCumpleanos()))+" de "+cliente.getAnoCumpleanos();
        etFechaNacimientoDetalle.setText(fechaNacimiento);
        etFechaNacimientoDetalle.setEnabled(false);
        ivCalendarioFechaNacimientoDetalle.setVisibility(View.GONE);

        diaCumple = Integer.parseInt(cliente.getDiaCumpleanos());
        mesCumple = Integer.parseInt(cliente.getMesCumpleanos());
        anoCumple = Integer.parseInt(cliente.getAnoCumpleanos());

        etCorreoDetalle.setText(cliente.getCorreo());
        etCelularDetalle.setText(cliente.getCelular());
        etDireccionDetalle.setText(cliente.getDireccion());

        String departamentoInicioNombre = "";
        String ciudad = "";

        for(Departamento d:departamentos.getListDepartamentos()){
            if(cliente.getCodigoPostal().equals(d.getMunicipio_code()) && cliente.getRegionId().equals(d.getRegion_code())){
                ciudad = d.getMunicipio_name();
                departamentoInicioNombre = d.getDepartamento();
            }
        }

        etDepartamentoDetalle.setText(departamentoInicioNombre.toUpperCase());
        etCiudadDetalle.setText(ciudad.toUpperCase());

        if(cliente.isEmpleado()){
            etCorreoDetalle.setEnabled(false);
            etCelularDetalle.setEnabled(false);
            etDireccionDetalle.setEnabled(false);
            etDepartamentoDetalle.setEnabled(false);
            etCiudadDetalle.setEnabled(false);
        }

        if(cliente.isAceptoHabeasData()){
            cbAutorizacion.setChecked(true);
        }else{
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando), false);
            presentador.consultarHabeasData(cliente.getCedula(false), cliente.getTipoDocumento());
        }
    }

    private void vistaConsultaCliente(View view){
        Intent intent = new Intent(contexto, VistaConsultaCliente.class);
        startActivity(intent);
        finish();
    }

    private void calendarioFechaNacimiento(View v){
        String fechaNacimiento = etFechaNacimientoDetalle.getText().toString();
        int dia, mes, ano;
        final Calendar calendar = Calendar.getInstance();

        if(!fechaNacimiento.isEmpty()){
            dia = diaCumple;
            mes = mesCumple-1;
            ano = anoCumple;
        }else{
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            ano = calendar.get(Calendar.YEAR);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(contexto, (view, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth+" de "+Utilidades.convertirNumeroMes((month+1))+" de "+year;

            if(!fechaNacimiento.isEmpty()){
                etFechaNacimientoDetalle.setText("");
            }

            diaCumple = dayOfMonth;
            mesCumple = (month+1);
            anoCumple = year;

            etFechaNacimientoDetalle.setText(fecha);
            etFechaNacimientoDetalle.setEnabled(false);
        },ano, mes, dia);
        datePickerDialog.setCancelable(false);

        datePickerDialog.show();
    }

    private void mostrarDepartamentos(View view){
        VistaDepartamentosDialogFrangment dialogDepatamentos = new VistaDepartamentosDialogFrangment(departamentosList);
        dialogDepatamentos.show(getSupportFragmentManager(),"DepartamentosDialog");
    }

    private void mostrarCiudades(View view){
        String departamento = etDepartamentoDetalle.getText().toString();
        if(!departamento.equals("")){
            List<Departamento> listaCiudades = new ArrayList<>();

            for(int x = 0; x < departamentos.getListDepartamentos().size(); x++){
                if(departamento.equals(departamentos.getListDepartamentos().get(x).getDepartamento().toUpperCase())){
                    listaCiudades.add(departamentos.getListDepartamentos().get(x));
                }
            }

            VistaCiudadesDialogFragment dialogCiudades = new VistaCiudadesDialogFragment(listaCiudades);
            dialogCiudades.show(getSupportFragmentManager(),"CiudadesDialog");
        }else{
            Utilidades.mjsToast(getResources().getString(R.string.departamento_requerido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_SHORT, contexto);
        }
    }

    private void continuar(View view){
        if(presentador.estadoProgress()){
            if(validarCamposCliente()){
                if(clienteNuevo){
                    presentador.dialogProgressBar(getResources().getString(R.string.progress_registrando_cliente),
                            false);
                    presentador.registrarCliente(clienteN);
                }else{
                    if(validarActualizacionDatos()){
                        presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                                false);
                        presentador.actualizarCliente(cliente);
                    }else{
                        validarHabeasData(cliente);
                    }
                }
            }
        }
    }

    private boolean validarActualizacionDatos(){
        boolean camposActualizados = false;

        String nombre = etNombreDetalle.getText().toString().toUpperCase();
        String apellido = etApellidoDetalle.getText().toString().toUpperCase();
        String fechaNacimiento = etFechaNacimientoDetalle.getText().toString();
        String correo = etCorreoDetalle.getText().toString();
        String celular = etCelularDetalle.getText().toString();
        String direccion = etDireccionDetalle.getText().toString().toUpperCase();
        String departamento = etDepartamentoDetalle.getText().toString();
        String genero = "";

        if (rbMasculinoDetalle.isChecked()) {
            genero = "M";
        } else {
            if (rbFemeninoDetalle.isChecked()) {
                genero = "F";
            }
        }

        if(!nombre.equals(cliente.getNombre())){
            cliente.setNombre(nombre);
            camposActualizados = true;
        }

        if(!apellido.equals(cliente.getApellido())){
            cliente.setApellido(apellido);
            camposActualizados = true;
        }

        if(!fechaNacimiento.equals(cliente.getDiaCumpleanos()+" de "+
                Utilidades.convertirNumeroMes(Integer.parseInt(cliente.getMesCumpleanos()))+" de "+cliente.getAnoCumpleanos())){
            cliente.setDiaCumpleanos(Integer.toString(diaCumple));
            cliente.setMesCumpleanos(Integer.toString(mesCumple));
            cliente.setAnoCumpleanos(Integer.toString(anoCumple));
            camposActualizados = true;
        }

        if(!correo.equals(cliente.getCorreo())){
            cliente.setCorreo(correo);
            camposActualizados = true;
        }

        if(!celular.equals(cliente.getCelular())){
            cliente.setCelular(celular);
            camposActualizados = true;
        }

        if(!direccion.equals(cliente.getDireccion())){
            cliente.setDireccion(direccion);
            camposActualizados = true;
        }

        Departamento departamentoSelec = null;

        for(Departamento d:departamentos.getListDepartamentos()){
            if(cliente.getCodigoPostal().equals(d.getMunicipio_code()) && cliente.getRegionId().equals(d.getRegion_code())){
                departamentoSelec = d;
            }
        }

        if(departamentoSelec != null){
            if(!departamento.equals(departamentoSelec.getDepartamento())){
                cliente.setCiudad(departamentoSelec.getMunicipio_name());
                cliente.setCodigoPostal(departamentoSelec.getMunicipio_code());
                cliente.setRegionId(departamentoSelec.getRegion_code());
                camposActualizados = true;
            }
        }

        if(!genero.equals(cliente.getSexo())){
            cliente.setSexo(genero);
            camposActualizados = true;
        }

        return camposActualizados;
    }

    private boolean validarCamposCliente(){
        boolean camposValidos = true;

        String nombre = etNombreDetalle.getText().toString();
        String apellido = etApellidoDetalle.getText().toString();
        String fechaNacimiento = etFechaNacimientoDetalle.getText().toString();
        String correo = etCorreoDetalle.getText().toString();
        String celular = etCelularDetalle.getText().toString();
        String genero = "";

        if (rbMasculinoDetalle.isChecked()) {
            genero = "M";
        } else {
            if (rbFemeninoDetalle.isChecked()) {
                genero = "F";
            }
        }

        if(!isTipoDocumento){
            Utilidades.mjsToast(getResources().getString(R.string.seleccione_tipo_documento),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            camposValidos = false;
        }else if(nombre.equals("") || nombre.isEmpty()){
            Utilidades.mjsToast(getResources().getString(R.string.registro_nombre_requerido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            etNombreDetalle.requestFocus();
            camposValidos = false;
        }else if(apellido.equals("") || apellido.isEmpty()){
            Utilidades.mjsToast(getResources().getString(R.string.registro_apellido_requerido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            etApellidoDetalle.requestFocus();
            camposValidos = false;
        }else if(fechaNacimiento.equals("") || fechaNacimiento.isEmpty()){
            Utilidades.mjsToast(getResources().getString(R.string.registro_fecha_nacimiento_requerido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            ivCalendarioFechaNacimientoDetalle.callOnClick();
            camposValidos = false;
        }else if(diaCumple == 0 && mesCumple == 0 && anoCumple == 0){
            Utilidades.mjsToast(getResources().getString(R.string.registro_fecha_nacimiento_requerido_valida),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            ivCalendarioFechaNacimientoDetalle.callOnClick();
            camposValidos = false;
        } else if(Cliente.isMenorEdad(15, anoCumple)) {
            Utilidades.mjsToast(getResources().getString(R.string.registro_fecha_nacimiento_requerido_valido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            camposValidos = false;
        }else if(correo.isEmpty() && celular.isEmpty()){
            Utilidades.mjsToast(getResources().getString(R.string.registro_correo_y_celular_requerido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            etCorreoDetalle.requestFocus();
            camposValidos = false;
        }else if(!correo.isEmpty() && !Cliente.validarEmail(correo)){
            Utilidades.mjsToast(getResources().getString(R.string.registro_correo_requerido_valido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            etCorreoDetalle.requestFocus();
            camposValidos = false;
        }else if(genero.equals("")){
            Utilidades.mjsToast(getResources().getString(R.string.registro_genero_requerido),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            camposValidos = false;
        }else if(!cbAutorizacion.isChecked()){
            Utilidades.mjsToast(getResources().getString(R.string.texto_sin_autorizacion),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            camposValidos = false;
        }

        return camposValidos;
    }

    @Override
    public void vistaCompraCliente(Cliente cliente){
        cliente.setSeleccionoDescuento(false);
        Intent intent = new Intent(contexto, VistaCompraCliente.class);
        intent.putExtra(getResources().getString(R.string.key_intent_cliente), cliente);
        startActivity(intent);
        finish();
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        Utilidades.sweetAlert(titulo, mensaje, SweetAlertDialog.ERROR_TYPE, contexto);
        presentador.ocultarDialogProgressBar();
    }

    @Override
    public void respuestaActualizarCliente(Cliente cliente) {
        validarHabeasData(cliente);
    }

    @Override
    public void respuestaCrearCliente() {
        presentador.actualizarCliente(clienteN);
    }

    @Override
    public void respuestaInsertarHabeas(Cliente cliente) {
        vistaCompraCliente(cliente);
    }

    private void validarHabeasData(Cliente cliente){
        if(insertarHabeasData){
            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                    false);
            presentador.insertarHabeasData(cliente);
        }else{
            vistaCompraCliente(cliente);
        }
    }

    @Override
    public void respuestaConsultaHabeasData(boolean solicitarHabeas) {
        insertarHabeasData = solicitarHabeas;
        if(!solicitarHabeas){
            cbAutorizacion.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void sendInputDepatamentosDialogFrangment(String departamento) {
        etDepartamentoDetalle.setText(departamento.toUpperCase());
        etCiudadDetalle.setText("");
    }

    @Override
    public void sendInputCiudadesDialogFragment(Departamento departamento) {
        etCiudadDetalle.setText(departamento.getMunicipio_name().toUpperCase());
        clienteN.setCiudad(departamento.getMunicipio_name().toUpperCase());
        clienteN.setCodigoPostal(departamento.getMunicipio_code());
        clienteN.setRegionId(departamento.getRegion_code());
        clienteN.setPaisId(departamento.getPais());
    }
}