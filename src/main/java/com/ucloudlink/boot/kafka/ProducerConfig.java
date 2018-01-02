package com.ucloudlink.boot.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ProducerConfig {
	
	@Autowired
    private Environment env;
	
	@Bean(name="kafkaProducer")
	public KafkaProducer<String, String> createProducer()
	{
		Properties props = new Properties();
		props.put("bootstrap.servers", env.getProperty("kafka.bootstrap.servers"));
		props.put("client.id", "DemoProducer");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);

		return producer;
	}

}
