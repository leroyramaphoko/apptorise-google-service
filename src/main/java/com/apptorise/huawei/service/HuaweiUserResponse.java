package com.apptorise.huawei.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuaweiUserResponse {

    @JsonProperty("displayName")
    private String fullName;

    private String email;

    @JsonProperty("headPictureURL")
    private String photo;

    @JsonProperty("mobileNumber")
    private String phone;

    @JsonProperty("openID")
    private String openId;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}