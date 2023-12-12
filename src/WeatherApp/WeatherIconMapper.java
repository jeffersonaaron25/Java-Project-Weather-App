package WeatherApp;

import java.util.HashMap;
import java.util.Map;

public class WeatherIconMapper {

    private static final Map<String, String> iconMap = new HashMap<>();

    static {
        iconMap.put("01d", "images/sunny.png");
        iconMap.put("02d", "images/partlycloudy.png");
        iconMap.put("03d", "images/cloudy.png");
        iconMap.put("04d", "images/cloudy.png");
        iconMap.put("09d", "images/rainy.png");
        iconMap.put("10d", "images/rainy.png");
        iconMap.put("11d", "images/thunderstorm.png");
        iconMap.put("13d", "images/snowy.png");
        iconMap.put("50d", "images/cloudy.png");
        
        iconMap.put("01n", "images/clearnight.png");
        iconMap.put("02n", "images/partlycloudynight.png");
        iconMap.put("03n", "images/cloudy.png");
        iconMap.put("04n", "images/cloudy.png");
        iconMap.put("09n", "images/rainy.png");
        iconMap.put("10n", "images/rainy.png");
        iconMap.put("11n", "images/thunderstorm.png");
        iconMap.put("13n", "images/snowy.png");
        iconMap.put("50n", "images/cloudy.png");
    }

    public static String getIconPath(String iconCode) {
//    	System.out.println(iconCode);
        return iconMap.getOrDefault(iconCode, "images/cloudy.png");
    }
}
