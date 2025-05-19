package com.crystal.selfcheckoutapp.Modelo.clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyWord {
    @SerializedName("IsScanned")
    @Expose
    private boolean isScanned;
    @SerializedName("KeyW")
    @Expose
    private String keyWord;

    public KeyWord(){

    }
    public KeyWord(boolean isScanned, String keyWord) {
        this.isScanned = isScanned;
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "KeyWord{" +
                "isScanned=" + isScanned +
                ", keyWord='" + keyWord + '\'' +
                '}';
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
