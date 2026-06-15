package com.devteria.profile.mapper;

import com.devteria.profile.dto.request.UpdateProfileRequest;
import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.repository.UserProfileRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile toUserProfile(UserProfileRequest userProfileCreationRequest);
    UserProfileResponse toUserProfileReponse(UserProfile userProfile);
    void update(@MappingTarget UserProfile userProfile, UpdateProfileRequest request);

}
