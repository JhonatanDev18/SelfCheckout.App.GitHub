package com.crystal.selfcheckoutapp.Modelo.Interface;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.ResponseAperturaCaja;

import java.util.List;

public interface IBase {
    interface Vista{
        void errorServicio(String titulo, String mensaje, int servicio);
        void respuestaValidarAperturaCaja(ResponseAperturaCaja respuesta);
    }

    interface Presentador{
        void errorServicio(String titulo, String mensaje, int servicio);
        void validarAperturaCaja(String codigoCaja);
        void respuestaValidarAperturaCaja(ResponseAperturaCaja respuesta);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void ocultarDialogProgressBar();
        boolean estadoProgress();
    }

    interface Modelo{
        void apiAperturaCaja(String codigoCaja);
    }
}
