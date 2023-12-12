package WeatherApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LocationValidator {

    public static boolean isValidLocation(String location) {
    	try {
            // Construct the URL for the OpenCage Geocoding API request
            String encodedLocation = URLEncoder.encode(location, "UTF-8");
            String apiUrl = String.format("%s?q=%s&key=%s","https://api.opencagedata.com/geocode/v1/json", encodedLocation, "3f8f616066cf432797a3c695345a0cd5");
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Check if the response code indicates success
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Parse the JSON response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                // Extract results from the OpenCage Geocoding API response
                JsonObject jsonResponse = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();
                int totalResults = jsonResponse.get("total_results").getAsInt();
                if (totalResults > 0) {
                    JsonArray results = jsonResponse.getAsJsonArray("results");
                    JsonObject firstResult = results.get(0).getAsJsonObject();
                    String _type = firstResult.getAsJsonObject("components").get("_type").getAsString();
                                        
                    if(_type.equals("neighbourhood") || _type.equals("city") || _type.equals("state") || _type.equals("country") || _type.equals("region") || _type.equals("island") || _type.equals("village") || _type.equals("postal_city") || _type.equals("state_district")) {
                    	return true;
                    }
                    
                    return false;
                }
            } else {
                System.out.println("Error: " + connection.getResponseCode() + " - " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Default to false if there's an error
        return false;
    }
}
