import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
//import org.openimaj.stream.functions.ImageFromURL;
//import org.openimaj.stream.functions.ImageSiteURLExtractor;
//import org.openimaj.stream.functions.twitter.TwitterURLExtractor;
//import org.openimaj.stream.provider.twitter.TwitterStreamDataset;
//import org.openimaj.util.api.auth.DefaultTokenFactory;
import org.openimaj.util.api.auth.common.TwitterAPIToken;
import org.openimaj.util.function.MultiFunction;
import org.openimaj.util.function.Operation;
import org.openimaj.util.stream.Stream;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.xuggle.XuggleVideo;
import java.io.File;

import static java.lang.Thread.sleep;

/**
 * Created by harsha on 11/17/16.
 */
public class FeatureExtraction {

    /**
     * Main method
     *
     * @param args
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		/*
		 * Construct a twitter stream with an
		 */
//        final TwitterAPIToken token = DefaultTokenFactory.get(TwitterAPIToken.class);
//        Stream<MBFImage> vidimg =
//        final Stream<Status> stream = new TwitterStreamDataset(token);
//
//        // Get the URLs
//        final Stream<URL> urlStream = stream.map(new TwitterURLExtractor());
//
//        // Transform/filter to get potential image URLs
//        final Stream<URL> imageUrlStream = urlStream.map(new ImageSiteURLExtractor(false));
//        final Stream<MBFImage> vid = new File("data/ATM_converted.mkv");
//
//        // Get images
////        final Stream<MBFImage> imageStream = imageUrlStream.map(ImageFromURL.MBFIMAGE_EXTRACTOR);
//        Video<MBFImage> video = new XuggleVideo("data/ATM_converted.mkv");
//        imageStream.map(new MultiFunction<MBFImage, MBFImage>() {
//            HaarCascadeDetector detector = HaarCascadeDetector.BuiltInCascade.frontalface_default.load();
//
//            @Override
//            public List<MBFImage> apply(MBFImage in) {
//                final List<DetectedFace> detected = detector.detectFaces(in.flatten());
//
//                final List<MBFImage> faces = new ArrayList<MBFImage>();
//                for (final DetectedFace face : detected)
//                {
//                    faces.add(in.extractROI(face.getBounds()));
//                }
//
//                return faces;
//            }
//        }).forEach(new Operation<MBFImage>() {
//            @Override
//            public void perform(MBFImage image) {
//                DisplayUtilities.displayName(image, "image", true);
//            }
//        });s
        final VideoCapture video = new VideoCapture(320, 240);
//        Video<MBFImage> video = new XuggleVideo("data/ATM_converted.mkv");
        final VideoDisplay<MBFImage> vd = VideoDisplay.createVideoDisplay(video);
        vd.addVideoListener(
                new VideoDisplayListener<MBFImage>() {
                    @Override
                    public void beforeUpdate(MBFImage frame) {
                        HaarCascadeDetector detector = HaarCascadeDetector.BuiltInCascade.frontalface_alt2.load();
                        final List<DetectedFace> detected = detector.detectFaces(frame.flatten());
                        final List<MBFImage> mfaces = new ArrayList<MBFImage>();
                        for (final DetectedFace face : detected)
                        {
                            mfaces.add(frame.extractROI(face.getBounds()));
                        }
                        int i=0;
                        for (final MBFImage face : mfaces) {

                            BufferedImage bImage = ImageUtilities.createBufferedImage(face);
                            File f = new File("data/" + i + ".png");
                            try {
                                ImageIO.write(bImage, "png", f);
                                i++;
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            DisplayUtilities.displayName(face, "image", true);
//                            try {
//                                sleep(1000);
//                            }
//                            catch (Exception e) {
//                                e.printStackTrace();
//                            }

                        }
                        final FaceDetector<DetectedFace, FImage> fd = new HaarCascadeDetector(40);
                        final List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(frame));

                        for (final DetectedFace face : faces) {
                            frame.drawShape(face.getBounds(), RGBColour.RED);
                        }
                    }

                    @Override
                    public void afterUpdate(VideoDisplay<MBFImage> display) {
                    }
                });
    }
}
