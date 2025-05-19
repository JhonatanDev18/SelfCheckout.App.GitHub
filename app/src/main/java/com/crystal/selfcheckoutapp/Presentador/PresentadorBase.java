package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IBase;
import com.crystal.selfcheckoutapp.Modelo.ModeloBase;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.ResponseAperturaCaja;

public class PresentadorBase implements IBase.Presentador {
    private IBase.Vista vista;
    private IBase.Modelo modelo;
    private Context contexto;
    private Utilidades util;
    private FragmentManager fragmentManager;

    public PresentadorBase(Context contexto, FragmentManager fragmentManager, IBase.Vista vista) {
        this.contexto = contexto;
        this.vista = vista;
        this.fragmentManager = fragmentManager;

        util = new Utilidades(contexto);
        modelo = new ModeloBase(contexto, util, this);
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        vista.errorServicio(titulo, mensaje, servicio);
    }

    @Override
    public void validarAperturaCaja(String codigoCaja) {
        modelo.apiAperturaCaja(codigoCaja);
    }

    @Override
    public void respuestaValidarAperturaCaja(ResponseAperturaCaja respuesta) {
        vista.respuestaValidarAperturaCaja(respuesta);
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
