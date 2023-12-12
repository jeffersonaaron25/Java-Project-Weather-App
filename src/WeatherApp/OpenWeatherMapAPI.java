package WeatherApp;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class OpenWeatherMapAPI {

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherData fetchWeatherData(Double lat, Double lon, String unit, String location) {
        try {
            // Construct the URL for the API request
            URI uri = new URI(API_URL + "?lat=" + lat + "&lon=" + lon + "&units=" + unit + "&appid=" + API_KEY);

            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create an HttpRequest
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

            // Send the request and receive the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response code indicates success
            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                // Parse the JSON response
                String responseBody = response.body();
                WeatherData weatherData = parseJson(responseBody, unit, location);

                if (weatherData != null) {
                    weatherData.setTimestamp(System.currentTimeMillis());
                    return weatherData;
                }

            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private WeatherData parseJson(String json, String unit, String location) {
        try {
            // Parse the JSON response
        	Gson gson = new Gson();
        	JsonObject jsonResponse = gson.fromJson(json, JsonObject.class);
        	String iconCode = jsonResponse.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("icon").getAsString();
        	double temperature = jsonResponse.getAsJsonObject("main").get("temp").getAsDouble();
            String description = jsonResponse.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("description").getAsString();
            String header = jsonResponse.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("main").getAsString();
            double feelsLike = jsonResponse.getAsJsonObject("main").get("feels_like").getAsDouble();
            double temperatureMin = jsonResponse.getAsJsonObject("main").get("temp_min").getAsDouble();
            double temperatureMax = jsonResponse.getAsJsonObject("main").get("temp_max").getAsDouble();
            Integer humidity = jsonResponse.getAsJsonObject("main").get("humidity").getAsInt();
            double visibility = jsonResponse.get("visibility").getAsDouble();
            double windSpeed = jsonResponse.getAsJsonObject("wind").get("speed").getAsDouble();
            
            return new WeatherData(iconCode, location, temperature, description, header, feelsLike, temperatureMin, temperatureMax, visibility, humidity, windSpeed, unit);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
