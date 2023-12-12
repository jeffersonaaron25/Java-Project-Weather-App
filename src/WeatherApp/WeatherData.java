package WeatherApp;

public class WeatherData {

    private String iconCode;
    private String location;
    private double temperature;
    private String description;
    private String iconPath;
	private String header;
	private double temperatureMin;
	private double temperatureMax;
	private double visibility;
	private Integer humidity;
	private double windSpeed;
	private String unit;
	private double feelsLike;
	private long timestamp;

    public WeatherData(String iconCode, String location, double temperature, String description, String header, double feelsLike, double temperatureMin, double temperatureMax, double visibility, Integer humidity, double windSpeed, String unit) {
        this.iconCode = iconCode;
        this.location = location;
        this.temperature = temperature;
        this.description = description;
        this.header = header;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.humidity = humidity;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.unit = unit;
        this.feelsLike = feelsLike;
    }

    public String getIconCode() {
        return iconCode;
    }

    public String getUnit() {
        return unit;
    }
    
    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and Setter for Timestamp
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    
    public String getHeader() {
        return header;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public double getVisibility() {
        return visibility;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getFormattedTemperature() {
        return String.format("%d°", (int) temperature);
    }

    public String getFormattedFeelsLike() {
        return String.format("Feels Like %d°", (int) feelsLike);
    }
    
    public String getFormattedTemperatureRange() {
        return String.format("L:%d° H:%d°", (int) temperatureMin, (int) temperatureMax);
    }
    
    public String getFormattedVisibility() {
    	if (visibility < 50) {
            return "Dense fog";
        } else if (visibility >= 50 && visibility < 200) {
            return "Thick fog";
        } else if (visibility >= 200 && visibility < 500) {
            return "Moderate fog";
        } else if (visibility >= 500 && visibility < 1000) {
            return "Light fog";
        } else if (visibility >= 1000 && visibility < 2000) {
            return "Thin fog";
        } else if (visibility >= 2000 && visibility < 4000) {
            return "Haze";
        } else if (visibility >= 4000 && visibility < 10000) {
            return "Light haze";
        } else if (visibility >= 10000 && visibility < 20000) {
            return "Clear";
        } else if (visibility >= 20000 && visibility < 50000) {
            return "Very clear";
        } else {
            return "Exceptionally clear";
        }
    }

    public String getFormattedWindSpeed() {
    	if(unit.equals("metric"))
    		return String.format("%d m/s", (int) windSpeed);
    	else
    		return String.format("%d MPH", (int) windSpeed);
    }
    
    public String getFormattedHumidity() {
        return String.format("%d%%", humidity);
    }

}
