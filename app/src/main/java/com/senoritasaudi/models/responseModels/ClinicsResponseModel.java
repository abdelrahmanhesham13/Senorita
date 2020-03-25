package com.senoritasaudi.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.senoritasaudi.models.ClinicModel;

import java.util.List;

public class ClinicsResponseModel {
    @SerializedName("clinics")
    @Expose
    private List<ClinicModel> clinics = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<ClinicModel> getClinics() {
        return clinics;
    }

    public void setClinics(List<ClinicModel> clinics) {
        this.clinics = clinics;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
