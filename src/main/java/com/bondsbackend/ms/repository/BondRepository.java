// BondRepository.java
package com.bondsbackend.ms.repository;

import com.bondsbackend.ms.entity.Bond;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BondRepository extends JpaRepository<Bond, Long> {
}
