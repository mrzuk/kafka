package com.tr.cd.kafka.common;

import com.google.gson.Gson;
import kafka.message.MessageAndMetadata;

/**
 * Created by U0145084 on 2016-12-21.
 */
public class KafkaHelper {
	public static KafkaMessage getMessage(MessageAndMetadata<String, String> messageAndMetadata) {
		String msg = null;
		try {
			msg= new String(messageAndMetadata.message());
			if(msg.isEmpty()) {
				System.out.println("Empty message");
				return null;
			}
		}
		catch(Exception e){
			System.out.println("Error while gotMessage");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		System.out.println("got message: "+msg);
		Gson gson = new Gson();
		KafkaMessage returnMssg = gson.fromJson(msg, KafkaMessage.class);
		return returnMssg;
	}
}
