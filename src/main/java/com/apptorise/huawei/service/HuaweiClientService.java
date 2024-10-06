package com.apptorise.huawei.service;

import com.apptorise.huawei.HuaweiTokenRequest;
import com.apptorise.huawei.HuaweiServiceGrpc;
import com.apptorise.huawei.HuaweiUser;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HuaweiClientService {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public HuaweiClientService(RestTemplate restTemplate, @Value("${huawei.api.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public HuaweiUserResponse getUserProfileByHuaweiAccessToken(String huaweiAccessToken) {
        return restTemplate.getForObject(baseUrl + "/rest.php?nsp_svc=GOpen.User.getInfo?access_token=" + huaweiAccessToken, HuaweiUserResponse.class);
    }

    @GrpcService
    public static class HuaweiService extends HuaweiServiceGrpc.HuaweiServiceImplBase {

        private final HuaweiController huaweiController;

        @Autowired
        public HuaweiService(HuaweiController huaweiController) {
            this.huaweiController = huaweiController;
        }

        @Override
        public void getUserByHuaweiToken(HuaweiTokenRequest request, StreamObserver<HuaweiUser> responseObserver) {
            HuaweiUserResponse huaweiUserResponse = huaweiController.getUserProfileByHuaweiToken(request.getToken());

            responseObserver.onNext(
                HuaweiUser.newBuilder()
                        .setFullName(huaweiUserResponse.getFullName())
                        .setEmail(huaweiUserResponse.getEmail())
                        .setPhoto(huaweiUserResponse.getPhoto())
                        .setOpenId(huaweiUserResponse.getOpenId())
                        .setPhoneNumber(huaweiUserResponse.getPhone())
                    .build()
            );

            responseObserver.onCompleted();
        }
    }
}