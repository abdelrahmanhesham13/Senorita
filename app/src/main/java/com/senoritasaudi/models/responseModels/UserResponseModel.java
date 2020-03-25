package com.senoritasaudi.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.senoritasaudi.models.UserModel;

public class UserResponseModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("user")
    @Expose
    private UserModel user;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("signup")
    @Expose
    private Boolean signup;

    public Boolean getSignup() {
        return signup;
    }

    public void setSignup(Boolean signup) {
        this.signup = signup;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
