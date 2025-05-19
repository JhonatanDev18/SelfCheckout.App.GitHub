package com.crystal.selfcheckoutapp.Modelo.Interface;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.Incentives;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.util.List;

public interface IVistaCompraCliente {

    interface Vista{
        void eliminarProducto(int posicion, boolean bolsa);
        void respuestaConsultaProductos(List<Producto> productos, List<RespuestaLine> respuestaLine);
        void errorServicio(String titulo, String mensaje, int servicio);
        void addBolsaProducto(int cantidadBolsas);
        void respuestaConsultaBolsa(List<Producto> bolsas);
        void vistaResumenCompra();
        void encerderRFID();
        void cerrarCajon();
    }

    interface Presentador{
        double calcularSubtotal(List<Producto> productoList);
        double calcularTotal(List<Producto> productoList);
        double calcularDescuentoTotal(List<Producto> productoList);
        void mostrarIncentivos(List<Incentives> listaIncentivos);
        void dialogProgressBar(String mensaje, boolean cancelable);
        void errorServicio(String titulo, String mensaje, int servicio);
        void consultarProductos(List<String> eanes);
        void consultarBolsa(int cantidadBolsas);
        void respuestaConsultaProductos(List<Producto> productos, List<RespuestaLine> respuestaLine);
        void respuestaConsultaBolsa(List<Producto> bolsas);
        String getThisDateSimple();
        void ocultarDialogProgressBar();
    }

    interface Modelo{
        void apiConsultarProductos(List<String> eanes);
        void apiCondicionesComerciales(List<Producto> productos);
        void apiConsultarBolsa(int cantidadBolsas);
    }
}
