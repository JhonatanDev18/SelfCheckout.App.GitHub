package com.crystal.selfcheckoutapp.Modelo.retrofit.response.documento;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCerrarDocumento extends ResponseBase {
    @SerializedName("dato")
    @Expose
    private DocumentoCerrado documento;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseCerrarDocumento(Boolean esValida, String mensaje, DocumentoCerrado documento, List<Object> listado) {
        super(esValida, mensaje);
        this.documento = documento;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseCerrarDocumento{" +
                "documento=" + documento +
                ", listado=" + listado +
                '}';
    }

    public DocumentoCerrado getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoCerrado documento) {
        this.documento = documento;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
