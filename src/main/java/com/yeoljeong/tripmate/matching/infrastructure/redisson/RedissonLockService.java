package com.yeoljeong.tripmate.matching.infrastructure.redisson;

import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.MATCHING_LOCK_FAILED;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.matching.application.aop.MatchingLockService;
import com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockService implements MatchingLockService {

	private final RedissonClient redissonClient;

	@Override
	public void lock(String key, long tryLockTime, long leaseTime) {
		RLock lock = redissonClient.getLock(key);
		log.debug("[MatchingLock] lock 시도 - key: {}", key);
		try {
			boolean isLock = lock.tryLock(tryLockTime, leaseTime, TimeUnit.MILLISECONDS);
			if (!isLock) {
				log.error("[MatchingLock] lock 획득 실패 - key: {}", key);
				throw new BusinessException(MATCHING_LOCK_FAILED);
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("[MatchingLock] redis lock 오류 - key: {}", key, e);
		}
	}

	@Override
	public void unlock(String key) {
		log.debug("[MatchingLock] lock 해제 - key: {}", key);
		redissonClient.getLock(key).unlock();
	}
}
