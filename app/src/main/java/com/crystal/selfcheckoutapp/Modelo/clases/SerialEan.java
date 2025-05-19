package com.crystal.selfcheckoutapp.Modelo.clases;

import java.io.Serializable;

public class SerialEan implements Serializable {
    private String ean;
    private String serial;

    public SerialEan(String ean, String serial) {
        this.ean = ean;
        this.serial = serial;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
