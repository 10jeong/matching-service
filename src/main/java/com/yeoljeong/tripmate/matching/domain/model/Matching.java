package com.yeoljeong.tripmate.matching.domain.model;

import com.yeoljeong.tripmate.matching.domain.constants.MatchingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matching")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private UUID hostUserId;

	@Column(nullable = false, length = 50)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Embedded
	private Location location;

	@Embedded
	private MatchingPeriod matchingPeriod;

	@Column(length = 500)
	private String chatUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MatchingStatus status;

	@Embedded
	private MatchingSetting matchingSetting;
}
