package com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.domain.constants.OutboxStatus;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.MatchingOutbox;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingOutboxRepository extends JpaRepository<MatchingOutbox, UUID> {

	List<MatchingOutbox> findAllByStatus(OutboxStatus status);

}
