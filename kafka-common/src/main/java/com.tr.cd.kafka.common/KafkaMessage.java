package com.tr.cd.kafka.common;

import com.google.gson.Gson;

/**
 * Created by U0145084 on 2016-12-21.
 */
public class KafkaMessage {
	String messageClass;
	String messageString;

	public <T> KafkaMessage(T object){
		this.messageClass = object.getClass().getCanonicalName();
		Gson gson = new Gson();
		this.messageString = gson.toJson(object);
	}

	public <T> T getMessage(){
		Gson gson = new Gson();
		Class<T> clazz = null;
		try {
			clazz = this.getMessageClass();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return gson.fromJson(this.messageString,clazz);
	}

	public Class getMessageClass() throws ClassNotFoundException {
		return Class.forName(this.messageClass);
	}
}
