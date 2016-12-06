import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

//import org.apache.kafka.clients.producer.Producer;

/**
 * Created by harsha on 11/17/16.
 */
public class SendFeaturesKafka {

    public void sendFrameFeatures(String features) {
        String topic = "featuresToStorm";
        Producer<Integer, String> producer;
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "127.0.0.1:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        properties.put("max.message.bytes", "20971520");

//        ProducerConfig config = new ProducerConfig(properties);
        producer = new Producer<Integer, String>(new ProducerConfig(properties));
        System.out.println("Key Frames Generated");
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, features);//Encoding the Video
//        System.out.println("Data is:"+ data);
        producer.send(data);
        System.out.println("Message Sent");
    }

    public static void main(String[] args) {

        String topic = "featuresToStorm";
        Producer<Integer, String> producer;
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "127.0.0.1:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        properties.put("max.message.bytes", "20971520");

        ProducerConfig config = new ProducerConfig(properties);

        producer = new Producer<Integer, String>(new ProducerConfig(properties));
        System.out.println("Key Frames Generated");
//        File folder = new File("output/mainframes");
//        File[] listOfFiles = folder.listFiles();
        String fileName = "output/features.txt";
        EncodeData ed = new EncodeData();
        String encStr = ed.encodeToString(fileName);
//        System.out.println("encStr: " + encStr);
        String fileNameLength = Integer.toString(fileName.length());
        System.out.println("Simple Filename Length: " + fileNameLength);
//            msg = "*@#Harsha*@#" +  " " + fileName + " " + msg + "#@*Sri#@*";
//        String encStrFName = fileName + ";" + encStr;
        String encStrFName = encStr;
        System.out.println("Encoded String: " + encStrFName.length());
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, "-119.0 -39.0 -63.0 -128.0 -128.0 -124.0 -113.0 -118.0 29.0 -5.0 -122.0 -128.0 -128.0 -128.0 -125.0 -110.0 -55.0 -93.0 -127.0 -128.0 -128.0 -128.0 -128.0 -128.0 -128.0 -123.0 -125.0 -128.0 -128.0 -128.0 -128.0 -128.0 -86.0 -32.0 -86.0 -128.0 -128.0 -127.0 -128.0 -128.0 31.0 -18.0 -124.0 -128.0 -128.0 -128.0 -128.0 -125.0 10.0 -100.0 -127.0 -128.0 -128.0 -128.0 -128.0 -127.0 -128.0 -122.0 -124.0 -128.0 -128.0 -128.0 -128.0 -128.0 -41.0 -109.0 -116.0 -125.0 -110.0 -100.0 -126.0 -115.0 31.0 -117.0 -128.0 -128.0 -128.0 -128.0 -126.0 -27.0 -25.0 -122.0 -128.0 -128.0 -128.0 -128.0 -100.0 -79.0 -127.0 -126.0 -128.0 -128.0 -128.0 -128.0 -104.0 -122.0 -40.0 -127.0 -125.0 -122.0 -117.0 -121.0 -127.0 -98.0 31.0 -128.0 -128.0 -128.0 -128.0 -128.0 -123.0 31.0 -107.0 -128.0 -128.0 -128.0 -128.0 -128.0 -61.0 -61.0 -128.0 -128.0 -128.0 -128.0 -128.0 -128.0 -81.0 -121.0");//Encoding the Video
//        System.out.println("Data is:"+ data);
        producer.send(data);
        System.out.println("Message Sent");
    }


}