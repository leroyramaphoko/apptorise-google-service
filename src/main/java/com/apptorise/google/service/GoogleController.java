package com.apptorise.google.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleController {

    private final GoogleClientService googleClientService;

    @Autowired
    public GoogleController(GoogleClientService googleClientService) {
        this.googleClientService = googleClientService;
    }

    @GetMapping("/get-user-profile/{accessToken}")
    public GoogleUserResponse getUserProfileByAccessToken(@PathVariable String accessToken) {
        return googleClientService.getUserProfileByAccessToken(accessToken);
    }
}
