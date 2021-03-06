package cn.mldn.mykafka.producer;


import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
/**
 * 通过使用SSL的验证的生产者
 * @author paul
 *
 */
public class SendMessageProducerSSL {
	public static final String SERVERS = "kafka-single:9095";
	public static final String TOPIC = "mldn-topic";
	public static void main(String[] args) {
		// 如果要想进行Kafka消息发送需要使用Properties定义一些环境属性
		Properties props = new Properties();
//		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "D:\\server.keystore.jks");
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "/Users/paul/git/kafka/mykafka/ca/server/server.keystore.jks");
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "mldnjava");
//		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "D:\\client.truststore.jks");
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/Users/paul/git/kafka/mykafka/ca/trust/client.truststore.jks");
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "mldnjava");
		props.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "JKS");
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS); // 定义Kafka服务地址
		// Kafka之中是以key和value的形式进行消息的发送处理， 所以为了保证Kafka的性能，专门提供有统一类型
		// props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer") ;
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()) ;
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName()) ;
		long start = System.currentTimeMillis() ;
		// 定义消息发送者对象，依靠此对象可以进行消息的传递
		Producer<String,Integer> producer = new KafkaProducer<String,Integer>(props) ;
		for (int x = 0 ; x < 1 ; x ++) {
			producer.send(new ProducerRecord<String,Integer>(TOPIC,"mldn-" + x,x)) ;
		}
		long end = System.currentTimeMillis() ;
		System.out.println("*** 消息发送完成：" + (end - start));
		producer.close(); 
	}

}
