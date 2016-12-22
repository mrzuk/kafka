import com.tr.cd.kafka.consumer.ConsumerInitializer;
import com.tr.cd.kafka.consumer.KafkaConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by U0145084 on 2016-12-22.
 */

@Configuration
@ComponentScan(basePackages = { "kafka.listener"})
public class KafkaInitializer extends ConsumerInitializer {

	private static final Logger log = LoggerFactory.getLogger(KafkaInitializer.class);
	@Bean
	KafkaConsumerConfig kafkaConsumerConfig(){
		KafkaConsumerConfig config = new KafkaConsumerConfig();

		config.setAutoCommitIntervalMs("1000");
		config.setGroupId("groupTest");
		config.setReadAttempts("2");
		config.setThreads("1");
		config.setZookeeperSyncTimeMs("200");
		config.setZookeper("c651vjrphkc02.int.thomsonreuters.com:2181,c054wmrphkc03.int.thomsonreuters.com:2181,c500mpgphkc04.int.thomsonreuters.com:2181/kafkaCourtwire");
		config.setTopic("test_rz");
		config.setSessionTimeout("5000");
		return config;
	}

}
