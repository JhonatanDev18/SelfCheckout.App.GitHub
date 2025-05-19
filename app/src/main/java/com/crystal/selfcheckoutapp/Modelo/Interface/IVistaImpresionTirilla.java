package com.crystal.selfcheckoutapp.Modelo.Interface;

import com.crystal.selfcheckoutapp.Modelo.clases.Descuento;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.Payment;
import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.util.List;

public interface IVistaImpresionTirilla {
    interface Vista{
        void respuestaCalificacion();
        void respuestaGuardarPerifericos(ResponseGuardarPerifericos respuesta);
        void errorServicio(String titulo, String mensaje, int servicio);
    }

    interface Presentador{
        void ingresarCalificacion(Factura factura);
        void respuestaCalificacion();
        void guardarPerifericos();
        void respuestaGuardarPerifericos(ResponseGuardarPerifericos respuesta);
        void errorServicio(String titulo, String mensaje, int servicio);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void ocultarDialogProgressBar();
        boolean estadoProgress();
    }

    interface Modelo{
        void apiPromotorPuntuacion(Factura factura);
        void apiGuardarPerifericos();
    }
}
