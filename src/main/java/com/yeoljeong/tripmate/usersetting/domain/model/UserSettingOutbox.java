package com.yeoljeong.tripmate.usersetting.domain.model;

import com.yeoljeong.tripmate.domain.Outbox;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_setting_outbox")
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
