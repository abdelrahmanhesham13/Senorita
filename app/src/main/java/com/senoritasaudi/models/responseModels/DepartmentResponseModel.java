package com.senoritasaudi.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.senoritasaudi.models.DepartmentModel;

import java.util.List;

public class DepartmentResponseModel {
    @SerializedName("categories")
    @Expose
    private List<DepartmentModel> categories = null;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<DepartmentModel> getCategories() {
        return categories;
    }

    public void setCategories(List<DepartmentModel> categories) {
        this.categories = categories;
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
