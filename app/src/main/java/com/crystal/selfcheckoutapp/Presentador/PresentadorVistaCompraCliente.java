package com.crystal.selfcheckoutapp.Presentador;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.ModeloVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.Incentives;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.util.List;

public class PresentadorVistaCompraCliente implements IVistaCompraCliente.Presentador {
    private Context contexto;
    private IVistaCompraCliente.Vista vista;
    private IVistaCompraCliente.Modelo modelo;
    private Utilidades util;
    private FragmentManager fragmentManager;
    private Activity activity;

    public PresentadorVistaCompraCliente(Context contexto, IVistaCompraCliente.Vista vista,
                                         FragmentManager fragmentManager, Activity activity) {
        this.contexto = contexto;
        this.vista = vista;
        this.fragmentManager = fragmentManager;
        this.activity = activity;

        modelo = new ModeloVistaCompraCliente(contexto, this);
        util = new Utilidades();
    }

    @Override
    public double calcularSubtotal(List<Producto> productoList) {
        return util.calcularSubtotal(productoList);
    }

    @Override
    public double calcularTotal(List<Producto> productoList) {
        return util.calcularTotal(productoList);
    }

    @Override
    public double calcularDescuentoTotal(List<Producto> productoList) {
        return util.calcularDescuentoTotal(productoList);
    }

    @Override
    public void mostrarIncentivos(List<Incentives> listaIncentivos) {
        for(Incentives incentivo : listaIncentivos){
            if(!incentivo.getMessage().isEmpty()){
                Utilidades.sweetAlertCustom(contexto.getResources().getString(R.string.incentivo),
                        incentivo.getMessage(), contexto, R.drawable.insentivo_condiciones);
            }
        }
    }

    @Override
    public void dialogProgressBar(String mensaje, boolean cancelable) {
        util.mostrarPantallaCargaCustom(fragmentManager, mensaje, cancelable);
    }

    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        vista.errorServicio(titulo, mensaje, servicio);
    }

    @Override
    public void consultarProductos(List<String> eanes) {
        modelo.apiConsultarProductos(eanes);
    }

    @Override
    public void consultarBolsa(int cantidadBolsas) {
        modelo.apiConsultarBolsa(cantidadBolsas);
    }

    @Override
    public void respuestaConsultaProductos(List<Producto> productos, List<RespuestaLine> respuestaLine) {
        vista.respuestaConsultaProductos(productos, respuestaLine);
    }

    @Override
    public void respuestaConsultaBolsa(List<Producto> bolsas) {
        vista.respuestaConsultaBolsa(bolsas);
    }

    @Override
    public String getThisDateSimple() {
        return util.getThisDateSimple();
    }

    @Override
    public void ocultarDialogProgressBar() {
        util.ocultarPantallaCargaCustom();
    }

    public boolean estadoProgress(){
        return util.comprobarDialogProgress();
    }
}
