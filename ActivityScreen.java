import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ActivityScreen extends JFrame {
    
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    // components for lap time analysis
    private JTextField[] lapTimeFields;
    private JLabel resultLabel;
    private JTextArea analysisResult;
    
    // components for race position tracker
    private JTextField[] positionFields;
    private JButton updateButton;
    private JLabel positionDisplay;
    
    public ActivityScreen() {
        // basic window setup like other screens
        setTitle("F1 Array Racing - Practice Activities");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // create all components
        createComponents();
        
        // make it visible
        setVisible(true);
    }
    
    private void createComponents() {
        // Set background
        try {
            final Image backgroundImage = ImageIO.read(getClass().getResource("./resources/background1.jpg"));
            setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    
                    // Add semi-transparent overlay
                    g.setColor(new Color(0, 0, 0, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        // make main panel with card layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(33, 33, 33));
        
        // create tabs for different activities
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // add activities to tabs
        tabbedPane.addTab("Lap Time Analysis", createLapTimePanel());
        tabbedPane.addTab("Race Position Tracker", createPositionTrackerPanel());
        
        // add back button at bottom
        JButton backButton = createBackButton();
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createLapTimePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // instruction label
        JLabel instructionLabel = new JLabel(
            "<html>Enter 5 lap times below (in seconds) and click analyze:<br>" +
            "For example: 82.5, 81.8, 81.9, 81.7, 82.0</html>"
        );
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // create input fields for lap times
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 5));
        lapTimeFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            inputPanel.add(new JLabel("Lap " + (i + 1) + ":"));
            lapTimeFields[i] = new JTextField(10);
            inputPanel.add(lapTimeFields[i]);
        }
        inputPanel.setMaximumSize(new Dimension(300, 150));
        
        // analyze button
        JButton analyzeButton = new JButton("Analyze Lap Times");
        analyzeButton.setBackground(new Color(220, 0, 0));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.addActionListener(e -> analyzeLapTimes());
        
        // result area
        analysisResult = new JTextArea(10, 40);
        analysisResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(analysisResult);
        
        // add all components
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(analyzeButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(scrollPane);
        
        return panel;
    }
    
    private JPanel createPositionTrackerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // instruction
        JLabel instructionLabel = new JLabel(
            "<html>Enter position numbers (1-20) for 5 drivers:<br>" +
            "Each number should be unique!</html>"
        );
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // input fields for positions
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 5));
        positionFields = new JTextField[5];
        String[] drivers = {"Hamilton", "Verstappen", "Leclerc", "Norris", "Russell"};
        for (int i = 0; i < 5; i++) {
            inputPanel.add(new JLabel(drivers[i] + ":"));
            positionFields[i] = new JTextField(5);
            inputPanel.add(positionFields[i]);
        }
        inputPanel.setMaximumSize(new Dimension(300, 150));
        
        // update button
        updateButton = new JButton("Update Positions");
        updateButton.setBackground(new Color(220, 0, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(e -> updatePositions());
        
        // display area
        positionDisplay = new JLabel();
        positionDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // add components
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(updateButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(positionDisplay);
        
        return panel;
    }
    
    private JButton createBackButton() {
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBackground(new Color(220, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> returnToMainMenu());
        return backButton;
    }
    
    private void analyzeLapTimes() {
        try {
            // convert input to array
            double[] lapTimes = new double[5];
            for (int i = 0; i < 5; i++) {
                lapTimes[i] = Double.parseDouble(lapTimeFields[i].getText().trim());
            }
            
            // do analysis
            double fastestLap = lapTimes[0];
            double slowestLap = lapTimes[0];
            double totalTime = lapTimes[0];
            int fastestLapNumber = 1;
            
            // find fastest and slowest laps
            for (int i = 1; i < lapTimes.length; i++) {
                if (lapTimes[i] < fastestLap) {
                    fastestLap = lapTimes[i];
                    fastestLapNumber = i + 1;
                }
                if (lapTimes[i] > slowestLap) {
                    slowestLap = lapTimes[i];
                }
                totalTime += lapTimes[i];
            }
            
            // calculate average
            double averageLap = totalTime / lapTimes.length;
            
            // format results
            DecimalFormat df = new DecimalFormat("#.###");
            StringBuilder result = new StringBuilder();
            result.append("Lap Time Analysis:\n\n");
            result.append("Fastest Lap: ").append(df.format(fastestLap)).append("s (Lap ").append(fastestLapNumber).append(")\n");
            result.append("Slowest Lap: ").append(df.format(slowestLap)).append("s\n");
            result.append("Average Lap: ").append(df.format(averageLap)).append("s\n\n");
            
            // calculate consistency (standard deviation)
            double variance = 0;
            for (double lapTime : lapTimes) {
                variance += Math.pow(lapTime - averageLap, 2);
            }
            double consistency = Math.sqrt(variance / lapTimes.length);
            result.append("Consistency Score: ").append(df.format(consistency)).append("s\n");
            result.append("(Lower score means more consistent lap times)");
            
            analysisResult.setText(result.toString());
            
        } catch (NumberFormatException e) {
            analysisResult.setText("Error: Please enter valid numbers for all lap times!");
        }
    }
    
    private void updatePositions() {
        try {
            // convert input to array
            int[] positions = new int[5];
            for (int i = 0; i < 5; i++) {
                positions[i] = Integer.parseInt(positionFields[i].getText().trim());
                if (positions[i] < 1 || positions[i] > 20) {
                    throw new IllegalArgumentException("Positions must be between 1 and 20");
                }
            }
            
            // check for duplicates
            for (int i = 0; i < positions.length; i++) {
                for (int j = i + 1; j < positions.length; j++) {
                    if (positions[i] == positions[j]) {
                        throw new IllegalArgumentException("Each position must be unique!");
                    }
                }
            }
            
            // format display
            String[] drivers = {"Hamilton", "Verstappen", "Leclerc", "Norris", "Russell"};
            StringBuilder display = new StringBuilder("<html>Current Race Positions:<br><br>");
            
            // create position array (1-20)
            String[] allPositions = new String[20];
            for (int i = 0; i < 20; i++) {
                allPositions[i] = "-";
            }
            
            // fill in our drivers
            for (int i = 0; i < 5; i++) {
                allPositions[positions[i]-1] = drivers[i];
            }
            
            // display grid
            for (int i = 0; i < 20; i++) {
                display.append(String.format("P%2d: %s<br>", i+1, allPositions[i]));
            }
            display.append("</html>");
            
            positionDisplay.setText(display.toString());
            
        } catch (NumberFormatException e) {
            positionDisplay.setText("Error: Please enter valid numbers!");
        } catch (IllegalArgumentException e) {
            positionDisplay.setText("Error: " + e.getMessage());
        }
    }
    
    private void returnToMainMenu() {
        dispose();
        new MainMenuScreen();
    }
}