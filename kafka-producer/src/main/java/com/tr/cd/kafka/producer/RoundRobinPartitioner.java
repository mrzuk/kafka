package com.tr.cd.kafka.producer;

import java.util.concurrent.atomic.AtomicInteger;

import kafka.utils.VerifiableProperties;
import org.apache.log4j.Logger;
import kafka.producer.Partitioner;

/**
 * Round robin partitioner using a simple thread safe AotmicInteger
 */
public class RoundRobinPartitioner implements Partitioner {
	private static final Logger logger = Logger.getLogger(RoundRobinPartitioner.class);

	final AtomicInteger counter = new AtomicInteger(0);

	public RoundRobinPartitioner(VerifiableProperties properties) {
		logger.trace("Initialize Round Robin Partitioner");
	}

	public int partition(Object key, int partitions) {

		int partitionId = counter.incrementAndGet() % partitions;
		if (counter.get() > 65536) {
			counter.set(0);
		}
		return partitionId;
	}
}
