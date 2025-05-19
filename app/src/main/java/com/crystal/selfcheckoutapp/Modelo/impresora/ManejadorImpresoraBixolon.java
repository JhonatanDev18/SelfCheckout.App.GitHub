package com.crystal.selfcheckoutapp.Modelo.impresora;

import static android.widget.RelativeLayout.ALIGN_RIGHT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import com.bxl.config.editor.BXLConfigLoader;
import com.zebra.sdk.comm.ConnectionException;

import java.io.IOException;

import jpos.CashDrawer;
import jpos.JposException;
import jpos.LocalSmartCardRW;
import jpos.MSR;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.SmartCardRW;
import jpos.config.JposEntry;
import jpos.events.DataEvent;
import jpos.events.DataListener;
import jpos.events.DirectIOEvent;
import jpos.events.DirectIOListener;
import jpos.events.ErrorEvent;
import jpos.events.ErrorListener;
import jpos.events.OutputCompleteEvent;
import jpos.events.OutputCompleteListener;
import jpos.events.StatusUpdateEvent;
import jpos.events.StatusUpdateListener;

public class ManejadorImpresoraBixolon implements IManejadorImpresora, ErrorListener, OutputCompleteListener, StatusUpdateListener, DirectIOListener, DataListener {


    // ------------------- Text attribute ------------------- //
    public static int ATTRIBUTE_NORMAL = 0;
    public static int ATTRIBUTE_FONT_A = 1;
    public static int ATTRIBUTE_FONT_B = 2;
    public static int ATTRIBUTE_FONT_C = 4;
    public static int ATTRIBUTE_BOLD = 8;
    public static int ATTRIBUTE_UNDERLINE = 16;
    public static int ATTRIBUTE_REVERSE = 32;
    public static int ATTRIBUTE_FONT_D = 64;

    // ------------------- Barcode Symbology ------------------- //
    public static int BARCODE_TYPE_UPCA = POSPrinterConst.PTR_BCS_UPCA;
    public static int BARCODE_TYPE_UPCE = POSPrinterConst.PTR_BCS_UPCE;
    public static int BARCODE_TYPE_EAN8 = POSPrinterConst.PTR_BCS_EAN8;
    public static int BARCODE_TYPE_EAN13 = POSPrinterConst.PTR_BCS_EAN13;
    public static int BARCODE_TYPE_ITF = POSPrinterConst.PTR_BCS_ITF;
    public static int BARCODE_TYPE_Codabar = POSPrinterConst.PTR_BCS_Codabar;
    public static int BARCODE_TYPE_Code39 = POSPrinterConst.PTR_BCS_Code39;
    public static int BARCODE_TYPE_Code93 = POSPrinterConst.PTR_BCS_Code93;
    public static int BARCODE_TYPE_Code128 = POSPrinterConst.PTR_BCS_Code128;
    public static int BARCODE_TYPE_PDF417 = POSPrinterConst.PTR_BCS_PDF417;
    public static int BARCODE_TYPE_MAXICODE = POSPrinterConst.PTR_BCS_MAXICODE;
    public static int BARCODE_TYPE_DATAMATRIX = POSPrinterConst.PTR_BCS_DATAMATRIX;
    public static int BARCODE_TYPE_QRCODE = POSPrinterConst.PTR_BCS_QRCODE;
    public static int BARCODE_TYPE_EAN128 = POSPrinterConst.PTR_BCS_EAN128;

    // ------------------- Barcode HRI ------------------- //
    public static int BARCODE_HRI_NONE = POSPrinterConst.PTR_BC_TEXT_NONE;
    public static int BARCODE_HRI_ABOVE = POSPrinterConst.PTR_BC_TEXT_ABOVE;
    public static int BARCODE_HRI_BELOW = POSPrinterConst.PTR_BC_TEXT_BELOW;

    // ------------------- Farsi Option ------------------- //
    public static int OPT_REORDER_FARSI_RTL = 0;
    public static int OPT_REORDER_FARSI_MIXED = 1;

    // ------------------- CharacterSet ------------------- //
    public static int CS_437_USA_STANDARD_EUROPE = 437;
    public static int CS_737_GREEK = 737;
    public static int CS_775_BALTIC = 775;
    public static int CS_850_MULTILINGUAL = 850;
    public static int CS_852_LATIN2 = 852;
    public static int CS_855_CYRILLIC = 855;
    public static int CS_857_TURKISH = 857;
    public static int CS_858_EURO = 858;
    public static int CS_860_PORTUGUESE = 860;
    public static int CS_862_HEBREW_DOS_CODE = 862;
    public static int CS_863_CANADIAN_FRENCH = 863;
    public static int CS_864_ARABIC = 864;
    public static int CS_865_NORDIC = 865;
    public static int CS_866_CYRILLIC2 = 866;
    public static int CS_928_GREEK = 928;
    public static int CS_1250_CZECH = 1250;
    public static int CS_1251_CYRILLIC = 1251;
    public static int CS_1252_LATIN1 = 1252;
    public static int CS_1253_GREEK = 1253;
    public static int CS_1254_TURKISH = 1254;
    public static int CS_1255_HEBREW_NEW_CODE = 1255;
    public static int CS_1256_ARABIC = 1256;
    public static int CS_1257_BALTIC = 1257;
    public static int CS_1258_VIETNAM = 1258;
    public static int CS_FARSI = 7065;
    public static int CS_KATAKANA = 7565;
    public static int CS_KHMER_CAMBODIA = 7572;
    public static int CS_THAI11 = 8411;
    public static int CS_THAI14 = 8414;
    public static int CS_THAI16 = 8416;
    public static int CS_THAI18 = 8418;
    public static int CS_THAI42 = 8442;
    public static int CS_KS5601 = 5601;
    public static int CS_BIG5 = 6605;
    public static int CS_GB2312 = 2312;
    public static int CS_SHIFT_JIS = 8374;
    public static int CS_TCVN_3_1 = 3031;
    public static int CS_TCVN_3_2 = 3032;

    private Context context = null;

    private BXLConfigLoader bxlConfigLoader = null;
    private POSPrinter posPrinter = null;
    private MSR msr = null;
    private SmartCardRW smartCardRW = null;
    private LocalSmartCardRW localSmartCardRW;
    private CashDrawer cashDrawer = null;

    private int mPortType;
    private String mAddress;
    private static final String MENSAJE_ERROR_IMPRESION = "Error imprimiendo en bixolon";
    private int alineacionBixolon;
    private int tamanoPersonalizado = 0;

    public ManejadorImpresoraBixolon(Context context) {
        this.context = context;

        posPrinter = new POSPrinter(this.context);
        posPrinter.addStatusUpdateListener(this);
        posPrinter.addErrorListener(this);
        posPrinter.addOutputCompleteListener(this);
        posPrinter.addDirectIOListener(this);

        msr = new MSR();
        msr.addDataListener(this);
        smartCardRW = new SmartCardRW();
        localSmartCardRW = new LocalSmartCardRW();
        cashDrawer = new CashDrawer();

        bxlConfigLoader = new BXLConfigLoader(this.context);
        try {
            bxlConfigLoader.openFile();
        } catch (Exception e) {
            bxlConfigLoader.newFile();
        }
    }

    @Override
    public void inicializar() {

    }

    @Override
    public void alinear(int alineacion) throws ExcepcionImpresion {
        try {
            switch (alineacion){
                case IManejadorImpresora.ALINEACION_IZQUIERDA_BX:
                    alineacionBixolon = 1;
                    break;
                case IManejadorImpresora.ALINEACION_CENTRO_BX:
                    alineacionBixolon = 2;
                    break;
                default:
                    alineacionBixolon = 4;
                    break;
            }
        }catch (Exception e){
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void saltarLinea() throws ExcepcionImpresion {
        try {
            posPrinter.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\n");
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void imprimirTexto(String texto, float tamano, boolean resaltar, boolean subrayar) throws ExcepcionImpresion {
        try {
            String strOption = EscapeSequence.getString(0);

            if ((alineacionBixolon & ALINEACION_IZQUIERDA_BX) == ALINEACION_IZQUIERDA_BX) {
                strOption += EscapeSequence.getString(4);
            }

            if ((alineacionBixolon & ALINEACION_CENTRO_BX) == ALINEACION_CENTRO_BX) {
                strOption += EscapeSequence.getString(5);
            }

            if ((alineacionBixolon & ALINEACION_DERECHA_BX) == ALINEACION_DERECHA_BX) {
                strOption += EscapeSequence.getString(6);
            }

            if (resaltar) {
                strOption += EscapeSequence.getString(7);
            }

            switch ((int) tamano) {
                case 2:
                    strOption += EscapeSequence.getString(17);
                    strOption += EscapeSequence.getString(26);
                    break;
                case 3:
                    strOption += EscapeSequence.getString(19);
                    strOption += EscapeSequence.getString(27);
                    break;
                case 4:
                    strOption += EscapeSequence.getString(20);
                    strOption += EscapeSequence.getString(28);
                    break;
                case 5:
                    strOption += EscapeSequence.getString(21);
                    strOption += EscapeSequence.getString(29);
                    break;
                case 6:
                    strOption += EscapeSequence.getString(22);
                    strOption += EscapeSequence.getString(30);
                    break;
                case 7:
                    strOption += EscapeSequence.getString(23);
                    strOption += EscapeSequence.getString(31);
                    break;
                case 8:
                    strOption += EscapeSequence.getString(24);
                    strOption += EscapeSequence.getString(32);
                    break;
                default:
                    strOption += EscapeSequence.getString(17);
                    strOption += EscapeSequence.getString(25);
                    break;
            }

            posPrinter.printNormal(POSPrinterConst.PTR_S_RECEIPT, strOption + texto);
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void imprimirColumnas(Object[] textos, int[] anchos, int[] alineaciones, int[] tamanos, boolean resaltar) throws ExcepcionImpresion {
        try {
            StringBuilder formato = new StringBuilder();
            for (int i=0; i < textos.length;i++){
                formato.append("%");
                formato.append(String.valueOf(i+1));
                formato.append("$");
                if (alineaciones[i] == IManejadorImpresora.ALINEACION_IZQUIERDA_BX){
                    formato.append("-");
                }
                formato.append(String.valueOf(anchos[i]));
                formato.append("s ");
            }

            if(tamanoPersonalizado != 0){
                imprimirTexto(String.format(formato.toString(), textos), tamanoPersonalizado,resaltar ,false );
                tamanoPersonalizado = 0;
            }else{
                imprimirTexto(String.format(formato.toString(), textos), 0,resaltar ,false );
            }
        } catch (Exception e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void cortarPapel() throws ExcepcionImpresion {
        try {
            @SuppressLint("DefaultLocale") String cutPaper = EscapeSequence.ESCAPE_CHARACTERS + String.format("%dfP", 90); // Feed Partial Cut
            posPrinter.printNormal(POSPrinterConst.PTR_S_RECEIPT, cutPaper);
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void alimentaPapel() throws ExcepcionImpresion {
    }

    @Override
    public void abrirCajon() throws ExcepcionImpresion {

    }

    @Override
    public void abrirConexion() throws ExcepcionImpresion {

    }

    @Override
    public boolean abrirConexion(int portType, String logicalName, String address, boolean isAsyncMode) throws ExcepcionImpresion {
        if (establecerImpresora(portType, logicalName, BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER, address)) {
            int retry = 1;
            if (portType == BXLConfigLoader.DEVICE_BUS_BLUETOOTH_LE) {
                retry = 5;
            }

            for (int i = 0; i < retry; i++) {
                try {
                    posPrinter.open(logicalName);
                    posPrinter.claim(5000 * 2);
                    posPrinter.setDeviceEnabled(true);
                    posPrinter.setAsyncMode(isAsyncMode);

                    mPortType = portType;
                    mAddress = address;
                    return true;
                } catch (JposException e) {
                    try {
                        posPrinter.close();
                    } catch (JposException e1) {
                        throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void confirmarComando() throws ExcepcionImpresion {

    }

    @Override
    public void iniciarImpresion() throws ExcepcionImpresion, IOException {

    }

    @Override
    public void imprimirSeparador() throws ExcepcionImpresion {
        try {
            imprimirTexto("------------------------------------------------", 1, false, false);
        } catch (ExcepcionImpresion e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void imprimirSeparador(int startX, int startY, int stopX, int stopY, int thickness) throws ExcepcionImpresion {
        try {
            posPrinter.printLine(startX, startY, stopX, stopY, thickness);
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void imprimirCodigoBarras(String codigoBarras, byte[] ancho) throws ExcepcionImpresion {

    }

    @Override
    public void imprimirCodigoBarras(String data, int width, int height, int alignment) throws ExcepcionImpresion {
        try {
            if (alignment == ALINEACION_IZQUIERDA_BX) {
                alignment = POSPrinterConst.PTR_BC_LEFT;
            } else if (alignment == ALINEACION_CENTRO_BX) {
                alignment = POSPrinterConst.PTR_BC_CENTER;
            } else {
                alignment = POSPrinterConst.PTR_BC_RIGHT;
            }

            posPrinter.printBarCode(POSPrinterConst.PTR_S_RECEIPT, data, BARCODE_TYPE_Code128, height, width, alignment, BARCODE_HRI_NONE);
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void imprimirQR(String texto, int tamano) throws ExcepcionImpresion {

    }

    @Override
    public void imprimirQR(String data, int width, int height, int alignment) throws ExcepcionImpresion {
        try {
            if (alignment == ALINEACION_IZQUIERDA_BX) {
                alignment = POSPrinterConst.PTR_BC_LEFT;
            } else if (alignment == ALINEACION_CENTRO_BX) {
                alignment = POSPrinterConst.PTR_BC_CENTER;
            } else {
                alignment = POSPrinterConst.PTR_BC_RIGHT;
            }

            posPrinter.printBarCode(POSPrinterConst.PTR_S_RECEIPT, data, BARCODE_TYPE_QRCODE, height, width, alignment, BARCODE_HRI_NONE);
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void desconectar() throws ExcepcionImpresion {
        try {
            if (posPrinter.getClaimed()) {
                posPrinter.setDeviceEnabled(false);
                posPrinter.release();
                posPrinter.close();
            }
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void comandoFormatoTexto(int comando) throws ExcepcionImpresion {

    }

    @Override
    public boolean isConnected() throws ExcepcionImpresion {
        return false;
    }

    @Override
    public void imprimirBitmap(Bitmap bitmap) throws ExcepcionImpresion {

    }

    @Override
    public void obtenerConeccionGlobal(String mac) throws ExcepcionImpresion {

    }

    @Override
    public void getConnection() throws ExcepcionImpresion {

    }

    @Override
    public void imprimirCodigoBarrasSunmi(String texto, int Simbologia, int alto, int ancho, int posicionTexto) throws ExcepcionImpresion {

    }

    @Override
    public void imprimirColumnasSunmi(String[] textos, int[] anchos, int[] alineaciones, boolean resaltar, float tamano) throws ExcepcionImpresion {

    }

    @Override
    public void imprimirColumnasImin(String[] textos, int[] anchos, int[] alineaciones, int[] tamanos, boolean resaltar) throws ExcepcionImpresion {

    }

    @Override
    public void imprimirCodigoBarrasImin(String codigoBarras, byte[] ancho) throws ExcepcionImpresion {

    }

    @Override
    public boolean establecerImpresora(int portType, String logicalName, int deviceCategory, String address) throws ExcepcionImpresion {
        try {
            for (JposEntry entry : bxlConfigLoader.getEntries()) {
                if (entry.getLogicalName().equals(logicalName)) {
                    bxlConfigLoader.removeEntry(logicalName);
                    break;
                }
            }

            bxlConfigLoader.addEntry(logicalName, deviceCategory, BXLConfigLoader.PRODUCT_NAME_SRP_Q302, portType, address);

            bxlConfigLoader.saveFile();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public int getPrinterMaxWidth() throws ExcepcionImpresion{
        int width = 0;
        try {
            if (!posPrinter.getDeviceEnabled()) {
                return width;
            }

            width = posPrinter.getRecLineWidth();

            return width;
        } catch (JposException e) {
            throw new ExcepcionImpresion(MENSAJE_ERROR_IMPRESION, e);
        }
    }

    @Override
    public void setTamano(int tamano) {
        tamanoPersonalizado = tamano;
    }

    @Override
    public void dataOccurred(DataEvent dataEvent) {

    }

    @Override
    public void directIOOccurred(DirectIOEvent directIOEvent) {

    }

    @Override
    public void errorOccurred(ErrorEvent errorEvent) {

    }

    @Override
    public void outputCompleteOccurred(OutputCompleteEvent outputCompleteEvent) {

    }

    @Override
    public void statusUpdateOccurred(StatusUpdateEvent statusUpdateEvent) {

    }
}
