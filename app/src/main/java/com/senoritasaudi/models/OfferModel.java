package com.senoritasaudi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("clinic_id")
    @Expose
    private String clinicId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("valid_date")
    @Expose
    private String validDate;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("favourite")
    @Expose
    private Boolean favourite;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("clinic_image")
    @Expose
    private String clinicImage;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("clinic")
    @Expose
    private ClinicModel clinic;
    @SerializedName("category")
    @Expose
    private Category category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicImage() {
        return clinicImage;
    }

    public void setClinicImage(String clinicImage) {
        this.clinicImage = clinicImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public ClinicModel getClinic() {
        return clinic;
    }

    public void setClinic(ClinicModel clinic) {
        this.clinic = clinic;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
