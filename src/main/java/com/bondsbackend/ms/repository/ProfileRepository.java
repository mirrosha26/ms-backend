// ProfileRepository.java
package com.bondsbackend.ms.repository;

import com.bondsbackend.ms.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Определите метод для поиска пользователя по имени пользователя
    Optional<Profile> findByUsername(String username);
}
