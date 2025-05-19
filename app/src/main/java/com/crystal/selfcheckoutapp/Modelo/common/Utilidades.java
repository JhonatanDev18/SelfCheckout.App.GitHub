package com.crystal.selfcheckoutapp.Modelo.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import androidx.fragment.app.FragmentManager;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import com.crystal.selfcheckoutapp.Modelo.clases.ClaveValor;
import com.crystal.selfcheckoutapp.Modelo.clases.DescuentoSimple;
import com.crystal.selfcheckoutapp.Modelo.clases.Factura;
import com.crystal.selfcheckoutapp.Modelo.clases.KeyWord;
import com.crystal.selfcheckoutapp.Modelo.clases.Line;
import com.crystal.selfcheckoutapp.Modelo.clases.UnitPrice;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCompraDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.CuerpoRespuestaDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.RespuestaCompletaTef;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.datafono.SeguridadDatafono;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.perifericos.RespuestaConsultarPerifericos;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaPantallaCargaDialogFragment;
import com.crystal.selfcheckoutapp.Modelo.retrofit.ClienteRetrofit;
import com.crystal.selfcheckoutapp.Modelo.retrofit.ServiceRetrofit;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class Utilidades {
    private SweetAlertDialog progressDialog;
    private VistaPantallaCargaDialogFragment pantallaCarga;
    private TextToSpeech speech;
    private transient SimpleDateFormat currentDateSimple;
    private Date todayDate;
    private Context contexto;

    public Utilidades(){

    }

    public Utilidades(Context contexto){
        this.contexto = contexto;

        switch (SPM.getString(Constantes.LENGUAJE_APP)){
            case Constantes.LENGUAJE_INGLES:
                speech =  new TextToSpeech(contexto, status -> {
                    if(status != TextToSpeech.ERROR){
                        speech.setLanguage(new Locale("en", "US"));
                    }
                });
                break;
            case Constantes.LENGUAJE_FRANCES:
                speech =  new TextToSpeech(contexto, status -> {
                    if(status != TextToSpeech.ERROR){
                        speech.setLanguage(new Locale("fr", "FR"));
                    }
                });
                break;
            case Constantes.LENGUAJE_RUSO:
                speech =  new TextToSpeech(contexto, status -> {
                    if(status != TextToSpeech.ERROR){
                        speech.setLanguage(new Locale("ru", "RU"));
                    }
                });
                break;
            case Constantes.LENGUAJE_MANDARIN:
                speech =  new TextToSpeech(contexto, status -> {
                    if(status != TextToSpeech.ERROR){
                        speech.setLanguage(new Locale("zh", ""));
                    }
                });
                break;
            default:
                speech =  new TextToSpeech(contexto, status -> {
                    if(status != TextToSpeech.ERROR){
                        speech.setLanguage(new Locale("es", "CO"));
                    }
                });
                break;
        }
    }

    public static void ocultarBarraEstado(Window window){
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void ocultarBarraNavegacion(Window window){
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static void modoInmersivo(Window window){
        // Obtener la vista raíz de tu actividad o fragmento
        View decorView = window.getDecorView();

        // Establecer las banderas para activar el modo inmersivo
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void mostrarDialogProgressBar(Context contexto, String mensaje, boolean cancelable){
        progressDialog = new SweetAlertDialog(contexto, SweetAlertDialog.PROGRESS_TYPE);

        progressDialog.getProgressHelper().setBarColor(contexto.getColor(R.color.azul_claro));
        progressDialog.setContentText(mensaje);
        progressDialog.setCancelable(cancelable);
        progressDialog.show();
    }

    public void mostrarPantallaCargaCustom(FragmentManager fragmentManager, String mensaje, boolean cancelable){
        pantallaCarga = new VistaPantallaCargaDialogFragment(mensaje, cancelable);
        pantallaCarga.show(fragmentManager, "CustomCargaDialogFragment");
    }

    public void ocultarPantallaCargaCustom(){
        if(pantallaCarga != null){
            pantallaCarga.dismiss();
            pantallaCarga = null;
        }
    }

    public void ocultarDialogProgressBar(){
        if(progressDialog != null){
            progressDialog.dismissWithAnimation();
            progressDialog = null;
        }
    }

    public boolean comprobarDialogProgress(){
        return pantallaCarga == null;
    }

    public static void sweetAlert(String titulo, String mensaje, int icono, Context contexto){
        SweetAlertDialog pDialog = new SweetAlertDialog(contexto, icono);

        pDialog.setTitleText(titulo)
                .setContentText(mensaje)
                .setConfirmButton(contexto.getResources().getString(R.string.entiendo), SweetAlertDialog::dismissWithAnimation)
                .setCancelable(false);

        pDialog.show();
    }

    public static void sweetAlertCustom(String titulo, String mensaje, Context contexto, int imagen){
        SweetAlertDialog alertDialog = new SweetAlertDialog(contexto, SweetAlertDialog.CUSTOM_IMAGE_TYPE);

        alertDialog.setTitleText(titulo)
                .setCustomImage(imagen)
                .setContentText(mensaje)
                .setConfirmButton(contexto.getResources().getString(R.string.entiendo), SweetAlertDialog::dismissWithAnimation)
                .setCancelable(false);

        alertDialog.show();
    }

    public static String formatearPrecio(Double precio){
        DecimalFormat formatea = new DecimalFormat(Constantes.DECIMAL_FORMAT);

        return formatea.format(precio);
    }

    public static ServiceRetrofit servicioRetrofit(int api){
        //Declaración del cliente REST
        ClienteRetrofit appCliente = ClienteRetrofit.obtenerInstancia(api);

        return appCliente.obtenerServicios();
    }

    public static void mjsToast(String mensaje, int tipo, int duracion, Context contexto){
        switch (tipo){
            case Constantes.TOAST_TYPE_ERROR:
                Toasty.error(contexto, mensaje, duracion).show();
                break;
            case Constantes.TOAST_TYPE_INFO:
                Toasty.info(contexto, mensaje, duracion).show();
                break;
            case Constantes.TOAST_TYPE_WARNING:
                Toasty.warning(contexto, mensaje, duracion).show();
                break;
            case Constantes.TOAST_TYPE_NORMAL:
                Toasty.normal(contexto, mensaje, duracion).show();
                break;
            case Constantes.TOAST_TYPE_SUCCESS:
                Toasty.success(contexto, mensaje, duracion).show();
                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static Date parceFecha (String fecha, int formatoFecha){
        SimpleDateFormat formato;

        switch (formatoFecha){
            case Constantes.FORMATO_CORTO:
                formato = new SimpleDateFormat(Constantes.FORMATO_FECHA_ANO_MES_DIA);
                break;
                case Constantes.FORMATO_LARGO:
                    formato = new SimpleDateFormat(Constantes.FORMATO_FECHA_ANO_MES_DIA_HORA_MIN_SEG);
                    break;
            default:
                throw new IllegalStateException("Unexpected value: " + formatoFecha);
        }

        Date fechaFormat = null;

        try {
            fechaFormat = formato.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fechaFormat;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formateaFecha(Date fecha, int formatoFecha){
        SimpleDateFormat formato;

        switch (formatoFecha){
            case Constantes.FORMATO_CORTO:
                formato = new SimpleDateFormat(Constantes.FORMATO_FECHA_DIA_MES_ANO);
                break;
            case Constantes.FORMATO_LARGO:
                formato = new SimpleDateFormat(Constantes.FORMATO_FECHA_DIA_MES_ANO_HORA_MIN_SEG);
                break;
            case Constantes.FORMATO_MEDIO:
                formato = new SimpleDateFormat(Constantes.FECHA_AND_HORA_SIMPLE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + formatoFecha);
        }

        return formato.format(fecha);
    }

    public void vozAndroid(String mensaje){
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(speech != null){
                speech.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);
    }

    public static String convertirNumeroMes(int mes){
        return Month.of(mes).getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public static String marcaTienda(){
        String formato = SPM.getString(Constantes.PARAM_FORMATO_TIENDA);
        String marca = "";

        switch (Objects.requireNonNull(formato)){
            case Constantes.FORMATO_GEF:
                marca = "GEF";
                break;
            case Constantes.FORMATO_PB:
                marca = "PUNTO BLANCO";
                break;
            case Constantes.FORMATO_BABY_FRESH:
                marca = "BABY FRESH";
                break;
            case Constantes.FORMATO_GALAX:
                marca = "GALAX";
                break;
            default:
                marca = "MULTIMARCA";
                break;
        }

        return marca;
    }

    @SuppressLint("SimpleDateFormat")
    public String getThisDateSimple() {
        todayDate = new Date();
        currentDateSimple = new SimpleDateFormat(Constantes.FORMATO_FECHA_ANO_MES_DIA);
        return currentDateSimple.format(todayDate);
    }

    @SuppressLint("SimpleDateFormat")
    public String getThisDateSimpleFE() {
        todayDate = new Date();
        currentDateSimple = new SimpleDateFormat(Constantes.FORMATO_FECHA_ANO_MES_DIA_FE);
        return currentDateSimple.format(todayDate);
    }

    @SuppressLint("SimpleDateFormat")
    public String getThisDateSimplePegado() {
        todayDate = new Date();
        currentDateSimple = new SimpleDateFormat(Constantes.FORMATO_FECHA_PEGADA);
        return currentDateSimple.format(todayDate);
    }

    @SuppressLint("SimpleDateFormat")
    public String getThisDate() {
        todayDate = new Date();
        currentDateSimple = new SimpleDateFormat(Constantes.FECHA_AND_HORA_SIMPLE);
        return currentDateSimple.format(todayDate);
    }

    public double calcularSubtotal(List<Producto> productoList) {
        double subtotal = 0.0;
        for(Producto producto : productoList){
            subtotal = subtotal + producto.getPrecioOriginal();
        }
        return subtotal;
    }

    public double calcularTotal(List<Producto> productoList) {
        double total = 0.0;
        for(Producto producto : productoList){
            total = total + producto.getPrecio();
        }
        return total;
    }

    public double calcularDescuentoTotal(List<Producto> productoList) {
        double descuentoTotal = 0.0;
        for(Producto producto : productoList){
            descuentoTotal = descuentoTotal + producto.getDescuentoNeto();
        }
        return descuentoTotal;
    }

    public String sacarOcteno(String tienda){
        String iptienda = "0";
        if(tienda != null){
            if(tienda.length() > 2){
                iptienda = tienda.substring(tienda.length() - 3);
                int octeto;
                try {
                    octeto = Integer.parseInt(iptienda);
                } catch(NumberFormatException e){
                    octeto = 0;
                }
                iptienda = Integer.toString(octeto);
            }
        }
        return iptienda;
    }

    public String getTexto(int texto){
        return contexto.getResources().getString(texto);
    }

    public String convertirTexto(int tamano, String texto){
        String textoImprimir;

        if(texto.length() > tamano){
            textoImprimir = texto.substring(0, tamano);
            texto = texto.replace(textoImprimir, "\n");
            textoImprimir = textoImprimir + texto;
        }else{
            textoImprimir = texto;
        }

        return textoImprimir;
    }

    public static boolean textoRFID(String texto){
        return texto.contains("RFID") || texto.contains("rfid");
    }

    public static boolean textoRfid(String texto){
        return texto.contains("rfid");
    }

    public static String subirCantidad(String num){
        return Integer.toString(Integer.parseInt(num)+1);
    }

    public static String bajarCantidad(String num){
        return num.equals("0") || num.equals("1") ? "0":Integer.toString(Integer.parseInt(num)-1);
    }

    public static void applyClickAnimation(View view) {
        AlphaAnimation clickAnimation = new AlphaAnimation(1.0f, 0.5f);
        clickAnimation.setDuration(100); // Duración de la animación en milisegundos
        view.startAnimation(clickAnimation);
    }

    public List<Line> construirLinesCondicion(List<Producto> productos){
        List<Line> linesProductos = new ArrayList<>();
        List<Producto> listaProductos = new ArrayList<>();

        for(int i=0;i<productos.size(); i++){
            Producto productoInsert = productos.get(i);
            Producto productoCopia = null;
            try{
                productoCopia = (Producto) productoInsert.clone();
            }catch (Exception ex){
                Log.e("LOGCAT", "Error en la copia");
            }

            listaProductos.add(i, productoCopia);
        }

        for(int i=0; i<listaProductos.size(); i++){
            Producto producto = listaProductos.get(i);
            UnitPrice unitPrice = new UnitPrice(producto.getPrecioOriginal(), producto.getPrecio());

            if(!producto.getPrecio().equals(producto.getPrecioOriginal())){
                double tarifaDescuento = producto.getPrecioOriginal() - producto.getPrecio();
                int porcentajeDescuento = (int) Math.round((tarifaDescuento / producto.getPrecioOriginal()) * 100.00);
                producto.setDescuentoTarifa("Descuento Tarifa -" + porcentajeDescuento + "%: "
                        +Utilidades.formatearPrecio(tarifaDescuento));
                producto.setDescuentoNeto(tarifaDescuento);

                producto.setDescuentoSimple(new DescuentoSimple("Descuento Tarifa", tarifaDescuento));

                producto.getListaDescuentoSimple().add(new DescuentoSimple("Descuento Tarifa -"+porcentajeDescuento+"%:",
                        tarifaDescuento));
            }

            Line line = new Line(i+1, false, producto.getArticulo(), producto.getCantidad(),
                    unitPrice);
            linesProductos.add(line);
        }

        return linesProductos;
    }

    public List<KeyWord> construirKeyWordCondicion(){
        List<KeyWord> keyWordList = new ArrayList<>();
        if(SPM.getString(Constantes.PALABRA_CLAVE_CLIENTE) != null){
            String palabraClave = SPM.getString(Constantes.PALABRA_CLAVE_CLIENTE);
            KeyWord keyWord = new KeyWord(true, palabraClave);
            keyWordList.add(keyWord);
        }

        return keyWordList;
    }

    public List<Producto> aplicarDescuentosLines(List<RespuestaLine> respuestaLine, List<Producto> productos){
        List<Producto> listaProductosDescuento = new ArrayList<>();

        for(int i=0;i<productos.size(); i++){
            Producto productoInsert = productos.get(i);
            Producto productoCopia = null;
            try{
                productoCopia = (Producto) productoInsert.clone();
            }catch (Exception ex){
                Log.e("LOGCAT", "Error en la copia");
            }

            listaProductosDescuento.add(i, productoCopia);
        }

        for(int j=0; j<respuestaLine.size(); j++) {
            RespuestaLine line = respuestaLine.get(j);
            double descuento;
            if(line.getAmount().contains(",")){
                descuento = Double.parseDouble(line.getAmount().replaceAll(",", "."));
            }else{
                descuento = Double.parseDouble(line.getAmount());
            }
            int inputposition = Integer.parseInt(line.getId());
            int posicionPro =(inputposition - 1);
            double precio = listaProductosDescuento.get(posicionPro).getPrecio();
            double precioConDescuento = (precio - descuento);

            listaProductosDescuento.get(posicionPro).setDescuento(line);
            listaProductosDescuento.get(posicionPro).setDescuentoNeto(listaProductosDescuento.get(posicionPro).getDescuentoNeto() + descuento);
            listaProductosDescuento.get(posicionPro).setDescuentoTarifa(line.getDescription() + ": "+ Utilidades.formatearPrecio(descuento));
            listaProductosDescuento.get(posicionPro).setPrecio(precioConDescuento);
            listaProductosDescuento.get(posicionPro).setValorFinal("Valor Final: "+ Utilidades.formatearPrecio(precioConDescuento));

            listaProductosDescuento.get(posicionPro).setDescuentoSimple(new DescuentoSimple(line.getDescription(), descuento));

            boolean encontrado = false;
            for(DescuentoSimple descuentoSimple: listaProductosDescuento.get(posicionPro).getListaDescuentoSimple()){
                if (descuentoSimple.getNombre().equals(line.getDescription())) {
                    encontrado = true;
                    break;
                }
            }

            if(!encontrado){
                listaProductosDescuento.get(posicionPro).getListaDescuentoSimple().add(new DescuentoSimple(line.getDescription(), descuento));
            }
        }

        for(Producto producto: listaProductosDescuento){
            if(producto.getValorFinal() == null){
                producto.setValorFinal("Valor Final: "+ Utilidades.formatearPrecio(producto.getPrecio()));
            }
        }

        return listaProductosDescuento;
    }

    public static List<String> eanesConsultar(List<Producto> productoList){
        List<String> eanes = new ArrayList<>();
        for(Producto producto: productoList){
            eanes.add(producto.getEan());
        }

        return eanes;
    }

    public static List<DescuentoSimple> distintDescuento(List<DescuentoSimple> descuentoSimples){
        Set<DescuentoSimple> conjuntosDistintos = new HashSet<>(descuentoSimples);

        return new ArrayList<>(conjuntosDistintos);
    }

    public static boolean orientacionDispositivo(WindowManager windowManager){
        boolean portrait = true;
        Display display = windowManager.getDefaultDisplay();
        int orientacion = display.getRotation();
        if(orientacion == Surface.ROTATION_90 || orientacion == Surface.ROTATION_270){
            portrait = false;
        }
        return portrait;
    }

    public static boolean comprobarInfoDatafono(){
        return SPM.getString(Constantes.IS_REDEBAN) != null;
    }

    public static boolean comprobarInfoDatafonoEmpty(){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));
        boolean vacios;

        if(isRedeban){
            vacios = !SPM.getString(Constantes.USUARIO_DATAFONO_RB).isEmpty() &&
                    !SPM.getString(Constantes.PASS_DATAFONO_RB).isEmpty() &&
                    !SPM.getString(Constantes.CODIGO_DATAFONO_RB).isEmpty() &&
                    !SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB).isEmpty() &&
                    !SPM.getString(Constantes.TIMEOUT_DATAFONO_RB).isEmpty() &&
                    !SPM.getString(Constantes.TIMEOUT_ESPERA_DATAFONO_RB).isEmpty() &&
                    !SPM.getString(Constantes.PASS_SUPERVISOR_RB).isEmpty();
        }else{
            vacios = !SPM.getString(Constantes.USUARIO_DATAFONO).isEmpty() &&
                    !SPM.getString(Constantes.PASS_DATAFONO).isEmpty() &&
                    !SPM.getString(Constantes.CODIGO_DATAFONO).isEmpty() &&
                    !SPM.getString(Constantes.CODIGO_UNICO_DATAFONO).isEmpty() &&
                    !SPM.getString(Constantes.TIMEOUT_DATAFONO).isEmpty() &&
                    !SPM.getString(Constantes.TIMEOUT_ESPERA_DATAFONO).isEmpty();
        }

        return vacios;
    }

    public static int tiempoDatafono(){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));
        return isRedeban ?  transformarTiempoSegundos(SPM.getString(Constantes.TIMEOUT_DATAFONO_RB)):
                transformarTiempoSegundos(SPM.getString(Constantes.TIMEOUT_DATAFONO));
    }

    public static long tiempoEsperaDatafono(){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));
        return isRedeban ?  transformarTiempoMinutos(SPM.getString(Constantes.TIMEOUT_ESPERA_DATAFONO_RB)):
                transformarTiempoMinutos(SPM.getString(Constantes.TIMEOUT_ESPERA_DATAFONO)) ;
    }

    private static int transformarTiempoSegundos(String tiempo){
        return Integer.parseInt(tiempo)*1000;
    }

    private static int transformarTiempoMinutos(String tiempo){
        return Integer.parseInt(tiempo)*60*1000;
    }

    public static RequestCompraDatafono peticionCompraDatafono(Factura factura){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));
        String codigoDatafono = isRedeban ? SPM.getString(Constantes.CODIGO_DATAFONO_RB)
                : SPM.getString(Constantes.CODIGO_DATAFONO);
        SeguridadDatafono seguridadDatafono = isRedeban ?
                new SeguridadDatafono(true,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB),
                        SPM.getString(Constantes.USUARIO_DATAFONO_RB),
                        SPM.getString(Constantes.PASS_DATAFONO_RB)) :
                new SeguridadDatafono(false,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO),
                        SPM.getString(Constantes.USUARIO_DATAFONO),
                        SPM.getString(Constantes.PASS_DATAFONO));

        LogFile.adjuntarLog("Request Compra Datafono", "IsRedeban: "+isRedeban+
                "\nCódigo Datafono: "+codigoDatafono+
                "\nReferencia: "+ factura.getReferenciaInterna() +
                "\nTotal a pagar: " +formatearPrecio(Double.parseDouble(SPM.getString(Constantes.TOTAL_A_PAGAR))));

        return new RequestCompraDatafono(Integer.toString((int) factura.calcularPrecioSinImpuesto()),
                Integer.toString((int) factura.calcularImpuesto()), "0", "0",
                SPM.getString(Constantes.TOTAL_A_PAGAR), codigoDatafono,
                factura.getCedulaCajero(), "", "0",
                "", "", "", factura.getReferenciaInterna(),
                "0", factura.getCaja(), seguridadDatafono);
    }

    public static RequestCompraDatafono peticionCompraDatafono(String referencia){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));
        String codigoDatafono = isRedeban ? SPM.getString(Constantes.CODIGO_DATAFONO_RB)
                : SPM.getString(Constantes.CODIGO_DATAFONO);
        SeguridadDatafono seguridadDatafono = isRedeban ?
                new SeguridadDatafono(true,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB),
                        SPM.getString(Constantes.USUARIO_DATAFONO_RB),
                        SPM.getString(Constantes.PASS_DATAFONO_RB)) :
                new SeguridadDatafono(false,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO),
                        SPM.getString(Constantes.USUARIO_DATAFONO),
                        SPM.getString(Constantes.PASS_DATAFONO));

        LogFile.adjuntarLog("Request Compra Datafono Prueba", "IsRedeban: "+isRedeban+"\nCódigo Datafono: "+codigoDatafono+
                "\nReferencia: "+ referencia);

        return new RequestCompraDatafono("10", "0", "0",
                "0", "10", codigoDatafono, "1",
                "", "10", "", "", "",
                "123456", "0", referencia, seguridadDatafono);
    }

    public static RequestCompraDatafono peticionAnulacionDatafono(RespuestaCompletaTef respuestaCompleta){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));
        CuerpoRespuestaDatafono cuerpoRespuestaDatafono = respuestaCompleta.getRespuestaTef();
        String codigoDatafono = isRedeban ? SPM.getString(Constantes.CODIGO_DATAFONO_RB)
                : SPM.getString(Constantes.CODIGO_DATAFONO);
        SeguridadDatafono seguridadDatafono = isRedeban ?
                new SeguridadDatafono(true,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB),
                        SPM.getString(Constantes.USUARIO_DATAFONO_RB),
                        SPM.getString(Constantes.PASS_DATAFONO_RB)) :
                new SeguridadDatafono(false,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO),
                        SPM.getString(Constantes.USUARIO_DATAFONO),
                        SPM.getString(Constantes.PASS_DATAFONO));

        LogFile.adjuntarLog("Request Anulación Datafono", "IsRedeban: "+isRedeban+"\nCódigo Datafono: "+codigoDatafono+
                "\nNúmero Recibo: "+ cuerpoRespuestaDatafono.getRecibo());

        RequestCompraDatafono requestAnulacionDatafono =
                new RequestCompraDatafono(cuerpoRespuestaDatafono.getValorTotal(),
                        cuerpoRespuestaDatafono.getValorIva(), "0", "0",
                        cuerpoRespuestaDatafono.getValorTotal(), codigoDatafono,
                        SPM.getString(Constantes.ID_CAJERO), "", "0",
                        "", "", "", respuestaCompleta.getHeader().getReferenciaInterna(),
                        "0", SPM.getString(Constantes.CAJA_CODIGO), seguridadDatafono);
        requestAnulacionDatafono.setRecibo(cuerpoRespuestaDatafono.getRecibo());

        if(isRedeban){
            requestAnulacionDatafono.setPasSupervisor(SPM.getString(Constantes.PASS_SUPERVISOR_RB));
        }

        return requestAnulacionDatafono;
    }

    public static RequestRespuestaDatafono peticionRespuestaDatafono(){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));

        String codigoDatafono = isRedeban ? SPM.getString(Constantes.CODIGO_DATAFONO_RB) :
                SPM.getString(Constantes.CODIGO_DATAFONO);
        LogFile.adjuntarLog("Request Respuesta Datafono", "IsRedeban: "+isRedeban+"\nCódigo Datafono: "+codigoDatafono);

        SeguridadDatafono seguridadDatafono = isRedeban ?
                new SeguridadDatafono(true,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO_RB),
                        SPM.getString(Constantes.USUARIO_DATAFONO_RB),
                        SPM.getString(Constantes.PASS_DATAFONO_RB)) :
                new SeguridadDatafono(false,
                        SPM.getString(Constantes.CODIGO_UNICO_DATAFONO),
                        SPM.getString(Constantes.USUARIO_DATAFONO),
                        SPM.getString(Constantes.PASS_DATAFONO));

        return new RequestRespuestaDatafono(codigoDatafono, "1", seguridadDatafono);
    }

    public static String codigoDatafono(){
        boolean isRedeban = Boolean.parseBoolean(SPM.getString(Constantes.IS_REDEBAN));

        return isRedeban ? SPM.getString(Constantes.CODIGO_DATAFONO_RB) : SPM.getString(Constantes.CODIGO_DATAFONO);
    }

    public static List<ClaveValor> politicasLegales(Context contexto){
        List<String> listaTitulos = Arrays.asList(contexto.getResources().getStringArray(R.array.titulos_politicas_gef));
        List<String> listaTextos = Arrays.asList(contexto.getResources().getStringArray(R.array.texto_politicas_gef));
        List<ClaveValor> politicas = new ArrayList<>();

        for(int i=0; i<listaTitulos.size(); i++){
            politicas.add(new ClaveValor(listaTitulos.get(i), listaTextos.get(i)));
        }

        return politicas;
    }

    public static void guardarPerifericos(List<RespuestaConsultarPerifericos> listPerifericos){
        //Método que funciona para configurar todos los perifericos
        for(RespuestaConsultarPerifericos periferico : listPerifericos){
            switch (periferico.getTipoDispositivo()){
                case Constantes.CONST_IMPRESORA_EPSON_AUTOPAGO:
                    SPM.setString(Constantes.IMPRESORA_IP, periferico.getIpEquipo());
                    SPM.setString(Constantes.IMPRESORA_PUERTO, periferico.getPuertoEquipo());
                    break;
                case Constantes.CONST_IMPRESORA_EPSON_BLUETOOTH_AUTOPAGO:
                    SPM.setString(Constantes.IMPRESORA_MAC, periferico.getMacDispositivo());
                    break;
                case Constantes.CONST_DATAFONO_CREDIBANCO_AUTOPAGO:
                    SPM.setString(Constantes.USUARIO_DATAFONO, periferico.getUsuarioWs());
                    SPM.setString(Constantes.PASS_DATAFONO, periferico.getPasswordWs());
                    SPM.setString(Constantes.CODIGO_DATAFONO, periferico.getCodigoDispositivo());
                    SPM.setString(Constantes.CODIGO_UNICO_DATAFONO, periferico.getCodigoUnicoComercio());
                    break;
                case Constantes.CONST_DATAFONO_REDEBAN_AUTOPAGO:
                    SPM.setString(Constantes.USUARIO_DATAFONO_RB, periferico.getUsuarioWs());
                    SPM.setString(Constantes.PASS_DATAFONO_RB, periferico.getPasswordWs());
                    SPM.setString(Constantes.CODIGO_DATAFONO_RB, periferico.getCodigoDispositivo());
                    SPM.setString(Constantes.CODIGO_UNICO_DATAFONO_RB, periferico.getCodigoUnicoComercio());
                    SPM.setString(Constantes.PASS_SUPERVISOR_RB, periferico.getPasswordSupervisor());
                    break;
            }
        }
    }

    public String obtenerHoraConGMT(Date fecha) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ssXXX", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        return timeFormat.format(fecha);
    }

    public static boolean containsSpecialCharacters(String input) {
        String[] caracteresMalCodificados = {
                "Ã¡", "Ã©", "Ã", "Ã³", "Ãº", "Ã‰", "Ã“", "Ãš", "Ã±", "Ã‘", "Ã¨", "Ã", "Ã¬", "Ã²",
                "Ã¹", "Ã’", "Ã‘", "Ã™", "Ã¼", "Ã«", "Ã¯", "Ã¶", "Ãœ", "Ã–", "Ã£", "Ãµ", "Ã¥", "Ã†", "Ã¦",
                "Ã²", "Ã˜", "ÃŸ", "Ã€", "ÃŠ", "Ã”", "ÃŽ"
        };

        for(String caracter : caracteresMalCodificados){
            if(input.contains(caracter)){
                return true;
            }
        }

        return false;
    }

    public static String eanBolsaTienda(){
        String formato = SPM.getString(Constantes.PARAM_FORMATO_TIENDA);
        String ean;

        if(formato != null){
            switch (Objects.requireNonNull(formato)){
                case Constantes.FORMATO_GEF:
                    ean = SPM.getString(Constantes.PARAM_EAN_BOLSA_GEF);
                    break;
                case Constantes.FORMATO_PB:
                    ean = SPM.getString(Constantes.PARAM_EAN_BOLSA_PB);
                    break;
                case Constantes.FORMATO_BABY_FRESH:
                    ean = SPM.getString(Constantes.PARAM_EAN_BOLSA_BF);
                    break;
                case Constantes.FORMATO_GALAX:
                    ean = SPM.getString(Constantes.PARAM_EAN_BOLSA_GALAX);
                    break;
                default:
                    ean = SPM.getString(Constantes.PARAM_EAN_BOLSA_MULTI);
                    break;
            }

            return ean;
        }else{
            return SPM.getString(Constantes.PARAM_EAN_BOLSA_GEF);
        }
    }

    public static Cliente clienteGenerico(Context contexto){
        String idGenerico = SPM.getString(Constantes.PARAM_CLIENTE_GENERICO);
        Cliente cliente = new Cliente();
        cliente.setNombre(contexto.getResources().getString(R.string._cliente));
        cliente.setApellido(contexto.getResources().getString(R.string._generico));
        cliente.setTipoDocumento("1");
        cliente.setTipo("N");
        cliente.setEmpresa("N");
        cliente.setCorreo("");
        cliente.setCelular("");
        cliente.setCustomerId(idGenerico);
        cliente.setCedula(idGenerico);

        return cliente;
    }

    public static boolean verificarClienteOTPFijo(String cedula){
        return cedula.equals(SPM.getString(Constantes.CEDULA_OTP_FIJO));
    }
}