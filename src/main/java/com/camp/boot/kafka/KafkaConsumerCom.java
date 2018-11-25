package com.camp.boot.kafka;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;


public class KafkaConsumerCom 
{

	/*@Autowired
    private Environment env;*/
	private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(KafkaConsumerCom.class);
	
	private Executor executor;
	
	
	public KafkaConsumerCom(Properties props,String topic,int num,int offset)
	{

		executor = Executors.newFixedThreadPool(num);

		for (int i = 0; i < num; i++) 
		{
			executor.execute(new Runnable() {

				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);

					consumer.subscribe(Collections.singletonList(topic));
		
					
					boolean beginFlag = true ;
					
					
					
					while (true) 
					{
						

						ConsumerRecords<Integer, String> records = consumer.poll(1000);
						
						if(beginFlag && offset>=0)
						{
							Set<TopicPartition> tpps = consumer.assignment();
							
							for(TopicPartition tp : tpps)
							{
								System.out.println("topic:"+tp.topic()+";partition:"+tp.partition());
								consumer.seek(tp, offset);
							}
							
						  
						   beginFlag = false;
						   
						}else
						{
							for (ConsumerRecord<Integer, String> record : records) 
							{
							
								logger.debug("Received message: (" + record.key() + ", " + record.value()
										+ ") at offset " + record.offset()+ " at partition "+record.partition());
							}
						}
						
						
						
						

					}
				}

			});
		}
	}
	
	
	
	
	/*@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
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

		KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);
		
		consumer.subscribe(Collections.singletonList("replicated-kafkatopic"));
		
		new Thread(new Runnable(){

			@Override
			public void run() 
			{
				// TODO Auto-generated method stub

				while (true) 
				{
					ConsumerRecords<Integer, String> records = consumer.poll(1000);
					for (ConsumerRecord<Integer, String> record : records) {
						System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset "
								+ record.offset());
					}

				}

			}
			
		}).start();
		
       

	}*/
	

}
