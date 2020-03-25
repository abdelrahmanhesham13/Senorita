package com.senoritasaudi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("clinic_id")
    @Expose
    private String clinicId;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("selected_time")
    @Expose
    private String selectedTime;
    @SerializedName("selected_date")
    @Expose
    private String selectedDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("scanned")
    @Expose
    private String scanned;
    @SerializedName("review")
    @Expose
    private Object review;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("clinic_image")
    @Expose
    private String clinicImage;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("offer")
    @Expose
    private OfferModel offer;
    @SerializedName("offer_image")
    @Expose
    private String offerImage;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("clinic")
    @Expose
    private ClinicModel clinic;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("price")
    @Expose
    private Object price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScanned() {
        return scanned;
    }

    public void setScanned(String scanned) {
        this.scanned = scanned;
    }

    public Object getReview() {
        return review;
    }

    public void setReview(Object review) {
        this.review = review;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public OfferModel getOffer() {
        return offer;
    }

    public void setOffer(OfferModel offer) {
        this.offer = offer;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
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

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }
}
