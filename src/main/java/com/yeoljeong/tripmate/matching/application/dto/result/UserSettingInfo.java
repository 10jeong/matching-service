package com.yeoljeong.tripmate.matching.application.dto.result;

import java.util.UUID;

public record UserSettingInfo (
	UUID userId,
	String gender,
	boolean isSmoking,
	String mbtiIE,
	String mbtiSN,
	String mbtiTF,
	String mbtiPJ
) {

}
