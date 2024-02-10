package com.bondsbackend.ms.service;

import com.bondsbackend.ms.dto.ProfileDto;

public interface ProfileService {
    ProfileDto createOrUpdateProfile(ProfileDto profileDto);
}
