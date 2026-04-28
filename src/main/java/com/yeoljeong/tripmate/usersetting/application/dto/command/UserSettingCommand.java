package com.yeoljeong.tripmate.usersetting.application.dto.command;

import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiTF;
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
