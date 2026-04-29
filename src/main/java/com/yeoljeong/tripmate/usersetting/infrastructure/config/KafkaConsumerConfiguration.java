package com.yeoljeong.tripmate.usersetting.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfiguration {

	@Value("${spring.retry.interval}")
	private long retryInterval;

	@Value("${spring.retry.max-attempts}")
	private int maxAttempts;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
		ConsumerFactory<String, Object> consumerFactory,
		KafkaTemplate<String, Object> kafkaTemplate
	) {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
			kafkaTemplate,
			(record, ex) -> new TopicPartition(record.topic() + ".DLT", -1));
		ExponentialBackOff backOff = new ExponentialBackOff(retryInterval, 3.0);
		backOff.setMaxAttempts(maxAttempts);

		DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
		errorHandler.setCommitRecovered(true);
		factory.setCommonErrorHandler(errorHandler);

		return factory;
	}

}
