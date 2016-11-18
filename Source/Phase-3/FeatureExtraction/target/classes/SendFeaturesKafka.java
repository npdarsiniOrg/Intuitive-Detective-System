import com.migcomponents.migbase64.Base64;
import com.xuggle.xuggler.IContainer;
import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import kafka.producer.ProducerConfig;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import java.io.File;
import java.util.Properties;

/**
 * Created by harsha on 11/17/16.
 */
public class SendFeaturesKafka {

    public static void main(String[] args) {
        String topic = "featuresToSpark";
        Producer<Integer, String> producer;
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "127.0.0.1:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        properties.put("message.max.bytes", "10000000");
        producer = new Producer<Integer, String>(new ProducerConfig(properties));
        System.out.println("Key Frames Generated");
//        File folder = new File("output/mainframes");
//        File[] listOfFiles = folder.listFiles();
        String fileName = "output/features.txt";
        EncodeData ed = new EncodeData();
        String encStr = ed.encodeToString(fileName);
        String fileNameLength = Integer.toString(fileName.length());
        System.out.println("Simple Filename Length: " + fileNameLength);
//            msg = "*@#Harsha*@#" +  " " + fileName + " " + msg + "#@*Sri#@*";
        encStr = fileName + " " + encStr;
        System.out.println("Encoded String: " + encStr.length());
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, encStr);//Encoding the Video
        producer.send(data);
        System.out.println("Message Sent");
    }


}
