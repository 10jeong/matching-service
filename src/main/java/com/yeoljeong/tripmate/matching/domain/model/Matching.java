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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

	@Column(columnDefinition = "TEXT", nullable = false, length = 500)
	private String description;

	@Embedded
	private Location location;

	@Embedded
	private MatchingPeriod matchingPeriod;

	@Column(length = 500, nullable = false)
	private String chatUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MatchingStatus status;

	@Embedded
	private MatchingSetting matchingSetting;

	public static Matching create(
		UUID userId,
		String title,
		String description,
		Location location,
		MatchingPeriod matchingPeriod,
		String chatUrl,
		MatchingSetting matchingSetting
	) {
		Matching matching = new Matching();
		matching.hostUserId = userId;
		matching.title = title;
		matching.description = description == null ? "" : description;
		matching.location = location;
		matching.matchingPeriod = matchingPeriod;
		matching.chatUrl = chatUrl == null ? "" : chatUrl;
		matching.status = MatchingStatus.OPEN;
		matching.matchingSetting = matchingSetting;
		return matching;
	}
}
