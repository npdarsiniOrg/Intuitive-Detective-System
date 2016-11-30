import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;

/**
 * Created by harsha on 11/28/16.
 */
public class DecodeData {
    public void decodeData(String encodedString, int k){
        // byte[] decodedBytes = Base64.decodeBase64(encodedString); //.decodeBase64(encodedString.getBytes()) ;  //decodeFast(encodedString.getBytes());
        byte [] decodedBytes = Base64.decodeBase64(encodedString);
//        System.out.println(decodedBytes);
        try {
            FileOutputStream out = new FileOutputStream("data/train/features.txt");
            out.write(decodedBytes);
            out.close();
            System.out.println("Created File");
        } catch (Exception e) {
            // TODO: handle exception
//            Log.e("Error", e.toString());
        }
    }
}
