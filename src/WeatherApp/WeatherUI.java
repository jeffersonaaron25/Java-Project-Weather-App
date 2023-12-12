package WeatherApp;

import javax.swing.*;
import java.awt.*;

public class WeatherUI {
    public static void main(String[] args) {
        // Set the look and feel to the system's default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the frame
        JFrame frame = new JFrame("Weather UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create the label components
        JLabel locationLabel = new JLabel("New York City", SwingConstants.CENTER);
        JLabel temperatureLabel = new JLabel("10°", SwingConstants.CENTER);
        JLabel conditionLabel = new JLabel("Cloudy", SwingConstants.CENTER);
        JLabel highLowLabel = new JLabel("H: 10° L: 3⁰", SwingConstants.CENTER);
        JLabel feelsLikeLabel = new JLabel("Feels Like 8 °", SwingConstants.CENTER);
        JLabel windLabel = new JLabel("Wind 20 MPH", SwingConstants.CENTER);

        // Create the separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

        // Add components to the main panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(locationLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(temperatureLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(conditionLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(highLowLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(feelsLikeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(windLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(separator);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Set the content pane of the frame
        frame.setContentPane(mainPanel);

        // Display the frame
        frame.setVisible(true);
    }
}