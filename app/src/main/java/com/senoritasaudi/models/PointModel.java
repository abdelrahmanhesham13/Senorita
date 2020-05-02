package com.senoritasaudi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PointModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("percent")
    @Expose
    private String percent;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("active_code")
    @Expose
    private Boolean activeCode;
    @SerializedName("code")
    @Expose
    private List<Object> code = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(Boolean activeCode) {
        this.activeCode = activeCode;
    }

    public List<Object> getCode() {
        return code;
    }

    public void setCode(List<Object> code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
}
