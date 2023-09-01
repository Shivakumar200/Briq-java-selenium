package briq;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class APIAssignment {

   public static void main(String[] args) {
       try {
        String folderPath = "./HOME/briq";

       // Check if the folder exists, and create it if it doesn't
       File folder = new File(folderPath);
       if (!folder.exists()) {
           folder.mkdirs(); // Creates the folder and any parent folders if needed
       }
       
           // Step 1: Fetch data from the URL
           String apiUrl = "https://data.sfgov.org/resource/p4e4-a5a7.json";
           HttpClient httpClient = HttpClients.createDefault();
           HttpGet httpGet = new HttpGet(apiUrl);
           HttpResponse response = httpClient.execute(httpGet);
           String jsonData = EntityUtils.toString(response.getEntity());

           // Step 2: Parse JSON data
           JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

           // Step 3: Process data and create JSON and CSV files
           SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy-HH-mm-ss");
           String timestamp = sdf.format(new Date());
           String jsonFileName = "sfgov_" + timestamp + ".json";
           String csvFileName = "sfgov_" + timestamp + ".csv";

           FileWriter jsonFileWriter = new FileWriter(jsonFileName);
           FileWriter csvFileWriter = new FileWriter("./HOME/briq" + csvFileName);

           // CSV header
           csvFileWriter.write("description,is_roof\n");

           for (int i = 0; i < jsonArray.size(); i++) {
               JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
               String description = jsonObject.get("description").getAsString();
               boolean isRoof = description.toLowerCase().contains("roof");

               // Create a new JSON object with the required format
               JsonObject outputJsonObject = new JsonObject();
               outputJsonObject.addProperty("description", description);
               outputJsonObject.addProperty("is_roof", isRoof);

               // Write JSON object to the JSON file
               jsonFileWriter.write(outputJsonObject.toString() + "\n");

               // Write data to the CSV file
               csvFileWriter.write(description + "," + isRoof + "\n");
           }

           // Close file writers
           jsonFileWriter.close();
           csvFileWriter.close();

           System.out.println("Data exported successfully.");

       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}