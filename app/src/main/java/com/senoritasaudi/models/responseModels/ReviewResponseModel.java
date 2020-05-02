package com.senoritasaudi.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.senoritasaudi.models.ReviewModel;

import java.util.List;

public class ReviewResponseModel {
    @SerializedName("requests")
    @Expose
    private List<ReviewModel> requests = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<ReviewModel> getRequests() {
        return requests;
    }

    public void setRequests(List<ReviewModel> requests) {
        this.requests = requests;
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
