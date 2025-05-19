package com.crystal.selfcheckoutapp.Modelo.retrofit.request;
public class RequestParametros {
    private String codigo;

    public RequestParametros(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "RequestParametros{" +
                "codigo=" + codigo +
                '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
