import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;

public class ConceptsScreen extends JFrame {
    
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 640;
    
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    public ConceptsScreen() {
        // make window basic setup
        setTitle("F1 Array Racing - Learn Concepts");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // create all stuff we need
        createComponents();
        
        // make it look nice
        createLayout();
        
        // show to user
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

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(33, 33, 33));
        
        // make tabs for different parts
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // add different content to tabs
        addBasicArrayTab();
        add2DArrayTab();
        addArrayOperationsTab();
        
        // make button for go back
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> returnToMainMenu());
        backButton.setBackground(new Color(220, 0, 0));
        backButton.setForeground(Color.WHITE);
        
        // put everything together
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
    }
    
    private void addBasicArrayTab() {
        // make panel that can scroll
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // title for this part
        JLabel titleLabel = new JLabel("1D Arrays in F1 Racing");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // explain what is array
        JTextPane explanationPane = new JTextPane();
        explanationPane.setEditable(false);
        explanationPane.setText(
            "In F1 race, we need remember many lap times for one driver. " +
            "Array help us do this easily!\n\n" +
            "Example: Save 5 lap times for Lewis Hamilton:\n"
        );
        
        // show code example
        JTextPane codePane = new JTextPane();
        codePane.setEditable(false);
        String codeExample = 
            "// make array for lap times\n" +
            "double[] lapTimes = new double[5];\n\n" +
            "// put lap times in array\n" +
            "lapTimes[0] = 82.5;  // first lap\n" +
            "lapTimes[1] = 81.8;  // second lap\n" +
            "lapTimes[2] = 81.9;  // third lap\n" +
            "lapTimes[3] = 81.7;  // fourth lap\n" +
            "lapTimes[4] = 82.0;  // fifth lap\n";
        
        // make code look nice with colors
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet codeStyle = sc.addAttribute(SimpleAttributeSet.EMPTY, 
            StyleConstants.FontFamily, "Monospaced");
        codeStyle = sc.addAttribute(codeStyle, StyleConstants.FontSize, 14);
        codeStyle = sc.addAttribute(codeStyle, StyleConstants.Foreground, Color.GREEN);
        
        codePane.setCharacterAttributes(codeStyle, true);
        codePane.setText(codeExample);
        
        // add everything to panel
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(explanationPane);
        panel.add(Box.createVerticalStrut(10));
        panel.add(codePane);
        
        // make it can scroll
        JScrollPane scrollPane = new JScrollPane(panel);
        tabbedPane.addTab("Basic Arrays", scrollPane);
    }
    
    private void add2DArrayTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // explain 2D array with F1 example
        JLabel titleLabel = new JLabel("2D Arrays - Multiple Drivers & Laps");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JTextPane explanationPane = new JTextPane();
        explanationPane.setEditable(false);
        explanationPane.setText(
            "When we want save lap times for many drivers, we need 2D array!\n\n" +
            "Think like this: each row is different driver, each column is different lap.\n" +
            "Like Excel sheet but in Java code!\n"
        );
        
        // show 2D array example
        JTextPane codePane = new JTextPane();
        codePane.setEditable(false);
        String codeExample = 
            "// make 2D array for 3 drivers, each has 4 laps\n" +
            "double[][] raceTimes = new double[3][4];\n\n" +
            "// put times for first driver (Hamilton)\n" +
            "raceTimes[0][0] = 82.5;  // Hamilton lap 1\n" +
            "raceTimes[0][1] = 81.8;  // Hamilton lap 2\n" +
            "raceTimes[0][2] = 81.9;  // Hamilton lap 3\n" +
            "raceTimes[0][3] = 81.7;  // Hamilton lap 4\n\n" +
            "// put times for second driver (Verstappen)\n" +
            "raceTimes[1][0] = 82.3;  // Verstappen lap 1\n" +
            "raceTimes[1][1] = 81.6;  // Verstappen lap 2\n" +
            "// ... and so on for all drivers";
            
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet codeStyle = sc.addAttribute(SimpleAttributeSet.EMPTY, 
            StyleConstants.FontFamily, "Monospaced");
        codeStyle = sc.addAttribute(codeStyle, StyleConstants.FontSize, 14);
        codeStyle = sc.addAttribute(codeStyle, StyleConstants.Foreground, Color.GREEN);
        
        codePane.setCharacterAttributes(codeStyle, true);
        codePane.setText(codeExample);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(explanationPane);
        panel.add(Box.createVerticalStrut(10));
        panel.add(codePane);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        tabbedPane.addTab("2D Arrays", scrollPane);
    }
    
    private void addArrayOperationsTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // explain array operations using F1 examples
        JLabel titleLabel = new JLabel("Array Operations in F1");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JTextPane explanationPane = new JTextPane();
        explanationPane.setEditable(false);
        explanationPane.setText(
            "We can do many things with array in F1:\n" +
            "1. Find fastest lap (minimum in array)\n" +
            "2. Calculate average lap time\n" +
            "3. Sort lap times from fast to slow\n\n" +
            "Let see how to do these things in code:\n"
        );
        
        JTextPane codePane = new JTextPane();
        codePane.setEditable(false);
        String codeExample = 
            "double[] lapTimes = {82.5, 81.8, 81.9, 81.7, 82.0};\n\n" +
            "// find fastest lap\n" +
            "double fastestLap = lapTimes[0];\n" +
            "for(int i = 1; i < lapTimes.length; i++) {\n" +
            "    if(lapTimes[i] < fastestLap) {\n" +
            "        fastestLap = lapTimes[i];\n" +
            "    }\n" +
            "}\n\n" +
            "// calculate average lap time\n" +
            "double totalTime = 0;\n" +
            "for(double lap : lapTimes) {\n" +
            "    totalTime += lap;\n" +
            "}\n" +
            "double averageLap = totalTime / lapTimes.length;\n";
            
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet codeStyle = sc.addAttribute(SimpleAttributeSet.EMPTY, 
            StyleConstants.FontFamily, "Monospaced");
        codeStyle = sc.addAttribute(codeStyle, StyleConstants.FontSize, 14);
        codeStyle = sc.addAttribute(codeStyle, StyleConstants.Foreground, Color.GREEN);
        
        codePane.setCharacterAttributes(codeStyle, true);
        codePane.setText(codeExample);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(explanationPane);
        panel.add(Box.createVerticalStrut(10));
        panel.add(codePane);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        tabbedPane.addTab("Array Operations", scrollPane);
    }
    
    private void createLayout() {
        add(mainPanel);
    }
    
    private void returnToMainMenu() {
        dispose();
        new MainMenuScreen();
    }
}