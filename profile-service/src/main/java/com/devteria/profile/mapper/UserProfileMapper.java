package com.devteria.profile.mapper;

import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.repository.UserProfileRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile toUserProfile(UserProfileRequest userProfileCreationRequest);
    UserProfileResponse toUserProfileReponse(UserProfile userProfile);

}
