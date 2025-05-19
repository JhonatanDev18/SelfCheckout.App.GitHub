package com.crystal.selfcheckoutapp.Vista;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class VistaSplashScreenInicio extends AppCompatActivity {
    private LottieAnimationView lottieAnimationInicio;
    private Timer tiempo;
    private TimerTask tarea;
    private Animation animacionAbajo;
    private TextView tvNombreApp;
    private Utilidades util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_splash_screen_inicio);

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

        animaciones();
        incializar();
        iniciarTarea();

        tiempo = new Timer();
        tiempo.schedule(tarea, 4000);
    }

    private void animaciones() {
        animacionAbajo = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
    }

    private void iniciarTarea() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(VistaSplashScreenInicio.this, VistaLogin.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private void incializar() {
        SPM.setString(Constantes.LENGUAJE_APP, "es");
        util = new Utilidades(VistaSplashScreenInicio.this);
        tvNombreApp = findViewById(R.id.tvNombreApp);
        tvNombreApp.setAnimation(animacionAbajo);

        lottieAnimationInicio = findViewById(R.id.lottieAnimationInicio);
        lottieAnimationInicio.setAnimation(getResources().getString(R.string.animacion_splash_inicio2));
        lottieAnimationInicio.playAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        util.vozAndroid(getResources().getString(R.string.bienvenido_al_autopago));
    }
}