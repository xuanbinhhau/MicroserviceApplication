package com.devteria.identity.repository.httpClient;


import com.devteria.identity.configuration.AuthenticationRequestIntereptor;
import com.devteria.identity.dto.request.ProfileCreationRequest;
import com.devteria.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Repository
@FeignClient(name = "profileservice",url = "${app.service.profile}",
        configuration = {AuthenticationRequestIntereptor.class})
public interface ProfileClient {

    @PostMapping(value = "/internal/userprofiles",produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest profileCreationRequest);

}
