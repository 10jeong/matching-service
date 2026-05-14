package com.yeoljeong.tripmate.matching.infrastructure.redis;

import com.yeoljeong.tripmate.matching.application.external.UserGeoStore;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserGeoRedisStore implements UserGeoStore {

	private final String GEO_KEY = "matching:geo:users";
	private final RedisTemplate<String, String> redisTemplate;


	@Override
	public void saveUserLocation(UUID userId, double latitude, double longitude) {
		log.info("[GEO] 위치 저장 - userId: {}, lat: {}, lng: {}", userId, latitude, longitude);
		redisTemplate.opsForGeo().add(GEO_KEY, new Point(longitude, latitude), userId.toString());
	}

	@Override
	public void removeUserLocation(UUID userId) {
		log.info("[GEO] 위치 삭제 - userId: {}", userId);
		redisTemplate.opsForGeo().remove(GEO_KEY, userId.toString());
	}
}
