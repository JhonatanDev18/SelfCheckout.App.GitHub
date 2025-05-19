package com.crystal.selfcheckoutapp.Vista;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.selfcheckoutapp.Modelo.Interface.IVistaCompraCliente;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.SerialEan;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.KeyboardUtils;
import com.crystal.selfcheckoutapp.Modelo.common.LogFile;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Presentador.PresentadorVistaCompraCliente;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.adapters.ProductosRecyclerViewAdapter;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCajonDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaCantidadBolsasDialogFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomDosAccionesDialogFragment;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomUnaAccionDialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VistaCompraCliente extends AppCompatActivity implements IVistaCompraCliente.Vista{

    //Inicializar variables de la vista (TexView etc...)
    private ProductosRecyclerViewAdapter adaptador;
    private RecyclerView rvProductos;
    private ConstraintLayout clFondoCompra;
    private List<Producto> listaProductos;
    private TextView tvComplementoTituloSaludoCliente;
    private TextView tvVolverEmpezar, tvNumeroArticulos, tvSubtotal, tvTotalDescuento, tvTotalPagar;
    private Button btnReiniciar, btnConsultarProductos, btnContinuarCompra, btnNuevosArticulos, btnMasBolsas,
            btnElimarArticulos;
    private EditText etConsultaProducto, etCedulaCliente, etCelularCliente, etCorreoCliente;
    private Context contexto;
    private PresentadorVistaCompraCliente presentador;
    private Cliente cliente;
    private boolean isTiendaRFID, isChangeFromRFID, comproBolsas, lectorEncendido;
    private Factura factura;
    private List<SerialEan> listaSeriales;
    private List<String> eanes;
    private static final int DELAY_MILLIS = 500;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private Utilidades util;
    private Timer tiempo;
    private TimerTask tarea;
    private int cantidadBolsas;
    private boolean pasoPantallaMedios;
    private TextView tvTextoLecturaArticulo, tvTextoNuevasBolsas;
    private boolean apretoBotonBolsas, eliminoPrenda, consultaNormal;
    private int tiempoEscuchaLector, tiempoMsjCajon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            SPM.setInt(Constantes.PROCESO_ACTIVITY, Constantes.VISTA_COMPRA_CLIENTE);
            setContentView(R.layout.vista_compra_cliente);

            LogFile.adjuntarLogTitulo("Abriendo [Pantalla Compra Cliente]");

            /*Esta acción permite que la pantalla no se apague sin importar que el dispositivo
            tenga una suspencion minima ejemplo (15 segundos), la pantalla seguira activa.
            Tener en cuenta que en el manifest debe existir el siguiente permiso
            (<uses-permission android:name="android.permission.WAKE_LOCK"/>)*/
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            //Ocultar el Action bar del aplicativo
            Objects.requireNonNull(getSupportActionBar()).hide();

            //Ocultar la barra de estado donde se ve lo siguiente(Wifi, Notificaciones, batería etc...)
            Utilidades.ocultarBarraEstado(getWindow());

            //Ocultar la barra de navegación, que son los 3 botones (Atras, Inicio y Recientes)
            Utilidades.ocultarBarraNavegacion(getWindow());

            //Inicializar variables de interfaz o otras variables.
            inicializar();

            /*En este metodo estan los eventos de los botones o si se usa otro evento en general
            ejemplo (OnClick).*/
            eventos();

            //Intents
            cargarIntents();
        }catch (Exception e){
            Utilidades.sweetAlert(getResources().getString(R.string.error), e.getMessage(),
                    SweetAlertDialog.ERROR_TYPE, this);
        }
    }

    @SuppressLint("SetTextI18n")
    private void inicializar() {
        factura = new Factura();
        SPM.setString(Constantes.PALABRA_CLAVE_CLIENTE, null);

        String[] tiempos = Objects.requireNonNull(SPM.getString(Constantes.PARAM_TIEMPO_ESCUCHA_LECTOR_AND_MSJ_CAJON))
                .split(";");

        tiempoEscuchaLector = Integer.parseInt(tiempos[0]);
        tiempoMsjCajon = Integer.parseInt(tiempos[1]);

        eanes = new ArrayList<>();
        listaSeriales = new ArrayList<>();
        isTiendaRFID = SPM.getBoolean(Constantes.PARAM_TIENDA_RFID);
        contexto = VistaCompraCliente.this;
        util = new Utilidades(contexto);

        presentador = new PresentadorVistaCompraCliente(contexto, this, getSupportFragmentManager(), this);
        clFondoCompra = findViewById(R.id.clFondoCompra);

        listaProductos = new ArrayList<>();
        rvProductos = findViewById(R.id.rvProductos);
        rvProductos.setLayoutManager(new LinearLayoutManager(this));
        tvNumeroArticulos = findViewById(R.id.tvNumeroArticulos);
        btnConsultarProductos = findViewById(R.id.btnConsultarProductos);
        tvVolverEmpezar = findViewById(R.id.tvVolverEmpezar);
        btnContinuarCompra = findViewById(R.id.btnContinuarCompra);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvTotalDescuento = findViewById(R.id.tvTotalDescuento);
        tvTotalPagar = findViewById(R.id.tvTotalPagar);
        etConsultaProducto = findViewById(R.id.etConsultaProducto);
        btnMasBolsas = findViewById(R.id.btnMasBolsas);
        tvComplementoTituloSaludoCliente = findViewById(R.id.tvComplementoTituloSaludoCliente);

        clFondoCompra.setVisibility(View.GONE);
        adaptador = new ProductosRecyclerViewAdapter(listaProductos, this);
        rvProductos.setAdapter(adaptador);
        tvNumeroArticulos.setText(Integer.toString(adaptador.getItemCount()));

        etCedulaCliente = findViewById(R.id.etCedulaCliente);
        etCelularCliente = findViewById(R.id.etCelularCliente);
        etCorreoCliente = findViewById(R.id.etCorreoCliente);

        btnNuevosArticulos = findViewById(R.id.btnNuevosArticulos);
        btnElimarArticulos = findViewById(R.id.btnElimarArticulos);
        tvTextoLecturaArticulo = findViewById(R.id.tvTextoLecturaArticulo);
        tvTextoNuevasBolsas = findViewById(R.id.tvTextoNuevasBolsas);
    }

    private void cargarIntents() {
        //Intent info cliente
        if(Objects.requireNonNull(getIntent().getExtras()).containsKey(getResources().getString(R.string.key_intent_cliente))){
            Cliente cliente = (Cliente) getIntent().getSerializableExtra(getResources().getString(R.string.key_intent_cliente));
            factura.setCliente(cliente);
            pasoPantallaMedios = false;
            cargarCliente(cliente);
            //mensaje tiendas RFID
            iniciarTareaCajon();
            tiempo = new Timer();
            tiempo.schedule(tarea, 200);
        }else if(getIntent().getExtras().containsKey(getResources().getString(R.string.key_intent_factura))){
            cantidadBolsas = 0;
            comproBolsas = true;
            pasoPantallaMedios = true;
            tvTextoNuevasBolsas.setVisibility(View.VISIBLE);
            btnMasBolsas.setVisibility(View.VISIBLE);
            btnContinuarCompra.setText(getResources().getString(R.string.ir_medio_pago));
            factura = (Factura) getIntent().getSerializableExtra(getResources().getString(R.string.key_intent_factura));

            assert factura != null;
            cargarCliente(factura.getCliente());

            listaProductos = factura.getProductos();
            listaSeriales = factura.getListaSeriales();

            for(Producto producto: listaProductos){
                if(producto.getEan().equals(Utilidades.eanBolsaTienda())){
                    cantidadBolsas++;
                }
            }

            if(presentador.estadoProgress()){
                presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                        false);
                presentador.consultarProductos(Utilidades.eanesConsultar(factura.getProductos()));
            }

            KeyboardUtils.hideKeyboard(this);
        }
    }

    @SuppressLint("SetTextI18n")
    private void cargarCliente(Cliente cli){
        cliente = cli;
        SPM.setString(Constantes.CUSTOMER_ID, cliente.getCustomerId());

        tvComplementoTituloSaludoCliente.setText(cliente.getNombre().split(" ")[0].toUpperCase() + " " +
                cliente.getApellido().split(" ")[0].toUpperCase());
        etCedulaCliente.setText(cliente.getCedula(false));
        etCelularCliente.setText(cliente.celularIncognito());
        etCorreoCliente.setText(cliente.correoIncognito());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void eventos() {
        btnReiniciar.setOnClickListener(this::reiniciar);
        btnConsultarProductos.setOnClickListener(this::consultarProducto);
        tvVolverEmpezar.setOnClickListener(this::volverAEmpezar);
        btnContinuarCompra.setOnClickListener(this::continuar);
        btnNuevosArticulos.setOnClickListener(this::nuevosArticulos);
        btnElimarArticulos.setOnClickListener(v -> eliminarProducto(0, false));
        btnMasBolsas.setOnClickListener(v -> {
            apretoBotonBolsas = true;
            masBolsas();
        });

        // Configurar el Listener para recibir el ENTER del lector RFID
        etConsultaProducto.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                if(etConsultaProducto.getText().toString().length() > 13){
                    isChangeFromRFID = true;
                    clFondoCompra.setVisibility(View.GONE);
                    btnNuevosArticulos.setVisibility(View.GONE);
                    tvTextoLecturaArticulo.setVisibility(View.GONE);
                    tvTextoNuevasBolsas.setVisibility(View.GONE);
                    btnMasBolsas.setVisibility(View.GONE);
                }else{
                    btnConsultarProductos.callOnClick();
                }
                return false;
            }
            return false;
        });

        etConsultaProducto.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtils.hideKeyboard(this);
            }
            return false;
        });

        etConsultaProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isChangeFromRFID){
                    handler.removeCallbacks(runnable);
                    runnable = () -> {
                        isChangeFromRFID = false;
                        lectorEncendido = false;
                        String lectura = etConsultaProducto.getText().toString().trim();
                        validarConsulta(lectura);
                    };
                    handler.postDelayed(runnable, DELAY_MILLIS);
                }
            }
        });
    }

    private void masBolsas() {
        logBoton("Agregar bolsas");
        VistaCantidadBolsasDialogFragment cantidadBolsasDialogFragment;
        if(comproBolsas){
            cantidadBolsasDialogFragment = new VistaCantidadBolsasDialogFragment(this, cantidadBolsas, false);
        }else{
            cantidadBolsasDialogFragment = new VistaCantidadBolsasDialogFragment(this, 0, true);
        }
        cantidadBolsasDialogFragment.show(getSupportFragmentManager(), "fragmentCantidadBolsas");
    }

    private void nuevosArticulos(View view) {
        eliminoPrenda = true;
        logBoton("Agregar nuevos artículos");
        mensajeCajon(getResources().getString(R.string.info_cajon_nuevos));
    }

    private List<String> filterDistinctElements(List<String> sourceList) {
        Set<String> set = new HashSet<>(sourceList);
        return new ArrayList<>(set);
    }

    private String agregarCaracter(String cadena, String caracter, int pasos) {
        StringBuilder cadenaConCaracteres = new StringBuilder();
        int longitudCadena = cadena.length();
        for (int i = 0; i < longitudCadena; i += pasos) {
            if (i + pasos < longitudCadena) {
                cadenaConCaracteres.append(cadena.substring(i, i + pasos)).append(caracter);
            } else {
                cadenaConCaracteres.append(cadena.substring(i, longitudCadena));
            }
        }
        return cadenaConCaracteres.toString();
    }

    public void reiniciar(View view){
        //Pasar a la vista de medios de pago
        logBoton("Ingresar otra cédula");
        Intent intent = new Intent(contexto, VistaConsultaCliente.class);
        startActivity(intent);
        finish();
    }

    private void logBoton(String texto){
        LogFile.adjuntarLog("Botón", texto);
    }

    //Método bóton "Buscar"
    @SuppressLint("SetTextI18n")
    public void consultarProducto(View view){
        logBoton("Ingresar Producto");
        String ean = etConsultaProducto.getText().toString().trim();
        etConsultaProducto.setText("");
        validarConsulta(ean);
    }

    private List<String> eanesConsulta(String ean){
        List<String> listEanes = new ArrayList<>();
        listEanes.add(ean);

        if(!listaProductos.isEmpty()){
            for(int i=0; i<listaProductos.size(); i++){
                Producto producto = listaProductos.get(i);
                listEanes.add(producto.getEan());
            }
        }

        return listEanes;
    }

    private List<String> eanesConsulta(){
        List<String> listEanes = new ArrayList<>();

        if(!listaProductos.isEmpty()){
            for(int i=0; i<listaProductos.size(); i++){
                Producto producto = listaProductos.get(i);
                listEanes.add(producto.getEan());
            }
        }

        return listEanes;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void volverAEmpezar(View view){
        VistaMsjCustomDosAccionesDialogFragment msjCustom =
                new VistaMsjCustomDosAccionesDialogFragment(R.drawable.sincronizar,
                        getResources().getString(R.string.title_volver_a_empezar),
                        getResources().getString(R.string.texto_volver_a_empezar),
                        "",
                        getResources().getString(R.string.si),
                        getResources().getString(R.string.no),
                        Constantes.ACCION_REINICIAR_TODO,
                        false);
        msjCustom.show(getSupportFragmentManager(), "MsjCustomDosDialogFragment");
        msjCustom.setVistaCompraCliente(this);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reiniciarTodo() {
        listaProductos.clear();
        listaSeriales.clear();
        eanes.clear();
        adaptador.notifyDataSetChanged();
        clFondoCompra.setVisibility(View.GONE);
        etConsultaProducto.setText("");
        KeyboardUtils.hideKeyboard(this);
        btnContinuarCompra.setText(getResources().getString(R.string.continuar));
        tvTextoNuevasBolsas.setVisibility(View.INVISIBLE);
        btnMasBolsas.setVisibility(View.INVISIBLE);
        comproBolsas = false;
        btnReiniciar.setVisibility(View.GONE);
        mensajeCajon(null);
    }

    public void continuar(View view){
        logBoton("Continuar");
        if(comproBolsas){
            vistaResumenCompra();
        }else{
            apretoBotonBolsas = false;
            masBolsas();
        }
    }

    public void insertarFactura(){
        factura.setCliente(cliente);
        factura.setTienda(SPM.getString(Constantes.CAJA_CODIGO_TIENDA));
        factura.setCaja(SPM.getString(Constantes.CAJA_CODIGO));
        factura.setDivisa(SPM.getString(Constantes.CAJA_DIVISA));
        factura.setNombreTienda(SPM.getString(Constantes.CAJA_NOMBRE_TIENDA));
        factura.setTotalCompra(presentador.calcularTotal(listaProductos));
        factura.setSubtotal(presentador.calcularSubtotal(listaProductos));
        factura.setDescuentoTotal(presentador.calcularDescuentoTotal(listaProductos));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void eliminarProducto(int posicion,boolean bolsa) {
        eliminoPrenda = true;
        if(bolsa){
            retirarProducto(posicion);
        }else{
            logBoton("Retirar artículos");
            util.vozAndroid(getResources().getString(R.string.voz_eliminar_productos_canasta));
            VistaCajonDialogFragment vistaCajonDialogFragment =
                    new VistaCajonDialogFragment(this, getResources().getString(R.string.texto_quitar_del_carrito),
                            getResources().getString(R.string.retirar_cajon), R.drawable.retirar, true);
            vistaCajonDialogFragment.show(getSupportFragmentManager(), "vistaCajonDialogFragment");
        }
    }

    private void retirarProducto(int posicion){
        if(listaProductos.size() > 1){
            for(int i = 0; i<listaSeriales.size(); i++){
                if(listaSeriales.get(i).getEan().equals(listaProductos.get(posicion).getEan())){
                    listaSeriales.remove(i);
                }
            }

            listaProductos.remove(posicion);
            adaptador.notifyItemRemoved(posicion);
            cantidadBolsas = 0;
            for(Producto producto: listaProductos){
                if(producto.getEan().equals(Utilidades.eanBolsaTienda())){
                    cantidadBolsas++;
                }
            }

            totalizar();

            presentador.dialogProgressBar(getResources().getString(R.string.progress_cargando),
                    false);
            presentador.consultarProductos(eanesConsulta());
        }else{
            for(int i = 0; i<listaSeriales.size(); i++){
                if(listaSeriales.get(i).getEan().equals(listaProductos.get(posicion).getEan())){
                    listaSeriales.remove(i);
                }
            }

            listaProductos.remove(posicion);
            adaptador.notifyItemRemoved(posicion);
            clFondoCompra.setVisibility(View.GONE);
            btnReiniciar.setVisibility(View.GONE);
            mensajeCajon(null);
        }
    }

    @SuppressLint({"NotifyDataSetChanged", "StringFormatMatches"})
    @Override
    public void respuestaConsultaProductos(List<Producto> productos, List<RespuestaLine> respuestaLine) {
        factura.setProductos(productos);
        factura.setDescuentos(respuestaLine);
        factura.setListaSeriales(listaSeriales);
        factura.buscarProductosPrecioCero(consultaNormal);

        listaProductos.clear();
        listaProductos.addAll(factura.getProductos());

        if(comproBolsas){
            adaptador = new ProductosRecyclerViewAdapter(listaProductos, this);
            rvProductos.setAdapter(adaptador);
        }
        adaptador.notifyDataSetChanged();

        totalizar();
        validarVisibilityCompra();
        eanes = new ArrayList<>();

        if(!eliminoPrenda){
            if(!pasoPantallaMedios){
                util.vozAndroid(listaProductos.size()>1 ? String.format(getResources().getString(R.string.voz_cantidad_articulos), listaProductos.size()) :
                        getResources().getString(R.string.voz_cantidad_articulo));
            }
        }

        if(factura.getProductos().isEmpty()){
            btnReiniciar.setVisibility(View.GONE);
            presentador.ocultarDialogProgressBar();
            clFondoCompra.setVisibility(View.GONE);
            mensajeCajon(null);
        }else{
            btnReiniciar.setVisibility(View.VISIBLE);
        }

        factura.mostrarMensajePrecioCero(getSupportFragmentManager(), contexto);

        eliminoPrenda = false;
    }

    private void validarVisibilityCompra(){
        if(clFondoCompra.getVisibility() == View.GONE){
            clFondoCompra.setVisibility(View.VISIBLE);
            btnNuevosArticulos.setVisibility(View.VISIBLE);
            tvTextoLecturaArticulo.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void totalizar() {
        double descuentoTotal = presentador.calcularDescuentoTotal(listaProductos);
        tvNumeroArticulos.setText(Integer.toString(adaptador.getItemCount()));
        tvSubtotal.setText(Utilidades.formatearPrecio(presentador.calcularSubtotal(listaProductos)));
        tvTotalDescuento.setText(descuentoTotal == 0.0 ? "0": Utilidades.formatearPrecio(descuentoTotal));
        tvTotalPagar.setText(Utilidades.formatearPrecio(presentador.calcularTotal(listaProductos)));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void errorServicio(String titulo, String mensaje, int servicio) {
        presentador.ocultarDialogProgressBar();
        etConsultaProducto.setText("");
        KeyboardUtils.hideKeyboard(this);
        if(!listaProductos.isEmpty()){
            validarVisibilityCompra();
        }

        if(servicio == Constantes.SERVICIO_CONSULTA_PRODUCTO_BOLSA){
            comproBolsas = false;
            btnContinuarCompra.setText(getResources().getString(R.string.btn_continuar));
        }

        if(servicio == Constantes.SERVICIO_CONSULTA_PRODUCTO){
            if(!eliminoPrenda){
                btnReiniciar.setVisibility(View.GONE);
                mensajeCajon(null);
            }
        }

        VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                getResources().getString(R.string.ups_algo_mal),
                String.format(getResources().getString(R.string.ups_algo_mal_msj), servicio), mensaje,
                getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
        msjCustomDialogFragment.show(getSupportFragmentManager(), "MsjCustomDialogFragment");
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void addBolsaProducto(int cantidad) {
        if(apretoBotonBolsas){
            if(cantidadBolsas < cantidad || cantidadBolsas > cantidad){
                reprocesarBolsas();
                procesarBolsas(cantidad);
            }
        }else{
            if(cantidad > 0){
                procesarBolsas(cantidad);
            }else{
                vistaResumenCompra();
            }
        }
    }

    private void reprocesarBolsas(){
        List<Producto> listaProductosTemp = new ArrayList<>();
        for(Producto producto: listaProductos){
            if(!producto.getEan().equals(Utilidades.eanBolsaTienda())){
                listaProductosTemp.add(producto);
            }
        }

        if(!listaProductosTemp.isEmpty()){
            factura.getProductos().clear();
            listaProductos.clear();

            factura.setProductos(listaProductosTemp);
            listaProductos.addAll(listaProductosTemp);
        }
    }

    @SuppressLint("StringFormatMatches")
    private void procesarBolsas(int cantidadBolsas){
        comproBolsas = true;
        presentador.dialogProgressBar(cantidadBolsas > 1 ? String.format(getResources().getString(R.string.progress_consultando_bolsas), cantidadBolsas) :
                        String.format(getResources().getString(R.string.progress_consultando_bolsa), cantidadBolsas),
                false);
        presentador.consultarBolsa(cantidadBolsas);
    }

    @SuppressLint({"StringFormatMatches", "NotifyDataSetChanged"})
    @Override
    public void respuestaConsultaBolsa(List<Producto> bolsas) {
        int totalProductos = factura.getProductos().size();
        int posicion = factura.getProductos().size();
        for(Producto producto: bolsas){
            if(producto.getPrecioBase() > 0){
                posicion++;
                producto.setLine(posicion);

                factura.getProductos().add(producto);
                listaProductos.add(producto);
            }
        }

        if(factura.getProductos().size() != totalProductos){
            adaptador.notifyDataSetChanged();
            totalizar();

            cantidadBolsas = bolsas.size();
            if(cantidadBolsas > 0){
                Utilidades.mjsToast(cantidadBolsas > 1 ? String.format(getResources().getString(R.string.texto_info_bolsas_agregadas), cantidadBolsas)
                                : getResources().getString(R.string.texto_info_bolsas_agregada),
                        Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                util.vozAndroid(cantidadBolsas > 1 ? String.format(getResources().getString(R.string.texto_info_bolsas_agregadas), cantidadBolsas)
                        : getResources().getString(R.string.texto_info_bolsas_agregada));
            }

            btnContinuarCompra.setText(getResources().getString(R.string.ir_medio_pago));
            comproBolsas = true;
            tvTextoNuevasBolsas.setVisibility(View.VISIBLE);
            btnMasBolsas.setVisibility(View.VISIBLE);
        }else{
            Utilidades.mjsToast("Las o las bolas agregadas tienen precio 0, comunicalo a un cajero",
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
        }
    }

    @Override
    public void vistaResumenCompra() {
        insertarFactura();

        Intent intent = new Intent(contexto, VistaMediosPago.class);
        intent.putExtra(getResources().getString(R.string.key_intent_factura), factura);
        startActivity(intent);
        finish();
    }

    @Override
    public void encerderRFID() {
        btnReiniciar.setVisibility(View.VISIBLE);
        presentador.dialogProgressBar(getResources().getString(R.string.progress_escaneando), false);
        accionLector(Constantes.ENCENDER_LECTOR);
        lectorEncendido = true;
    }

    @Override
    public void cerrarCajon() {
        lectorEncendido = false;
    }

    private void iniciarTareaCajon() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->{
                    mensajeCajon(null);
                });
            }
        };
    }

    public void procesarLecturaRfid(String lectura) {
        List<String> array;
        List<String> tempArray;

        int indiceSerial = lectura.indexOf("D")+13;
        String cadena = agregarCaracter(lectura, ",", indiceSerial);

        tempArray = Arrays.asList(cadena.split(","));
        eanes = new ArrayList<>();

        eanes.addAll(eanesConsulta());

        array = filterDistinctElements(tempArray);

        for(int i=0; i<array.size(); i++){
            String eanLectura = array.get(i);
            if(Utilidades.textoRFID(eanLectura)){
                int indiceEan;
                int indiceSeri;
                if(Utilidades.textoRfid(eanLectura)){
                    indiceEan = eanLectura.indexOf("r");
                    indiceSeri = eanLectura.indexOf('d');
                }else{
                    indiceEan = eanLectura.indexOf("R");
                    indiceSeri = eanLectura.indexOf('D');
                }

                String ean = eanLectura.substring(0, indiceEan);
                String serial = eanLectura.substring(indiceSeri + 1);

                SerialEan serialEan = new SerialEan(ean, serial);

                boolean encontrado = false;
                for(SerialEan serialE : listaSeriales){
                    if(serialE.getEan().equals(ean) && serialE.getSerial().equals(serial)){
                        encontrado = true;
                        break;
                    }
                }

                if(!encontrado){
                    listaSeriales.add(serialEan);
                    eanes.add(ean);
                }
            }else{
                eanes.add(eanLectura);
            }
        }

        runOnUiThread(() ->{
            if(presentador.estadoProgress()){
                List<String> seriales = new ArrayList<>();
                List<String> eaness = new ArrayList<>();
                for (SerialEan s : listaSeriales) {
                    if (noSerial(s.getSerial())) {
                        seriales.add(s.getSerial());
                        eaness.add(s.getEan());
                    }
                }

                if(!eaness.isEmpty()){
                    etConsultaProducto.setText("");
                    presentador.dialogProgressBar(getResources().getString(R.string.progress_consultando_productos),
                            false);
                    presentador.consultarProductos(eanes);
                }else{
                    Utilidades.mjsToast(getResources().getString(R.string.serial_seriales_registrados),
                            Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
                    etConsultaProducto.setText("");
                }
            }
        });
    }

    public void validarConsulta(String lectura) {
        if(isTiendaRFID && Utilidades.textoRFID(lectura)){
            consultaNormal = false;
            procesarLecturaRfid(lectura);
        }else{
            consultaNormal = true;
            etConsultaProducto.setText("");
            if(presentador.estadoProgress()){
                if(!lectura.isEmpty()){
                    presentador.dialogProgressBar(getResources().getString(R.string.progress_consultando_producto),
                            false);
                    presentador.consultarProductos(eanesConsulta(lectura));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

    private void mensajeCajon(String mensaje){
        if(isTiendaRFID){
            KeyboardUtils.hideKeyboard(this);
            lectorEncendido = false;
            if(mensaje != null){
                util.vozAndroid(getResources().getString(R.string.voz_inserte_productos_canasta_nuevos));
            }else{
                util.vozAndroid(getResources().getString(R.string.voz_inserte_productos_canasta));
            }

            VistaCajonDialogFragment cajonDialogFragment = new VistaCajonDialogFragment(this, mensaje);
            cajonDialogFragment.show(getSupportFragmentManager(), "CajonDialogFragment");
        }else{
            btnReiniciar.setVisibility(View.VISIBLE);
        }
    }

    private boolean noSerial(String serial) {
        for (Producto p : listaProductos) {
            if (p.getSerialNumberId() != null) {
                if (p.getSerialNumberId().equals(serial)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        try{
            super.onResume();
            KeyboardUtils.hideKeyboard(this);
        }catch (Exception e){
            Utilidades.sweetAlert(getResources().getString(R.string.error), e.getMessage(),
                    SweetAlertDialog.ERROR_TYPE, this);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            assert data != null;
            String result = data.getStringExtra("datos");

            if(result != null){
                presentador.ocultarDialogProgressBar();
                // Elimina los espacios en blanco de la cadena
                String cadena = result.replaceAll("[\\s\\n\\r]", "");

                if(cadena.isEmpty() && eliminoPrenda){
                    reiniciarTodo();
                }else if(eliminoPrenda){
                    cantidadBolsas = 0;
                    for(Producto producto: listaProductos){
                        if(producto.getEan().equals(Utilidades.eanBolsaTienda())){
                            cantidadBolsas++;
                        }
                    }

                    listaSeriales.clear();
                    listaProductos.clear();

                    if(cantidadBolsas > 0){
                        for(int i=0; i<cantidadBolsas;i++){
                            Producto producto = new Producto();
                            producto.setEan(Utilidades.eanBolsaTienda());
                            listaProductos.add(producto);
                        }
                    }

                    adaptador.notifyDataSetChanged();
                    clFondoCompra.setVisibility(View.GONE);

                    isChangeFromRFID = true;
                    etConsultaProducto.setText(cadena);
                }else{
                    if(!cadena.isEmpty()){
                        isChangeFromRFID = true;
                        etConsultaProducto.setText(cadena);
                    }else{
                        btnReiniciar.setVisibility(View.GONE);
                        presentador.ocultarDialogProgressBar();
                        mensajeCajon(null);
                    }
                }
            }
        }else{
            btnReiniciar.setVisibility(View.GONE);
            presentador.ocultarDialogProgressBar();
            Utilidades.mjsToast(getResources().getString(R.string.problemas_lector),
                    Constantes.TOAST_TYPE_INFO, Toast.LENGTH_LONG, contexto);
            mensajeCajon(null);
        }
    }

    private void accionLector(int accion){
        if(accion == Constantes.APAGAR_LECTOR){
            try{
                Intent intent = new Intent();
                intent.setClassName("com.zebra.zap3", "reader.api");
                intent.putExtra("state", "off");
                startActivity(intent);
            }catch (Exception ex){
                Toast.makeText(contexto,  ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LOGCAT", Objects.requireNonNull(ex.getMessage()));
            }
        }else if(accion == Constantes.ENCENDER_LECTOR){
            try{
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setClassName("com.zebra.zap3", "reader.api");
                i.putExtra("state", "on");
                i.putExtra("time", Integer.toString(tiempoEscuchaLector));
                startActivityForResult(i, 1);
            }catch (Exception ex){
                Toast.makeText(contexto,  ex.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LOGCAT", Objects.requireNonNull(ex.getMessage()));
            }
        }
    }
}