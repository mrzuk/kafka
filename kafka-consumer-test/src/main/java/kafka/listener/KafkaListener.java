package kafka.listener;

import com.tr.cd.kafka.consumer.KafkaConsumer;
import com.tr.cd.kafka.consumer.KafkaReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by U0145084 on 2016-12-22.
 */
@Component
public class KafkaListener implements KafkaReceiver<String> {

	private static final Logger log = LoggerFactory.getLogger(KafkaListener.class);

	public void receive(String message) {
		log.info("Received message: " +message );
	}

	public Class getMessageClass() {
		return String.class;
	}
}
