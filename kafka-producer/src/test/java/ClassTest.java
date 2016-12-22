import com.tr.cd.kafka.producer.KafkaProducer;
import com.tr.cd.kafka.producer.KafkaProducerConfig;
import com.tr.cd.kafka.producer.RoundRobinPartitioner;
import org.junit.Test;
/**
 * Created by U0145084 on 2016-12-22.
 */
public class ClassTest {

	@Test
	public void Test(){
		KafkaProducerConfig config = new KafkaProducerConfig();
		//config.setZookeper("c651vjrphkc02.int.thomsonreuters.com:2181,c054wmrphkc03.int.thomsonreuters.com:2181,c500mpgphkc04.int.thomsonreuters.com:2181/kafkaCourtwire");
		config.setBrokers("c909brxpasm09.int.thomsonreuters.com:9094,c528kjfpasm18.int.thomsonreuters.com:9094");
		config.setPartitionerClassName(RoundRobinPartitioner.class.getCanonicalName());

		KafkaProducer kafkaProducer = KafkaProducer.with(config);

		kafkaProducer.sendAsMessage("test_rz","test");
	}
}
