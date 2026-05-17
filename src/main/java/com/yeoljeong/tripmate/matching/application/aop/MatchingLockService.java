package com.yeoljeong.tripmate.matching.application.aop;

public interface MatchingLockService {
	void lock(String key, long tryLockTime, long leaseTime);
	void unlock(String key);
}
