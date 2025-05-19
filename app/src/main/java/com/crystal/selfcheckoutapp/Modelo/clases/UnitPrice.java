package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitPrice {
    @SerializedName("base")
    @Expose
    private Double base;

    @SerializedName("net")
    @Expose
    private Double net;

    public UnitPrice(Double base, Double net) {
        this.base = base;
        this.net = net;
    }

    @Override
    public String toString() {
        return "UnitPrice{" +
                "base=" + base +
                ", net=" + net +
                '}';
    }

    public Double getBase() {
        return base;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public Double getNet() {
        return net;
    }

    public void setNet(Double net) {
        this.net = net;
    }
}
