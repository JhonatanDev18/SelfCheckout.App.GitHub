package com.crystal.selfcheckoutapp.Modelo.clases;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.MyApp;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.crystal.selfcheckoutapp.Modelo.common.Utilidades;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCerrarDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.request.RequestCrearDocumento;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.aperturacaja.AperturaCaja;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente.Cliente;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales.RespuestaLine;
import com.crystal.selfcheckoutapp.Modelo.retrofit.response.producto.Producto;
import com.crystal.selfcheckoutapp.R;
import com.crystal.selfcheckoutapp.Vista.dialogfragment.VistaMsjCustomUnaAccionDialogFragment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura implements Serializable {

    private String nombreEmpresa = "Crystal S.A.S";
    private String nitEmpresa = "NIT: 890901672-5";
    private String usuarioTienda = SPM.getString(Constantes.USER_NAME) + " - "+SPM.getString(Constantes.CAJA_CODIGO);
    private String cedulaCajero = SPM.getString(Constantes.ID_CAJERO);
    private String usuarioUtil = SPM.getString(Constantes.USER_UTIL);
    private String urlBaseFacturaElectronicaQR = SPM.getString(Constantes.PARAM_URL_BASE_FE_QR);
    private Cliente cliente;
    private Payment medioPago;
    private String tipoResolucion;
    private List<Producto> productos = new ArrayList<>();
    private List<Producto> productosRFID = new ArrayList<>();
    private List<SerialEan> listaSeriales = new ArrayList<>();
    private List<RespuestaLine> descuentos = new ArrayList<>();
    private List<DescuentoSimple> descuentoSimples = new ArrayList<>();
    private List<Detalle> detalleDescuentos = new ArrayList<>();
    private AperturaCaja aperturaCaja;
    private RespuestaDatafono respuestaDatafono;
    private String referenciaInterna;
    private String claveFacturaElectronica;
    private String referenciaInternaFE;
    private String numeroTransaccion;
    private String prefijo;
    private String consecutivo;
    private String textoFiscal;
    private String tienda;
    private String nombreTienda;
    private StringBuilder productosPrecioCero;
    private String formatoTienda = SPM.getString(Constantes.PARAM_FORMATO_TIENDA);
    private String tirillaM2Formato = SPM.getString(Constantes.PARAM_TIQUETE_M2_FORMATO);
    private String tirillaM1Formato = SPM.getString(Constantes.PARAM_TIQUETE_M1_FORMATO);
    private String tirillaM1Marca = SPM.getString(Constantes.PARAM_TIQUETE_M1_MARCA);
    private String tirillaM2Marca = SPM.getString(Constantes.PARAM_TIQUETE_M2_MARCA);
    private String tirillaM1PalabraClave = SPM.getString(Constantes.PARAM_TIQUETE_M1_PALABRA_CLAVE);
    private String tirillaM2PalabraClave = SPM.getString(Constantes.PARAM_TIQUETE_M2_PALABRA_CLAVE);
    private String textMillasHeaderM1 = SPM.getString(Constantes.PARAM_TIQUETE_M1_TEXTO1);
    private String textMillasFooterM1 = SPM.getString(Constantes.PARAM_TIQUETE_M1_TEXTO2);
    private String textMillasHeaderM2 = SPM.getString(Constantes.PARAM_TIQUETE_M2_TEXTO1);
    private String textMillasFooterM2 = SPM.getString(Constantes.PARAM_TIQUETE_M2_TEXTO2);
    private String tiriMensajeDia = SPM.getString(Constantes.PARAM_TIQUETE_MSJ_DIA_DESC);
    private String lineasAtencion = SPM.getString(Constantes.PARAM_TIQUETE_LINEAS_ATENCION);
    private String politaCambios = SPM.getString(Constantes.PARAM_TIQUETE_POLITICA_CAMBIO);
    private String plazos = SPM.getString(Constantes.PARAM_TIQUETE_PLAZO_CAMBIO);
    private String textoContribuyentes = SPM.getString(Constantes.PARAM_TIQUETE_CONTRIBUYENTES);
    private String tarifa = SPM.getString(Constantes.PARAM_TIQUETE_TEXTO_IVA);
    private float tirillaM1Monto = SPM.getFloat(Constantes.PARAM_TIQUETE_M1_MONTO);
    private float tirillaM2Monto = SPM.getFloat(Constantes.PARAM_TIQUETE_M2_MONTO);
    private String proveedorTecnologicoFE = SPM.getString(Constantes.PARAM_PROVEEDOR_TECNOLOGICO_FE);
    private String caja;
    private String divisa;
    private Double baseLmp = 0.0;
    private double ivaCompra = 0;
    private Double totalM1 = 0.0;
    private Double totalM2 = 0.0;
    private Double totalCompraIva;
    private Double totalCompra;
    private Double total;
    private Double subtotal;
    private Double descuentoTotal;
    private int cantidadBolsas = 0;
    private int cantidadArticulos = 0;
    private double precioBolsa = 0;
    private double precioBolsaTotal = 0;
    private boolean pagoTef;
    private boolean configuracion;
    private boolean isCufeCalculado;
    private boolean bluetooth;
    private boolean tirillaM1 = SPM.getBoolean(Constantes.PARAM_TIQUETE_M1_ACTIVO);
    private boolean tirillaM2 = SPM.getBoolean(Constantes.PARAM_TIQUETE_M2_ACTIVO);
    private boolean tirillaM1Gen = SPM.getBoolean(Constantes.PARAM_TIQUETE_M1_CLGEN);
    private boolean tirillaM2Gen = SPM.getBoolean(Constantes.PARAM_TIQUETE_M2_CLGEN);
    private boolean tirillaM1Multiplo = SPM.getBoolean(Constantes.PARAM_TIQUETE_M1_MULTIPLO);
    private boolean tirillaMensajeDia = SPM.getBoolean(Constantes.PARAM_TIQUETE_MSJ_DIA_ACTIVO);
    private String vaucherText;
    private String vaucherPalabra;
    private String cufeHash;
    private Double tarifaDtoUni, porcDtoUni;
    private String impresora;
    private String labelImprimir = "";
    private int activity;
    private List<ClaveValor> listaClaveValor = new ArrayList<>();
    private String tituloClaveValor;
    private Date fechaCreacionFE;
    private boolean errorConsecutivoFiscal, errorCompraDatafono, errorCrearDocumento, errorRfidVenta,
    errorEnviarDescuentos, errorDocumentoDian, errorDocumentoTef, errorActualizarCupoEmpleado,
    errorGuardarRespuestaTef, errorExpirarCodigoReferido, errorFacturaElectronicaQr, errorGuardarTrazaFE,
    errorExtraerInfoDocumento, errorRespuestaDatafono;
    private int cantidadProductosPrecioCero;
    public RequestCrearDocumento crearDocumento(){
        Utilidades util = new Utilidades();
        String customerId = cliente.getCustomerId();
        String salesPersonId = aperturaCaja.getCajero();

        /*Recordar type de documento, tipos validos:
        0: ReturnNotice,
        1: CustomerDelivery,
        2: CustomerOrder,
        3: AvailableOrder,
        4: CustomerReservationRequest,
        5: ReceiptOnHold,
        6: Receipt, ---> CREAR DOCUMENTO
        7: DeliveryPreparation,
        8: CustomersReservation*/
        HeaderCD header = new HeaderCD(true, divisa, customerId, util.getThisDateSimple(),
                referenciaInterna, salesPersonId, tienda, false,
                6, tienda, "");

        List<LineCD> listaLines = new ArrayList<>();
        for(Producto producto: productos){
            LineCD line = new LineCD();

            if(producto.getDescuento() != null){
                line.setDiscountTypeId(producto.getDescuento().getMarkDown());
            }else{
                line.setDiscountTypeId("");
            }

            line.setNetUnitPrice(producto.getPrecio());
            line.setQuantity("1");
            line.setReference(producto.getEan());
            line.setSalesPersonId(salesPersonId);
            line.setUnitPrice(producto.getPrecioOriginal());
            line.setSerialNumberId(producto.getSerialNumberId());
            line.setMovementReasonId("");

            listaLines.add(line);
        }

        List<PaymentCD> listaPayment = new ArrayList<>();
        Payment payment = medioPago;
        PaymentCD paymentCD = new PaymentCD(payment.getAmount(), payment.getCurrencyId(),
                util.getThisDateSimple(), payment.getId(), true, payment.getName(),
                payment.getMethodId());

        listaPayment.add(paymentCD);

        return new RequestCrearDocumento(header, listaLines, listaPayment, caja);
    }

    public RequestCerrarDocumento cerrarDocumento(){
        return new RequestCerrarDocumento(numeroTransaccion, caja);
    }

    public List<RFIDVenta> rfidVenta(){
        Utilidades util = new Utilidades();
        List<RFIDVenta> rfidVentas = new ArrayList<>();

        for(Producto producto : productosRFID){
            if(producto.getSerialNumberId() != null){
                rfidVentas.add(new RFIDVenta(producto.getSerialNumberId(), producto.getEan(),
                        tienda, "1", util.getThisDate(), caja, numeroTransaccion));
            }
        }

        return rfidVentas;
    }

    public List<Detalle> construirDescuentos(){
        Utilidades util = new Utilidades();
        List<Detalle> detalleDesc = new ArrayList<>();
        int contador = 1;
        cantidadArticulos = 0;

        for (Producto producto : productos) {
            int ordenDelDescuento = 1;
            cantidadArticulos++;

            if (producto.getCodigoTasaImpuesto().equals("NOR")) {
                double precioSinImpuesto = Math.round((producto.getPrecio()) / (((producto.getValorTasa()) / 100) + 1));
                baseLmp = baseLmp + precioSinImpuesto;
                ivaCompra = ivaCompra + (producto.getPrecio() - precioSinImpuesto);
            }

            if (tirillaM1Marca.contains(producto.getCodigoMarca().trim())) {
                totalM1 = totalM1 + producto.getPrecio();
            }

            if (tirillaM2Marca.contains(producto.getCodigoMarca().trim())) {
                totalM2 = totalM2 + producto.getPrecio();
            }

            if(producto.getCodigoTasaImpuesto().equals("BO1")){
                cantidadBolsas = cantidadBolsas + 1;
                precioBolsa = producto.getPrecio();
                precioBolsaTotal = precioBolsaTotal + producto.getPrecio();
            }

            if(producto.getPrecioOriginal() > 0){
                if(producto.getDescuento() != null){
                    if(producto.getPrecioOriginal() != producto.getPrecioBase()){
                        double importeIngresoConImpuestos = producto.getPrecioOriginal() - producto.getPrecioBase();
                        double porcDto = (importeIngresoConImpuestos / producto.getPrecioOriginal()) * 100.00;

                        double porcentajeDescuento = round(porcDto, 2);
                        porcDto = importeIngresoConImpuestos / calcularIva(producto);
                        double importeIngresoSinImpuestos = Math.round(porcDto);

                        Detalle detalle = new Detalle(motivoDescuento(producto.getPeriodoTarifa()), porcentajeDescuento, 0,
                                producto.getPrecioOriginal(), producto.getPrecioOriginal(),
                                importeIngresoSinImpuestos, importeIngresoSinImpuestos, importeIngresoConImpuestos,
                                importeIngresoConImpuestos, 0, "FFO", 0, contador,
                                contador, ordenDelDescuento, 0, tienda, util.getThisDateSimple(),
                                divisa, divisa, "001", "", "0");

                        ordenDelDescuento++;
                        detalleDesc.add(detalle);
                    }

                    for (RespuestaLine str : descuentos) {
                        int inputposition = Integer.parseInt(str.getId());
                        int posc = (inputposition -1);

                        if (posc == producto.getLine()) {
                            double unitPrice;
                            if(str.getUnitPrice().contains(",")){
                                unitPrice = Double.parseDouble(str.getUnitPrice().replaceAll(",", "."));
                            }else{
                                unitPrice = Double.parseDouble(str.getUnitPrice());
                            }

                            double importeIngresoConImpuestos;
                            if(str.getAmount().contains(",")){
                                importeIngresoConImpuestos = Double.parseDouble(str.getAmount().replaceAll(",", "."));
                            }else{
                                importeIngresoConImpuestos = Double.parseDouble(str.getAmount());
                            }

                            double porcDto = (importeIngresoConImpuestos / unitPrice) * 100.00;

                            String motivoDescuento = str.getMarkDown();
                            Double porcentajeDescuento = round(porcDto, 2);

                            porcDto = importeIngresoConImpuestos / calcularIva(producto);
                            Double importeIngresoSinImpuestos = round(porcDto, 0);

                            Detalle d = new Detalle(motivoDescuento, porcentajeDescuento, 0, unitPrice, unitPrice,
                                    importeIngresoSinImpuestos, importeIngresoSinImpuestos, importeIngresoConImpuestos, importeIngresoConImpuestos,
                                    0, "FFO", 0, contador, contador, ordenDelDescuento,
                                    0, tienda,  util.getThisDateSimple(), divisa, divisa, "006", str.getIdSaleCondition(), "0");

                            ordenDelDescuento++;
                            detalleDesc.add(d);
                        }
                    }
                }else{
                    if(producto.getPrecioOriginal() == producto.getPrecioBase()){
                        if(producto.getPrecio() != producto.getPrecioBase()){
                            double importeIngresoConImpuestos = producto.getPrecioBase() - producto.getPrecio();
                            double porcDto = (importeIngresoConImpuestos / producto.getPrecioBase()) * 100.00;

                            Double porcentajeDescuento = round(porcDto, 2);

                            porcDto = importeIngresoConImpuestos / calcularIva(producto);
                            Double importeIngresoSinImpuestos = round(porcDto, 0);

                            Detalle d = new Detalle(motivoDescuento(producto.getPeriodoTarifa()), porcentajeDescuento, 0,
                                    producto.getPrecioOriginal(), producto.getPrecioOriginal(),
                                    importeIngresoSinImpuestos, importeIngresoSinImpuestos, importeIngresoConImpuestos,
                                    importeIngresoConImpuestos, 0, "FFO", 0,
                                    contador, contador, ordenDelDescuento, 0, tienda,
                                    util.getThisDateSimple(), divisa, divisa, "002",
                                    "", "0");

                            detalleDesc.add(d);
                        }
                    }else{
                        double importeIngresoConImpuestos = producto.getPrecioOriginal() - producto.getPrecioBase();
                        double porcDto = (importeIngresoConImpuestos / producto.getPrecioOriginal()) * 100.00;

                        double porcentajeDescuento = round(porcDto, 2);
                        porcDto = importeIngresoConImpuestos / calcularIva(producto);
                        Double importeIngresoSinImpuestos = round(porcDto, 0);

                        Detalle d = new Detalle(motivoDescuento(producto.getPeriodoTarifa()), porcentajeDescuento, 0,
                                producto.getPrecioOriginal(), producto.getPrecioOriginal(), importeIngresoSinImpuestos,
                                importeIngresoSinImpuestos, importeIngresoConImpuestos, importeIngresoConImpuestos,
                                0, "FFO", 0, contador, contador, ordenDelDescuento,
                                0, tienda,  util.getThisDateSimple(), divisa, divisa,
                                "001", "", "0");

                        ordenDelDescuento++;
                        detalleDesc.add(d);

                        if (!producto.getPrecio().equals(producto.getPrecioBase())) {

                            importeIngresoConImpuestos = producto.getPrecioBase() - producto.getPrecio();
                            porcDto = (importeIngresoConImpuestos / producto.getPrecioBase()) * 100.00;

                            porcentajeDescuento = round(porcDto, 2);

                            porcDto = importeIngresoConImpuestos / calcularIva(producto);
                            importeIngresoSinImpuestos = round(porcDto, 0);

                            d = new Detalle(motivoDescuento(producto.getPeriodoTarifa()), porcentajeDescuento, 0,
                                    producto.getPrecioOriginal(), producto.getPrecioOriginal(),
                                    importeIngresoSinImpuestos, importeIngresoSinImpuestos, importeIngresoConImpuestos,
                                    importeIngresoConImpuestos, 0, "FFO", 0,
                                    contador, contador, ordenDelDescuento, 0, tienda,
                                    util.getThisDateSimple(), divisa, divisa, "002",
                                    "", "0");

                            detalleDesc.add(d);
                        }
                    }
                }
            }
            contador++;
        }

        return detalleDesc;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private String motivoDescuento(String periodo){
        String motivoDescuento = "SOL";
        if (periodo.equals("000006") || periodo.equals("COVKP0")) {
            motivoDescuento = "SAL";
        }

        return motivoDescuento;
    }

    private double calcularIva(Producto producto){
        double ivaCalc = 1.00;
        if (producto.getValorTasa() != null){
            if (producto.getValorTasa() > 0) {
                ivaCalc = 1 + (producto.getValorTasa() / 100);
            }
        }
        return ivaCalc;
    }

    public String convertirVaucherText(int tamano){
        String textoImprimir;

        if(vaucherText.length() > tamano){
            textoImprimir = vaucherText.substring(0, tamano);
            vaucherText = vaucherText.replace(textoImprimir, "\n");
            textoImprimir = textoImprimir + vaucherText;
        }else{
            textoImprimir = vaucherText;
        }

        return textoImprimir;
    }

    public String convertirTextFooter(int tamaño){
        double totalM2num = Math.floor(totalM2 / tirillaM2Monto);
        String textoImprimir;

        if(textMillasFooterM2.length() > tamaño){
            textMillasFooterM2 = textMillasFooterM2 + (int) totalM2num;
            textoImprimir = textMillasFooterM2.substring(0, tamaño);
            textMillasFooterM2 = textMillasFooterM2.replace(textoImprimir, "\n");
            textoImprimir = textoImprimir + textMillasFooterM2;
        }else{
            textoImprimir = textMillasFooterM2;
        }

        return textoImprimir;
    }

    public String convertirTextoFiscal(int tamano){
        String textoImprimir;
        textoFiscal = textoFiscal.replace("  ", " ");

        if(textoFiscal.length() > tamano){
            textoImprimir = textoFiscal.substring(0, tamano);
            textoFiscal = textoFiscal.replace(textoImprimir, "\n");
            textoImprimir = textoImprimir + textoFiscal;
        }else{
            textoImprimir = textoFiscal;
        }

        return textoImprimir;
    }

    public void calcularTotalCompraIva(){
        totalCompraIva = totalCompra;
        if (!totalCompra.equals(baseLmp + ivaCompra)) {
            totalCompraIva = totalCompra - (totalCompra - baseLmp - ivaCompra);
        }
    }

    public void calcularDescuentosSimples(){
        descuentoSimples = new ArrayList<>();
        List<DescuentoSimple> listaAuxSimple = new ArrayList<>();

        for(Producto producto: productos){
            if(producto.getDescuentoSimple() != null){
                listaAuxSimple.add(producto.getDescuentoSimple());
            }
        }

        if(listaAuxSimple.size() > 0){
            for(DescuentoSimple descuentoSimple: listaAuxSimple){
                double descuentoTotal = 0.0;
                boolean agregar = true;
                for(Producto producto: productos){
                    if(producto.getDescuentoSimple() == null){
                        continue;
                    }
                    if(descuentoSimple.getNombre().equals(producto.getDescuentoSimple().getNombre())){
                        descuentoTotal = descuentoTotal + producto.getDescuentoSimple().getValor();
                    }
                }

                for(DescuentoSimple descuentoSimple1: descuentoSimples){
                    if (descuentoSimple1.getNombre().equals(descuentoSimple.getNombre())) {
                        agregar = false;
                        break;
                    }
                }

                if(agregar && descuentoTotal > 0.0){
                    descuentoSimples.add(new DescuentoSimple(descuentoSimple.getNombre(), descuentoTotal));
                }
            }
        }
    }

    public void calcularPorDescAndTarifaDesc(Double precio, Double otroPrecio){
        tarifaDtoUni = precio - otroPrecio;
        porcDtoUni = (  tarifaDtoUni /  precio * 100.00);
        porcDtoUni = (double) Math.round(porcDtoUni);
    }

    public double calcularPrecioSinImpuesto(){
        double precioSinImpuesto = 0.0;
        for(Producto producto: productos){
            precioSinImpuesto = precioSinImpuesto + producto.getPrecioSinImpuesto();
        }

        return precioSinImpuesto;
    }

    public double calcularImpuesto(){
        double impuesto = 0.0;
        for(Producto producto: productos){
            if (producto.getCodigoTasaImpuesto().equals("NOR")) {
                impuesto = impuesto + Math.round(producto.getPrecio()
                        - ((producto.getPrecio()) / (((producto.getValorTasa()) / 100) + 1)));
            }
        }

        return impuesto;
    }

    public String getVaucherText() {
        vaucherText = SPM.getString(Constantes.TIQUETE_VAUCHER_TEXT);
        return vaucherText;
    }

    public String getVaucherPalabra() {
        vaucherPalabra = SPM.getString(Constantes.TIQUETE_VAUCHER_PALABRA);
        return vaucherPalabra;
    }

    public boolean validarErrores(){
        return errorConsecutivoFiscal || errorCompraDatafono || errorRespuestaDatafono || errorCrearDocumento || errorRfidVenta
                || errorEnviarDescuentos || errorDocumentoDian || errorDocumentoTef || errorActualizarCupoEmpleado
                || errorGuardarRespuestaTef || errorExtraerInfoDocumento || errorFacturaElectronicaQr || errorGuardarTrazaFE;
    }

    public void calcularCufe() {
        Utilidades util = new Utilidades();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#0.00", symbols);

        BigDecimal total = BigDecimal.valueOf(totalCompra);
        BigDecimal iva = BigDecimal.valueOf(ivaCompra);
        BigDecimal totalSinIva = total.subtract(iva);

        String numFac = prefijo + consecutivo;
        String fecha = util.getThisDateSimpleFE();
        String horFac = util.obtenerHoraConGMT(fechaCreacionFE);
        String valorFac = decimalFormat.format(totalSinIva.setScale(2, RoundingMode.HALF_UP));
        String valImp1 = decimalFormat.format(iva.setScale(2, RoundingMode.HALF_UP));
        String valImp2 = "0.00";
        String valImp3 = "0.00";
        String valTotal = decimalFormat.format(total.setScale(2, RoundingMode.HALF_UP));
        String nitFE = "890901672";
        String numAdq = cliente.getCedula(true);
        String clTec = claveFacturaElectronica;
        String tipoAmbiente = SPM.getString(Constantes.PARAM_AMBIENTE_FE_QR);

        String codImp1 = "01";
        String codImp2 = "04";
        String codImp3 = "03";

        String cufeInput = numFac + fecha + horFac + valorFac + codImp1 + valImp1 + codImp2 + valImp2 + codImp3
                + valImp3 + valTotal + nitFE + numAdq + clTec + tipoAmbiente;

        cufeHash = generateSHA384Hash(cufeInput).toUpperCase();
    }

    private static String generateSHA384Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-384");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating SHA-384 hash", e);
        }
    }

    public void construirReferenciaInterna(){
        referenciaInterna = prefijo + "-" + consecutivo;
        referenciaInternaFE = prefijo + "  " + consecutivo;
    }

    public void buscarProductosPrecioCero(boolean tipoConsulta){
        productosPrecioCero = new StringBuilder();
        productosPrecioCero.append("(");
        cantidadProductosPrecioCero = 0;
        List<Producto> productosTemp = new ArrayList<>();
        if(tipoConsulta){
            for (Producto producto : productos) {
                if (producto.getPrecioBase() > 0) {
                    productosTemp.add(producto);
                }else{
                    cantidadProductosPrecioCero++;
                    productosPrecioCero.append(producto.getNombre()).append(",");
                }
            }
        }else{
            if(!listaSeriales.isEmpty()){
                for(Producto producto : productos){
                    try {
                        if (producto.getPrecioBase() > 0) {
                            Producto p = (Producto) producto.clone();
                            for (SerialEan serialEan : listaSeriales) {
                                if (serialEan.getEan().equals(p.getEan())) {
                                    p.setSerialNumberId(serialEan.getSerial());
                                    break;
                                }
                            }

                            productosTemp.add(p);
                        }else{
                            cantidadProductosPrecioCero++;
                            productosPrecioCero.append(producto.getNombre()).append(",");
                        }
                    } catch (CloneNotSupportedException e) {
                        Log.e("logcat","Error clone: "+e.getMessage());
                    }
                }
            }else{
                for (Producto producto : productos) {
                    if (producto.getPrecioBase() > 0) {
                        productosTemp.add(producto);
                    }else{
                        cantidadProductosPrecioCero++;
                        productosPrecioCero.append(producto.getNombre()).append(",");
                    }
                }
            }
        }

        if(cantidadProductosPrecioCero > 0){
            productosPrecioCero.append(").");
        }

        productos = new ArrayList<>();
        productos.addAll(productosTemp);
    }

    public void mostrarMensajePrecioCero(FragmentManager fragmentManager, Context contexto){
        if(cantidadProductosPrecioCero > 0){
            VistaMsjCustomUnaAccionDialogFragment msjCustomDialogFragment;
            if(cantidadProductosPrecioCero == 1) {
                msjCustomDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                        contexto.getResources().getString(R.string.ups_algo_mal),
                        String.format(contexto.getResources().getString(R.string.producto_precio_cero), productosPrecioCero.toString()), "",
                        contexto.getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            }else{
                msjCustomDialogFragment = new VistaMsjCustomUnaAccionDialogFragment(R.drawable.advertencia,
                        contexto.getResources().getString(R.string.ups_algo_mal),
                        String.format(contexto.getResources().getString(R.string.productos_precio_cero), productosPrecioCero.toString()), "",
                        contexto.getResources().getString(R.string.entendido), Constantes.ACCION_DEFAULT);
            }
            msjCustomDialogFragment.show(fragmentManager, "MsjCustomDialogFragment");
        }
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Payment getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(Payment medioPago) {
        this.medioPago = medioPago;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<RespuestaLine> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<RespuestaLine> descuentos) {
        this.descuentos = descuentos;
    }

    public List<DescuentoSimple> getDescuentoSimples() {
        return descuentoSimples;
    }

    public void setDescuentoSimples(List<DescuentoSimple> descuentoSimples) {
        this.descuentoSimples = descuentoSimples;
    }

    public List<Detalle> getDetalleDescuentos() {
        return detalleDescuentos;
    }

    public void setDetalleDescuentos(List<Detalle> detalleDescuentos) {
        this.detalleDescuentos = detalleDescuentos;
    }

    public AperturaCaja getAperturaCaja() {
        return aperturaCaja;
    }

    public void setAperturaCaja(AperturaCaja aperturaCaja) {
        this.aperturaCaja = aperturaCaja;
    }

    public RespuestaDatafono getRespuestaDatafono() {
        return respuestaDatafono;
    }

    public void setRespuestaDatafono(RespuestaDatafono respuestaDatafono) {
        this.respuestaDatafono = respuestaDatafono;
    }

    public String getReferenciaInterna() {
        return referenciaInterna;
    }

    public void setReferenciaInterna(String referenciaInterna) {
        this.referenciaInterna = referenciaInterna;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getTextoFiscal() {
        return textoFiscal;
    }

    public void setTextoFiscal(String textoFiscal) {
        this.textoFiscal = textoFiscal;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getFormatoTienda() {
        return formatoTienda;
    }

    public void setFormatoTienda(String formatoTienda) {
        this.formatoTienda = formatoTienda;
    }

    public String getTirillaM2Formato() {
        return tirillaM2Formato;
    }

    public void setTirillaM2Formato(String tirillaM2Formato) {
        this.tirillaM2Formato = tirillaM2Formato;
    }

    public String getTirillaM1Formato() {
        return tirillaM1Formato;
    }

    public void setTirillaM1Formato(String tirillaM1Formato) {
        this.tirillaM1Formato = tirillaM1Formato;
    }

    public String getTirillaM1Marca() {
        return tirillaM1Marca;
    }

    public void setTirillaM1Marca(String tirillaM1Marca) {
        this.tirillaM1Marca = tirillaM1Marca;
    }

    public String getTirillaM2Marca() {
        return tirillaM2Marca;
    }

    public void setTirillaM2Marca(String tirillaM2Marca) {
        this.tirillaM2Marca = tirillaM2Marca;
    }

    public String getTirillaM1PalabraClave() {
        return tirillaM1PalabraClave;
    }

    public void setTirillaM1PalabraClave(String tirillaM1PalabraClave) {
        this.tirillaM1PalabraClave = tirillaM1PalabraClave;
    }

    public String getTirillaM2PalabraClave() {
        return tirillaM2PalabraClave;
    }

    public void setTirillaM2PalabraClave(String tirillaM2PalabraClave) {
        this.tirillaM2PalabraClave = tirillaM2PalabraClave;
    }

    public String getTextMillasHeaderM1() {
        return textMillasHeaderM1;
    }

    public void setTextMillasHeaderM1(String textMillasHeaderM1) {
        this.textMillasHeaderM1 = textMillasHeaderM1;
    }

    public String getTextMillasFooterM1() {
        return textMillasFooterM1;
    }

    public void setTextMillasFooterM1(String textMillasFooterM1) {
        this.textMillasFooterM1 = textMillasFooterM1;
    }

    public String getTextMillasHeaderM2() {
        return textMillasHeaderM2;
    }

    public void setTextMillasHeaderM2(String textMillasHeaderM2) {
        this.textMillasHeaderM2 = textMillasHeaderM2;
    }

    public String getTextMillasFooterM2() {
        return textMillasFooterM2;
    }

    public void setTextMillasFooterM2(String textMillasFooterM2) {
        this.textMillasFooterM2 = textMillasFooterM2;
    }

    public String getTiriMensajeDia() {
        return tiriMensajeDia;
    }

    public void setTiriMensajeDia(String tiriMensajeDia) {
        this.tiriMensajeDia = tiriMensajeDia;
    }

    public String getLineasAtencion() {
        return convertirTextoEspacios(lineasAtencion);
    }

    public void setLineasAtencion(String lineasAtencion) {
        this.lineasAtencion = lineasAtencion;
    }

    public String getPolitaCambios() {
        return convertirTextoEspacios(politaCambios);
    }

    public void setPolitaCambios(String politaCambios) {
        this.politaCambios = politaCambios;
    }

    public String getPlazos() {
        return convertirTextoEspacios(plazos);
    }

    public void setPlazos(String plazos) {
        this.plazos = plazos;
    }

    public String getTextoContribuyentes() {
        return convertirTextoEspacios(textoContribuyentes);
    }

    private String convertirTextoEspacios(String texto){
        return texto.replaceAll("\\\\n", "\n");
    }

    public void setTextoContribuyentes(String textoContribuyentes) {
        this.textoContribuyentes = textoContribuyentes;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public float getTirillaM1Monto() {
        return tirillaM1Monto;
    }

    public void setTirillaM1Monto(float tirillaM1Monto) {
        this.tirillaM1Monto = tirillaM1Monto;
    }

    public float getTirillaM2Monto() {
        return tirillaM2Monto;
    }

    public void setTirillaM2Monto(float tirillaM2Monto) {
        this.tirillaM2Monto = tirillaM2Monto;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public Double getBaseLmp() {
        return baseLmp;
    }

    public void setBaseLmp(Double baseLmp) {
        this.baseLmp = baseLmp;
    }

    public double getIvaCompra() {
        return ivaCompra;
    }

    public void setIvaCompra(double ivaCompra) {
        this.ivaCompra = ivaCompra;
    }

    public Double getTotalM1() {
        return totalM1;
    }

    public void setTotalM1(Double totalM1) {
        this.totalM1 = totalM1;
    }

    public Double getTotalM2() {
        return totalM2;
    }

    public void setTotalM2(Double totalM2) {
        this.totalM2 = totalM2;
    }

    public Double getTotalCompraIva() {
        return totalCompraIva;
    }

    public void setTotalCompraIva(Double totalCompraIva) {
        this.totalCompraIva = totalCompraIva;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDescuentoTotal() {
        return descuentoTotal;
    }

    public void setDescuentoTotal(Double descuentoTotal) {
        this.descuentoTotal = descuentoTotal;
    }

    public int getCantidadBolsas() {
        return cantidadBolsas;
    }

    public void setCantidadBolsas(int cantidadBolsas) {
        this.cantidadBolsas = cantidadBolsas;
    }

    public int getCantidadArticulos() {
        return cantidadArticulos;
    }

    public void setCantidadArticulos(int cantidadArticulos) {
        this.cantidadArticulos = cantidadArticulos;
    }

    public double getPrecioBolsa() {
        return precioBolsa;
    }

    public void setPrecioBolsa(double precioBolsa) {
        this.precioBolsa = precioBolsa;
    }

    public double getPrecioBolsaTotal() {
        return precioBolsaTotal;
    }

    public void setPrecioBolsaTotal(double precioBolsaTotal) {
        this.precioBolsaTotal = precioBolsaTotal;
    }

    public boolean isPagoTef() {
        return pagoTef;
    }

    public void setPagoTef(boolean pagoTef) {
        this.pagoTef = pagoTef;
    }

    public boolean isConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(boolean configuracion) {
        this.configuracion = configuracion;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean isTirillaM1() {
        return tirillaM1;
    }

    public void setTirillaM1(boolean tirillaM1) {
        this.tirillaM1 = tirillaM1;
    }

    public boolean isTirillaM2() {
        return tirillaM2;
    }

    public void setTirillaM2(boolean tirillaM2) {
        this.tirillaM2 = tirillaM2;
    }

    public boolean isTirillaM1Gen() {
        return tirillaM1Gen;
    }

    public void setTirillaM1Gen(boolean tirillaM1Gen) {
        this.tirillaM1Gen = tirillaM1Gen;
    }

    public boolean isTirillaM2Gen() {
        return tirillaM2Gen;
    }

    public void setTirillaM2Gen(boolean tirillaM2Gen) {
        this.tirillaM2Gen = tirillaM2Gen;
    }

    public boolean isTirillaM1Multiplo() {
        return tirillaM1Multiplo;
    }

    public void setTirillaM1Multiplo(boolean tirillaM1Multiplo) {
        this.tirillaM1Multiplo = tirillaM1Multiplo;
    }

    public boolean isTirillaMensajeDia() {
        return tirillaMensajeDia;
    }

    public void setTirillaMensajeDia(boolean tirillaMensajeDia) {
        this.tirillaMensajeDia = tirillaMensajeDia;
    }

    public void setVaucherText(String vaucherText) {
        this.vaucherText = vaucherText;
    }

    public void setVaucherPalabra(String vaucherPalabra) {
        this.vaucherPalabra = vaucherPalabra;
    }

    public Double getTarifaDtoUni() {
        return tarifaDtoUni;
    }

    public void setTarifaDtoUni(Double tarifaDtoUni) {
        this.tarifaDtoUni = tarifaDtoUni;
    }

    public Double getPorcDtoUni() {
        return porcDtoUni;
    }

    public void setPorcDtoUni(Double porcDtoUni) {
        this.porcDtoUni = porcDtoUni;
    }

    public String getImpresora() {
        return impresora;
    }

    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

    public String getUsuarioTienda() {
        return usuarioTienda;
    }

    public void setUsuarioTienda(String usuarioTienda) {
        this.usuarioTienda = usuarioTienda;
    }

    public String getUsuarioUtil() {
        return usuarioUtil;
    }

    public void setUsuarioUtil(String usuarioUtil) {
        this.usuarioUtil = usuarioUtil;
    }

    public String getCedulaCajero() {
        return cedulaCajero;
    }

    public void setCedulaCajero(String cedulaCajero) {
        this.cedulaCajero = cedulaCajero;
    }

    public String getLabelImprimir() {
        return labelImprimir;
    }

    public void setLabelImprimir(String labelImprimir) {
        this.labelImprimir = labelImprimir;
    }

    public List<ClaveValor> getListaClaveValor() {
        return listaClaveValor;
    }

    public void setListaClaveValor(List<ClaveValor> listaClaveValor) {
        this.listaClaveValor = listaClaveValor;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getTituloClaveValor() {
        return tituloClaveValor;
    }

    public void setTituloClaveValor(String tituloClaveValor) {
        this.tituloClaveValor = tituloClaveValor;
    }

    public boolean isErrorConsecutivoFiscal() {
        return errorConsecutivoFiscal;
    }

    public void setErrorConsecutivoFiscal(boolean errorConsecutivoFiscal) {
        this.errorConsecutivoFiscal = errorConsecutivoFiscal;
    }

    public boolean isErrorCompraDatafono() {
        return errorCompraDatafono;
    }

    public void setErrorCompraDatafono(boolean errorCompraDatafono) {
        this.errorCompraDatafono = errorCompraDatafono;
    }

    public boolean isErrorRespuestaDatafono() {
        return errorRespuestaDatafono;
    }

    public void setErrorRespuestaDatafono(boolean errorRespuestaDatafono) {
        this.errorRespuestaDatafono = errorRespuestaDatafono;
    }

    public boolean isErrorCrearDocumento() {
        return errorCrearDocumento;
    }

    public void setErrorCrearDocumento(boolean errorCrearDocumento) {
        this.errorCrearDocumento = errorCrearDocumento;
    }

    public boolean isErrorRfidVenta() {
        return errorRfidVenta;
    }

    public void setErrorRfidVenta(boolean errorRfidVenta) {
        this.errorRfidVenta = errorRfidVenta;
    }

    public boolean isErrorEnviarDescuentos() {
        return errorEnviarDescuentos;
    }

    public void setErrorEnviarDescuentos(boolean errorEnviarDescuentos) {
        this.errorEnviarDescuentos = errorEnviarDescuentos;
    }

    public boolean isErrorDocumentoDian() {
        return errorDocumentoDian;
    }

    public void setErrorDocumentoDian(boolean errorDocumentoDian) {
        this.errorDocumentoDian = errorDocumentoDian;
    }

    public boolean isErrorDocumentoTef() {
        return errorDocumentoTef;
    }

    public void setErrorDocumentoTef(boolean errorDocumentoTef) {
        this.errorDocumentoTef = errorDocumentoTef;
    }

    public boolean isErrorActualizarCupoEmpleado() {
        return errorActualizarCupoEmpleado;
    }

    public void setErrorActualizarCupoEmpleado(boolean errorActualizarCupoEmpleado) {
        this.errorActualizarCupoEmpleado = errorActualizarCupoEmpleado;
    }

    public boolean isErrorGuardarRespuestaTef() {
        return errorGuardarRespuestaTef;
    }

    public void setErrorGuardarRespuestaTef(boolean errorGuardarRespuestaTef) {
        this.errorGuardarRespuestaTef = errorGuardarRespuestaTef;
    }

    public List<SerialEan> getListaSeriales() {
        return listaSeriales;
    }

    public void setListaSeriales(List<SerialEan> listaSeriales) {
        this.listaSeriales = listaSeriales;
    }

    public boolean isErrorExpirarCodigoReferido() {
        return errorExpirarCodigoReferido;
    }

    public void setErrorExpirarCodigoReferido(boolean errorExpirarCodigoReferido) {
        this.errorExpirarCodigoReferido = errorExpirarCodigoReferido;
    }

    public List<Producto> getProductosRFID() {
        return productosRFID;
    }

    public void setProductosRFID(List<Producto> productosRFID) {
        this.productosRFID = productosRFID;
    }

    public String getReferenciaInternaFE() {
        return referenciaInternaFE;
    }

    public void setReferenciaInternaFE(String referenciaInternaFE) {
        this.referenciaInternaFE = referenciaInternaFE;
    }

    public boolean isErrorFacturaElectronicaQr() {
        return errorFacturaElectronicaQr;
    }

    public void setErrorFacturaElectronicaQr(boolean errorFacturaElectronicaQr) {
        this.errorFacturaElectronicaQr = errorFacturaElectronicaQr;
    }

    public String getCufeHash() {
        return cufeHash;
    }

    public void setCufeHash(String cufeHash) {
        this.cufeHash = cufeHash;
    }

    public boolean isCufeCalculado() {
        return isCufeCalculado;
    }

    public void setCufeCalculado(boolean cufeCalculado) {
        isCufeCalculado = cufeCalculado;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getClaveFacturaElectronica() {
        return claveFacturaElectronica;
    }

    public void setClaveFacturaElectronica(String claveFacturaElectronica) {
        this.claveFacturaElectronica = claveFacturaElectronica;
    }

    public String getTipoResolucion() {
        return tipoResolucion;
    }

    public void setTipoResolucion(String tipoResolucion) {
        this.tipoResolucion = tipoResolucion;
    }

    public boolean isErrorGuardarTrazaFE() {
        return errorGuardarTrazaFE;
    }

    public void setErrorGuardarTrazaFE(boolean errorGuardarTrazaFE) {
        this.errorGuardarTrazaFE = errorGuardarTrazaFE;
    }

    public String getUrlBaseFacturaElectronicaQR() {
        return urlBaseFacturaElectronicaQR;
    }

    public String getProveedorTecnologicoFE(boolean isBixolon) {
        if(isBixolon){
            return proveedorTecnologicoFE.replace("\\n", "\r\n");
        }else{
            return proveedorTecnologicoFE;
        }
    }

    public boolean isErrorExtraerInfoDocumento() {
        return errorExtraerInfoDocumento;
    }

    public void setErrorExtraerInfoDocumento(boolean errorExtraerInfoDocumento) {
        this.errorExtraerInfoDocumento = errorExtraerInfoDocumento;
    }

    public void setFechaCreacionFE(Date fechaCreacionFE) {
        this.fechaCreacionFE = fechaCreacionFE;
    }

    public int getCantidadProductosPrecioCero() {
        return cantidadProductosPrecioCero;
    }
}