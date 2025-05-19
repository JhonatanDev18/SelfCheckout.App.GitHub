package com.crystal.selfcheckoutapp.Vista;

import androidx.appcompat.app.AppCompatActivity;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaDatafono;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.Parametro;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaDatafono;
import com.crystal.selfcheckoutapp.R;
import com.google.android.material.textfield.TextInputLayout;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaDatafono extends AppCompatActivity implements IVistaDatafono.Vista {

    private Button btnPruebaDatafono, btnSalirDatafono;
    private Context contexto;
    private TextInputLayout tliPassSupervisor;
    private EditText etUsuarioDatafono, etPassDatafono, etCodigoDatafono,etCodigoUnicoDatafono, etPassSupervisor;
    private PresentadorVistaDatafono presentador;
    private RadioGroup rgOpcionesDatafono;
    private RadioButton rbCredibanco, rbRedeban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_datafono);

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

        validarTipoDatafono();
    }

    private void validarTipoDatafono() {
        if(SPM.getString(Constantes.IS_REDEBAN) != null){
            if(Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN))){
                llenarDatosRedeban();
                SPM.setString(Constantes.IS_REDEBAN, "TRUE");
                rbRedeban.setChecked(true);
            }else{
                llenarDatosCredibanco();
                SPM.setString(Constantes.IS_REDEBAN, "FALSE");
                rbCredibanco.setChecked(true);
            }
        }else{
            llenarDatosCredibanco();
            SPM.setString(Constantes.IS_REDEBAN, "FALSE");
            rbCredibanco.setChecked(true);
        }
    }

    private void inicializar() {
        contexto = VistaDatafono.this;
        presentador = new PresentadorVistaDatafono(contexto, this, getSupportFragmentManager());
        btnPruebaDatafono = findViewById(R.id.btnPruebaDatafono);
        btnSalirDatafono = findViewById(R.id.btnSalirDatafono);
        etUsuarioDatafono = findViewById(R.id.etUsuarioDatafono);
        etPassDatafono = findViewById(R.id.etPassDatafono);
        etCodigoDatafono = findViewById(R.id.etCodigoDatafono);
        etCodigoUnicoDatafono = findViewById(R.id.etCodigoUnicoDatafono);
        rgOpcionesDatafono = findViewById(R.id.rgOpcionesDatafono);
        rbCredibanco = findViewById(R.id.rbCredibanco);
        rbRedeban = findViewById(R.id.rbRedeban);
        etPassSupervisor = findViewById(R.id.etPassSupervisor);
        tliPassSupervisor = findViewById(R.id.tliPassSupervisor);
    }

    private void eventos() {
        btnPruebaDatafono.setOnClickListener(this::pruebaDatafono);
        btnSalirDatafono.setOnClickListener(this::regresarConfiguracion);

        rgOpcionesDatafono.setOnCheckedChangeListener((group, checkedId) -> {
            if(isRedeban()){
                llenarDatosRedeban();
                SPM.setString(Constantes.IS_REDEBAN, "TRUE");
            }else{
                llenarDatosCredibanco();
                SPM.setString(Constantes.IS_REDEBAN, "FALSE");
            }
        });

        etUsuarioDatafono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isRedeban()){
                    SPM.setString(Constantes.USUARIO_DATAFONO_RB,etUsuarioDatafono.getText().toString());
                }else{
                    SPM.setString(Constantes.USUARIO_DATAFONO,etUsuarioDatafono.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassDatafono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isRedeban()){
                    SPM.setString(Constantes.PASS_DATAFONO_RB,etPassDatafono.getText().toString());
                }else{
                    SPM.setString(Constantes.PASS_DATAFONO,etPassDatafono.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCodigoDatafono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isRedeban()){
                    SPM.setString(Constantes.CODIGO_DATAFONO_RB,etCodigoDatafono.getText().toString());
                }else{
                    SPM.setString(Constantes.CODIGO_DATAFONO,etCodigoDatafono.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCodigoUnicoDatafono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isRedeban()){
                    SPM.setString(Constantes.CODIGO_UNICO_DATAFONO_RB,etCodigoUnicoDatafono.getText().toString());
                }else{
                    SPM.setString(Constantes.CODIGO_UNICO_DATAFONO,etCodigoUnicoDatafono.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassSupervisor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isRedeban()){
                    SPM.setString(Constantes.PASS_SUPERVISOR_RB,etPassSupervisor.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void llenarDatosRedeban(){
        tliPassSupervisor.setVisibility(View.VISIBLE);

        if(SPM.getString(Constantes.USUARIO_DATAFONO_RB) != null){
            etUsuarioDatafono.setText(SPM.getString(Constantes.USUARIO_DATAFONO_RB));
        }else{
            etUsuarioDatafono.setText("");
        }

        if(SPM.getString(Constantes.PASS_DATAFONO_RB) != null){
            etPassDatafono.setText(SPM.getString(Constantes.PASS_DATAFONO_RB));
        }else{
            etPassDatafono.setText("");
        }

        if(SPM.getString(Constantes.CODIGO_DATAFONO_RB) != null){
            etCodigoDatafono.setText(SPM.getString(Constantes.CODIGO_DATAFONO_RB));
        }else{
            etCodigoDatafono.setText("");
        }

        if(SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB) != null){
            etCodigoUnicoDatafono.setText(SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB));
        }else{
            etCodigoUnicoDatafono.setText("");
        }

        if(SPM.getString(Constantes.PASS_SUPERVISOR_RB) != null){
            etPassSupervisor.setText(SPM.getString(Constantes.PASS_SUPERVISOR_RB));
        }else{
            etPassSupervisor.setText("");
        }
    }

    private void llenarDatosCredibanco(){
        tliPassSupervisor.setVisibility(View.GONE);

        if(SPM.getString(Constantes.USUARIO_DATAFONO) != null){
            etUsuarioDatafono.setText(SPM.getString(Constantes.USUARIO_DATAFONO));
        }else{
            etUsuarioDatafono.setText("");
        }

        if(SPM.getString(Constantes.PASS_DATAFONO) != null){
            etPassDatafono.setText(SPM.getString(Constantes.PASS_DATAFONO));
        }else{
            etPassDatafono.setText("");
        }

        if(SPM.getString(Constantes.CODIGO_DATAFONO) != null){
            etCodigoDatafono.setText(SPM.getString(Constantes.CODIGO_DATAFONO));
        }else{
            etCodigoDatafono.setText("");
        }

        if(SPM.getString(Constantes.CODIGO_UNICO_DATAFONO) != null){
            etCodigoUnicoDatafono.setText(SPM.getString(Constantes.CODIGO_UNICO_DATAFONO));
        }else{
            etCodigoUnicoDatafono.setText("");
        }
    }

    private boolean isRedeban() {
        return rbRedeban.isChecked();
    }

    private void regresarConfiguracion(View view){
        presentador.guardarPerifericos();
    }

    private void validaConfiDatafono() {
        String usuario = etUsuarioDatafono.getText().toString().trim();
        String pass = etPassDatafono.getText().toString().trim();
        String codigo = etCodigoDatafono.getText().toString().trim();
        String codigoUnico = etCodigoUnicoDatafono.getText().toString().trim();

        if(isRedeban()){
            String passSupervisor = etPassSupervisor.getText().toString().trim();

            if(usuario.isEmpty()){
                SPM.setString(Constantes.USUARIO_DATAFONO_RB, "");
            }else{
                SPM.setString(Constantes.USUARIO_DATAFONO_RB, usuario);
            }

            if(pass.isEmpty()){
                SPM.setString(Constantes.PASS_DATAFONO_RB, "");
            }else{
                SPM.setString(Constantes.PASS_DATAFONO_RB, pass);
            }

            if(codigo.isEmpty()){
                SPM.setString(Constantes.CODIGO_DATAFONO_RB, "");
            }else{
                SPM.setString(Constantes.CODIGO_DATAFONO_RB, codigo);
            }

            if(codigoUnico.isEmpty()){
                SPM.setString(Constantes.CODIGO_UNICO_DATAFONO_RB, "");
            }else{
                SPM.setString(Constantes.CODIGO_UNICO_DATAFONO_RB, codigoUnico);
            }

            if(passSupervisor.isEmpty()){
                SPM.setString(Constantes.PASS_SUPERVISOR_RB, "");
            }else{
                SPM.setString(Constantes.PASS_SUPERVISOR_RB, passSupervisor);
            }
        }else{
            if(usuario.isEmpty()){
                SPM.setString(Constantes.USUARIO_DATAFONO, "");
            }else{
                SPM.setString(Constantes.USUARIO_DATAFONO, usuario);
            }

            if(pass.isEmpty()){
                SPM.setString(Constantes.PASS_DATAFONO, "");
            }else{
                SPM.setString(Constantes.PASS_DATAFONO, pass);
            }

            if(codigo.isEmpty()){
                SPM.setString(Constantes.CODIGO_DATAFONO, "");
            }else{
                SPM.setString(Constantes.CODIGO_DATAFONO, codigo);
            }

            if(codigoUnico.isEmpty()){
                SPM.setString(Constantes.CODIGO_UNICO_DATAFONO, "");
            }else{
                SPM.setString(Constantes.CODIGO_UNICO_DATAFONO, codigoUnico);
            }
        }
    }

    private void pruebaDatafono(View view){
        try{
            if(etUsuarioDatafono.getText().toString().isEmpty()){
                Utilidades.mjsToast("Ingrese el usuario para el datáfono", Constantes.TOAST_TYPE_INFO,
                        Toast.LENGTH_LONG, contexto);
            }else if(etPassDatafono.getText().toString().isEmpty()){
                Utilidades.mjsToast("Ingrese la contraseña para el datáfono", Constantes.TOAST_TYPE_INFO,
                        Toast.LENGTH_LONG, contexto);
            }else if(etCodigoDatafono.getText().toString().isEmpty()){
                Utilidades.mjsToast("Ingrese el código del datáfono", Constantes.TOAST_TYPE_INFO,
                        Toast.LENGTH_LONG, contexto);
            }else if(etCodigoUnicoDatafono.getText().toString().isEmpty()) {
                Utilidades.mjsToast("Ingrese el código unico del datáfono", Constantes.TOAST_TYPE_INFO,
                        Toast.LENGTH_LONG, contexto);
            }else{
                presentador.dialogProgressBar(getResources().getString(R.string.progress_procesando_compra_datafono),
                        false);
                presentador.generarCompraDatafono();
            }
        }catch (Exception e){
            Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void respuestaGuardarPerifericos(ResponseGuardarPerifericos respuesta) {
        if(respuesta.isEsValida()){
            irVistaConfiguracion();
        }else{
            Utilidades.mjsToast(respuesta.getMensaje(), Constantes.TOAST_TYPE_ERROR,
                    Toast.LENGTH_LONG, contexto);
            irVistaConfiguracion();
        }
    }

    public void irVistaConfiguracion(){
        finish();
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        Utilidades.sweetAlert(getResources().getString(R.string.error),
                mensaje + " " +servicio, SweetAlertDialog.ERROR_TYPE, contexto);
        presentador.ocultarDialogProgressBar();
    }
}