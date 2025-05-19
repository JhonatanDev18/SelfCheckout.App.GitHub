package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaImpresionTirilla;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaImpresionTirilla;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;

public class PresentadorImpresionTirilla implements IVistaImpresionTirilla.Presentador{
    private IVistaImpresionTirilla.Vista vista;
    private IVistaImpresionTirilla.Modelo modelo;
    private FragmentManager fragmentManager;
    private Context contexto;
    private Utilidades util;

    public PresentadorImpresionTirilla(IVistaImpresionTirilla.Vista vista, FragmentManager fragmentManager,
                                       Context contexto) {
        this.vista = vista;
        this.contexto = contexto;
        this.fragmentManager = fragmentManager;

        modelo = new ModeloVistaImpresionTirilla(contexto, this);
        util = new Utilidades(contexto);
    }


    @Override
    public void ingresarCalificacion(Factura factura) {
        modelo.apiPromotorPuntuacion(factura);
    }

    @Override
    public void respuestaCalificacion() {
        vista.respuestaCalificacion();
    }

    @Override
    public void guardarPerifericos() {
        modelo.apiGuardarPerifericos();
    }

    @Override
    public void respuestaGuardarPerifericos(ResponseGuardarPerifericos respuesta) {
        vista.respuestaGuardarPerifericos(respuesta);
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
