package com.crystal.selfcheckoutapp.Modelo.clases;

public class ImpresoraDevice {
    private String nombre;
    private String logicalName;
    private String address;
    private String device;
    private int position;
    private boolean isConnected;

    public ImpresoraDevice(String nombre, String logicalName, String address, String device, int position, boolean isConnected) {
        this.nombre = nombre;
        this.logicalName = logicalName;
        this.address = address;
        this.device = device;
        this.position = position;
        this.isConnected = isConnected;
    }

    public String getLogicalName() {
        return logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}