package com.yeoljeong.tripmate.matching.application.external;

import java.util.UUID;

public interface UserGeoStore {
	void saveUserLocation(UUID userId, double latitude, double longitude);
	void removeUserLocation(UUID userId);
}
