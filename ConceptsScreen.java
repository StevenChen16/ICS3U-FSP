import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

// This class is responsible for displaying the concepts screen
public class ConceptsScreen extends JFrame {
    private static final int FRAME_WIDTH = 1600;
    private static final int FRAME_HEIGHT = 900;
    
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private final ConceptsLoader loader;
    
    public ConceptsScreen() {
        setTitle("F1 Array Racing - Learn Concepts");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        loader = new ConceptsLoader();
        createComponents();
        createLayout();
        
        setVisible(true);
    }
    
    private void createComponents() {
        try {
            final Image backgroundImage = ImageIO.read(getClass().getResource("./resources/background2.jpg"));
            setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Create top panel for back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setOpaque(false);
        
        // Create styled back button
        JButton backButton = createStyledButton("Back to Main Menu");
        backButton.addActionListener(e -> returnToMainMenu());
        topPanel.add(backButton);
        
        // Create and setup tabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        createTabs();
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    // Create styled button method (similar to MainMenuScreen style)
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(220, 0, 0)); // F1 red color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
    
    
    private void createTabs() {
        for (Section section : loader.getSections()) {
            JPanel sectionPanel = new JPanel();
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
            sectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            sectionPanel.setOpaque(false);
            
            // Create title panel
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.setOpaque(false);
            
            JLabel titleLabel = new JLabel(section.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(titleLabel);
            titlePanel.add(Box.createVerticalStrut(10));
            
            JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" + 
                                        section.getDescription() + "</div></html>");
            descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            descLabel.setForeground(Color.WHITE);
            descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(descLabel);
            
            sectionPanel.add(titlePanel);
            
            // Handle each example
            for (CodeExample example : section.getExamples()) {
                sectionPanel.add(Box.createVerticalStrut(30));
                
                JPanel exampleTitlePanel = new JPanel();
                exampleTitlePanel.setLayout(new BoxLayout(exampleTitlePanel, BoxLayout.Y_AXIS));
                exampleTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                exampleTitlePanel.setOpaque(false);
                
                JLabel exampleTitle = new JLabel(example.getTitle());
                exampleTitle.setFont(new Font("Arial", Font.BOLD, 20));
                exampleTitle.setForeground(Color.WHITE);
                exampleTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
                exampleTitlePanel.add(exampleTitle);
                exampleTitlePanel.add(Box.createVerticalStrut(5));
                
                JLabel exampleDesc = new JLabel("<html><div style='text-align: center;'>" + 
                                              example.getDescription() + "</div></html>");
                exampleDesc.setFont(new Font("Arial", Font.PLAIN, 14));
                exampleDesc.setForeground(Color.WHITE);
                exampleDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
                exampleTitlePanel.add(exampleDesc);
                
                sectionPanel.add(exampleTitlePanel);
                
                // Handle code blocks using CustomCodePanel
                for (CodeBlock codeBlock : example.getCodeBlocks()) {
                    sectionPanel.add(Box.createVerticalStrut(10));
                    
                    CustomCodePanel customCodePanel = new CustomCodePanel();
                    String code = codeBlock.getCode();
                    
                    // Create line-by-line explanations map
                    Map<Integer, String> explanations = new HashMap<>();
                    String explanation = codeBlock.getExplanation();
                    if (code != null && explanation != null) {
                        String[] lines = code.split("\n");
                        // Add explanation for each line
                        for (int i = 0; i < lines.length; i++) {
                            explanations.put(i + 1, explanation);
                        }
                    }
                    
                    customCodePanel.setContent(code, explanations);
                    customCodePanel.setMaximumSize(new Dimension(FRAME_WIDTH - 100, 400));
                    customCodePanel.setOpaque(false);
                    
                    sectionPanel.add(customCodePanel);
                }
            }
            
            JScrollPane scrollPane = new JScrollPane(sectionPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setBorder(null);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            
            tabbedPane.addTab(section.getTitle(), scrollPane);
        }
    }
    
    private void createLayout() {
        add(mainPanel);
        setMinimumSize(new Dimension(800, 600));
    }
    
    private void returnToMainMenu() {
        dispose();
        new MainMenuScreen();
    }
}