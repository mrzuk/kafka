package com.tr.cd.kafka.producer;

import java.util.Properties;

/**
 * Created by U0145084 on 2016-12-21.
 */

public class KafkaProducerConfig {

	private String brokers;
	private String zookeper;
	private String partitionerClassName;

	public void setBrokers(String value){
		this.brokers=value;
	}

	public void setZookeper(String value){
		this.zookeper=value;
	}

	public void setPartitionerClassName(String value){
		this.partitionerClassName = value;
	}

	public String getBroker(){return this.brokers;}

	public String getZookeper() {return this.zookeper;}

	public String getPartitionerClassName() {return this.partitionerClassName;}

	protected Class getPartitionerClass(){
		try {
			return Class.forName(this.partitionerClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Properties getProducerConfig(){
		Properties properties = new Properties();
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		properties.put("metadata.broker.list", getBroker());
		if(this.partitionerClassName.isEmpty()){
			this.partitionerClassName = RoundRobinPartitioner.class.getCanonicalName();
		}
		properties.put("partitioner.class",this.partitionerClassName);

		return properties;
	}


}