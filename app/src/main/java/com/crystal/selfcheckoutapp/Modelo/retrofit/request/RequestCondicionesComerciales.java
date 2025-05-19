package com.crystal.selfcheckoutapp.Modelo.retrofit.request;

import com.crystal.selfcheckoutapp.Modelo.clases.Header;
import com.crystal.selfcheckoutapp.Modelo.clases.Line;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestCondicionesComerciales {
    @SerializedName("header")
    @Expose
    private Header header;

    @SerializedName("lines")
    @Expose
    private List<Line> lines;

    public RequestCondicionesComerciales(Header header, List<Line> lines) {
        this.header = header;
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "RequestCondicionesComerciales{" +
                "header=" + header +
                ", lines=" + lines +
                '}';
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
