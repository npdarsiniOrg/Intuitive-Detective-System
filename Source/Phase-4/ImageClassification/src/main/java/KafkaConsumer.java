//import com.migcomponents.migbase64.Base64;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by npdarsini on 11/19/16.
 */
public class KafkaConsumer {


        private final ConsumerConnector consumer;
        private final String topic;


        public KafkaConsumer(String zookeeper, String groupId, String topic) {
            Properties props = new Properties();
            props.put("zookeeper.connect", zookeeper);
            props.put("group.id", groupId);
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("zookeeper.session.timeout.ms", "500");
            props.put("zookeeper.sync.time.ms", "250");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "3000");
            props.put("fetch.message.max.bytes", "20971520");

//        KafkaConsumer<Integer, String> kafkaConsumer = new KafkaConsumer<Integer, String>(props);
//        kafkaConsumer.subscribe(Arrays.asList(topic));
            consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
            this.topic = topic;

            System.out.println("Topic is: "+ topic);
        }



    public void testConsumer() {
        Map<String, Integer> topicCount = new HashMap<String, Integer>();
        topicCount.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
        List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
        StringBuilder sb = new StringBuilder();
        for ( KafkaStream stream : streams) {

            int j=0;
            ConsumerIterator<byte[], byte[]> it = stream.iterator();

            int i=0;

            while (it.hasNext()) {
                DecodeData dd = new DecodeData();
                byte[] message = it.next().message();
                sb.setLength(0);
                String value = new String(message);
//                System.out.println("Value is : "+ value);



//                sb.append(value);
//                System.out.println(value.split(";")[1]);
//                dd.decodeData(value.split(";")[1], j);
                dd.decodeData(value.split(";")[1], j);
                j++;

            }

        }
        if (consumer != null) {
            consumer.shutdown();
        }
    }


        public static void main(String[] args) {
            String topic = "featuresToSpark"; //args[0]; //Topic Name
            KafkaConsumer consumer = new KafkaConsumer("localhost:2181", "testgroup", topic);
            consumer.testConsumer();

        }

    }
