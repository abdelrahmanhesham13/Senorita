package com.senoritasaudi.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.senoritasaudi.models.SliderModel;

import java.util.List;

public class SliderResponseModel {
    @SerializedName("sliders")
    @Expose
    private List<SliderModel> sliders = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<SliderModel> getSliders() {
        return sliders;
    }

    public void setSliders(List<SliderModel> sliders) {
        this.sliders = sliders;
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
