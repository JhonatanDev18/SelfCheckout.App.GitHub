package com.crystal.selfcheckoutapp.Modelo.common;

public class Constantes {
    public static final String API_POSSERVICE_URL= "API_POSSERVICE_URL";
    public static final String API_POSSERVICE_URL_NN="API_POSSERVICE_URL_NN";
    public static final String URL_BASE_INTERMEDIA="https://pvp.crystal.com.co:3000/";
    public static final String URL_BASE_NN="https://pvp.crystal.com.co/apps/POSMovilAPI/Api/";

    //public static final String URL_BASE_INTERMEDIA="http://172.18.171.153:3000/";
    //public static final String URL_BASE_NN="http://172.18.171.153:80/apps/POSMovilAPI/Api/";
    public static final String LENGUAJE_APP = "language";
    public static final String LENGUAJE_ESPANOL = "es";
    public static final String LENGUAJE_INGLES = "en";
    public static final String LENGUAJE_FRANCES = "fr";
    public static final String LENGUAJE_RUSO = "ru";
    public static final String LENGUAJE_MANDARIN = "zh";

    //Datáfono
    public static final String CODIGO_DATAFONO = "CODIGO_DATAFONO";
    public static final String USUARIO_DATAFONO = "USUARIO_DATAFONO";
    public static final String PASS_DATAFONO = "PASS_DATAFONO";
    public static final String TIMEOUT_DATAFONO = "TIMEOUT_DATAFONO";
    public static final String TIMEOUT_ESPERA_DATAFONO = "TIMEOUT_ESPERA_DATAFONO";
    public static final String CODIGO_UNICO_DATAFONO = "CODIGO_UNICO_DATAFONO";
    public static final String CODIGO_DATAFONO_RB = "CODIGO_DATAFONO_RB";
    public static final String USUARIO_DATAFONO_RB = "USUARIO_DATAFONO_RB";
    public static final String PASS_DATAFONO_RB = "PASS_DATAFONO_RB";
    public static final String TIMEOUT_DATAFONO_RB = "TIMEOUT_DATAFONO_RB";
    public static final String CEDULA_OTP_FIJO = "CEDULA_OTP_FIJO";
    public static final String CODIGO_OTP_FIJO = "CODIGO_OTP_FIJO";
    public static final String TIMEOUT_ESPERA_DATAFONO_RB = "TIMEOUT_ESPERA_DATAFONO_RB";
    public static final String CODIGO_UNICO_DATAFONO_RB = "CODIGO_UNICO_DATAFONO_RB";
    public static final String PASS_SUPERVISOR_RB = "PASS_SUPERVISOR_RB";
    public static final int PERMISO_WRITE_EXTERNAL_STORAGE = 2;
    public static final String NOMBRE_CARPETA = "SelfCheckout";
    public static final String OBJECT_BIXOLON_DEVICE = "OBJECT_BIXOLON_DEVICE";
    public static final String OBJECT_EPSON_DEVICE = "OBJECT_EPSON_DEVICE";
    public static final String CODIGO_REFERIDO_WSP = "CODIGO_REFERIDO_WSP";
    public static final String CONFIG_IMPRESORA_BIXOLON = "CONFIG_IMPRESORA_BIXOLON";
    public static final String CONFIG_IMPRESORA_EPSON = "CONFIG_IMPRESORA_EPSON";

    //Banderas
    public static final int IMPRESORA_EPSON = 0;
    public static final int IMPRESORA_BIXOLON = 1;

    public static final int APAGAR_LECTOR = 0;
    public static final int ENCENDER_LECTOR = 1;

    public static final String LOGICAL_NAME_SRPQ302 = "SRP-Q302";

    //Acciones dialogCustom
    public static final int ACCION_DEFAULT = 0;
    public static final int ACCION_RETIRAR_PRODUCTO = 1;
    public static final int ACCION_COMPROBAR_RETIRO_PRODUCTO = 2;
    public static final int ACCION_ANULAR_TEF = 3;
    public static final int ACCION_ACTUALIZAR_TRANSACCION_TEF = 4;
    public static final int ACCION_REINICIAR_TODO = 5;
    public static final int ACCION_CUPO_EMPLEADO = 6;
    public static final int ACCION_TEF = 7;
    public static final int ACCION_CLIENTE_CARACTER_ESPECIAL = 8;

    //Calificación
    public static final int CALIFICACION_ENOJADO = 1;
    public static final int CALIFICACION_NEUTRAL = 2;
    public static final int CALIFICACION_FELIZ = 3;

    public static final String DECIMAL_FORMAT = "$#,###.###";
    public static final String FORMATO_FECHA_ANO_MES_DIA_HORA_MIN_SEG = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMATO_FECHA_ANO_MES_DIA = "yyyy-MM-dd";
    public static final String FORMATO_FECHA_ANO_MES_DIA_FE = "yyyy-MM-dd";
    public static final String FORMATO_FECHA_DIA_MES_ANO = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_DIA_MES_ANO_HORA_MIN_SEG = "dd/MM/yyyy'T'HH:mm:ss";
    public static final String FECHA_AND_HORA_SIMPLE = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMATO_FECHA_PEGADA = "yyyyMMddhhmmss";

    public static final int FORMATO_CORTO = 1;
    public static final int FORMATO_LARGO = 2;

    public static final int FORMATO_MEDIO = 3;
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String REFERENCIA_QRBANCOLOMBIA = "REFERENCIA_QRBANCOLOMBIA";
    public static final String PALABRA_CLAVE_CLIENTE = "PALABRA_CLAVE_CLIENTE";

    public static final String CLASE_DOCUMENTO = "FFO";
    public static final String ESTADO_DOCUMENTO = "PEN";
    public static final String TOTAL_A_PAGAR = "TOTAL_A_PAGAR";

    public static final String ID_CAJERO = "ID_CAJERO";

    //Medio de pago
    public static final String MEDIO_PAGO_CODIGO = "MEDIO_PAGO";
    public static final String REFERENCIA_PORCENTAJE = "%";

    public static final int RESP_ACT_BLUE_SELECT = 21;
    public static final int RESP_ACT_BLUE_PRINT = 22;

    //Vistas
    public static final String PROCESO_ACTIVITY = "PROCESO_ACTIVITY";
    public static final int VISTA_CONSULTA_CLIENTE = 1;
    public static final int VISTA_DETALLE_CLIENTE = 2;
    public static final int VISTA_IMPRESION = 3;
    public static final int VISTA_COMPRA_CLIENTE = 4;
    public static final int VISTA_MEDIO_PAGO = 5;

    //Cliente tipo documento letra
    public static final String DOCUMENTO_CC = "1";
    public static final String DOCUMENTO_NIT = "2";
    public static final String DOCUMENTO_PASAPORTE = "8";
    public static final String DOCUMENTO_CICR = "11";
    public static final String DOCUMENTO_CJ = "12";
    public static final String DOCUMENTO_DIMEX = "13";
    public static final String DOCUMENTO_NITE = "14";
    public static final String DOCUMENTO_EXTRANJERO = "15";

    //Constantes xml configuración
    public static final String NOMBRE_ARCHIVO_PREFS = "CRYSTAL_SELFCHECKOUT.xml";

    //Habeas data
    public static final int ESTADO_CONSENTIMIENTO_ACEPTADO = 1;

    //Impresora
    public static final String IMPRESORA_MAC = "IMPRESORA_MAC";
    public static final String IMPRESORA_IP = "IMPRESORA_IP";
    public static final String IMPRESORA_PUERTO = "IMPRESORA_PUERTO";

    public static final String LABEL_CLAVE_VALOR = "ClaveValor";
    public static final String LABEL_CERTIFICADO_REGALO = "CertificadoRegalo";

    //Login
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_UTIL = "USER_UTIL";
    public static final String TIENDA_DEFECTO = "TIENDA_DEFECTO";
    public static final String MSG_ERROR_CONSECUTIVO = "MSG_ERROR_CONSECUTIVO";
    public static final String MSG_ALERTA_APERTURA = "MSG_ALERTA_APERTURA";
    public static final String MSGS_PRIMERA_VEZ = "MSGS_PRIMERA_VEZ";

    //Info Caja
    public static final String CAJA_MEDIO_PAGO_AUTORIZADO = "CAJA_MEDIO_PAGO_AUTORIZADO";
    public static final String CAJA_CODIGO = "CAJA_CODIGO";
    public static final String CAJA_CODIGO_TIENDA = "CAJA_CODIGO_TIENDA";
    public static final String TIENDA_NOMBRE = "TIENDA_NOMBRE";
    public static final String TIENDA_MERCHANTID = "TIENDA_MERCHANTID";
    public static final String PROPOSITO_BANCOLOMBIA_QR_COMPRA = "COMPRA";
    public static final String CAJA_MOVIL = "CAJA_MOVIL";
    public static final String CAJA_PREFIJO = "CAJA_PREFIJO";
    public static final String CAJA_DIVISA = "CAJA_DIVISA";
    public static final String CAJA_PAIS = "CAJA_PAIS";
    public static final String CAJA_IDENTIFICADOR = "CAJA_IDENTIFICADOR";
    public static final String CAJA_NOMBRE_TIENDA = "CAJA_NOMBRE_TIENDA";
    public static final String IS_REDEBAN = "IS_REDEBAN";
    public static final String IS_ACTIVE_LOG = "IS_ACTIVE_LOG";

    //Configuración

    public static final String CONFIGURACION_CONSULTA_ULTIMA_TEF = "CF01";
    public static final String CONFIGURACION_ANULAR_TRANSACION_TEF = "CF02";

    //Menú medios de pago
    public static final int MENU_QR_BANCOLOMBIA = 0;
    public static final int MENU_TARJETA = 1;

    //Menú configuración
    public static final int MENU_IMPRESORA = 0;
    public static final int MENU_TEF = 1;
    public static final int MENU_PERIFERICOS = 2;
    public static final int MENU_LOG = 3;

    //opción salto url base
    public static final int API_INTERMEDIA = 0;
    public static final int API_NN = 1;

    //Toast personalizado
    public static final int TOAST_TYPE_ERROR = 0;
    public static final int TOAST_TYPE_INFO = 1;
    public static final int TOAST_TYPE_SUCCESS = 2;
    public static final int TOAST_TYPE_WARNING = 3;
    public static final int TOAST_TYPE_NORMAL = 4;

    //Servicios
    public static final int SERVICIO_LOGIN = 0;
    public static final int SERVICIO_CAJAS = 1;
    public static final int SERVICIO_CONSECUTIVO_FISCAL = 2;
    public static final int SERVICIO_APERTURA_CAJA = 3;
    public static final int SERVICIO_PARAMETROS = 4;
    public static final int SERVICIO_CONSULTA_CLIENTE = 5;
    public static final int SERVICIO_CONSULTA_HABEAS = 6;
    public static final int SERVICIO_ACTUALIZAR_CLIENTE = 7;
    public static final int SERVICIO_CREAR_CLIENTE = 8;
    public static final int SERVICIO_INSERTAR_HABEAS = 9;
    public static final int SERVICIO_CONSULTA_PRODUCTO = 10;
    public static final int SERVICIO_CONSULTA_CONDICIONES = 11;
    public static final int SERVICIO_GENERAR_QRBANCOLOMBIA = 12;
    public static final int SERVICIO_CONSULTAR_PAGO_QRBANCOLOMBIA = 13;
    public static final int SERVICIO_CONSULTAR_MEDIOS_PAGO_CAJA = 14;
    public static final int SERVICIO_CREAR_DOCUMENTO = 15;
    public static final int SERVICIO_CERRAR_DOCUMENTO = 16;
    public static final int SERVICIO_CREAR_RFID_VENTA = 17;
    public static final int SERVICIO_DETALLE_DESCUENTOS = 18;
    public static final int SERVICIO_DOCUMENTO_DIAN = 19;
    public static final int SERVICIO_DOCUMENTO_TEF = 20;
    public static final int SERVICIO_GENERAR_CODIGO_OTP = 21;
    public static final int SERVICIO_VALIDAR_CODIGO_REFERIDO_WSP = 22;
    public static final int SERVICIO_CONSULTAR_CUPO_EMPLEADO = 23;
    public static final int SERVICIO_ACTUALIZAR_CUPO_EMPLEADO = 24;
    public static final int SERVICIO_CONSULTA_PRODUCTO_BOLSA = 25;
    public static final int SERVICIO_GENERAR_PETICION_COMPRA_DATAFONO = 26;
    public static final int SERVICIO_PETICION_RESPUESTA_DATAFONO = 27;
    public static final int SERVICIO_CALIFICACION_NPS = 28;
    public static final int SERVICIO_CONSULTAR_DOCUMENTO = 29;
    public static final int SERVICIO_GUARDAR_RESPUESTA_TEF = 30;
    public static final int SERVICIO_RESPUESTAS_TEF = 31;
    public static final int SERVICIO_ANULAR_TEF = 32;
    public static final int SERVICIO_ACTUALIZAR_TRANSACCION_TEF = 33;
    public static final int SERVICIO_EXPIRAR_CODIGO_REFERIDO_WSP = 34;
    public static final int SERVICIO_FACTURA_ELECTRONICA_QR = 35;
    public static final int SERVICIO_FACTURA_ELECTRONICA_GUARDAR_TRAZA = 36;
    public static final int SERVICIO_CONSULTAR_PERIFERICOS = 37;
    public static final int SERVICIO_GUARDAR_PERIFERICOS = 38;
    public static final int SERVICIO_CONSULTAR_TIENDAS = 39;
    public static final int SERVICIO_EXTRAER_INFO_DOCUMENTO = 40;
    public static final int SERVICIO_CONSULTA_CLIENTE_CARACTER_ESPECIAL = 41;
    public static final int SERVICIO_PETICION_RESPUESTA_DATAFONO_EXCEPTION = 42;
    public static final int SERVICIO_CONSULTAR_TIENDA = 43;

    //Formatos
    public static final String FORMATO_GEF = "003";
    public static final String FORMATO_PB = "004";
    public static final String FORMATO_BABY_FRESH = "006";
    public static final String FORMATO_GALAX = "010";
    public static final String BOOL_ANIMACION_DATAFONO = "BOOL_ANIMACION_DATAFONO";

    public static final String CONST_IMPRESORA_EPSON_AUTOPAGO = "IMPRESORA EPSON AUTOPAGO";
    public static final String CONST_IMPRESORA_EPSON_BLUETOOTH_AUTOPAGO = "IMPRESORA EPSON BLUETOOTH AUTOPAGO";

    public static final String CONST_DATAFONO_CREDIBANCO_AUTOPAGO = "DATAFONO CREDIBANCO AUTOPAGO";
    public static final String CONST_DATAFONO_REDEBAN_AUTOPAGO = "DATAFONO REDEBAN AUTOPAGO";

    //Parametros
    public static final String PARAM_FORMATO_TIENDA = "PARAM_FORMATO_TIENDA";
    public static final String PARAM_TIENDA_RFID = "PARAM_TIENDA_RFID";
    public static final String PARAM_COL_DESCUENTO_EMPLEADO = "PARAM_COL_DESCUENTO_EMPLEADO";
    public static final String PARAM_COL_MEDIOPAGO_CLI = "PARAM_COL_MEDIOPAGO_CLI";
    public static final String PARAM_EAN_DONACION_UNICEF = "PARAM_EAN_DONACION_UNICEF";
    public static final String PARAM_CAJA_HABEAS = "PARAM_CAJA_HABEAS";
    public static final String PARAM_MSG_ARTICULO_DESCONTABLE = "PARAM_MSG_ARTICULO_DESCONTABLE";
    public static final String PARAM_CODIGO_MEDIOPAGO_QRBANCOLOMBIA = "PARAM_CODIGO_MEDIOPAGO_QRBANCOLOMBIA";
    public static final String PARAM_CODIGO_MEDIOPAGO_TEF = "PARAM_CODIGO_MEDIOPAGO_TEF";
    public static final String PARAM_AMBIENTE_FE_QR="PARAM_AMBIENTE_FE_QR";
    public static final String PARAM_URL_BASE_FE_QR="PARAM_URL_BASE_FE_QR";
    public static final String PARAM_PROVEEDOR_TECNOLOGICO_FE = "PARAM_PROVEEDOR_TECNOLOGICO_FE";

    public static final String PARAM_MENSAJE_OTP = "PARAM_MENSAJE_OTP";
    public static final String PARAM_CLIENTE_GENERICO = "PARAM_CLIENTE_GENERICO";
    public static final String PARAM_CODIGO_MEDIOPAGO_CREDITO1 = "PARAM_CODIGO_MEDIOPAGO_CREDITO1";
    public static final String PARAM_CODIGO_MEDIOPAGO_CREDITO10 = "PARAM_CODIGO_MEDIOPAGO_CREDITO10";
    public static final String PARAM_CANAL_CLIENTE = "PARAM_CANAL_CLIENTE";
    public static final String PARAM_TIPOS_CLIENTE_DESCUENTO = "PARAM_TIPOS_CLIENTE_DESCUENTO";
    public static final String PARAM_TIPOS_EMPRESA_DESCUENTO = "PARAM_TIPOS_EMPRESA_DESCUENTO";
    public static final String PARAM_INSERTAR_DIAN = "PARAM_INSERTAR_DIAN";
    public static final String PARAM_GRUPO_CONFIGURADOR = "PARAM_GRUPO_CONFIGURADOR";
    public static final String PARAM_EAN_BOLSA = "PARAM_EAN_BOLSA";
    public static final String PARAM_EAN_BOLSA_GEF = "PARAM_EAN_BOLSA_GEF";
    public static final String PARAM_EAN_BOLSA_PB = "PARAM_EAN_BOLSA_PB";
    public static final String PARAM_EAN_BOLSA_BF = "PARAM_EAN_BOLSA_BF";
    public static final String PARAM_EAN_BOLSA_GALAX = "PARAM_EAN_BOLSA_GALAX";

    public static final String PARAM_EAN_BOLSA_MULTI = "PARAM_EAN_BOLSA_MULTI";
    public static final String PARAM_TIPOS_CLIENTE_DESCUENTO_REF = "PARAM_TIPOS_CLIENTE_DESCUENTO_REF";
    public static final String PARAM_COL_DESCUENTO_REFERIDO_A = "PARAM_COL_DESCUENTO_REFERIDO_A";
    public static final String PARAM_COL_DESCUENTO_REFERIDO_B = "PARAM_COL_DESCUENTO_REFERIDO_B";
    public static final String PARAM_COL_DESCUENTO_REFERIDO_E = "PARAM_COL_DESCUENTO_REFERIDO_E";
    public static final String PARAM_COL_DESCUENTO_REFERIDO_F = "PARAM_COL_DESCUENTO_REFERIDO_F";
    public static final String PARAM_COL_DESCUENTO_REFERIDO_J = "PARAM_COL_DESCUENTO_REFERIDO_J";
    public static final String PARAM_COL_DESCUENTO_REFERIDO_L = "PARAM_COL_DESCUENTO_REFERIDO_L";
    public static final String PARAM_COL_DESCUENTO_REFERIDO_S = "PARAM_COL_DESCUENTO_REFERIDO_S";
    public static final String PARAM_MEDIOS_PAGOS_CREDITO = "PARAM_MEDIOS_PAGOS_CREDITO";

    //Parametros del vaucher
    public static final String PARAM_TIQUETE_CONTRIBUYENTES = "PARAM_TIQUETE_CONTRIBUYENTES";
    public static final String PARAM_TIQUETE_PLAZO_CAMBIO = "PARAM_TIQUETE_PLAZO_CAMBIO";
    public static final String PARAM_TIQUETE_POLITICA_CAMBIO = "PARAM_TIQUETE_POLITICA_CAMBIO";
    public static final String PARAM_TIQUETE_LINEAS_ATENCION = "PARAM_TIQUETE_LINEAS_ATENCION";
    public static final String PARAM_TIQUETE_MSJ_DIA_ACTIVO = "PARAM_TIQUETE_MSJ_DIA_ACTIVO";
    public static final String PARAM_TIQUETE_MSJ_DIA_DESC = "PARAM_TIQUETE_MSJ_DIA_DESC";
    public static final String PARAM_TIQUETE_TEXTO_IVA = "PARAM_TIQUETE_TEXTO_IVA";
    public static final String PARAM_TIQUETE_M1_ACTIVO = "PARAM_TIQUETE_M1_ACTIVO";
    public static final String PARAM_TIQUETE_M1_FORMATO = "PARAM_TIQUETE_M1_FORMATO";
    public static final String PARAM_TIQUETE_M1_CLGEN = "PARAM_TIQUETE_M1_CLGEN";
    public static final String PARAM_TIQUETE_M1_MARCA = "PARAM_TIQUETE_M1_MARCA";
    public static final String PARAM_TIQUETE_M1_MONTO = "PARAM_TIQUETE_M1_MONTO";
    public static final String PARAM_TIQUETE_M1_MULTIPLO = "PARAM_TIQUETE_M1_MULTIPLO";
    public static final String PARAM_TIQUETE_M1_PALABRA_CLAVE = "PARAM_TIQUETE_M1_PALABRA_CLAVE";
    public static final String PARAM_TIQUETE_M1_TEXTO1 = "PARAM_TIQUETE_M1_TEXTO1";
    public static final String PARAM_TIQUETE_M1_TEXTO2 = "PARAM_TIQUETE_M1_TEXTO2";
    public static final String PARAM_TIQUETE_M2_ACTIVO = "PARAM_TIQUETE_M2_ACTIVO";
    public static final String PARAM_TIQUETE_M2_FORMATO = "PARAM_TIQUETE_M2_FORMATO";
    public static final String PARAM_TIQUETE_M2_CLGEN = "PARAM_TIQUETE_M2_CLGEN";
    public static final String PARAM_TIQUETE_M2_MARCA = "PARAM_TIQUETE_M2_MARCA";
    public static final String PARAM_TIQUETE_M2_MONTO = "PARAM_TIQUETE_M2_MONTO";
    public static final String PARAM_TIQUETE_M2_PALABRA_CLAVE = "PARAM_TIQUETE_M2_PALABRA_CLAVE";
    public static final String PARAM_TIQUETE_M2_TEXTO1 = "PARAM_TIQUETE_M2_TEXTO1";
    public static final String PARAM_TIQUETE_M2_TEXTO2 = "PARAM_TIQUETE_M2_TEXTO2";
    public static final String PARAM_EMPRESA_TEMPORALES = "PARAM_EMPRESA_TEMPORALES";
    public static final String PARAM_OPCIONES_CONFIGURACION = "PARAM_OPCIONES_CONFIGURACION";
    public static final String PARAM_TIEMPO_ESCUCHA_LECTOR_AND_MSJ_CAJON = "PARAM_TIEMPO_ESCUCHA_LECTOR_AND_MSJ_CAJON";
    public static final String PARAM_CANTIDAD_BOLSAS_FACTURABLES = "PARAM_CANTIDAD_BOLSAS_FACTURABLES";
    public static final String PARAM_GENERAR_CERTIFICADO_AUTOMATICO = "PARAM_GENERAR_CERTIFICADO_AUTOMATICO";
    public static final String TIQUETE_VAUCHER_TEXT = "TIQUETE_VAUCHER_TEXT";
    public static final String TIQUETE_VAUCHER_PALABRA = "TIQUETE_VAUCHER_PALABRA";
}
