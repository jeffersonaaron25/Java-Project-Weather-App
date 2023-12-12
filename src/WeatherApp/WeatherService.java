package WeatherApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherService {

    private OpenWeatherMapAPI openWeatherMapAPI;
    private Map<String, WeatherData> memoryCache;
  	String GEO_API_KEY = "YOUR_API_KEY";
  	
    public WeatherService() {
        openWeatherMapAPI = new OpenWeatherMapAPI();
        memoryCache = new HashMap<>();
    }

    public WeatherData getWeatherData(String location, String unit) {
    	
    	if (memoryCache.containsKey(location+"_"+unit)) {
            WeatherData cachedData = memoryCache.get(location+"_"+unit);
            if (System.currentTimeMillis() - cachedData.getTimestamp() < 5 * 60 * 1000) {
                if (cachedData.getLocation().equals(location) && cachedData.getUnit().equals(unit)) {
	                System.out.println("Fetching weather data from memory cache for: " + location);
	                return cachedData;
                }
            }
        }
        
        // Convert city name to coordinates
        Coordinates coordinates = getCoordinates(location);
        
        if (coordinates != null) {
            // Fetch weather data using coordinates
        	 WeatherData weatherData = openWeatherMapAPI.fetchWeatherData(coordinates.getLatitude(), coordinates.getLongitude(), unit, location);
        	 
             if (weatherData != null) {
                 weatherData.setIconPath(WeatherIconMapper.getIconPath(weatherData.getIconCode()));
            	 memoryCache.put(location+"_"+unit, weatherData);
             }
             return weatherData;
        } else {
            System.out.println("Failed to retrieve coordinates for the city.");
            return null;
        }
    }
    
    
    private Coordinates getCoordinates(String city) {
        try {
            // Construct the URL for the OpenCage Geocoding API request
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            String apiUrl = String.format("%s?q=%s&key=%s","https://api.opencagedata.com/geocode/v1/json", encodedCity, GEO_API_KEY);
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

                // Extract coordinates from the OpenCage Geocoding API response
                JsonObject jsonResponse = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();
                JsonObject geometry = jsonResponse.getAsJsonArray("results")
                        .get(0).getAsJsonObject().getAsJsonObject("geometry");
                double latitude = geometry.get("lat").getAsDouble();
                double longitude = geometry.get("lng").getAsDouble();

                return new Coordinates(latitude, longitude);
            } else {
                System.out.println("Error: " + connection.getResponseCode() + " - " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private static class Coordinates {
        private double latitude;
        private double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
