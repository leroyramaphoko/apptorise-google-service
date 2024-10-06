package com.apptorise.google.service;

import com.apptorise.google.GoogleAccessTokenRequest;
import com.apptorise.google.GoogleIdTokenRequest;
import com.apptorise.google.GoogleServiceGrpc;
import com.apptorise.google.GoogleUser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.protobuf.ServiceException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleClientService {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GoogleClientService(RestTemplate restTemplate, @Value("${google.api.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public GoogleUserResponse getUserProfileByAccessToken(String accessToken) {
        return restTemplate.getForObject(baseUrl + "/oauth2/v3/userinfo?access_token=" + accessToken, GoogleUserResponse.class);
    }

    @GrpcService
    public static class GoogleService extends GoogleServiceGrpc.GoogleServiceImplBase {

        private final GoogleController googleController;

        @Autowired
        public GoogleService(GoogleController googleController) {
            this.googleController = googleController;
        }

        @Override
        public void getUserByAccessToken(GoogleAccessTokenRequest request, StreamObserver<GoogleUser> responseObserver) {
            GoogleUserResponse googleUserResponse = googleController.getUserProfileByAccessToken(request.getAccessToken());

            responseObserver.onNext(
                GoogleUser.newBuilder()
                    .setName(googleUserResponse.getName())
                    .setSurname(googleUserResponse.getSurname())
                    .setEmail(googleUserResponse.getEmail())
                    .setPhoto(googleUserResponse.getPhoto())
                    .setEmailVerified(googleUserResponse.isEmailVerified())
                    .build()
            );

            responseObserver.onCompleted();
        }

        @Override
        public void getUserByIdToken(GoogleIdTokenRequest request, StreamObserver<GoogleUser> responseObserver) {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).build();

            try {
                GoogleIdToken idToken = verifier.verify(request.getIdToken());
                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    responseObserver.onNext(
                        GoogleUser.newBuilder()
                            .setName(valueOf(payload.get("given_name"), ""))
                            .setSurname(valueOf(payload.get("family_name"), ""))
                            .setEmail(payload.getEmail())
                            .setPhoto(valueOf(payload.get("picture"), null))
                            .setEmailVerified(payload.getEmailVerified())
                            .build()
                    );

                    responseObserver.onCompleted();
                } else {
                    responseObserver.onError(new ServiceException("Something went wrong"));
                }
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }

        private String valueOf(Object input, String defaultValue) {
            return input == null ? defaultValue : (String) input;
        }
    }
}