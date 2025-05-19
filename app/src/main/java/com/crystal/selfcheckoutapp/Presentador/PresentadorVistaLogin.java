package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaLogin;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaLogin;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCajaSelectDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCambiarUrlDialogFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;

import java.util.List;

public class PresentadorVistaLogin implements IVistaLogin.Presentador {
    private Context contexto;
    private IVistaLogin.Vista vista;
    private IVistaLogin.Modelo modelo;
    private FragmentManager fragmentManager;
    private Utilidades util;

    public PresentadorVistaLogin(Context contexto, IVistaLogin.Vista vista, FragmentManager fragmentManager) {
        this.contexto = contexto;
        this.vista = vista;
        this.fragmentManager = fragmentManager;

        util = new Utilidades();
        modelo = new ModeloVistaLogin(contexto, this);
    }

    @Override
    public void iniciarSesion(String usuario, String contrasena, boolean seguridad) {
        modelo.apiLogin(usuario, contrasena, seguridad);
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        vista.errorServicio(titulo, mensaje, servicio);
    }

    @Override
    public void seleccionarCaja(List<Caja> cajasList) {
        //Dialog fragment para seleccionar la caja.
        VistaCajaSelectDialogFragment cajaSelectDialogFragment = new VistaCajaSelectDialogFragment(cajasList, contexto);
        cajaSelectDialogFragment.show(fragmentManager, "CajasSelect");
    }

    @Override
    public void consecutivoFiscal(String codigoCaja) {
        modelo.apiConsecutivoFiscal(codigoCaja);
    }

    @Override
    public void vistaConsultaCliente() {
        vista.vistaConsultaCliente();
    }

    @Override
    public void vistaConfiguracion() {
        vista.vistaConfiguracion();
    }

    @Override
    public void dialogProgressBar(String mensaje, boolean cancelable){
        util.mostrarPantallaCargaCustom(fragmentManager, mensaje, cancelable);
    }

    @Override
    public void dialogCambiarUrl() {
        VistaCambiarUrlDialogFragment dialogFragment = new VistaCambiarUrlDialogFragment();
        dialogFragment.show(fragmentManager,"CambiarUrlDialogFragment");
    }

    @Override
    public void ocultarDialogProgressBar(){
        util.ocultarPantallaCargaCustom();
    }

    public boolean estadoProgress(){
        return util.comprobarDialogProgress();
    }
}
