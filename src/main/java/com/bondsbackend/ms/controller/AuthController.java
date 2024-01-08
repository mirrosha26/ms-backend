// AuthController.java
package com.bondsbackend.ms.controller;

import com.bondsbackend.ms.dto.AuthDto;
import com.bondsbackend.ms.entity.Profile;
import com.bondsbackend.ms.repository.ProfileRepository;
import com.bondsbackend.ms.security.JwtTokenProvider;
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

    public AuthController(JwtTokenProvider jwtTokenProvider, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signin(@RequestBody AuthDto data) {
        // Найти пользователя по имени пользователя
        System.out.println("signin");
        Optional<Profile> user = profileRepository.findByUsername(data.getUsername());
        if (user.isPresent() && passwordEncoder.matches(data.getPassword(), user.get().getPasswordHash())) {
            // Если пользователь найден и пароль совпадает, создать JWT токен
            String token = jwtTokenProvider.createToken(data.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("username", data.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            // Если пользователь не найден или пароль не совпадает, вернуть ошибку
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }
}
