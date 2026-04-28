package com.yeoljeong.tripmate.usersetting.application.dto.command;

import java.util.UUID;

public record UserSettingCommand (
	UUID userId,
	boolean isSmoking,
	String ie,
	String sn,
	String tf,
	String pj
) {

}
