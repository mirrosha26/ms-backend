// ProfileServiceImpl.java
package com.bondsbackend.ms.service.impl;

import com.bondsbackend.ms.dto.ProfileDto;
import com.bondsbackend.ms.entity.Profile;
import com.bondsbackend.ms.mapper.ProfileMapper;
import com.bondsbackend.ms.repository.ProfileRepository;
import com.bondsbackend.ms.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfileDto createProfile(ProfileDto profileDto) {
        Profile profile = ProfileMapper.mapToProfile(profileDto, passwordEncoder);
        Profile savedProfile = profileRepository.save(profile);
        return ProfileMapper.mapToProfileDto(savedProfile);
    }
}