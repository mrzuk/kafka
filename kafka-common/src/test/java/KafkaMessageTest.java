
import com.tr.cd.kafka.common.KafkaMessage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by U0145084 on 2016-12-21.
 */
public class KafkaMessageTest {

	@Test
	public void Test1() throws ClassNotFoundException {

		TestMessageClass kafkaMessage = new TestMessageClass("test","test2");

		KafkaMessage kafkaMessage1 = new KafkaMessage(kafkaMessage);

		try {
			Assert.assertEquals(kafkaMessage1.getMessageClass().getCanonicalName(),TestMessageClass.class.getCanonicalName());
			TestMessageClass message = kafkaMessage1.getMessage();
			Assert.assertTrue(kafkaMessage.equals(message));
			Assert.assertNotEquals(message,kafkaMessage);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


}
