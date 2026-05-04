package com.yeoljeong.tripmate.matching.domain.model;

import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.HOST_CANNOT_ACCEPT_OWN_MATCHING;
import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.MATCHING_ALREADY_MATCHED;

import com.yeoljeong.tripmate.domain.BaseAuditEntity;
import com.yeoljeong.tripmate.exception.BusinessException;
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
public class Matching extends BaseAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private UUID hostUserId;

	@Column
	private UUID mateUserId;

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

	public void accept(UUID userId) {
		if (this.hostUserId.equals(userId)) {
			throw new BusinessException(HOST_CANNOT_ACCEPT_OWN_MATCHING);
		}
		if (this.status == MatchingStatus.MATCHED) {
			throw new BusinessException(MATCHING_ALREADY_MATCHED);
		}
		this.mateUserId = userId;
		this.status = MatchingStatus.MATCHED;
	}
}
