package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import com.yeoljeong.tripmate.common.aop.TripmateLock;
import com.yeoljeong.tripmate.domain.constants.OutboxStatus;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.UserSettingOutbox;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa.UserSettingOutboxRepository;
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
public class UserSettingOutboxDispatcher {

	private final UserSettingOutboxRepository outboxRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Scheduled(fixedDelay = 3000)
	@Transactional
	@TripmateLock(key = "'user-setting:outbox:dispatcher'", tryLockTime = 0)
	public void dispatch() {
		List<UserSettingOutbox> pendingEvents = outboxRepository
			.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
		pendingEvents.forEach(outbox -> {
			try {
				kafkaTemplate.send(outbox.getTopic(), outbox.getPayload()).get();
				outbox.published();
			} catch (Exception e) {
				log.error("[OUTBOX_DISPATCHER] 발행 실패 - topic: {}, id: {}", outbox.getTopic(), outbox.getId(), e);
				outbox.fail();
			}
		});
		}
}
