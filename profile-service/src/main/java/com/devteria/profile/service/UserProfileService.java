package com.devteria.profile.service;

import com.devteria.profile.dto.request.UpdateProfileRequest;
import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.errorcode.AppException;
import com.devteria.profile.errorcode.ErrorCode;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;
import com.devteria.profile.repository.httpcliennt.FileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {

    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    FileClient fileClient;

    public UserProfileResponse createUserProfile(UserProfileRequest userProfileRequest){
        UserProfile userProfile = userProfileMapper.toUserProfile(userProfileRequest);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileReponse(userProfile);
    }

    public UserProfileResponse getByUserId(String id){
        UserProfile userProfile = userProfileRepository.findByUserId(id).orElseThrow(
                ()->
                new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        return userProfileMapper.toUserProfileReponse(userProfile);
    }
    public UserProfileResponse getProfile(String id){
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        return userProfileMapper.toUserProfileReponse(userProfile);
    }


    public UserProfileResponse getMyProFile(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userProfileMapper.toUserProfileReponse(userProfile);
    }

    public UserProfileResponse updateUserProfile(UpdateProfileRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userProfileMapper.update(userProfile,request);
        return userProfileMapper.toUserProfileReponse(userProfileRepository.save(userProfile));
    }



    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getUserProfile(){
        List<UserProfileResponse> userProfileResponses =
        userProfileRepository.findAll().stream().map(userProfileMapper ::toUserProfileReponse).toList();
        return userProfileResponses;
    }

    public UserProfileResponse updateAvatar(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        var profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        //Upload file - invoke an api File Service
        var response = fileClient.uploadMedia(file);

        profile.setAvatarUrl(response.getResult().getUrl());

        return userProfileMapper.toUserProfileReponse(userProfileRepository.save(profile));
    }
}
