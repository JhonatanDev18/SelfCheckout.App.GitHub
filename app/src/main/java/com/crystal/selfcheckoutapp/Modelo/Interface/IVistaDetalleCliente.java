package com.crystal.selfcheckoutapp.Modelo.Interface;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;

public interface IVistaDetalleCliente {
    interface Vista{
        void vistaCompraCliente(Cliente cliente);
        void errorServicio(String titulo, String mensaje, int servicio);
        void respuestaActualizarCliente(Cliente cliente);
        void respuestaCrearCliente();
        void respuestaInsertarHabeas(Cliente cliente);
        void respuestaConsultaHabeasData(boolean solicitarHabeas);
    }

    interface Presentador{
        void consultarHabeasData(String cedula, String tipoDocumento);
        void errorServicio(String titulo, String mensaje, int servicio);
        void respuestaConsultaHabeasData(boolean solicitarHabeas);
        void respuestaActualizarCliente(Cliente cliente);
        void respuestaCrearCliente();
        void respuestaInsertarHabeas(Cliente cliente);
        void insertarHabeasData(Cliente cliente);
        void registrarCliente(Cliente cliente);
        void actualizarCliente(Cliente cliente);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void ocultarDialogProgressBar();
    }

    interface Modelo{
        void apiConsultarHabeasData(String cedula, String tipoDocumento);
        void apiRegistrarCliente(Cliente cliente);
        void apiActualizarCliente(Cliente cliente);
        void apiInsertarHabeasData(Cliente cliente);
    }
}
