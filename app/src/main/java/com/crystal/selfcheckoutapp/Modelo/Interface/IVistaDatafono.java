package com.crystal.selfcheckoutapp.Modelo.Interface;

import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.parametros.ResponseParametros;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.ResponseGuardarPerifericos;

import java.util.List;

public interface IVistaDatafono {
    interface Vista{
        void respuestaGuardarPerifericos(ResponseGuardarPerifericos respuesta);
        void errorServicio(String titulo, String mensaje, int servicio);
    }

    interface Presentador{
        void generarCompraDatafono();
        void guardarPerifericos();
        void respuestaGuardarPerifericos(ResponseGuardarPerifericos respuesta);
        void errorServicio(String titulo, String mensaje, int servicio);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void ocultarDialogProgressBar();
        boolean estadoProgress();
    }

    interface Modelo{
        void apiCompraDatafono();
        void apiGuardarPerifericos();
    }
}
