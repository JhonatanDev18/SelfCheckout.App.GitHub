package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaDetalleCliente;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaClienteDetalle;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;

public class PresentadorVistaClienteDetalle implements IVistaDetalleCliente.Presentador {
    private IVistaDetalleCliente.Vista vista;
    private IVistaDetalleCliente.Modelo modelo;
    private Context contexto;
    private Utilidades util;
    private FragmentManager fragmentManager;

    public PresentadorVistaClienteDetalle(IVistaDetalleCliente.Vista vista, Context contexto, FragmentManager fragmentManager) {
        this.vista = vista;
        this.contexto = contexto;
        this.fragmentManager = fragmentManager;

        modelo = new ModeloVistaClienteDetalle(this, contexto);
        util = new Utilidades();
    }

    @Override
    public void consultarHabeasData(String cedula, String tipoDocumento) {
        modelo.apiConsultarHabeasData(cedula, tipoDocumento);
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        vista.errorServicio(titulo, mensaje, servicio);
    }

    @Override
    public void respuestaConsultaHabeasData(boolean solicitarHabeas) {
        vista.respuestaConsultaHabeasData(solicitarHabeas);
    }

    @Override
    public void respuestaActualizarCliente(Cliente cliente) {
        vista.respuestaActualizarCliente(cliente);
    }

    @Override
    public void respuestaCrearCliente() {
        vista.respuestaCrearCliente();
    }

    @Override
    public void respuestaInsertarHabeas(Cliente cliente) {
        vista.respuestaInsertarHabeas(cliente);
    }

    @Override
    public void insertarHabeasData(Cliente cliente) {
        modelo.apiInsertarHabeasData(cliente);
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        modelo.apiRegistrarCliente(cliente);
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        modelo.apiActualizarCliente(cliente);
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
