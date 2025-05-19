package com.crystal.selfcheckoutapp.Modelo.retrofit.response.condicionescomerciales;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Incentives {
    @SerializedName("information")
    @Expose
    private String information;

    @SerializedName("message")
    @Expose
    private String message;

    public Incentives(String information, String message) {
        this.information = information;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Incentives{" +
                "information='" + information + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
