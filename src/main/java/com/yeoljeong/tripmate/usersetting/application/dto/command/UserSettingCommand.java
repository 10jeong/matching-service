package com.yeoljeong.tripmate.usersetting.application.dto.command;

import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiTF;
import java.util.UUID;

public record UserSettingCommand (
	UUID userId,
	boolean isSmoking,
	MbtiIE ie,
	MbtiSN sn,
	MbtiTF tf,
	MbtiPJ pj
) {

}
