package com.yeoljeong.tripmate.usersetting.infrastructure.repository;

import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import com.yeoljeong.tripmate.usersetting.infrastructure.repository.jpa.SpringDataUserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSettingJpaRepository implements UserSettingRepository {
	private final SpringDataUserSettingRepository userSettingRepository;
}

