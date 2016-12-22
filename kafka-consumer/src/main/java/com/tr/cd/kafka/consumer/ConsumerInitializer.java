package com.tr.cd.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created by U0145084 on 2016-12-21.
 */
@Component
public abstract class ConsumerInitializer {
	private static final Logger log = LoggerFactory.getLogger(ConsumerInitializer.class);
	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	private KafkaConsumerConfig config;


	public void Initialize(){

		KafkaReceiver<?> receiver = applicationContext.getBean(KafkaReceiver.class);

		if(this.config==null)
			throw new RuntimeException("KafkaConfig is not set");

		ConsumerGroup group = new ConsumerGroup(this.config);
		log.info("Adding listener: class="+receiver.getMessageClass());
		group.addListener(receiver.getMessageClass(),receiver);
		group.run();
	}
}
