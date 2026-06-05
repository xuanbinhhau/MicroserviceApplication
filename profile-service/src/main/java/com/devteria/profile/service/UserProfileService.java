package com.devteria.profile.service;

import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {

    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createUserProfile(UserProfileRequest userProfileRequest){
        UserProfile userProfile = userProfileMapper.toUserProfile(userProfileRequest);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileReponse(userProfile);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getUserProfile(){
        List<UserProfileResponse> userProfileResponses =
        userProfileRepository.findAll().stream().map(userProfileMapper ::toUserProfileReponse).toList();
        return userProfileResponses;
    }

}
