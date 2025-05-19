package com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Condiciones {
    @SerializedName("respuestaID")
    @Expose
    private List<String> respuestaID;

    @SerializedName("incentives")
    @Expose
    private List<Incentives> incentives;

    @SerializedName("respuestaLines")
    @Expose
    private List<RespuestaLine> respuestaLine;

    @SerializedName("vaucherText")
    @Expose
    private String vaucherText;

    @SerializedName("vaucherPalabra")
    @Expose
    private String vaucherPalabra;

    public Condiciones(List<String> respuestaID, List<Incentives> incentives, List<RespuestaLine> respuestaLine,
                       String vaucherText, String vaucherPalabra) {
        this.respuestaID = respuestaID;
        this.incentives = incentives;
        this.respuestaLine = respuestaLine;
        this.vaucherText = vaucherText;
        this.vaucherPalabra = vaucherPalabra;
    }

    @Override
    public String toString() {
        return "Condiciones{" +
                "respuestaID=" + respuestaID +
                ", incentives=" + incentives +
                ", respuestaLineCCS=" + respuestaLine +
                ", vaucherText='" + vaucherText + '\'' +
                ", vaucherPalabra='" + vaucherPalabra + '\'' +
                '}';
    }

    public List<String> getRespuestaID() {
        return respuestaID;
    }

    public void setRespuestaID(List<String> respuestaID) {
        this.respuestaID = respuestaID;
    }

    public List<Incentives> getIncentives() {
        return incentives;
    }

    public void setIncentives(List<Incentives> incentives) {
        this.incentives = incentives;
    }

    public List<RespuestaLine> getRespuestaLine() {
        return respuestaLine;
    }

    public void setRespuestaLine(List<RespuestaLine> respuestaLine) {
        this.respuestaLine = respuestaLine;
    }

    public String getVaucherText() {
        return vaucherText;
    }

    public void setVaucherText(String vaucherText) {
        this.vaucherText = vaucherText;
    }

    public String getVaucherPalabra() {
        return vaucherPalabra;
    }

    public void setVaucherPalabra(String vaucherPalabra) {
        this.vaucherPalabra = vaucherPalabra;
    }
}
