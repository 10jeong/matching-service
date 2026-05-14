package com.yeoljeong.tripmate.matching.application;

import java.util.Set;
import java.util.UUID;

public interface NearbyUserFiltering {
	Set<UUID> findNearbyUsers(double latitude, double longitude, double radiusKm);
}
