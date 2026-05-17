package com.yeoljeong.tripmate.matching.infrastructure.message;

import com.yeoljeong.tripmate.common.aop.TripmateLock;
import com.yeoljeong.tripmate.domain.constants.OutboxStatus;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.MatchingOutbox;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa.MatchingOutboxRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchingOutboxDispatcher {

	private final MatchingOutboxRepository matchingOutboxRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Scheduled(fixedDelay = 3000)
	@Transactional
	@TripmateLock(key = "'matching:outbox:dispatcher'", tryLockTime = 0)
	public void dispatch() {
		List<MatchingOutbox> events = matchingOutboxRepository
			.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
		events.forEach(outbox -> {
			try {
				kafkaTemplate.send(outbox.getTopic(), outbox.getPayload()).get(); // 동기 대기
				outbox.published();
			} catch (Exception e) {
				log.error("[OUTBOX_DISPATCHER] 발행 실패 - topic: {}, id: {}", outbox.getTopic(), outbox.getId(), e);
				outbox.fail();
			}
		});
	}
}
