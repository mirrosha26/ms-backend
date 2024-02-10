package com.bondsbackend.ms.mapper;

import com.bondsbackend.ms.dto.ProfileDto;
import com.bondsbackend.ms.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ProfileDto toDto(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setId(profile.getId());
        dto.setFirstName(profile.getFirstName());
        dto.setLastName(profile.getLastName());
        dto.setEmail(profile.getEmail());
        dto.setUsername(profile.getUsername());
        // Не устанавливаем пароль в DTO
        return dto;
    }

    public Profile toEntity(ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setEmail(profileDto.getEmail());
        profile.setUsername(profileDto.getUsername());
        profile.setPasswordHash(passwordEncoder.encode(profileDto.getPassword()));
        // Заполнить остальные поля при необходимости
        return profile;
    }
}
