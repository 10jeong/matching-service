package com.yeoljeong.tripmate.matching.infrastructure.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoljeong.tripmate.common.message.MatchingSsePayload;
import com.yeoljeong.tripmate.matching.application.external.MatchingSseManager;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingRedisSseManager implements MatchingSseManager {

	private final static String CHANNEL_PREFIX = "matching:sse:";
	private final static long TIMEOUT = 30 * 1000L;

	private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final Map<UUID, MessageListener> listeners = new ConcurrentHashMap<>();

	private final RedisMessageListenerContainer listenerContainer;
	private final ObjectMapper objectMapper;

	@Override
	public SseEmitter subscribe(UUID userId) {
		disconnect(userId);
		SseEmitter emitter = new SseEmitter(TIMEOUT);

		registerRedisListener(userId, emitter);
		sendConnectEvent(userId, emitter);

		emitter.onCompletion(() -> cleanup(userId));
		emitter.onTimeout(() -> cleanup(userId));
		emitter.onError(e -> cleanup(userId));

		return emitter;
	}

	@Override
	public void disconnect(UUID userId) {
		cleanup(userId);
	}

	private void registerRedisListener(UUID userId, SseEmitter emitter) {
		ChannelTopic topic = new ChannelTopic(CHANNEL_PREFIX + userId);
		MessageListener listener = (message, pattern) -> {
			try {
				log.info("[SSE] Redis 메시지 수신 - userId: {}", userId); // 추가
				MatchingSsePayload payload = objectMapper.readValue(
					message.getBody(), MatchingSsePayload.class
				);
				emitter.send(
					SseEmitter.event()
						.name("matching")
						.data(payload)
				);
			} catch (IOException e) {
				log.error("[MatchingSSE] emitter send failed - userId: {}", userId);
				cleanup(userId);
				emitter.completeWithError(e);
			}
		};
		listeners.put(userId, listener);
		listenerContainer.addMessageListener(listener, topic);
	}

	private void cleanup(UUID userId) {
		SseEmitter emitter = emitters.remove(userId);
		if (emitter != null) {
			emitter.complete();
		}
		MessageListener listener = listeners.remove(userId);
		if (listener != null) {
			listenerContainer.removeMessageListener(
				listener,
				new ChannelTopic(CHANNEL_PREFIX + userId)
			);
		}
	}

	private void sendConnectEvent(UUID userId, SseEmitter emitter) {
		try {
			emitter.send(
				SseEmitter.event()
					.name("connect")
					.data("connected. userId=" + userId)
			);
		} catch (IOException e) {
			log.error("[MatchingSSE] connect event failed - userId: {}", userId);
			cleanup(userId);
			emitter.completeWithError(e);
		}
	}
}
