package com.crystal.selfcheckoutapp.Modelo.retrofit.response.cliente;

import android.annotation.SuppressLint;
import android.util.Patterns;

import com.crystal.selfcheckoutapp.Modelo.clases.Payment;
import com.crystal.selfcheckoutapp.Modelo.common.Constantes;
import com.crystal.selfcheckoutapp.Modelo.common.SPM;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente implements Serializable {
    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("sexo")
    @Expose
    private String sexo;

    @SerializedName("celular")
    @Expose
    private String celular;

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("segmentoGEF")
    @Expose
    private String segmentoGEF;

    @SerializedName("segmentoPB")
    @Expose
    private String segmentoPB;

    @SerializedName("segmentoBF")
    @Expose
    private String segmentoBF;

    @SerializedName("segmentoGX")
    @Expose
    private String segmentoGX;

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("empresa")
    @Expose
    private String empresa;

    @SerializedName("estado31")
    @Expose
    private String estado31;

    @SerializedName("medio32")
    @Expose
    private String medio32;

    @SerializedName("adjunto33")
    @Expose
    private String adjunto33;

    @SerializedName("parfois35")
    @Expose
    private String parfois35;

    @SerializedName("diaCumpleanos")
    @Expose
    private String diaCumpleanos;

    @SerializedName("mesCumpleanos")
    @Expose
    private String mesCumpleanos;

    @SerializedName("anoCumpleanos")
    @Expose
    private String anoCumpleanos;

    @SerializedName("tipoDocumento")
    @Expose
    private String tipoDocumento;

    @SerializedName("cedula")
    @Expose
    private String cedula;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("direccion2")
    @Expose
    private String direccion2;

    @SerializedName("ciudad")
    @Expose
    private String ciudad;

    @SerializedName("paisId")
    @Expose
    private String paisId;

    @SerializedName("regionId")
    @Expose
    private String regionId;

    @SerializedName("codigoPostal")
    @Expose
    private String codigoPostal;

    @SerializedName("segmento1")
    @Expose
    private String segmento1;

    @SerializedName("segmento2")
    @Expose
    private String segmento2;

    @SerializedName("nacionalidadId")
    @Expose
    private String nacionalidadId;

    @SerializedName("aceptoHabeasData")
    @Expose
    private boolean aceptoHabeasData;

    private String descuentoPalabraClave;

    private boolean seleccionoDescuento;

    private boolean empleado;

    private String codigoOTP = null;

    private boolean otpVerificado;

    private boolean isReferido;

    private String descuentoReferido;

    private Double cupoEmpleado = 0.0;
    private int calificacion = 0;
    private String empresaTemporal;
    public Cliente(){

    }

    public Cliente(String nombre, String apellido, String sexo, String celular, String correo,
                   String tipo, String segmentoGEF, String segmentoPB, String segmentoBF,
                   String segmentoGX, String customerId, String empresa, String estado31,
                   String medio32, String adjunto33, String parfois35, String diaCumpleanos,
                   String mesCumpleanos, String anoCumpleanos, String tipoDocumento,
                   String cedula, String direccion, String direccion2, String ciudad, String paisId,
                   String regionId, String codigoPostal, String segmento1, String segmento2,
                   String nacionalidadId, boolean aceptoHabeasData) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.celular = celular;
        this.correo = correo;
        this.tipo = tipo;
        this.segmentoGEF = segmentoGEF;
        this.segmentoPB = segmentoPB;
        this.segmentoBF = segmentoBF;
        this.segmentoGX = segmentoGX;
        this.customerId = customerId;
        this.empresa = empresa;
        this.estado31 = estado31;
        this.medio32 = medio32;
        this.adjunto33 = adjunto33;
        this.parfois35 = parfois35;
        this.diaCumpleanos = diaCumpleanos;
        this.mesCumpleanos = mesCumpleanos;
        this.anoCumpleanos = anoCumpleanos;
        this.tipoDocumento = tipoDocumento;
        this.cedula = cedula;
        this.direccion = direccion;
        this.direccion2 = direccion2;
        this.ciudad = ciudad;
        this.paisId = paisId;
        this.regionId = regionId;
        this.codigoPostal = codigoPostal;
        this.segmento1 = segmento1;
        this.segmento2 = segmento2;
        this.nacionalidadId = nacionalidadId;
        this.aceptoHabeasData = aceptoHabeasData;
    }

    public String celularIncognito(){
        String celularIncognito = "";

        if(!celular.isEmpty()){
            celularIncognito = asteriscos(celular.length() - 4) + celular.substring(celular.length() - 4);
        }

        return celularIncognito;
    }

    public String correoIncognito(){
        String correoIncognito = "";

        if(!correo.isEmpty()){
            int indiceArroba = correo.indexOf("@")-4;
            correoIncognito = asteriscos(indiceArroba) + correo.substring(indiceArroba);
        }

        return correoIncognito;
    }

    public String asteriscos(int tamano){
        StringBuilder asteriscos = new StringBuilder();
        for(int i=0; i<tamano; i++){
            asteriscos.append("*");
        }

        return asteriscos.toString();
    }

    public static boolean validarEmail(String email) {
        // Expresión regular para validar un correo electrónico
        String patron = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Compilar la expresión regular en un objeto Pattern
        Pattern pattern = Pattern.compile(patron);

        // Crear un objeto Matcher para realizar la validación
        Matcher matcher = pattern.matcher(email);

        // Verificar si el correo coincide con el patrón
        return matcher.matches();
    }

    public static boolean isMenorEdad(int menorAnos, int anoCliente){
        boolean isClienteMenorEdad = false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy");
        Date todayDate = new Date();
        String anoActual = currentDate.format(todayDate);

        int anoActualInt = Integer.parseInt(anoActual);

        if( (anoActualInt-anoCliente) <= menorAnos ){
            isClienteMenorEdad = true;
        }

        return isClienteMenorEdad;
    }

    public String getTipoDocumentoLetra(){
        String tipoDocumentoLetra = "";
        switch (tipoDocumento){
            case Constantes.DOCUMENTO_CC:
            case Constantes.DOCUMENTO_CICR:
            case Constantes.DOCUMENTO_CJ:
                tipoDocumentoLetra = "CC";
                break;
            case Constantes.DOCUMENTO_NIT:
            case Constantes.DOCUMENTO_NITE:
                tipoDocumentoLetra = "NIT";
                break;
            case Constantes.DOCUMENTO_PASAPORTE:
                tipoDocumentoLetra = "PASS";
                break;
            case Constantes.DOCUMENTO_DIMEX:
            case Constantes.DOCUMENTO_EXTRANJERO:
                tipoDocumentoLetra = "CE";
                break;
        }

        return tipoDocumentoLetra;
    }

    public String getNombreCompleto(){
        return nombre + " " + apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSegmentoGEF() {
        return segmentoGEF;
    }

    public void setSegmentoGEF(String segmentoGEF) {
        this.segmentoGEF = segmentoGEF;
    }

    public String getSegmentoPB() {
        return segmentoPB;
    }

    public void setSegmentoPB(String segmentoPB) {
        this.segmentoPB = segmentoPB;
    }

    public String getSegmentoBF() {
        return segmentoBF;
    }

    public void setSegmentoBF(String segmentoBF) {
        this.segmentoBF = segmentoBF;
    }

    public String getSegmentoGX() {
        return segmentoGX;
    }

    public void setSegmentoGX(String segmentoGX) {
        this.segmentoGX = segmentoGX;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado31() {
        return estado31;
    }

    public void setEstado31(String estado31) {
        this.estado31 = estado31;
    }

    public String getMedio32() {
        return medio32;
    }

    public void setMedio32(String medio32) {
        this.medio32 = medio32;
    }

    public String getAdjunto33() {
        return adjunto33;
    }

    public void setAdjunto33(String adjunto33) {
        this.adjunto33 = adjunto33;
    }

    public String getParfois35() {
        return parfois35;
    }

    public void setParfois35(String parfois35) {
        this.parfois35 = parfois35;
    }

    public String getDiaCumpleanos() {
        return diaCumpleanos;
    }

    public void setDiaCumpleanos(String diaCumpleanos) {
        this.diaCumpleanos = diaCumpleanos;
    }

    public String getMesCumpleanos() {
        return mesCumpleanos;
    }

    public void setMesCumpleanos(String mesCumpleanos) {
        this.mesCumpleanos = mesCumpleanos;
    }

    public String getAnoCumpleanos() {
        return anoCumpleanos;
    }

    public void setAnoCumpleanos(String anoCumpleanos) {
        this.anoCumpleanos = anoCumpleanos;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCedula(boolean isImpresion) {
        if(isImpresion){
            if(cedula.equals(SPM.getString(Constantes.PARAM_CLIENTE_GENERICO))){
                return "222222222222";
            }else{
                return cedula;
            }
        }else{
            return cedula;
        }
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getSegmento1() {
        return segmento1;
    }

    public void setSegmento1(String segmento1) {
        this.segmento1 = segmento1;
    }

    public String getSegmento2() {
        return segmento2;
    }

    public void setSegmento2(String segmento2) {
        this.segmento2 = segmento2;
    }

    public String getNacionalidadId() {
        return nacionalidadId;
    }

    public void setNacionalidadId(String nacionalidadId) {
        this.nacionalidadId = nacionalidadId;
    }

    public boolean isAceptoHabeasData() {
        return aceptoHabeasData;
    }

    public void setAceptoHabeasData(boolean aceptoHabeasData) {
        this.aceptoHabeasData = aceptoHabeasData;
    }

    public String getDescuentoPalabraClave() {
        return descuentoPalabraClave;
    }

    public void setDescuentoPalabraClave(String descuentoPalabraClave) {
        this.descuentoPalabraClave = descuentoPalabraClave;
    }

    public boolean isSeleccionoDescuento() {
        return seleccionoDescuento;
    }

    public void setSeleccionoDescuento(boolean seleccionoDescuento) {
        this.seleccionoDescuento = seleccionoDescuento;
    }

    public boolean isEmpleado() {
        return empleado;
    }

    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }

    public String getCodigoOTP() {
        return codigoOTP;
    }

    public void setCodigoOTP(String codigoOTP) {
        this.codigoOTP = codigoOTP;
    }

    public boolean isOtpVerificado() {
        return otpVerificado;
    }

    public void setOtpVerificado(boolean otpVerificado) {
        this.otpVerificado = otpVerificado;
    }

    public boolean isReferido() {
        return isReferido;
    }

    public void setReferido(boolean referido) {
        isReferido = referido;
    }

    public String getDescuentoReferido() {
        return descuentoReferido;
    }

    public void setDescuentoReferido(String descuentoReferido) {
        this.descuentoReferido = descuentoReferido;
    }

    public Double getCupoEmpleado() {
        return cupoEmpleado;
    }

    public void setCupoEmpleado(Double cupoEmpleado) {
        this.cupoEmpleado = cupoEmpleado;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getEmpresaTemporal() {
        return empresaTemporal;
    }

    public void setEmpresaTemporal(String empresaTemporal) {
        this.empresaTemporal = empresaTemporal;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo='" + sexo + '\'' +
                ", celular='" + celular + '\'' +
                ", correo='" + correo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", segmentoGEF='" + segmentoGEF + '\'' +
                ", segmentoPB='" + segmentoPB + '\'' +
                ", segmentoBF='" + segmentoBF + '\'' +
                ", segmentoGX='" + segmentoGX + '\'' +
                ", customerId='" + customerId + '\'' +
                ", empresa='" + empresa + '\'' +
                ", estado31='" + estado31 + '\'' +
                ", medio32='" + medio32 + '\'' +
                ", adjunto33='" + adjunto33 + '\'' +
                ", parfois35='" + parfois35 + '\'' +
                ", diaCumpleanos='" + diaCumpleanos + '\'' +
                ", mesCumpleanos='" + mesCumpleanos + '\'' +
                ", anoCumpleanos='" + anoCumpleanos + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", cedula='" + cedula + '\'' +
                ", direccion='" + direccion + '\'' +
                ", direccion2='" + direccion2 + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", paisId='" + paisId + '\'' +
                ", regionId='" + regionId + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", segmento1='" + segmento1 + '\'' +
                ", segmento2='" + segmento2 + '\'' +
                ", nacionalidadId='" + nacionalidadId + '\'' +
                ", aceptoHabeasData=" + aceptoHabeasData +
                ", descuentoPalabraClave='" + descuentoPalabraClave + '\'' +
                ", seleccionoDescuento=" + seleccionoDescuento +
                ", empleado=" + empleado +
                ", codigoOTP='" + codigoOTP + '\'' +
                ", otpVerificado=" + otpVerificado +
                ", isReferido=" + isReferido +
                ", descuentoReferido='" + descuentoReferido + '\'' +
                ", cupoEmpleado=" + cupoEmpleado +
                ", calificacion=" + calificacion +
                ", empresaTemporal='" + empresaTemporal + '\'' +
                '}';
    }
}
