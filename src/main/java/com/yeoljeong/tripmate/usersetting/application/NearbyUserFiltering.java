package com.yeoljeong.tripmate.usersetting.application;

import java.util.Set;
import java.util.UUID;

public interface NearbyUserFiltering {
	Set<UUID> findNearbyUsers(double latitude, double longitude, double radiusKm);
}
