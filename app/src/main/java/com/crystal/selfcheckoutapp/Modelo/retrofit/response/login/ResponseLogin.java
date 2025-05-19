package com.crystal.selfcheckoutapp.Modelo.retrofit.response.login;

import com.crystal.selfcheckoutapp.Modelo.retrofit.response.ResponseBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLogin extends ResponseBase {

    @SerializedName("dato")
    @Expose
    private Login login;

    @SerializedName("listado")
    @Expose
    private List<Object> listado;

    public ResponseLogin(Boolean esValida, String mensaje, Login login, List<Object> listado) {
        super(esValida, mensaje);
        this.login = login;
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "login=" + login +
                ", listado=" + listado +
                '}';
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }
}
