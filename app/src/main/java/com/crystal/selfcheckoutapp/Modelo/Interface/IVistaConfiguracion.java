package com.crystal.selfcheckoutapp.Modelo.Interface;


import android.view.View;

import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseTiendas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.ResponseCajas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseConsultarPerifericos;

import java.util.List;

public interface IVistaConfiguracion {
    interface Vista{
        void seleccionConfiguracion(int configuracion, View view);
        void respuestaConfigurarPerifericos(ResponseConsultarPerifericos respuesta);
        void respuestaConsultarTiendas(ResponseTiendas respuesta);
        void respuestaConsultarCajas(ResponseCajas respuesta, String tienda);
        void respuestaParametros(ResponseParametros respuesta, List<RequestParametros> listaParametros);
        void errorServicio(String titulo, String mensaje, int servicio);
    }

    interface Presentador{
        void configurarPerifericos(String tienda, String caja);
        void respuestaConfigurarPerifericos(ResponseConsultarPerifericos respuesta);
        void consultarTiendas();
        void consultarParametrosQRBancolombia(String tienda);
        void respuestaConsultarTiendas(ResponseTiendas respuesta);
        void consultarCajas(String tienda);
        void respuestaConsultarCajas(ResponseCajas respuesta, String tienda);
        void consultarParametros();
        void respuestaParametros(ResponseParametros respuesta, List<RequestParametros> listaParametros);
        void errorServicio(String titulo, String mensaje, int servicio);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void ocultarDialogProgressBar();
        boolean estadoProgress();
    }

    interface Modelo{
        void apiConsultarPerifericos(String tienda, String caja);
        void apiConsultarTiendas();
        void apiConsultarCajas(String tienda);
        void apiConsultarParametros();
        void apiConsultarParametrosQRBancolombia(String tienda);
    }
}
