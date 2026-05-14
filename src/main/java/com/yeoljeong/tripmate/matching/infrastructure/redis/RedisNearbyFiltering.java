package com.yeoljeong.tripmate.matching.infrastructure.redis;

import com.yeoljeong.tripmate.matching.application.NearbyUserFiltering;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisNearbyFiltering implements NearbyUserFiltering {

	private final static String GEO_KEY = "matching:geo:users";
	private final RedisTemplate<String, String> redisTemplate;


	@Override
	public Set<UUID> findNearbyUsers(double latitude, double longitude, double radiusKm) {
		GeoResults<GeoLocation<String>> results = redisTemplate.opsForGeo()
			.search(GEO_KEY,
				new Circle(new Point(longitude, latitude), new Distance(radiusKm, Metrics.KILOMETERS)));

		if (results == null) return Set.of();
		return results.getContent().stream()
			.map(r -> UUID.fromString(r.getContent().getName()))
			.collect(Collectors.toSet());
	}
}
