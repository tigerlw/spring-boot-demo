package com.ucloudlink.boot.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class KafkaConsumerConfig 
{
	@Autowired
    private Environment env;
	
	@Bean
	public KafkaConsumerCollection createConsumerCollection()
	{
		List<String> consumers = Arrays.asList(env.getProperty("kafka.consumers").split(","));
		
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,env.getProperty("kafka.bootstrap.servers"));
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		
		List<KafkaConsumerCom> KafkaConsumerComs = new ArrayList<KafkaConsumerCom>();

		for (String consumer : consumers)
		{
			if (!consumer.isEmpty()) 
			{
				String topic = env.getProperty("kafka." + consumer + ".topic");

				int num = Integer.valueOf(env.getProperty("kafka." + consumer + ".num"));
				
				int offset = Integer.valueOf(env.getProperty("kafka." + consumer + ".offset"));

				KafkaConsumerComs.add(new KafkaConsumerCom(props, topic, num,offset));
			}

		}

		KafkaConsumerCollection kafkaConsumerCollection = new KafkaConsumerCollection();
		
		kafkaConsumerCollection.setKafkaConsumerComs(KafkaConsumerComs);
		
		
		return kafkaConsumerCollection;
	}

}
