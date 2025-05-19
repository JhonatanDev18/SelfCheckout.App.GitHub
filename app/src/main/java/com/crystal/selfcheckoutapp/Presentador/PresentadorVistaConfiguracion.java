package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConfiguracion;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaConfiguracion;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseTiendas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.ResponseCajas;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseConsultarPerifericos;

import java.util.List;

public class PresentadorVistaConfiguracion implements IVistaConfiguracion.Presentador {
    private Context contexto;
    private IVistaConfiguracion.Vista vista;
    private IVistaConfiguracion.Modelo modelo;
    private Utilidades util;
    private FragmentManager fragmentManager;

    public PresentadorVistaConfiguracion(Context contexto, IVistaConfiguracion.Vista vista,
                                         FragmentManager fragmentManager) {
        this.contexto = contexto;
        this.vista = vista;
        this.fragmentManager = fragmentManager;

        modelo = new ModeloVistaConfiguracion(contexto, this);
        util = new Utilidades();
    }

    @Override
    public void configurarPerifericos(String tienda, String caja) {
        modelo.apiConsultarPerifericos(tienda, caja);
    }

    @Override
    public void respuestaConfigurarPerifericos(ResponseConsultarPerifericos respuesta) {
        vista.respuestaConfigurarPerifericos(respuesta);
    }

    @Override
    public void consultarTiendas() {
        modelo.apiConsultarTiendas();
    }

    @Override
    public void respuestaConsultarTiendas(ResponseTiendas respuesta) {
        vista.respuestaConsultarTiendas(respuesta);
    }

    @Override
    public void consultarCajas(String tienda) {
        modelo.apiConsultarCajas(tienda);
    }
    @Override
    public void consultarParametrosQRBancolombia(String tienda){
        modelo.apiConsultarParametrosQRBancolombia(tienda);
    }

    @Override
    public void respuestaConsultarCajas(ResponseCajas respuesta, String tienda) {
        vista.respuestaConsultarCajas(respuesta, tienda);
    }

    @Override
    public void consultarParametros() {
        modelo.apiConsultarParametros();
    }

    @Override
    public void respuestaParametros(ResponseParametros respuesta, List<RequestParametros> listaParametros) {
        vista.respuestaParametros(respuesta, listaParametros);
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        vista.errorServicio(titulo, mensaje, servicio);
    }

    @Override
    public void dialogProgressBar(String mensaje, boolean cancelable) {
        util.mostrarPantallaCargaCustom(fragmentManager, mensaje, cancelable);
    }

    @Override
    public void ocultarDialogProgressBar() {
        util.ocultarPantallaCargaCustom();
    }

    @Override
    public boolean estadoProgress() {
        return util.comprobarDialogProgress();
    }
}
