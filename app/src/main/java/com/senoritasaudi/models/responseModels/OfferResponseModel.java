package com.senoritasaudi.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.senoritasaudi.models.OfferModel;

import java.util.List;

public class OfferResponseModel {


    @SerializedName("offers")
    @Expose
    private List<OfferModel> offers = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<OfferModel> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferModel> offers) {
        this.offers = offers;
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
