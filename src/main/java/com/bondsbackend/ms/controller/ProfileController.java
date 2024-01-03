// ProfileController.java
package com.bondsbackend.ms.controller;

import com.bondsbackend.ms.dto.ProfileDto;
import com.bondsbackend.ms.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profiles")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileDto profileDto){
        ProfileDto savedProfile = profileService.createProfile(profileDto);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }
}