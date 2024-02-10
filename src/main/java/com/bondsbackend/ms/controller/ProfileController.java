package com.bondsbackend.ms.controller;

import com.bondsbackend.ms.dto.ProfileDto;
import com.bondsbackend.ms.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody ProfileDto profileDto) {
        ProfileDto savedProfile = profileService.createOrUpdateProfile(profileDto);
        if (savedProfile == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
        }
        return ResponseEntity.ok(savedProfile);
    }
}
