package com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingJpaRepository extends JpaRepository<Matching, UUID> {

}
