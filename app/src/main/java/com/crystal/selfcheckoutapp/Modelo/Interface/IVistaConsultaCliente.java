package com.crystal.selfcheckoutapp.Modelo.Interface;

import com.crystal.selfcheckoutapp.Modelo.clases.RespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;

import java.util.List;

public interface IVistaConsultaCliente {
    interface Vista{
        void errorServicio(String titulo, String mensaje, int servicio);
        void vistaClienteDetalle(Cliente cliente);
        void clienteGenerico(Cliente cliente);
        void respuestaDatafono(CuerpoRespuestaDatafono respuestaDatafono);
        void respuestaActualizarAnulacion();
    }

    interface Presentador{
        void consultarCedula(String cedula);
        void errorServicio(String titulo, String mensaje, int servicio);
        void respuestaConsultaCliente(Cliente cliente);
        void clienteGenerico(Cliente cliente);
        void autorizar(boolean isConfiguracion);
        void consultarRespuestaDatafono(boolean anulacion);
        void mostrarAnulacionesTef(List<RespuestaCompletaTef> respuestaDatafonoList);
        void respuestaDatafono(CuerpoRespuestaDatafono respuestaDatafono);
        void consultarRespuestasTef();
        void anularTransaccionTef(RespuestaCompletaTef respuestaCompletaTef);
        void actualizarTransaccion(RespuestaCompletaTef respuestaCompletaTef);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void respuestaActualizarAnulacion();
        void cerrarDialogAnularTef();
        void ocultarDialogProgressBar();
        boolean estadoProgress();
    }

    interface Modelo{
        void apiConsultaCedula(String cedula);
        void apiRespuestaDatafono(boolean anulacion);
        void apiConsultarRespuestasTef();
        void apiActualizarTransaccion(RespuestaCompletaTef respuestaCompletaTef);
        void apiAnularTransaccionTef(RespuestaCompletaTef respuestaCompletaTef);
    }
}
