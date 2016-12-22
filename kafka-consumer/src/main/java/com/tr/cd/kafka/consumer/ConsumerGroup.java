package com.tr.cd.kafka.consumer;


import kafka.consumer.KafkaStream;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by U0145084 on 2016-12-21.
 */
public class ConsumerGroup {
	private static final Logger log = LoggerFactory.getLogger(ConsumerGroup.class);
	private final kafka.javaapi.consumer.ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;
	private KafkaConsumerConfig consumerConfig;
	private HashMap<Class,KafkaReceiver> listeners = new HashMap<Class,KafkaReceiver>();
	private int threads;
	public ConsumerGroup(KafkaConsumerConfig consumerConfig) {
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig.getConsumerConfig());
		this.topic = consumerConfig.getTopic();
		this.threads = consumerConfig.getThreads();
		this.consumerConfig = consumerConfig;
	}

	public void shutdown() {
		if (consumer != null)
			consumer.shutdown();
		if (executor != null)
			executor.shutdown();
		try {
			if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
				System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted during shutdown, exiting uncleanly");
		}
	}

	public void run() {
		log.info("Running ConsumerGroup with ["+this.threads + "] threads");
		int a_numThreads = this.threads;
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(a_numThreads));
		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(
				new VerifiableProperties());
		Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
		List<KafkaStream<String, String>> streams = consumerMap.get(topic);

		// now launch all the threads
		//
		executor = Executors.newFixedThreadPool(a_numThreads);

		// now create an object to consume the messages
		//
		int threadNumber = 0;
		for (final KafkaStream stream : streams) {
			executor.submit(new KafkaConsumer(stream, threadNumber,listeners,this.consumerConfig.getReadAttempts()));
			threadNumber++;
		}
	}

	public void addListener(Class messageClass, KafkaReceiver listener){
		this.listeners.put(messageClass,listener);
	}

}