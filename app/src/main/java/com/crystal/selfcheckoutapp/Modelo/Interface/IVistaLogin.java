package com.crystal.selfcheckoutapp.Modelo.Interface;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cajas.Caja;
import java.util.List;

public interface IVistaLogin {
    interface Vista{
        void errorServicio(String titulo, String mensaje, int servicio);
        void vistaConsultaCliente();
        void vistaConfiguracion();
    }

    interface Presentador{
        void iniciarSesion(String usuario, String contrasena, boolean seguridad);
        void errorServicio(String titulo, String mensaje, int servicio);
        void seleccionarCaja(List<Caja> cajasList);
        void consecutivoFiscal(String codigoCaja);
        void vistaConsultaCliente();
        void vistaConfiguracion();
        void dialogProgressBar(String mensaje, boolean cancelable);
        void dialogCambiarUrl();
        void ocultarDialogProgressBar();
    }

    interface Modelo{
        void apiLogin(String usuario, String contrasena, boolean seguridad);
        void apiCajas(String tiendaDefecto);
        void apiConsecutivoFiscal(String codigoCaja);
        void apiAperturaCaja(String codigoCaja);
        void apiParametros(String codigoCaja, String tienda, boolean seguridad, AperturaCaja aperturaCaja);
    }
}
