package com.devteria.profile.controller;


import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("internal/userprofiles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InternalProfileController {

    UserProfileService userProfileService;

    @PostMapping()
    UserProfileResponse createUserprofile(@RequestBody UserProfileRequest userProfileRequest){
        return userProfileService.createUserProfile(userProfileRequest);
    }
}
