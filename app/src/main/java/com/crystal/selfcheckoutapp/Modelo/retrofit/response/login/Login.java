package com.crystal.selfcheckoutapp.Modelo.retrofit.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("grupo")
    @Expose
    private String grupo;

    @SerializedName("usuarioUtil")
    @Expose
    private String usuarioUtil;

    @SerializedName("tiendaPorDefecto")
    @Expose
    private String tiendaPorDefecto;

    @SerializedName("pais")
    @Expose
    private String pais;

    public Login(String usuario, String grupo, String usuarioUtil, String tiendaPorDefecto, String pais) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.usuarioUtil = usuarioUtil;
        this.tiendaPorDefecto = tiendaPorDefecto;
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Login{" +
                "usuario='" + usuario + '\'' +
                ", grupo='" + grupo + '\'' +
                ", usuarioUtil='" + usuarioUtil + '\'' +
                ", tiendaPorDefecto='" + tiendaPorDefecto + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getUsuarioUtil() {
        return usuarioUtil;
    }

    public void setUsuarioUtil(String usuarioUtil) {
        this.usuarioUtil = usuarioUtil;
    }

    public String getTiendaPorDefecto() {
        return tiendaPorDefecto;
    }

    public void setTiendaPorDefecto(String tiendaPorDefecto) {
        this.tiendaPorDefecto = tiendaPorDefecto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
