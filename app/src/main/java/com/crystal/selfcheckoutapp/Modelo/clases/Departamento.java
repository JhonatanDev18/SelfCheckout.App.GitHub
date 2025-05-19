package com.crystal.selfcheckoutapp.Modelo.clases;

public class Departamento {
    private String region_code;
    private String departamento;
    private String municipio_code;
    private String municipio_name;
    private String pais;

    public Departamento(String region_code, String departamento, String municipio_code, String municipio_name, String pais) {
        this.region_code = region_code;
        this.departamento = departamento;
        this.municipio_code = municipio_code;
        this.municipio_name = municipio_name;
        this.pais = pais;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio_code() {
        return municipio_code;
    }

    public void setMunicipio_code(String municipio_code) {
        this.municipio_code = municipio_code;
    }

    public String getMunicipio_name() {
        return municipio_name;
    }

    public void setMunicipio_name(String municipio_name) {
        this.municipio_name = municipio_name;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
