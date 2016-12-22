import com.tr.cd.kafka.consumer.KafkaConsumerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by U0145084 on 2016-12-22.
 */
public class Main {

	public static void main(String[] args){

		System.out.print("Start");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaInitializer.class);

		KafkaInitializer initializer = (KafkaInitializer)context.getBean(KafkaInitializer.class);
		initializer.Initialize();

		System.out.print("Waiting");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finished waiting");
	}
}
