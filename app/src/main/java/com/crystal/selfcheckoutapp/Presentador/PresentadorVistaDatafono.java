package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaDatafono;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaDatafono;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaPantallaCargaDialogFragment;

import java.util.List;

public class PresentadorVistaDatafono implements IVistaDatafono.Presentador {
    private Context contexto;
    private IVistaDatafono.Vista vista;
    private IVistaDatafono.Modelo modelo;
    private FragmentManager fragmentManager;
    private Utilidades util;
    private VistaPantallaCargaDialogFragment pantallaCarga;

    public PresentadorVistaDatafono(Context contexto, IVistaDatafono.Vista vista, FragmentManager fragmentManager) {
        this.contexto = contexto;
        this.vista = vista;
        this.fragmentManager = fragmentManager;

        util = new Utilidades(contexto);
        modelo = new ModeloVistaDatafono(contexto, this);
    }

    @Override
    public void generarCompraDatafono() {
        modelo.apiCompraDatafono();
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

    public boolean estadoProgress(){
        return util.comprobarDialogProgress();
    }
}
