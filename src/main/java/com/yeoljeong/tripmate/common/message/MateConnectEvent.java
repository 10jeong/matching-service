package com.yeoljeong.tripmate.common.message;

import java.util.UUID;

public class MateConnectEvent {

	public record Subscribe(UUID userId) {}
	public record Unsubscribe(UUID userId) {}

}
