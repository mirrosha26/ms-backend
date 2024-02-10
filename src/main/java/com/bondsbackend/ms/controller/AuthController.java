package com.bondsbackend.ms.controller;

import com.bondsbackend.ms.dto.AuthDto;
import com.bondsbackend.ms.dto.RefreshTokenRequest;
import com.bondsbackend.ms.entity.Profile;
import com.bondsbackend.ms.exceptions.JwtAuthenticationException;
import com.bondsbackend.ms.repository.ProfileRepository;
import com.bondsbackend.ms.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AuthDto data) {
        Optional<Profile> user = profileRepository.findByUsername(data.getUsername());
        if (user.isPresent() && passwordEncoder.matches(data.getPassword(), user.get().getPasswordHash())) {
            Profile userProfile = user.get();
            String accessToken = jwtTokenProvider.createToken(data.getUsername(), userProfile.getRole());
            String refreshToken = jwtTokenProvider.createRefreshToken(data.getUsername());

            userProfile.setRefreshToken(refreshToken);
            profileRepository.save(userProfile);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        return profileRepository.findByRefreshToken(refreshToken)
                .map(profile -> {
                    if (jwtTokenProvider.validateToken(refreshToken)) {
                        String newAccessToken = jwtTokenProvider.createToken(profile.getUsername(), profile.getRole());
                        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid refresh token"));
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Profile not found")));
    }
}
