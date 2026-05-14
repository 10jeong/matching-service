package com.yeoljeong.tripmate.matching.presentation.errorhandler;

import com.yeoljeong.tripmate.matching.presentation.MatchingController;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

@RestControllerAdvice(assignableTypes = MatchingController.class)
public class MatchingExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getFieldErrors().stream()
			.map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
			.collect(Collectors.joining(", "));
		return ResponseEntity
			.badRequest()
			.contentType(MediaType.TEXT_PLAIN)
			.body(message);
	}

	@ExceptionHandler(AsyncRequestTimeoutException.class)
	public void handleSseTimeout() {
		// SSE 타임아웃은 정상 종료
	}
}
