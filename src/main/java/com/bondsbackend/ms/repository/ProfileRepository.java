//ProfileRepository
package com.bondsbackend.ms.repository;

import com.bondsbackend.ms.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByRefreshToken(String refreshToken);
    Optional<Profile> findByEmail(String email);
}