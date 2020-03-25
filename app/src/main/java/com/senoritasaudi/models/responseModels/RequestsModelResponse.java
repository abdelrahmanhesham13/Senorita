package com.senoritasaudi.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.senoritasaudi.models.RequestModel;

import java.util.List;

public class RequestsModelResponse {

    @SerializedName("requests")
    @Expose
    private List<RequestModel> requests = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<RequestModel> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestModel> requests) {
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
