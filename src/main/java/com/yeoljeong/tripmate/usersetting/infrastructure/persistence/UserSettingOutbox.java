package com.yeoljeong.tripmate.usersetting.infrastructure.persistence;

import com.yeoljeong.tripmate.domain.Outbox;
import jakarta.persistence.Entity;
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
@Table(name = "user_setting_outbox")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSettingOutbox extends Outbox {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	public static UserSettingOutbox create(String topic, String payload) {
		UserSettingOutbox outbox = new UserSettingOutbox();
		init(outbox, topic, payload);
		return outbox;
	}
}
