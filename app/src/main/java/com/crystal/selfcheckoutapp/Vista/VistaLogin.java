package com.crystal.selfcheckoutapp.Vista;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaLogin;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaLogin;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCajaSelectDialogFragment;
import com.zebra.scannercontrol.SDKHandler;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaLogin extends AppCompatActivity implements IVistaLogin.Vista, VistaCajaSelectDialogFragment.OnInputListener{

    private Button btnLogin;
    private Context contexto;
    private TextView etUsuarioLogin, etContrasenaLogin;
    private PresentadorVistaLogin presentador;
    SDKHandler sdkHandler;
    private static final String ACTION_USB_PERMISSION = "com.crystal.selfcheckoutapp.USB_PERMISSION";
    private UsbManager usbManager;
    private UsbDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_login);

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

        if(SPM.getString(Constantes.API_POSSERVICE_URL) == null){
            SPM.setString(Constantes.API_POSSERVICE_URL, Constantes.URL_BASE_INTERMEDIA);
        }

        if(SPM.getString(Constantes.API_POSSERVICE_URL_NN) == null){
            SPM.setString(Constantes.API_POSSERVICE_URL_NN, Constantes.URL_BASE_NN);
        }

        //Valido si la aplicación tiene los permisos para escribir en la memoria externa
        int estadoPermiso = ContextCompat.checkSelfPermission(VistaLogin.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        permisos(estadoPermiso);

        if (!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(intent);
        }
    }

    private void requestUsbPermission(UsbDevice device) {
        if (!usbManager.hasPermission(device)) {
            // Solicitar permiso
            PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            usbManager.requestPermission(device, permissionIntent);
        }
    }

    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            // Call method to set up device communication
                        }
                    } else {
                        // Permission denied for device
                    }
                }
            }
        }
    };

    private void permisos(int permiso) {
        if(permiso == PackageManager.PERMISSION_GRANTED){
            inicio();
        }else{
            dialogPermisos();
        }
    }

    private void inicio() {
        LogFile.adjuntarLogTitulo("Abriendo [App SelfCheckout]");
        LogFile.adjuntarLogTitulo("Abriendo [Pantalla Login]");
        //Inicializar variables de interfaz o otras variables.
        inicializar();

        /*En este metodo estan los eventos de los botones o si se usa otro evento en general
        ejemplo (OnClick).*/
        eventos();

        //Permisos USB
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(usbReceiver, filter);

        // Check for connected devices
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            if (device.getVendorId() == 5380 && device.getProductId() == 137) {
                requestUsbPermission(device);
            }
        }
    }

    private void dialogPermisos() {
        SweetAlertDialog alertDialog = new SweetAlertDialog(VistaLogin.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);

        alertDialog.setTitleText(getResources().getString(R.string.informacion))
                .setCustomImage(R.drawable.crystal)
                .setContentText(getResources().getString(R.string.alertaPermisosMemoriaInterna))
                .setCancelButton(getResources().getString(R.string.salir), sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    finish();
                })
                .setConfirmButton(getResources().getString(R.string.aceptar), sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    ActivityCompat.requestPermissions(VistaLogin.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE},
                            Constantes.PERMISO_WRITE_EXTERNAL_STORAGE);
                })
                .setCancelable(false);

        alertDialog.show();
    }

    private void inicializar() {
        accionLector(Constantes.ENCENDER_LECTOR);
        contexto = VistaLogin.this;
        SPM.setBoolean(Constantes.PARAM_GENERAR_CERTIFICADO_AUTOMATICO, false);

        presentador = new PresentadorVistaLogin(contexto, this, getSupportFragmentManager());

        etUsuarioLogin = findViewById(R.id.etUsuarioLogin);
        etContrasenaLogin = findViewById(R.id.etContrasenaLogin);

        btnLogin = findViewById(R.id.btnIniciarSesion);
    }

    private void accionLector(int accion) {
        if(accion == Constantes.APAGAR_LECTOR){
            try{
                Intent intent = new Intent();
                intent.setClassName("com.zebra.zap3", "reader.api");
                intent.putExtra("state", "off");
                startActivity(intent);
            }catch (Exception ex){
                Log.e("LOGCAT", ex.getMessage());
            }
        }else if(accion == Constantes.ENCENDER_LECTOR){
            try{
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setClassName("com.zebra.zap3", "reader.api");
                i.putExtra("state", "on");
                i.putExtra("time", "1000");
                startActivityForResult(i, 1);
            }catch (Exception ex){
                Log.e("LOGCAT", ex.getMessage());
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            assert data != null;
            String result = data.getStringExtra("datos");
        }
    }

    private void eventos() {
        btnLogin.setOnClickListener(this::iniciarSesion);

        /*Detecta si se aprento el ENTER*/
        etContrasenaLogin.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                btnLogin.callOnClick();
                return true;
            }
            return false;
        });

        etContrasenaLogin.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                btnLogin.callOnClick();
            }
            return false;
        });
    }

    public void iniciarSesion(View view) {
        String usuario = etUsuarioLogin.getText().toString();
        String contrasena = etContrasenaLogin.getText().toString();

        if(usuario.isEmpty()){
            Utilidades.mjsToast(getResources().getString(R.string.usuario_requerido), Constantes.TOAST_TYPE_ERROR,
                    Toast.LENGTH_SHORT, contexto);
        }else if(contrasena.isEmpty()){
            Utilidades.mjsToast(getResources().getString(R.string.contrasena_requerido), Constantes.TOAST_TYPE_ERROR,
                    Toast.LENGTH_SHORT, contexto);
        }else if(usuario.equals("1234") && contrasena.equals("1234")){
            presentador.dialogCambiarUrl();
        }else{
            if(presentador.estadoProgress()){
                presentador.dialogProgressBar(getResources().getString(R.string.progress_iniciando_sesion), false);
                presentador.iniciarSesion(usuario, contrasena, true);
            }
        }
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        Utilidades.sweetAlert(titulo, mensaje, SweetAlertDialog.ERROR_TYPE, contexto);
        switch (servicio){
            case Constantes.SERVICIO_LOGIN:
                SPM.setString(Constantes.USER_NAME, "");
                etUsuarioLogin.setText("");
                etContrasenaLogin.setText("");
                etUsuarioLogin.requestFocus();
                break;
            case Constantes.SERVICIO_CAJAS:
            case Constantes.SERVICIO_CONSECUTIVO_FISCAL:
            case Constantes.SERVICIO_APERTURA_CAJA:
            case Constantes.SERVICIO_PARAMETROS:
        }
        presentador.ocultarDialogProgressBar();
    }

    @Override
    public void vistaConsultaCliente() {
        presentador.ocultarDialogProgressBar();
        if(Utilidades.comprobarInfoDatafono()){
            if(Utilidades.comprobarInfoDatafonoEmpty()){
                Intent intent = new Intent(contexto, VistaConsultaCliente.class);
                startActivity(intent);
                finish();
            }else{
                msjErrorDatafono();
            }
        }else{
            msjErrorDatafono();
        }
    }

    public void msjErrorDatafono(){
        Utilidades.sweetAlert(getResources().getString(R.string.informacion),
                "Configure el datáfono para el buen funcionamiento del autopago",
                SweetAlertDialog.WARNING_TYPE, contexto);
        etUsuarioLogin.setText("");
        etContrasenaLogin.setText("");
        etUsuarioLogin.requestFocus();
    }

    @Override
    public void vistaConfiguracion() {
        presentador.ocultarDialogProgressBar();
        Intent intent = new Intent(contexto, VistaConfiguracion.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constantes.PERMISO_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                inicio();
            } else {
                finish();
            }
        }
    }

    @Override
    public void sendInputItemCajaSelectDialogFragment(Caja caja) {
        presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                false);
        presentador.consecutivoFiscal(caja.getCodigoCaja());
    }
}