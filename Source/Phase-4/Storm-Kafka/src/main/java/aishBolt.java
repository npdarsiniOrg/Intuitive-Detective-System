import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class aishBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(aishBolt.class);
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String s = tuple.getString(0);
            String features = s;

            double[] feature = fromString(s);
            Boolean check = checkaish(feature);
            insertIntoMongoDB(check);
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
            jsonObject.put("Context", "aish");
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

    public Boolean checkaish(double[] feature) {
        

  if (feature[122] <= -109.0) 
   if (feature[101] <= -67.0) 
    if (feature[76] <= -30.0) 
     if (feature[82] <= -2.0) 
      if (feature[125] <= -104.0) 
       if (feature[55] <= -120.0) 
        if (feature[124] <= -93.0) 
         if (feature[72] <= -46.0) 
          if (feature[44] <= -122.0) 
           if (feature[97] <= -98.0) 
            return false;
           else if (feature[97] > -98.0) 
            return false;
          else if (feature[44] > -122.0) 
           if (feature[5] <= -54.0) 
            return false;
           else if (feature[5] > -54.0) 
            return false;
         else if (feature[72] > -46.0) 
          if (feature[85] <= -48.0) 
           if (feature[42] <= -23.0) 
            return false;
           else if (feature[42] > -23.0) 
            return false;
          else if (feature[85] > -48.0) 
           return false;
        else if (feature[124] > -93.0) 
         if (feature[68] <= -119.0) 
          if (feature[5] <= -121.0) 
           return false;
          else if (feature[5] > -121.0) 
           return false;
         else if (feature[68] > -119.0) 
          return false;
       else if (feature[55] > -120.0) 
        if (feature[71] <= -62.0) 
         if (feature[100] <= -104.0) 
          if (feature[39] <= -128.0) 
           if (feature[68] <= -116.0) 
            return false;
           else if (feature[68] > -116.0) 
            return false;
          else if (feature[39] > -128.0) 
           if (feature[57] <= -122.0) 
            return false;
           else if (feature[57] > -122.0) 
            return false;
         else if (feature[100] > -104.0) 
          if (feature[48] <= 59.0) 
           if (feature[94] <= -128.0) 
            return false;
           else if (feature[94] > -128.0) 
            return false;
          else if (feature[48] > 59.0) 
           if (feature[4] <= -127.0) 
            return false;
           else if (feature[4] > -127.0) 
            return false;
        else if (feature[71] > -62.0) 
         if (feature[14] <= -66.0) 
          if (feature[1] <= -57.0) 
           if (feature[42] <= -64.0) 
            return false;
           else if (feature[42] > -64.0) 
            return false;
          else if (feature[1] > -57.0) 
           return false;
         else if (feature[14] > -66.0) 
          return false;
      else if (feature[125] > -104.0) 
       if (feature[104] <= 8.0) 
        if (feature[59] <= -8.0) 
         if (feature[60] <= -113.0) 
          if (feature[6] <= -59.0) 
           if (feature[19] <= -36.0) 
            return false;
           else if (feature[19] > -36.0) 
            return false;
          else if (feature[6] > -59.0) 
           return false;
         else if (feature[60] > -113.0) 
          if (feature[3] <= -86.0) 
           if (feature[127] <= -101.0) 
            return false;
           else if (feature[127] > -101.0) 
            return false;
          else if (feature[3] > -86.0) 
           if (feature[17] <= -41.0) 
            return false;
           else if (feature[17] > -41.0) 
            return false;
        else if (feature[59] > -8.0) 
         if (feature[3] <= -124.0) 
          return false;
         else if (feature[3] > -124.0) 
          return false;
       else if (feature[104] > 8.0) 
        if (feature[45] <= -128.0) 
         return false;
        else if (feature[45] > -128.0) 
         if (feature[2] <= -127.0) 
          return false;
         else if (feature[2] > -127.0) 
          if (feature[0] <= -115.0) 
           return false;
          else if (feature[0] > -115.0) 
           return false;
     else if (feature[82] > -2.0) 
      if (feature[27] <= -120.0) 
       if (feature[58] <= -90.0) 
        if (feature[8] <= 25.0) 
         if (feature[15] <= -28.0) 
          return false;
         else if (feature[15] > -28.0) 
          return false;
        else if (feature[8] > 25.0) 
         return false;
       else if (feature[58] > -90.0) 
        return false;
      else if (feature[27] > -120.0) 
       return false;
    else if (feature[76] > -30.0) 
     if (feature[38] <= -102.0) 
      if (feature[123] <= -117.0) 
       if (feature[74] <= -109.0) 
        if (feature[30] <= -108.0) 
         if (feature[80] <= -21.0) 
          if (feature[0] <= -128.0) 
           return false;
          else if (feature[0] > -128.0) 
           return false;
         else if (feature[80] > -21.0) 
          return false;
        else if (feature[30] > -108.0) 
         return false;
       else if (feature[74] > -109.0) 
        return false;
      else if (feature[123] > -117.0) 
       if (feature[8] <= -124.0) 
        return false;
       else if (feature[8] > -124.0) 
        return false;
     else if (feature[38] > -102.0) 
      if (feature[50] <= -128.0) 
       if (feature[77] <= -90.0) 
        return false;
       else if (feature[77] > -90.0) 
        if (feature[5] <= -108.0) 
         return false;
        else if (feature[5] > -108.0) 
         return false;
      else if (feature[50] > -128.0) 
       return false;
   else if (feature[101] > -67.0) 
    if (feature[55] <= -125.0) 
     if (feature[2] <= -87.0) 
      return false;
     else if (feature[2] > -87.0) 
      return false;
    else if (feature[55] > -125.0) 
     if (feature[85] <= -127.0) 
      if (feature[42] <= -110.0) 
       if (feature[11] <= -125.0) 
        return false;
       else if (feature[11] > -125.0) 
        return false;
      else if (feature[42] > -110.0) 
       if (feature[6] <= -123.0) 
        return false;
       else if (feature[6] > -123.0) 
        return false;
     else if (feature[85] > -127.0) 
      if (feature[55] <= -47.0) 
       if (feature[100] <= -128.0) 
        if (feature[0] <= -101.0) 
         return false;
        else if (feature[0] > -101.0) 
         return false;
       else if (feature[100] > -128.0) 
        if (feature[102] <= -125.0) 
         if (feature[1] <= -118.0) 
          return false;
         else if (feature[1] > -118.0) 
          return false;
        else if (feature[102] > -125.0) 
         if (feature[99] <= -103.0) 
          if (feature[36] <= -10.0) 
           return false;
          else if (feature[36] > -10.0) 
           if (feature[1] <= -128.0) 
            return false;
           else if (feature[1] > -128.0) 
            return false;
         else if (feature[99] > -103.0) 
          if (feature[17] <= -128.0) 
           return false;
          else if (feature[17] > -128.0) 
           return false;
      else if (feature[55] > -47.0) 
       if (feature[0] <= -121.0) 
        return false;
       else if (feature[0] > -121.0) 
        return false;
  else if (feature[122] > -109.0) 
   if (feature[6] <= -59.0) 
    if (feature[72] <= 19.0) 
     if (feature[103] <= -19.0) 
      if (feature[15] <= -9.0) 
       if (feature[87] <= -113.0) 
        if (feature[115] <= -127.0) 
         if (feature[121] <= -67.0) 
          if (feature[3] <= -115.0) 
           return false;
          else if (feature[3] > -115.0) 
           return false;
         else if (feature[121] > -67.0) 
          return false;
        else if (feature[115] > -127.0) 
         if (feature[71] <= -62.0) 
          if (feature[17] <= -127.0) 
           if (feature[120] <= -117.0) 
            return false;
           else if (feature[120] > -117.0) 
            return false;
          else if (feature[17] > -127.0) 
           if (feature[114] <= -127.0) 
            return false;
           else if (feature[114] > -127.0) 
            return false;
         else if (feature[71] > -62.0) 
          if (feature[2] <= -79.0) 
           if (feature[8] <= -113.0) 
            return false;
           else if (feature[8] > -113.0) 
            return false;
          else if (feature[2] > -79.0) 
           return false;
       else if (feature[87] > -113.0) 
        if (feature[9] <= 3.0) 
         if (feature[47] <= 6.0) 
          if (feature[59] <= -35.0) 
           if (feature[41] <= -123.0) 
            return false;
           else if (feature[41] > -123.0) 
            return false;
          else if (feature[59] > -35.0) 
           if (feature[42] <= -23.0) 
            return false;
           else if (feature[42] > -23.0) 
            return false;
         else if (feature[47] > 6.0) 
          return false;
        else if (feature[9] > 3.0) 
         return false;
      else if (feature[15] > -9.0) 
       if (feature[0] <= -128.0) 
        return false;
       else if (feature[0] > -128.0) 
        return false;
     else if (feature[103] > -19.0) 
      if (feature[0] <= -126.0) 
       return false;
      else if (feature[0] > -126.0) 
       return false;
    else if (feature[72] > 19.0) 
     if (feature[19] <= -127.0) 
      if (feature[33] <= -73.0) 
       return false;
      else if (feature[33] > -73.0) 
       return false;
     else if (feature[19] > -127.0) 
      if (feature[3] <= -128.0) 
       return false;
      else if (feature[3] > -128.0) 
       return false;
   else if (feature[6] > -59.0) 
    if (feature[8] <= -127.0) 
     return false;
    else if (feature[8] > -127.0) 
     return false;
    return false;
    }
}
