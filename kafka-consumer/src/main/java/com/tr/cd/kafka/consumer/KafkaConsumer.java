package com.tr.cd.kafka.consumer;

import com.tr.cd.kafka.common.KafkaHelper;
import com.tr.cd.kafka.common.KafkaMessage;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by U0145084 on 2016-12-21.
 */
public class KafkaConsumer implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
	private Boolean isRunning = true;
	private KafkaStream stream;
	private int threadNumber;
	private HashMap<Class,KafkaReceiver> messageReceivers;
	private int attemptsNumber;

	KafkaConsumer(KafkaStream stream, int threadNumber, HashMap<Class,KafkaReceiver> receiverList, int attemptsNumber){
		this.stream = stream;
		this.threadNumber = threadNumber;
		this.messageReceivers = receiverList;
		this.attemptsNumber = attemptsNumber;
	}

	public void stop(){
		log.info("Stopping KafkaConsumer");
		this.isRunning = false;
	}

	public void run() {
		log.info("Starting KafkaConsumer with threadId: " + this.threadNumber);

		while(isRunning){

			try{
				ConsumerIterator<String, String> iterator = stream.iterator();
				if (iterator.hasNext()) {
					log.debug("Has message ");
					MessageAndMetadata<String, String> mssg = iterator.peek();

					KafkaMessage message = KafkaHelper.getMessage(mssg);
					Class t = message.getMessageClass();


					log.debug("Checking if receivers contains key: "+t.getCanonicalName());
					if (messageReceivers.containsKey(t)) {
						log.debug("Receiver contains key: " + t.getCanonicalName());
						int attempt = attemptsNumber;
						do {
							try {
								log.info("Invoking receive");
								messageReceivers.get(t).receive(message.getMessage());
								attempt = 0;
							} catch (Exception e) {
								attempt--;
								log.error(String.format("Message could not be processed. (%d try). ", attemptsNumber - attempt), e);
							}
						} while (attempt > 0);
					} else {
						log.error("No receiver for message: {}", t.toString());
						for(HashMap.Entry entry: messageReceivers.entrySet()){
							log.info(entry.getKey().getClass().getCanonicalName());
						}
					}
					iterator.next();

					log.info("[END] Thread " + threadNumber + ": " + new String(mssg.message()) + ", partition " + mssg.partition());
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}
}
