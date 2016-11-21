import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

//import org.apache.kafka.clients.producer.Producer;

/**
 * Created by harsha on 11/17/16.
 */
public class SendFeaturesKafka {


    public static void main(String[] args) {

        String topic = "priya";
        Producer<Integer, String> producer;
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "192.168.0.3:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        //properties.put("message.max.bytes", "10000000");

        ProducerConfig config = new ProducerConfig(properties);

        producer = new Producer<Integer, String>(new ProducerConfig(properties));
        System.out.println("Key Frames Generated");
//        File folder = new File("output/mainframes");
//        File[] listOfFiles = folder.listFiles();
        String fileName = "output/features.txt";
        EncodeData_Priya ed = new EncodeData_Priya();
        byte [] encStr = ed.encodeToString(fileName);
        System.out.println("encStr: " + encStr);
        String fileNameLength = Integer.toString(fileName.length());
        System.out.println("Simple Filename Length: " + fileNameLength);
//            msg = "*@#Harsha*@#" +  " " + fileName + " " + msg + "#@*Sri#@*";
        String encStrFName = fileName + ";" + encStr;
        System.out.println("Encoded String: " + encStrFName.length());
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, encStrFName);//Encoding the Video
        System.out.println("Data is:"+ data);
        producer.send(data);
        System.out.println("Message Sent");
    }


}