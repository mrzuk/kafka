package com.tr.cd.kafka.producer;

import com.google.gson.Gson;

import com.tr.cd.kafka.common.KafkaMessage;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Created by U0145084 on 2016-12-16.
 */
public class KafkaProducer {

	private KafkaProducerConfig kafkaConfig;

	private KafkaProducer(KafkaProducerConfig kafkaConfig) {
		this.kafkaConfig = kafkaConfig;
	}

	public static KafkaProducer with(KafkaProducerConfig kafkaConfig) {
		if(kafkaConfig == null)
		{
			throw new IllegalArgumentException("kafkaConfig");
		}
		return new KafkaProducer(kafkaConfig);
	}

	public <T> void sendAsPlainText(String topic, String messageKey, String message)
	{
		if(messageKey == null || messageKey.isEmpty())
		{
			throw new IllegalStateException("Message key cannot be null or empty.");
		}

		Producer<String, String> producer = new Producer<String, String>(
				new ProducerConfig(kafkaConfig.getProducerConfig()));

		KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(topic, messageKey,	message);

		producer.send(keyedMessage);
	}

	public <T> void sendAsMessage(String topic, String messageKey, T message) {

		if(messageKey == null || messageKey.isEmpty()) {
			throw new IllegalStateException("Message key cannot be null or empty.");
		}

		Producer<String, String> producer = new Producer<String, String>(
				new ProducerConfig(kafkaConfig.getProducerConfig()));

		Gson gson = new Gson();
		KafkaMessage kafkaMessage = new KafkaMessage(message);


		KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(topic, messageKey,	gson.toJson(kafkaMessage));

		producer.send(keyedMessage);
	}

	public <T> void sendAsMessage(String topic, T message) {
		sendAsMessage(topic, java.util.UUID.randomUUID().toString(), message);
	}

	public <T> void sendAsPlainText(String topic, String message) {
		sendAsPlainText(topic, java.util.UUID.randomUUID().toString(), message);
	}
}