package WeatherApp;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SuppressWarnings("serial")
public class WeatherApp extends JFrame {

    private JComboBox<String> unitComboBox;
    private JTextField locationTextField;
    private JButton searchButton;
    private JLabel weatherIconLabel;
    private JLabel temperatureLabel;
    private JLabel descriptionLabel;

    private WeatherService weatherService;
	private JLabel temperatureRangeLabel;
	private JLabel windLabel;
	private JLabel visibilityLabel;
	private JLabel humidityLabel;
	private JLabel locationLabel;
	private JLabel FeelsLikeLabel;
	private JButton locateButton;
	private JLabel suggestionArea;
	private Map<String, String[]> suggestionMap;
    private ExecutorService executorService;


    public WeatherApp() {
        super("Weather App");
        suggestionMap = generateSuggestionsMap();
        weatherService = new WeatherService();
        executorService = Executors.newCachedThreadPool();

        initUI();
    }

    private Map<String, String[]> generateSuggestionsMap() {
        Map<String, String[]> map = new HashMap<>();
        
        map.put("01d", new String[]{"Enjoy the sunny weather!", "Clear skies today.", "Perfect day for outdoor activities."});
        map.put("02d", new String[]{"Partly cloudy day.", "Outdoor activities with some clouds.", "Nice weather with scattered clouds."});
        map.put("03d", new String[]{"Cloudy day ahead.", "Expect overcast skies today.", "Cozy weather for indoor activities."});
        map.put("04d", new String[]{"Cloudy day ahead.", "Expect overcast skies today.", "Cozy weather for indoor activities."});
        map.put("09d", new String[]{"Rainy day! Grab your raincoat.", "A good day to stay indoors.", "Listen to the sound of rain."});
        map.put("10d", new String[]{"Rainy day! Grab your raincoat.", "A good day to stay indoors.", "Listen to the sound of rain."});
        map.put("11d", new String[]{"Thunderstorm expected.", "Stay indoors and stay safe.", "Thunder and lightning today."});
        map.put("13d", new String[]{"Snowfall expected.", "Dress warmly and enjoy the snow.", "Winter wonderland outside."});
        map.put("50d", new String[]{"Foggy conditions.", "Drive carefully in reduced visibility.", "A misty day ahead."});

        map.put("01n", new String[]{"Clear night sky.", "Stargazing weather.", "Enjoy a quiet night."});
        map.put("02n", new String[]{"Partly cloudy night.", "A mix of stars and clouds.", "Peaceful night outdoors."});
        map.put("03n", new String[]{"Cloudy night.", "Expect overcast skies tonight.", "Cozy evening indoors."});
        map.put("04n", new String[]{"Cloudy night.", "Expect overcast skies tonight.", "Cozy evening indoors."});
        map.put("09n", new String[]{"Rainy night! Stay dry.", "A good night for a book indoors.", "Listen to the raindrops."});
        map.put("10n", new String[]{"Rainy night! Stay dry.", "A good night for a book indoors.", "Listen to the raindrops."});
        map.put("11n", new String[]{"Thunderstorm expected tonight.", "Stay indoors and stay safe.", "Thunder and lightning in the night."});
        map.put("13n", new String[]{"Snowfall expected tonight.", "Dress warmly and enjoy the snow.", "Winter wonderland under the moonlight."});
        map.put("50n", new String[]{"Foggy night.", "Drive carefully in reduced visibility.", "A misty night ahead."});

        return map;
    }
    
    private JLabel createStyledLabel(String text, int fontSize, int weight) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Helvetica", weight, fontSize));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add some padding
        return label;
    }
    
    private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    unitComboBox = new JComboBox<>(new String[]{"Fahrenheit","Celsius"});
	    locationTextField = new JTextField("");
	    locationTextField.setColumns(10);
	    searchButton = new JButton("Search");
	    locateButton = new JButton("Locate Me");

	    weatherIconLabel = new JLabel("",SwingConstants.CENTER);
	    
        locationLabel = createStyledLabel("", 12, Font.BOLD);
        temperatureLabel = createStyledLabel("Loading...", 60, Font.PLAIN);
        descriptionLabel = createStyledLabel("", 14, Font.BOLD);
        temperatureRangeLabel = createStyledLabel("", 12, Font.PLAIN);
        FeelsLikeLabel = createStyledLabel(".", 12, Font.PLAIN);
        visibilityLabel = createStyledLabel("Loading...", 18, Font.BOLD);
        humidityLabel = createStyledLabel("Loading...", 18, Font.BOLD);
        windLabel = createStyledLabel("Loading...", 18, Font.BOLD);
        
        suggestionArea = createStyledLabel("", 16, Font.BOLD);

	    setLayout(new BorderLayout());

	    JPanel northPanel = new JPanel();
	    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

	    JPanel northPanel1 = new JPanel();
	    northPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
	    northPanel1.add(locationTextField);
	    northPanel1.add(searchButton);

	    JPanel northPanel2 = new JPanel();
	    northPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    northPanel2.add(locateButton);
	    northPanel2.add(unitComboBox);

	    northPanel.add(northPanel1);
	    northPanel.add(northPanel2);

	    add(northPanel, BorderLayout.NORTH);
	    
	    JPanel westPanel = new JPanel();
	    westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
	    westPanel.setBackground(new Color(48, 46, 46));
	    westPanel.add(Box.createRigidArea(new Dimension(40, -20)));
	    westPanel.add(weatherIconLabel);

	    add(westPanel, BorderLayout.WEST);

	    JPanel eastPanel = new JPanel();
	    eastPanel.setBackground(new Color(48, 46, 46));
	    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(Box.createRigidArea(new Dimension(-20, 40)));
        eastPanel.add(locationLabel);
        eastPanel.add(temperatureLabel);
        eastPanel.add(Box.createRigidArea(new Dimension(0, -10)));
        eastPanel.add(descriptionLabel);
        eastPanel.add(Box.createRigidArea(new Dimension(0, -5)));
        eastPanel.add(temperatureRangeLabel);
        eastPanel.add(Box.createRigidArea(new Dimension(0, -5)));
        eastPanel.add(FeelsLikeLabel);
        eastPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        add(eastPanel, BorderLayout.CENTER);
        
        JPanel southPanelMain = new JPanel();
        southPanelMain.setLayout(new BoxLayout(southPanelMain, BoxLayout.Y_AXIS));
        southPanelMain.setBackground(new Color(48, 46, 46));
        
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setBackground(new Color(48, 46, 46));

        JPanel southPanel1 = new JPanel();
        southPanel1.setLayout(new BoxLayout(southPanel1, BoxLayout.Y_AXIS));
        southPanel1.setBackground(new Color(33, 31, 31));
        southPanel1.setMaximumSize(new Dimension(120,100));
        TitledBorder border1 = BorderFactory.createTitledBorder("Visibility");
        border1.setTitleColor(Color.WHITE);
        southPanel1.setBorder(border1);
        southPanel1.add(Box.createRigidArea(new Dimension(0,20)));
        southPanel1.add(visibilityLabel);

        southPanel.add(southPanel1);
        southPanel.add(Box.createRigidArea(new Dimension(34,0)));
        
        JPanel southPanel2 = new JPanel();
        southPanel2.setLayout(new BoxLayout(southPanel2, BoxLayout.Y_AXIS));
        southPanel2.setBackground(new Color(33, 31, 31));
        southPanel2.setMaximumSize(new Dimension(120,100));
        TitledBorder border2 = BorderFactory.createTitledBorder("Humidity");
        border2.setTitleColor(Color.WHITE);
        southPanel2.setBorder(border2);  
        southPanel2.add(Box.createRigidArea(new Dimension(0,20)));
        southPanel2.add(humidityLabel);


        southPanel.add(southPanel2);
        southPanel.add(Box.createRigidArea(new Dimension(34,0)));
        
        JPanel southPanel3 = new JPanel();
        southPanel3.setLayout(new BoxLayout(southPanel3, BoxLayout.Y_AXIS));
        southPanel3.setMaximumSize(new Dimension(120,100));
        TitledBorder border3 = BorderFactory.createTitledBorder("Wind");
        border3.setTitleColor(Color.WHITE);
        southPanel3.setBorder(border3);
        southPanel3.setBackground(new Color(33, 31, 31));
        southPanel3.add(Box.createRigidArea(new Dimension(0,20)));
        southPanel3.add(windLabel);

        southPanel.add(southPanel3);
        southPanel.add(Box.createRigidArea(new Dimension(0,150)));
        
        southPanelMain.add(suggestionArea);
        southPanelMain.add(southPanel);
        
        add(southPanelMain, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchWeather();
            }
        });
        
        unitComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchWeather();
            }
        });
        
        locateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentLocationWeather();
            }
        });

        pack();
        setSize(500, 450);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getCurrentLocationWeather();

        setVisible(true);
    }

    private void getCurrentLocationWeather() {
        String currentLocation = getCurrentLocation();
        if (currentLocation != null) {
            locationTextField.setText(currentLocation);
            searchWeather();
        } else {
            JOptionPane.showMessageDialog(this, "Unable to determine current location.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getCurrentLocation() {
        try {
            URL url = new URL("http://ipinfo.io/json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                // Parse JSON response with Gson
                JsonObject jsonResponse = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();

                String city = jsonResponse.get("city").getAsString();

                return city;

            } else {
                System.out.println("Error: " + connection.getResponseCode() + " - " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private void searchWeather() {
        String location = locationTextField.getText();
        String unit = unitComboBox.getSelectedItem().toString();
        final String unit_;
        if(unit.equals("Celsius"))
        	unit_ = "metric";
        else
        	unit_ = "imperial";
        
        if(!LocationValidator.isValidLocation(location)) {
            JOptionPane.showMessageDialog(this, "Location not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
        	
        	SwingWorker<WeatherData, Void> worker = new SwingWorker<>() {
                @Override
                protected WeatherData doInBackground() {
                	return weatherService.getWeatherData(location, unit_);
                }

                @Override
                protected void done() {
                    try {
                        WeatherData weatherData = get();
                        if (weatherData != null) {
            	            updateUI(weatherData);
            	        } else {
            	            System.out.println("Error fetching weather data.");
            	        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        	
            executorService.submit(worker);
	        
        }
    }

    private void updateUI(WeatherData weatherData) {
    	ImageIcon imageIcon = new ImageIcon(weatherData.getIconPath());
    	Image image = imageIcon.getImage();
    	Image newimg = image.getScaledInstance(256, 256,  java.awt.Image.SCALE_SMOOTH);
    	imageIcon = new ImageIcon(newimg);
        weatherIconLabel.setIcon(imageIcon);
        locationLabel.setText(locationTextField.getText());
        temperatureLabel.setText(weatherData.getFormattedTemperature());
        temperatureRangeLabel.setText(weatherData.getFormattedTemperatureRange());
        descriptionLabel.setText(weatherData.getDescription().substring(0, 1).toUpperCase() + weatherData.getDescription().substring(1));
        windLabel.setText(weatherData.getFormattedWindSpeed());
        visibilityLabel.setText(weatherData.getFormattedVisibility());
        humidityLabel.setText(weatherData.getFormattedHumidity());
        FeelsLikeLabel.setText(weatherData.getFormattedFeelsLike());

        String weatherCondition = weatherData.getIconCode();
        String[] suggestions = suggestionMap.getOrDefault(weatherCondition, new String[]{"No suggestions available."});

        // Display a random suggestion in the text area
        String randomSuggestion = suggestions[(int) (Math.random() * suggestions.length)];
        SwingUtilities.invokeLater(() -> {
            suggestionArea.setText(weatherData.getHeader() + ": " +randomSuggestion);
        });
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WeatherApp());
    }
}