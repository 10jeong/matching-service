package com.yeoljeong.tripmate.matching.domain.model;

import com.yeoljeong.tripmate.domain.Outbox;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matching_outbox")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingOutbox extends Outbox {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	public static MatchingOutbox create(String topic, String payload) {
		MatchingOutbox outbox = new MatchingOutbox();
		init(outbox, topic, payload);
		return outbox;
	}
}
