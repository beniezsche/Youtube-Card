package com.beniezsche.tubecard.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails {

    @SerializedName("default")
    @Expose
    private Image _default;
    @SerializedName("medium")
    @Expose
    private Image medium;
    @SerializedName("high")
    @Expose
    private Image high;
    @SerializedName("standard")
    @Expose
    private Image standard;
    @SerializedName("maxres")
    @Expose
    private Image maxres;

    public Image getDefault() {
        return _default;
    }

    public void setDefault(Image _default) {
        this._default = _default;
    }

    public Image getMedium() {
        return medium;
    }

    public void setMedium(Image medium) {
        this.medium = medium;
    }

    public Image getHigh() {
        return high;
    }

    public void setHigh(Image high) {
        this.high = high;
    }

    public Image getStandard() {
        return standard;
    }

    public void setStandard(Image standard) {
        this.standard = standard;
    }

    public Image getMaxres() {
        return maxres;
    }

    public void setMaxres(Image maxres) {
        this.maxres = maxres;
    }
}
