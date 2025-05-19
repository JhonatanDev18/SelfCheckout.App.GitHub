package com.crystal.selfcheckoutapp.Presentador;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IBase;
import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaConsultaCliente;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaConsultaCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaAnularTefDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaAutorizacionSimpleFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaClienteConsultaDialogFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;

import java.util.List;

public class PresentadorVistaConsultaCliente extends PresentadorBase implements IVistaConsultaCliente.Presentador {
    private Context contexto;
    private Utilidades util;
    private IVistaConsultaCliente.Vista vistaConsulta;
    private IVistaConsultaCliente.Modelo modelo;
    private FragmentManager fragmentManager;
    private VistaAutorizacionSimpleFragment autorizacionSimpleFragment;
    private VistaAnularTefDialogFragment anularTefDialogFragment;

    public PresentadorVistaConsultaCliente(Context contexto, FragmentManager fragmentManager,
                                           IBase.Vista vista, IVistaConsultaCliente.Vista vistaConsulta) {
        super(contexto, fragmentManager, vista);
        this.contexto = contexto;
        this.vistaConsulta = vistaConsulta;
        this.fragmentManager = fragmentManager;

        util = new Utilidades();
        modelo = new ModeloVistaConsultaCliente(contexto, this);
    }

    @Override
    public void consultarCedula(String cedula) {
        modelo.apiConsultaCedula(cedula);
    }

    @Override
    public void respuestaConsultaCliente(Cliente cliente) {
        VistaClienteConsultaDialogFragment vistaClienteConsultaDialogFragment =
                new VistaClienteConsultaDialogFragment(cliente, vistaConsulta);
        vistaClienteConsultaDialogFragment.show(fragmentManager, "ClienteConsultaFragment");
    }

    @Override
    public void clienteGenerico(Cliente cliente) {
        vistaConsulta.clienteGenerico(cliente);
    }

    @Override
    public void autorizar(boolean isConfiguracion) {
        autorizacionSimpleFragment = new VistaAutorizacionSimpleFragment(vistaConsulta, this, isConfiguracion);
        autorizacionSimpleFragment.show(fragmentManager, "AutorizacionSimple");
    }

    @Override
    public void consultarRespuestaDatafono(boolean anulacion) {
        modelo.apiRespuestaDatafono(anulacion);
    }

    @Override
    public void mostrarAnulacionesTef(List<RespuestaCompletaTef> respuestaDatafonoList) {
        anularTefDialogFragment = new VistaAnularTefDialogFragment(respuestaDatafonoList);
        anularTefDialogFragment.show(fragmentManager, "AnularTefDialogFragment");
    }

    @Override
    public void respuestaDatafono(CuerpoRespuestaDatafono respuestaDatafono) {
        vistaConsulta.respuestaDatafono(respuestaDatafono);
    }

    @Override
    public void consultarRespuestasTef() {
        modelo.apiConsultarRespuestasTef();
    }

    @Override
    public void anularTransaccionTef(RespuestaCompletaTef respuestaDatafono) {
        modelo.apiAnularTransaccionTef(respuestaDatafono);
    }

    @Override
    public void actualizarTransaccion(RespuestaCompletaTef respuestaCompletaTef) {
        modelo.apiActualizarTransaccion(respuestaCompletaTef);
    }

    @Override
    public void respuestaActualizarAnulacion() {
        vistaConsulta.respuestaActualizarAnulacion();
    }

    @Override
    public void cerrarDialogAnularTef() {
        anularTefDialogFragment.dismiss();
    }
}
