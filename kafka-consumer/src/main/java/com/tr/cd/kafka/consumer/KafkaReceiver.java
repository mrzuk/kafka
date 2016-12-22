package com.tr.cd.kafka.consumer;

/**
 * Created by U0145084 on 2016-12-21.
 */
public interface KafkaReceiver<T> {
	void receive(T message);

	Class getMessageClass();
}
