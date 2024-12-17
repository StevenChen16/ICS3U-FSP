import javax.swing.*;
import java.awt.*;

public class ActivityScreen extends JFrame {
    public ActivityScreen() {
        setTitle("Activity");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Interactive Coding Challenges");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea activityText = new JTextArea();
        activityText.setText("Lap Time Analysis:\n" +
                "Fill an array with lap times and calculate the average lap time.\n\n" +
                "Pit Stop Strategy:\n" +
                "Represent a team's pit stop durations in an array and find the shortest/longest pit stops.\n\n" +
                "Driver Comparison:\n" +
                "Use a 2D array where each row represents a driver, and each column represents a lap time in a race. Calculate the fastest driver for each lap.");
        activityText.setEditable(false);
        activityText.setLineWrap(true);
        activityText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(activityText);
        scrollPane.setPreferredSize(new Dimension(580, 300));

        panel.add(titleLabel);
        panel.add(scrollPane);

        add(panel);
    }

    public void showScreen() {
        setVisible(true);
    }
}
