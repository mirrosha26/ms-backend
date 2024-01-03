// ProfileRepository.java
package com.bondsbackend.ms.repository;

import com.bondsbackend.ms.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}