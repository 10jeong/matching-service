package com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.matching.domain.entity.Matching;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMatchingRepository extends JpaRepository<Matching, UUID> {

}
