package com.apptorise.google.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleUserResponse {

    @JsonProperty("given_name")
    private String name;

    @JsonProperty("family_name")
    private String surname;
    private String email;

    @JsonProperty("picture")
    private String photo;

    @JsonProperty("email_verified")
    private boolean isEmailVerified;

    private String locale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}