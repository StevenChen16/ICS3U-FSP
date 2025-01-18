import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenuScreen extends JFrame {
    
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    // 主要组件
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton conceptsButton;
    private JButton activityButton;
    private JButton assessmentButton;
    
    public MainMenuScreen() {
        // basic settings of the window
        setTitle("F1 Array Racing - Learn Java Arrays");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize components
        createComponents();
        
        // Set layout
        createLayout();
        
        // Add event listeners
        addEventListeners();
        
        // Display the window
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
    
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);  // Set to transparent
        
        // Create title label
        titleLabel = new JLabel("F1 Array Racing");
        titleLabel.setFont(new Font("Formula1", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        
        // Create styled buttons
        conceptsButton = createStyledButton("Learn Concepts");
        activityButton = createStyledButton("Practice Activities");
        assessmentButton = createStyledButton("Take Assessment");
    }
    
    private void createLayout() {
        // Use BoxLayout for main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);  // Set as transparent
        titlePanel.add(titleLabel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Set as transparent
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // Add components to button panel
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(conceptsButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(activityButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(assessmentButton);
        
        // Add components to main panel
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(titlePanel);
        mainPanel.add(buttonPanel);
        
        // Set alignment for components
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        conceptsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        activityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        assessmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add main panel to content pane
        add(mainPanel);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(220, 0, 0)); // F1 Red
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
    
    private void addEventListeners() {
        conceptsButton.addActionListener(e -> openConceptsScreen());
        activityButton.addActionListener(e -> openActivityScreen());
        assessmentButton.addActionListener(e -> openAssessmentScreen());
    }
    
    private void openConceptsScreen() {
        dispose(); // Close current window
        new ConceptsScreen();
    }
    
    private void openActivityScreen() {
        dispose();
        new ActivityScreen();
    }
    
    private void openAssessmentScreen() {
        dispose();
        new AssessmentScreen();
    }
}