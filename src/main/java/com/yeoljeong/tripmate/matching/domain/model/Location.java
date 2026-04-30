package com.yeoljeong.tripmate.matching.domain.model;

import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.INVALID_LATITUDE_RANGE;
import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.INVALID_LONGITUDE_RANGE;
import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.LOCATION_REQUIRED;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {

	@Column(nullable = false, precision = 10, scale = 7)
	private BigDecimal lat;

	@Column(nullable = false, precision = 10, scale = 7)
	private BigDecimal lng;

	public static Location of(BigDecimal lat, BigDecimal lng) {
		if (lat == null || lng == null) {
			throw new BusinessException(LOCATION_REQUIRED);
		}
		if (lat.compareTo(BigDecimal.valueOf(-90)) < 0 || lat.compareTo(BigDecimal.valueOf(90)) > 0) {
			throw new BusinessException(INVALID_LATITUDE_RANGE);
		}
		if (lng.compareTo(BigDecimal.valueOf(-180)) < 0 || lng.compareTo(BigDecimal.valueOf(180)) > 0) {
			throw new BusinessException(INVALID_LONGITUDE_RANGE);
		}
		return new Location(lat, lng);
	}
}
