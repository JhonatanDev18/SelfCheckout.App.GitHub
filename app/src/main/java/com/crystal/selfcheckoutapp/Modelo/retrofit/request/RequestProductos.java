package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestProductos {
    @SerializedName("eanes")
    @Expose
    private List<String> eanes;

    @SerializedName("pais")
    @Expose
    private String pais;

    @SerializedName("tienda")
    @Expose
    private String tienda;

    public RequestProductos(List<String> eanes, String pais, String tienda) {
        this.eanes = eanes;
        this.pais = pais;
        this.tienda = tienda;
    }

    public List<String> getEanes() {
        return eanes;
    }

    public void setEanes(List<String> eanes) {
        this.eanes = eanes;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }
}
