package com.yeoljeong.tripmate.matching.application.dto.command;

import java.util.UUID;

public record UserMatchingCriteriaCommand(
	UUID userId,
	String gender,
	boolean smoking,
	String mbtiIE,
	String mbtiSN,
	String mbtiTF,
	String mbtiPJ
) {
}
