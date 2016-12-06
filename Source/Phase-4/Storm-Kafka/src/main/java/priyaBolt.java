import org.apache.commons.io.FileUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class priyaBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(priyaBolt.class);
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String s = tuple.getString(0);
            String features = s;

            double[] feature = fromString(s);
            Boolean check = checkpriya(feature);
if(check == true) {
            insertIntoMongoDB(check);

    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
    Calendar cal = Calendar.getInstance();
//            System.out.println(dateFormat.format(cal)); //2016/11/16 12:08:43
    File alertFile = new File("/media/harsha/64B41DEAB41DBF8A/RTB-Project/Source/Phase-4/AlertMe-Server/public/alerts.txt");
    String msg = "Suspect Priya identified at " + dateFormat.format(cal);
    FileUtils.writeStringToFile(alertFile, msg, "UTF-8", true);
}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("context","status"));
    }

    private static double[] fromString(String string) {
        String[] strings = string.split(" ");
        double result[] = new double[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(strings[i]);
        }
        return result;
    }

    public static void insertIntoMongoDB(Boolean check) {
        String API_KEY = "-Q-ydWDJ6ULbB2_e5yLZaloAOMkh9lax";
        String DATABASE_NAME = "db_team6";
        String COLLECTION_NAME = "c_team6_predictions";
        String urlString = "https://api.mlab.com/api/1/databases/" +
                DATABASE_NAME + "/collections/" + COLLECTION_NAME + "?apiKey=" + API_KEY;
        LOG.info(urlString);

        StringBuilder result = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Context", "priya");
            jsonObject.put("Decision", check);
            jsonObject.put("Timestamp", System.currentTimeMillis());
            writer.write(jsonObject.toString());
            LOG.info(jsonObject.toString());
            writer.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Uploaded data to Mongo");

    }

    public Boolean checkpriya(double[] feature) {
        

  if (feature[122] <= -85.0) 
   if (feature[86] <= -127.0) 
    if (feature[42] <= -111.0) 
     if (feature[55] <= -49.0) 
      if (feature[87] <= -7.0) 
       if (feature[85] <= -74.0) 
        if (feature[37] <= -125.0) 
         if (feature[1] <= -120.0) 
          if (feature[93] <= -97.0) 
           if (feature[17] <= -78.0) 
            return true;
           else if (feature[17] > -78.0) 
            return false;
          else if (feature[93] > -97.0) 
           if (feature[16] <= -33.0) 
            return false;
           else if (feature[16] > -33.0) 
            return false;
         else if (feature[1] > -120.0) 
          if (feature[65] <= -118.0) 
           if (feature[127] <= -126.0) 
            return true;
           else if (feature[127] > -126.0) 
            return false;
          else if (feature[65] > -118.0) 
           if (feature[108] <= -77.0) 
            return false;
           else if (feature[108] > -77.0) 
            return false;
        else if (feature[37] > -125.0) 
         if (feature[20] <= -100.0) 
          if (feature[80] <= 56.0) 
           if (feature[8] <= -107.0) 
            return false;
           else if (feature[8] > -107.0) 
            return true;
          else if (feature[80] > 56.0) 
           if (feature[100] <= -118.0) 
            return true;
           else if (feature[100] > -118.0) 
            return false;
         else if (feature[20] > -100.0) 
          return false;
       else if (feature[85] > -74.0) 
        if (feature[1] <= -126.0) 
         return false;
        else if (feature[1] > -126.0) 
         return false;
      else if (feature[87] > -7.0) 
       if (feature[40] <= 33.0) 
        return false;
       else if (feature[40] > 33.0) 
        return true;
     else if (feature[55] > -49.0) 
      if (feature[113] <= -82.0) 
       if (feature[81] <= -78.0) 
        return true;
       else if (feature[81] > -78.0) 
        if (feature[6] <= -127.0) 
         if (feature[108] <= -128.0) 
          return false;
         else if (feature[108] > -128.0) 
          if (feature[1] <= -105.0) 
           return false;
          else if (feature[1] > -105.0) 
           return true;
        else if (feature[6] > -127.0) 
         if (feature[3] <= -127.0) 
          return true;
         else if (feature[3] > -127.0) 
          return false;
      else if (feature[113] > -82.0) 
       if (feature[79] <= -125.0) 
        if (feature[0] <= -128.0) 
         if (feature[12] <= -122.0) 
          return false;
         else if (feature[12] > -122.0) 
          return true;
        else if (feature[0] > -128.0) 
         return false;
       else if (feature[79] > -125.0) 
        if (feature[53] <= -128.0) 
         return true;
        else if (feature[53] > -128.0) 
         return false;
    else if (feature[42] > -111.0) 
     if (feature[44] <= -118.0) 
      if (feature[100] <= -84.0) 
       if (feature[12] <= -73.0) 
        if (feature[102] <= -28.0) 
         return false;
        else if (feature[102] > -28.0) 
         return true;
       else if (feature[12] > -73.0) 
        return false;
      else if (feature[100] > -84.0) 
       if (feature[1] <= -127.0) 
        return false;
       else if (feature[1] > -127.0) 
        return true;
     else if (feature[44] > -118.0) 
      if (feature[108] <= -111.0) 
       if (feature[76] <= -115.0) 
        if (feature[24] <= -66.0) 
         return true;
        else if (feature[24] > -66.0) 
         return false;
       else if (feature[76] > -115.0) 
        if (feature[84] <= -124.0) 
         if (feature[0] <= -128.0) 
          return true;
         else if (feature[0] > -128.0) 
          return false;
        else if (feature[84] > -124.0) 
         return false;
      else if (feature[108] > -111.0) 
       if (feature[111] <= -122.0) 
        return false;
       else if (feature[111] > -122.0) 
        if (feature[8] <= -103.0) 
         return false;
        else if (feature[8] > -103.0) 
         return true;
   else if (feature[86] > -127.0) 
    if (feature[61] <= -113.0) 
     if (feature[44] <= -11.0) 
      if (feature[26] <= -34.0) 
       if (feature[117] <= -108.0) 
        if (feature[110] <= -8.0) 
         if (feature[45] <= -126.0) 
          if (feature[71] <= -120.0) 
           if (feature[94] <= -128.0) 
            return false;
           else if (feature[94] > -128.0) 
            return false;
          else if (feature[71] > -120.0) 
           if (feature[33] <= -101.0) 
            return false;
           else if (feature[33] > -101.0) 
            return true;
         else if (feature[45] > -126.0) 
          if (feature[9] <= -128.0) 
           if (feature[50] <= -126.0) 
            return true;
           else if (feature[50] > -126.0) 
            return false;
          else if (feature[9] > -128.0) 
           if (feature[40] <= -94.0) 
            return true;
           else if (feature[40] > -94.0) 
            return false;
        else if (feature[110] > -8.0) 
         if (feature[0] <= -51.0) 
          return true;
         else if (feature[0] > -51.0) 
          return false;
       else if (feature[117] > -108.0) 
        if (feature[68] <= 1.0) 
         if (feature[6] <= -38.0) 
          if (feature[47] <= 1.0) 
           if (feature[85] <= -127.0) 
            return true;
           else if (feature[85] > -127.0) 
            return false;
          else if (feature[47] > 1.0) 
           return true;
         else if (feature[6] > -38.0) 
          return false;
        else if (feature[68] > 1.0) 
         return false;
      else if (feature[26] > -34.0) 
       if (feature[115] <= -117.0) 
        if (feature[25] <= -112.0) 
         if (feature[0] <= -128.0) 
          return true;
         else if (feature[0] > -128.0) 
          return false;
        else if (feature[25] > -112.0) 
         return false;
       else if (feature[115] > -117.0) 
        return true;
     else if (feature[44] > -11.0) 
      if (feature[68] <= -69.0) 
       if (feature[29] <= -123.0) 
        return false;
       else if (feature[29] > -123.0) 
        if (feature[1] <= -128.0) 
         return true;
        else if (feature[1] > -128.0) 
         return false;
      else if (feature[68] > -69.0) 
       return false;
    else if (feature[61] > -113.0) 
     if (feature[71] <= -121.0) 
      if (feature[72] <= 7.0) 
       if (feature[15] <= -127.0) 
        if (feature[6] <= -78.0) 
         if (feature[33] <= -101.0) 
          if (feature[48] <= 7.0) 
           if (feature[0] <= -127.0) 
            return true;
           else if (feature[0] > -127.0) 
            return false;
          else if (feature[48] > 7.0) 
           if (feature[9] <= -117.0) 
            return false;
           else if (feature[9] > -117.0) 
            return true;
         else if (feature[33] > -101.0) 
          return false;
        else if (feature[6] > -78.0) 
         return false;
       else if (feature[15] > -127.0) 
        if (feature[97] <= -72.0) 
         if (feature[88] <= -36.0) 
          if (feature[61] <= -25.0) 
           if (feature[36] <= -5.0) 
            return false;
           else if (feature[36] > -5.0) 
            return false;
          else if (feature[61] > -25.0) 
           if (feature[121] <= -128.0) 
            return false;
           else if (feature[121] > -128.0) 
            return true;
         else if (feature[88] > -36.0) 
          if (feature[13] <= -128.0) 
           return false;
          else if (feature[13] > -128.0) 
           return true;
        else if (feature[97] > -72.0) 
         if (feature[0] <= -116.0) 
          return true;
         else if (feature[0] > -116.0) 
          return false;
      else if (feature[72] > 7.0) 
       if (feature[113] <= -102.0) 
        if (feature[86] <= -101.0) 
         return false;
        else if (feature[86] > -101.0) 
         if (feature[0] <= -115.0) 
          return true;
         else if (feature[0] > -115.0) 
          return false;
       else if (feature[113] > -102.0) 
        return false;
     else if (feature[71] > -121.0) 
      if (feature[98] <= -125.0) 
       if (feature[65] <= -125.0) 
        if (feature[54] <= -96.0) 
         if (feature[31] <= -111.0) 
          if (feature[38] <= -121.0) 
           if (feature[0] <= -95.0) 
            return true;
           else if (feature[0] > -95.0) 
            return false;
          else if (feature[38] > -121.0) 
           return false;
         else if (feature[31] > -111.0) 
          return true;
        else if (feature[54] > -96.0) 
         return false;
       else if (feature[65] > -125.0) 
        if (feature[78] <= -125.0) 
         if (feature[10] <= -126.0) 
          if (feature[14] <= -102.0) 
           return false;
          else if (feature[14] > -102.0) 
           return false;
         else if (feature[10] > -126.0) 
          if (feature[1] <= -107.0) 
           return true;
          else if (feature[1] > -107.0) 
           return false;
        else if (feature[78] > -125.0) 
         if (feature[86] <= -32.0) 
          if (feature[71] <= -46.0) 
           if (feature[80] <= -119.0) 
            return false;
           else if (feature[80] > -119.0) 
            return true;
          else if (feature[71] > -46.0) 
           if (feature[0] <= -126.0) 
            return true;
           else if (feature[0] > -126.0) 
            return false;
         else if (feature[86] > -32.0) 
          if (feature[3] <= -127.0) 
           return false;
          else if (feature[3] > -127.0) 
           return true;
      else if (feature[98] > -125.0) 
       if (feature[75] <= -68.0) 
        if (feature[13] <= -74.0) 
         if (feature[70] <= -120.0) 
          if (feature[99] <= -118.0) 
           if (feature[8] <= -59.0) 
            return true;
           else if (feature[8] > -59.0) 
            return false;
          else if (feature[99] > -118.0) 
           return false;
         else if (feature[70] > -120.0) 
          if (feature[54] <= -4.0) 
           if (feature[59] <= -49.0) 
            return false;
           else if (feature[59] > -49.0) 
            return false;
          else if (feature[54] > -4.0) 
           return true;
        else if (feature[13] > -74.0) 
         if (feature[0] <= -110.0) 
          return true;
         else if (feature[0] > -110.0) 
          return false;
       else if (feature[75] > -68.0) 
        return true;
  else if (feature[122] > -85.0) 
   if (feature[30] <= -24.0) 
    if (feature[112] <= 4.0) 
     if (feature[16] <= -55.0) 
      if (feature[6] <= -78.0) 
       if (feature[117] <= -54.0) 
        if (feature[8] <= -4.0) 
         if (feature[8] <= -15.0) 
          if (feature[55] <= -6.0) 
           if (feature[54] <= -11.0) 
            return false;
           else if (feature[54] > -11.0) 
            return false;
          else if (feature[55] > -6.0) 
           if (feature[0] <= -128.0) 
            return true;
           else if (feature[0] > -128.0) 
            return false;
         else if (feature[8] > -15.0) 
          if (feature[2] <= -127.0) 
           return false;
          else if (feature[2] > -127.0) 
           return true;
        else if (feature[8] > -4.0) 
         return true;
       else if (feature[117] > -54.0) 
        if (feature[0] <= -128.0) 
         return false;
        else if (feature[0] > -128.0) 
         return true;
      else if (feature[6] > -78.0) 
       if (feature[30] <= -113.0) 
        return false;
       else if (feature[30] > -113.0) 
        if (feature[0] <= -128.0) 
         return false;
        else if (feature[0] > -128.0) 
         return true;
     else if (feature[16] > -55.0) 
      if (feature[123] <= -120.0) 
       if (feature[18] <= -126.0) 
        return true;
       else if (feature[18] > -126.0) 
        return false;
      else if (feature[123] > -120.0) 
       if (feature[124] <= -97.0) 
        if (feature[6] <= -78.0) 
         if (feature[25] <= -29.0) 
          if (feature[55] <= -125.0) 
           return true;
          else if (feature[55] > -125.0) 
           return false;
         else if (feature[25] > -29.0) 
          return true;
        else if (feature[6] > -78.0) 
         return true;
       else if (feature[124] > -97.0) 
        if (feature[3] <= -128.0) 
         return false;
        else if (feature[3] > -128.0) 
         return true;
    else if (feature[112] > 4.0) 
     if (feature[8] <= -111.0) 
      return false;
     else if (feature[8] > -111.0) 
      return true;
   else if (feature[30] > -24.0) 
    if (feature[0] <= -128.0) 
     return false;
    else if (feature[0] > -128.0) 
     return false;
    return false;
    }
}
