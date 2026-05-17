package com.yeoljeong.tripmate.matching.application.aop;

import com.yeoljeong.tripmate.common.aop.TripmateLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TripmateLockAspect {

	private final MatchingLockService lockService;
	private final ExpressionParser parser = new SpelExpressionParser();

	@Around("@annotation(tripmateLock)")
	public Object aroundMethod(
		ProceedingJoinPoint pjp, TripmateLock tripmateLock
	) throws Throwable {
		String key = resolveKey(pjp, tripmateLock.key());
		lockService.lock(key, tripmateLock.tryLockTime(), tripmateLock.leaseTime());
		try {
			return pjp.proceed();
		} finally {
			lockService.unlock(key);
		}
	}

	private String resolveKey(ProceedingJoinPoint pjp, String keyExpression) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		String[] paramNames = signature.getParameterNames();
		Object[] args = pjp.getArgs();

		StandardEvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < paramNames.length; i++) {
			context.setVariable(paramNames[i], args[i]);
		}
		return parser.parseExpression(keyExpression).getValue(context, String.class);
	}
}
