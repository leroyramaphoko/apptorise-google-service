package com.apptorise.huawei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HuaweiController {

    private final HuaweiClientService huaweiClientService;

    @Autowired
    public HuaweiController(HuaweiClientService huaweiClientService) {
        this.huaweiClientService = huaweiClientService;
    }

    @GetMapping("/get-user-profile/{huaweiAccessToken}")
    public HuaweiUserResponse getUserProfileByHuaweiToken(@PathVariable String huaweiAccessToken) {
        return huaweiClientService.getUserProfileByHuaweiAccessToken(huaweiAccessToken);
    }
}