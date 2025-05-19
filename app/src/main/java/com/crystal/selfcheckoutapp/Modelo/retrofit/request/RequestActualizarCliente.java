package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestActualizarCliente {
    @SerializedName("cedula")
    @Expose
    private String cedula;

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("nombres")
    @Expose
    private String nombres;

    @SerializedName("apellidos")
    @Expose
    private String apellidos;

    @SerializedName("genero")
    @Expose
    private String genero;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("celular")
    @Expose
    private String celular;

    @SerializedName("diaCumple")
    @Expose
    private String diaCumple;

    @SerializedName("mesCumple")
    @Expose
    private String mesCumple;

    @SerializedName("anoCumple")
    @Expose
    private String anoCumple;

    @SerializedName("tienda")
    @Expose
    private String tienda;

    @SerializedName("direccion1")
    @Expose
    private String direccion1;

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

    @SerializedName("InsertarSegmentoHabeasData")
    @Expose
    private Boolean insertarSegmentoHabeasData;

    public RequestActualizarCliente(String cedula, String customerId, String nombres,
                                    String apellidos, String genero, String email, String celular,
                                    String diaCumple, String mesCumple, String anoCumple,
                                    String tienda, String direccion1, String direccion2,
                                    String ciudad, String paisId, String regionId, String codigoPostal,
                                    Boolean insertarSegmentoHabeasData) {
        this.cedula = cedula;
        this.customerId = customerId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.email = email;
        this.celular = celular;
        this.diaCumple = diaCumple;
        this.mesCumple = mesCumple;
        this.anoCumple = anoCumple;
        this.tienda = tienda;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.ciudad = ciudad;
        this.paisId = paisId;
        this.regionId = regionId;
        this.codigoPostal = codigoPostal;
        this.insertarSegmentoHabeasData = insertarSegmentoHabeasData;
    }

    @Override
    public String toString() {
        return "RequestActualizarCliente{" +
                "cedula='" + cedula + '\'' +
                ", customerId='" + customerId + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", genero='" + genero + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", diaCumple='" + diaCumple + '\'' +
                ", mesCumple='" + mesCumple + '\'' +
                ", anoCumple='" + anoCumple + '\'' +
                ", tienda='" + tienda + '\'' +
                ", direccion1='" + direccion1 + '\'' +
                ", direccion2='" + direccion2 + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", paisId='" + paisId + '\'' +
                ", regionId='" + regionId + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", insertarSegmentoHabeasData=" + insertarSegmentoHabeasData +
                '}';
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDiaCumple() {
        return diaCumple;
    }

    public void setDiaCumple(String diaCumple) {
        this.diaCumple = diaCumple;
    }

    public String getMesCumple() {
        return mesCumple;
    }

    public void setMesCumple(String mesCumple) {
        this.mesCumple = mesCumple;
    }

    public String getAnoCumple() {
        return anoCumple;
    }

    public void setAnoCumple(String anoCumple) {
        this.anoCumple = anoCumple;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
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

    public Boolean getInsertarSegmentoHabeasData() {
        return insertarSegmentoHabeasData;
    }

    public void setInsertarSegmentoHabeasData(Boolean insertarSegmentoHabeasData) {
        this.insertarSegmentoHabeasData = insertarSegmentoHabeasData;
    }
}
