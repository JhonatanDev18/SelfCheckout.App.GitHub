package com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCrearDocumento extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private DocumentoCreado documento;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseCrearDocumento(Boolean esValida, String mensaje, DocumentoCreado documento, List<Object> listado) {
        super(esValida, mensaje);
        this.documento = documento;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseCrearDocumento{" +
                "documento=" + documento +
                ", listado=" + listado +
                '}';
    }

    public DocumentoCreado getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoCreado documento) {
        this.documento = documento;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
