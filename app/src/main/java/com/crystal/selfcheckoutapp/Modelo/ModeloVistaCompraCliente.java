package com.crystal.selfcheckoutapp.Modelo;

import android.content.Context;
import android.widget.Toast;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.Header;
import com.crystal.selfcheckoutapp.Modelo.clases.KeyWord;
import com.crystal.selfcheckoutapp.Modelo.clases.Line;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCondicionesComerciales;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestProductos;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.Condiciones;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.ResponseCondicionesComerciales;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.ResponseProductos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloVistaCompraCliente implements IVistaCompraCliente.Modelo {
    private Context contexto;
    private IVistaCompraCliente.Presentador presentador;
    private String tienda;
    private String pais;
    private String divisa;
    private Utilidades util;
    private String caja;

    public ModeloVistaCompraCliente(Context contexto, IVistaCompraCliente.Presentador presentador) {
        this.contexto = contexto;
        this.presentador = presentador;
        tienda = SPM.getString(Constantes.CAJA_CODIGO_TIENDA);
        pais = SPM.getString(Constantes.CAJA_PAIS);
        divisa = SPM.getString(Constantes.CAJA_DIVISA);
        util = new Utilidades();
        caja = SPM.getString(Constantes.CAJA_CODIGO);
    }

    @Override
    public void apiConsultarProductos(List<String> eanes) {
        //API para consultar productos
        RequestProductos requestProductos = new RequestProductos(eanes, pais, tienda);
        LogFile.adjuntarLog("PASO 2: Request Consultar Productos", requestProductos);
        Call<ResponseProductos> responseProductosCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsultarProductos(requestProductos);
        responseProductosCall.enqueue(new Callback<ResponseProductos>() {
            @Override
            public void onResponse(@NotNull Call<ResponseProductos> call, @NotNull Response<ResponseProductos> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        List<Producto> listaProductos = response.body().getListaProductos();
                        if(listaProductos.isEmpty()){
                            LogFile.adjuntarLog("PASO 2: Consultar Productos Error", "Productos no encontrados");
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    contexto.getResources().getString(R.string.error_producto_no_encontrado),
                                    Constantes.SERVICIO_CONSULTA_PRODUCTO);
                        }else{
                            LogFile.adjuntarLog("PASO 2: Consultar Productos", response.body().getListaProductos());
                            List<Producto> listaProductosAgregados = new ArrayList<>();
                            String articuloDescontable = SPM.getString(Constantes.PARAM_MSG_ARTICULO_DESCONTABLE);
                            StringBuilder mensaje;
                            boolean addEan;

                            for(Producto producto : listaProductos){
                                mensaje = new StringBuilder();
                                addEan = true;
                                assert articuloDescontable != null;
                                if(!articuloDescontable.isEmpty()){
                                    if(producto.getDescontable() != null){
                                        if(!producto.getDescontable()){
                                            if(!producto.getPrecio().equals(producto.getPrecioOriginal())){
                                                mensaje.append(producto.getEan()).append(" - ").append(producto.getNombre()).append(". ").append(articuloDescontable);
                                                addEan = false;
                                            }
                                        }
                                    }
                                }

                                if(addEan){
                                    listaProductosAgregados.add(producto);
                                }

                                if(mensaje.length() > 0){
                                    Utilidades.mjsToast(mensaje.toString(), Constantes.TOAST_TYPE_INFO,
                                            Toast.LENGTH_LONG, contexto);
                                }
                            }

                            if(!listaProductosAgregados.isEmpty()){
                                List<Producto> listaProductosFront = new ArrayList<>();

                                for(int i=0; i<listaProductosAgregados.size(); i++){
                                    Producto producto = listaProductosAgregados.get(i);

                                    for(int j=0; j<eanes.size(); j++){
                                        String ean = eanes.get(j);
                                        if(ean.equals(producto.getEan())){
                                            listaProductosFront.add(producto);
                                        }
                                    }
                                }

                                for(int i=0; i<listaProductosFront.size(); i++){
                                    listaProductosFront.get(i).setLine(i);
                                    listaProductosFront.get(i).setCantidad(1);
                                    listaProductosFront.get(i).setPrecioBase(listaProductosFront.get(i).getPrecio());
                                }

                                apiCondicionesComerciales(listaProductosFront);
                            }else{
                                presentador.ocultarDialogProgressBar();
                            }
                        }
                    }else{
                        LogFile.adjuntarLog("PASO 2: Consultar Productos Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_PRODUCTO);
                    }
                }else{
                    LogFile.adjuntarLog("PASO 2: Consultar Productos Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_PRODUCTO);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseProductos> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("PASO 2: Consultar Productos Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_PRODUCTO);
            }
        });
    }

    @Override
    public void apiCondicionesComerciales(List<Producto> productos) {
        String customerId = SPM.getString(Constantes.CUSTOMER_ID);
        List<String> respuestaID = new ArrayList<>();
        List<Line> linesProductos = util.construirLinesCondicion(productos);

        //Creando las lineas de palabra claves para cosumir el servicio.
        List<KeyWord> keyWordList = new ArrayList<>();

        Header header = new Header(divisa, customerId, presentador.getThisDateSimple(), keyWordList,
                tienda, false);

        //API para las condiciones comerciales
        RequestCondicionesComerciales requestCondicionesComerciales = new RequestCondicionesComerciales(header, linesProductos);
        LogFile.adjuntarLog("Request Condiciones Comerciales", requestCondicionesComerciales);
        Call<ResponseCondicionesComerciales> responseCondicionesComercialesCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsultaCondicionesComerciales(requestCondicionesComerciales);
        responseCondicionesComercialesCall.enqueue(new Callback<ResponseCondicionesComerciales>() {
            @Override
            public void onResponse(@NotNull Call<ResponseCondicionesComerciales> call,
                                   @NotNull Response<ResponseCondicionesComerciales> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        Condiciones condiciones = response.body().getCondiciones();
                        LogFile.adjuntarLog("Condiciones Comerciales", condiciones);
                        SPM.setString(Constantes.TIQUETE_VAUCHER_TEXT, condiciones.getVaucherText());
                        SPM.setString(Constantes.TIQUETE_VAUCHER_PALABRA, condiciones.getVaucherPalabra());

                        respuestaID.addAll(condiciones.getRespuestaID());

                        if(!condiciones.getRespuestaLine().isEmpty()){
                            List<Producto> productoList = util.aplicarDescuentosLines(condiciones.getRespuestaLine(),
                                    productos);

                            if(!condiciones.getIncentives().isEmpty()){
                                presentador.mostrarIncentivos(condiciones.getIncentives());
                            }

                            presentador.respuestaConsultaProductos(productoList, condiciones.getRespuestaLine());
                        }else{
                            for(Producto producto:productos){
                                producto.setValorFinal("Valor Final: "+ Utilidades.formatearPrecio(producto.getPrecio()));
                            }

                            presentador.respuestaConsultaProductos(productos, condiciones.getRespuestaLine());
                        }
                        presentador.ocultarDialogProgressBar();
                    }else {
                        LogFile.adjuntarLog("Condiciones Comerciales Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_CONDICIONES);
                    }
                }else {
                    LogFile.adjuntarLog("Condiciones Comerciales Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_CONDICIONES);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseCondicionesComerciales> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Condiciones Comerciales Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_CONDICIONES);
            }
        });
    }

    @Override
    public void apiConsultarBolsa(int cantidadBolsas) {
        String eanBolsa = Utilidades.eanBolsaTienda();
        List<String> eanes = new ArrayList<>();
        eanes.add(eanBolsa);
        //API para consultar productos
        RequestProductos requestProductos = new RequestProductos(eanes, pais, tienda);
        LogFile.adjuntarLog("Request Consultar Bolsas [Cantidad: "+cantidadBolsas+"]", requestProductos);
        Call<ResponseProductos> responseProductosCall = Utilidades.servicioRetrofit(Constantes.API_NN)
                .doConsultarProductos(requestProductos);
        responseProductosCall.enqueue(new Callback<ResponseProductos>() {
            @Override
            public void onResponse(@NotNull Call<ResponseProductos> call, @NotNull Response<ResponseProductos> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getEsValida()){
                        List<Producto> listaProductos = response.body().getListaProductos();
                        if(listaProductos.isEmpty()){
                            LogFile.adjuntarLog("Consultar Bolsas Error", "Producto no encontrado");
                            presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                    contexto.getResources().getString(R.string.error_producto_no_encontrado),
                                    Constantes.SERVICIO_CONSULTA_PRODUCTO_BOLSA);
                        }else{
                            Producto producto = listaProductos.get(0);
                            producto.setCantidad(1);
                            producto.setPrecioBase(producto.getPrecio());
                            producto.setValorFinal("Valor Final: "+ Utilidades.formatearPrecio(producto.getPrecio()));

                            List<Producto> listaBolsas = new ArrayList<>();
                            for(int i=0; i<cantidadBolsas; i++){
                                listaBolsas.add(producto);
                            }

                            LogFile.adjuntarLog("Consultar Bolsas", producto);

                            presentador.ocultarDialogProgressBar();
                            presentador.respuestaConsultaBolsa(listaBolsas);
                        }
                    }else{
                        LogFile.adjuntarLog("Consultar Bolsas Error", response.body().getMensaje());
                        presentador.errorServicio(contexto.getResources().getString(R.string.error),
                                response.body().getMensaje(), Constantes.SERVICIO_CONSULTA_PRODUCTO_BOLSA);
                    }
                }else{
                    LogFile.adjuntarLog("Consultar Bolsas Error", response.message());
                    presentador.errorServicio(contexto.getResources().getString(R.string.error),
                            contexto.getResources().getString(R.string.error_conexion_sb) + response.message(),
                            Constantes.SERVICIO_CONSULTA_PRODUCTO_BOLSA);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseProductos> call, @NotNull Throwable t) {
                LogFile.adjuntarLog("Consultar Bolsas Error", t);
                presentador.errorServicio(contexto.getResources().getString(R.string.error),
                        contexto.getResources().getString(R.string.error_conexion) + t.getMessage(),
                        Constantes.SERVICIO_CONSULTA_PRODUCTO_BOLSA);
            }
        });
    }
}