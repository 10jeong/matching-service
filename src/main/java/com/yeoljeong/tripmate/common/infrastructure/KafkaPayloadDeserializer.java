package com.yeoljeong.tripmate.common.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaPayloadDeserializer {

	private final ObjectMapper objectMapper;

	public <T> T deserialize(String payload, Class<T> clazz) {
		try {
			return objectMapper.readValue(payload, clazz);
		} catch (Exception e) {
			log.error("[KAFKA] 역직렬화 실패 : {}", payload, e);
			throw new RuntimeException(e);
		}
	}
}
