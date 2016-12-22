package com.tr.cd.kafka.consumer;

import kafka.consumer.ConsumerConfig;

import java.util.Properties;

/**
 * Created by U0145084 on 2016-12-21.
 */
public class KafkaConsumerConfig {

	private String groupId;
	private String zookeper;
	private String sessionTimeout;
	private String zookeeperSyncTimeMs;
	private String autoCommitIntervalMs;
	private String topic;
	private int readAttempts;
	private int threads;

	public void setGroupId(String value){
		this.groupId = value;
	}

	public void setThreads(String threads){
		this.threads = Integer.parseInt(threads);
	}

	public void setZookeper(String value){
		this.zookeper = value;
	}

	public void setSessionTimeout(String value){
		this.sessionTimeout = value;
	}

	public void setZookeeperSyncTimeMs(String value){
		this.zookeeperSyncTimeMs = value;
	}

	public void setAutoCommitIntervalMs(String value){
		this.autoCommitIntervalMs = value;
	}

	public void setTopic(String value){
		this.topic = value;
	}

	public void setReadAttempts(String value){
		this.readAttempts = Integer.parseInt(value);
	}

	public String getTopic(){
		return this.topic;
	}

	public String getGroupId(){
		return this.groupId;
	}

	public int getThreads(){return this.threads;}

	public int getReadAttempts(){return this.readAttempts;}

	public ConsumerConfig getConsumerConfig() {
		Properties props = new Properties();
		props.put("zookeeper.connect", this.zookeper);
		props.put("group.id", this.groupId);
		props.put("zookeeper.session.timeout.ms", this.sessionTimeout);
		props.put("zookeeper.sync.time.ms", this.zookeeperSyncTimeMs);
		props.put("auto.commit.interval.ms", this.autoCommitIntervalMs);

		return new ConsumerConfig(props);
	}
}
