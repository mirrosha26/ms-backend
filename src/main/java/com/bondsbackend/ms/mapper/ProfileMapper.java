// ProfileMapper.java
package com.bondsbackend.ms.mapper;

import com.bondsbackend.ms.dto.ProfileDto;
import com.bondsbackend.ms.entity.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProfileMapper {

    public static ProfileDto mapToProfileDto(Profile profile){
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setEmail(profile.getEmail());
        profileDto.setUsername(profile.getUsername());
        // Не устанавливаем пароль в DTO
        return profileDto;
    }

    public static Profile mapToProfile(ProfileDto profileDto, PasswordEncoder passwordEncoder){
        Profile profile = new Profile();
        profile.setId(profileDto.getId());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setEmail(profileDto.getEmail());
        profile.setUsername(profileDto.getUsername());
        profile.setPasswordHash(passwordEncoder.encode(profileDto.getPassword()));
        return profile;
    }
}