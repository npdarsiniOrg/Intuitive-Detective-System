import com.migcomponents.migbase64.Base64;
import com.xuggle.xuggler.IContainer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.video.Video;
import org.openimaj.video.xuggle.XuggleVideo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

//import kafka.javaapi.producer.Producer;
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;

public class SimpleProducer {
    private static Producer<Integer, String> producer;
    private final Properties properties = new Properties();
    static Video<MBFImage> video;
    //    VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
    static List<MBFImage> imageList = new ArrayList<MBFImage>();
    static List<Long> timeStamp = new ArrayList<Long>();
    static List<Double> mainPoints = new ArrayList<Double>();
    private static long frames, duration;
    private static int height, width;
    private static double fps;
    static List<String> mainframesList = new ArrayList<String>();


    public SimpleProducer() {
//        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("serializer.class", "kafka.serializer.StringEncoder");
//        properties.put("request.required.acks", "1");
        properties.put("acks", "1");
        properties.put("max.message.bytes", "10000000");
        producer = new KafkaProducer<Integer, String>(properties);
    }

    public static void Frames(String path){
        video = new XuggleVideo(new File(path));
        IContainer container = IContainer.make();
        int result = container.open("countdown.mkv", IContainer.Type.READ, null);
        duration = container.getDuration()/1000000;
        long fileSize = container.getFileSize();
//        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
        fps = video.getFPS();
//        duration = frames/fps;
        height = video.getHeight();
        width = video.getWidth();
        frames = (long)fps*duration;
        System.out.println("Total Frames: " + frames + "\nFPS: " + fps + "\nDuration: " + duration + " sec");
        System.out.println("Resolution: " + width + "*" + height + "\nFile Size: " + fileSize + " bytes");
        int j=0;
        for (MBFImage mbfImage : video) {
            BufferedImage bufferedFrame = ImageUtilities.createBufferedImageForDisplay(mbfImage);
            j++;
            String name = "output/frames/new" + j + ".jpg";
            File outputFile = new File(name);
            try {

                ImageIO.write(bufferedFrame, "jpg", outputFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
            MBFImage b = mbfImage.clone();
            imageList.add(b);
            timeStamp.add(video.getTimeStamp());
        }
    }

    public static void MainFrames(){
        for (int i=0; i<imageList.size() - 1; i++)
        {
            MBFImage image1 = imageList.get(i);
            MBFImage image2 = imageList.get(i+1);
            DoGSIFTEngine engine = new DoGSIFTEngine();
            LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(image1.flatten());
            LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(image2.flatten());
            RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,
                    new RANSAC.PercentageInliersStoppingCondition(0.5));
            LocalFeatureMatcher<Keypoint> matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
                    new FastBasicKeypointMatcher<Keypoint>(8), modelFitter);
            matcher.setModelFeatures(queryKeypoints);
            matcher.findMatches(targetKeypoints);
            double size = matcher.getMatches().size();
            mainPoints.add(size);
            System.out.println(size);
        }
        Double max = Collections.max(mainPoints);
        for(int i=0; i<mainPoints.size(); i++){
            if(((mainPoints.get(i))/max < 0.5) || i==0){
                Double name1 = mainPoints.get(i)/max;
                BufferedImage bufferedFrame = ImageUtilities.createBufferedImageForDisplay(imageList.get(i+1));
                String name = "output/mainframes/" + i + "_" + name1.toString() + ".jpg";
                File outputFile = new File(name);
                mainframesList.add(name);
                try {
                    ImageIO.write(bufferedFrame, "jpg", outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Main frames names: ");
        for(String mainFrame: mainframesList) {
            System.out.println(mainFrame);
        }
    }

    public static String EncodeVideo(String file){
        String encodedString = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            // TODO: handle exception
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        encodedString = Base64.encodeToString(bytes, true);
        return encodedString;
    }

    public static void main(String[] args) {
        String path = "countdown.mkv";
        String topic = "priya"; //args[0];  //Topic Name
        Frames(path);
        MainFrames();
//        File folder = new File("output/mainframes");
//        File[] listOfFiles = folder.listFiles();
        new SimpleProducer(); //Setting properties for kafka producer
       for(String mainFrame: mainframesList) {
           String msg = EncodeVideo(mainFrame); //Encoding the Video
           // String msg = EncodeVideo(path); //Encoding the Video
//            Iterable<String> result = Splitter.fixedLength(100000).split(msg); //Splitting the video file
//            String[] parts = Iterables.toArray(result, String.class); //Parts of video file
//            System.out.println("Priya"+ parts.length);
        //for(int i=0; i<parts.length; i++){
                ProducerRecord<Integer, String> data = new ProducerRecord<Integer, String>(topic, msg);
//                System.out.println(parts[i]);
                producer.send(data);
           // }
//            break;
       }

        System.out.println("Key Frames Sent");
        producer.close();
    }
}


