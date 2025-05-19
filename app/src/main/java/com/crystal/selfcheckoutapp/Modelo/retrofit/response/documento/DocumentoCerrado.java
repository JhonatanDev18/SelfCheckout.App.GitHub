package com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentoCerrado {
    @SerializedName("codigoCaja")
    @Expose
    private String codigoCaja;

    @SerializedName("naturaleza")
    @Expose
    private String naturaleza;

    @SerializedName("codigoTienda")
    @Expose
    private String codigoTienda;

    @SerializedName("numeroTransaccion")
    @Expose
    private int numeroTransaccion;

    @SerializedName("indice")
    @Expose
    private Integer indice;

    @SerializedName("numeroDia")
    @Expose
    private Integer numeroDia;

    @SerializedName("referenciaFiscalFirma")
    @Expose
    private String referenciaFiscalFirma;

    @SerializedName("referenciaInterna")
    @Expose
    private String referenciaInterna;

    public DocumentoCerrado(String codigoCaja, String naturaleza, String codigoTienda,
                            int numeroTransaccion, Integer indice, Integer numeroDia,
                            String referenciaFiscalFirma, String referenciaInterna) {
        this.codigoCaja = codigoCaja;
        this.naturaleza = naturaleza;
        this.codigoTienda = codigoTienda;
        this.numeroTransaccion = numeroTransaccion;
        this.indice = indice;
        this.numeroDia = numeroDia;
        this.referenciaFiscalFirma = referenciaFiscalFirma;
        this.referenciaInterna = referenciaInterna;
    }

    @Override
    public String toString() {
        return "DocumentoCerrado{" +
                "codigoCaja='" + codigoCaja + '\'' +
                ", naturaleza='" + naturaleza + '\'' +
                ", codigoTienda='" + codigoTienda + '\'' +
                ", numeroTransaccion=" + numeroTransaccion +
                ", indice=" + indice +
                ", numeroDia=" + numeroDia +
                ", referenciaFiscalFirma='" + referenciaFiscalFirma + '\'' +
                ", referenciaInterna='" + referenciaInterna + '\'' +
                '}';
    }

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public int getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(int numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public Integer getNumeroDia() {
        return numeroDia;
    }

    public void setNumeroDia(Integer numeroDia) {
        this.numeroDia = numeroDia;
    }

    public String getReferenciaFiscalFirma() {
        return referenciaFiscalFirma;
    }

    public void setReferenciaFiscalFirma(String referenciaFiscalFirma) {
        this.referenciaFiscalFirma = referenciaFiscalFirma;
    }

    public String getReferenciaInterna() {
        return referenciaInterna;
    }

    public void setReferenciaInterna(String referenciaInterna) {
        this.referenciaInterna = referenciaInterna;
    }
}
