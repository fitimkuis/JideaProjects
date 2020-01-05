import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import groovy.json.JsonSlurper;

public class FileCounter {

    public static String getEmailAddress(){

        JSONObject obj = new JSONObject();

        File counterFile = new File(System.getProperty("user.dir") + "/src/main/DataFiles/mailCounter.json");
        if(!counterFile.exists()) {
            System.out.println("Not able to find config file for email generator.");
        }
        String pattern = "timo+test%d@(companyname).com";

        //read
        JsonSlurper slurper = new JsonSlurper();
        Map parsedJson = (Map) slurper.parse(counterFile);
        int getNumber = (int) parsedJson.get("count");
        String result = String.format(pattern, getNumber);

        //update
        obj.put("count", ++getNumber);
        try {
            FileWriter file = new FileWriter(counterFile);
            file.write(obj.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        // do something here...
        String res = FileCounter.getEmailAddress();
        System.out.println(res);
    }
}
