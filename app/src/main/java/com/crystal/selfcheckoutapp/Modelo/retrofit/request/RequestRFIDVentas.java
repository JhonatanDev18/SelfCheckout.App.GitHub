package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.clases.RFIDVenta;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestRFIDVentas {
    @SerializedName("VentasRFID")
    private List<RFIDVenta> rfidVentas;

    public RequestRFIDVentas(List<RFIDVenta> rfidVentas) {
        this.rfidVentas = rfidVentas;
    }

    @Override
    public String toString() {
        return "RequestRFIDVentas{" +
                "rfidVentas=" + rfidVentas +
                '}';
    }

    public List<RFIDVenta> getRfidVentas() {
        return rfidVentas;
    }

    public void setRfidVentas(List<RFIDVenta> rfidVentas) {
        this.rfidVentas = rfidVentas;
    }
}
