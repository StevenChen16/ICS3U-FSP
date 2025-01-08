import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CodeExplainer extends JFrame {
    private ArrayList<JLabel> explanationLabels;
    private JPanel codePanel;
    private JPanel explanationPanel;
    private static final Color[] LINE_COLORS = {
        new Color(240, 240, 250),
        new Color(245, 245, 245),
        new Color(240, 250, 240),
        new Color(250, 240, 240)
    };
    private static final int LINE_HEIGHT = 30;

    public CodeExplainer() {
        setTitle("Code Explainer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2, 10, 10));

        // Initialize panels
        codePanel = new JPanel();
        explanationPanel = new JPanel();
        explanationLabels = new ArrayList<>();

        // Set layouts
        codePanel.setLayout(new BoxLayout(codePanel, BoxLayout.Y_AXIS));
        explanationPanel.setLayout(new BoxLayout(explanationPanel, BoxLayout.Y_AXIS));
        
        // Add borders
        codePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        explanationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add sample code lines
        addCodeLine(
            "public class HelloWorld {",
            "Declares a public class named HelloWorld", 
            0
        );
        
        addCodeLine(
            "    public static void main(String[] args) {",
            "The main method - entry point of the program. Takes command line arguments as parameter.",
            1
        );
        
        addCodeLine(
            "        System.out.println(\"Hello, World!\");",
            "Prints 'Hello, World!' to the console using the standard output stream.",
            2
        );
        
        addCodeLine(
            "    }",
            "Closes the main method",
            3
        );
        
        addCodeLine(
            "}",
            "Closes the HelloWorld class definition",
            0
        );

        // Create scroll panes with synchronized scrolling
        JScrollPane codeScroll = new JScrollPane(codePanel);
        JScrollPane explanationScroll = new JScrollPane(explanationPanel);

        // Synchronize vertical scrolling
        codeScroll.getVerticalScrollBar().setModel(
            explanationScroll.getVerticalScrollBar().getModel()
        );

        // Add components to frame
        add(codeScroll);
        add(explanationScroll);
        
        setSize(800, 400);
        setLocationRelativeTo(null);
    }

    private class LinePanel extends JPanel {
        private Color defaultBackground;
        private static final Color HOVER_COLOR = new Color(230, 230, 230);

        public LinePanel(String text, Color bgColor) {
            setLayout(new BorderLayout());
            setBackground(bgColor);
            defaultBackground = bgColor;
            
            JLabel label = new JLabel(text);
            label.setFont(new Font("Monospaced", Font.PLAIN, 14));
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            add(label, BorderLayout.WEST);
            
            // Set fixed height
            setPreferredSize(new Dimension(Integer.MAX_VALUE, LINE_HEIGHT));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, LINE_HEIGHT));
            setMinimumSize(new Dimension(10, LINE_HEIGHT));
            
            // Add hover effect
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(HOVER_COLOR);
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(defaultBackground);
                }
            });
        }
    }

    private void addCodeLine(String code, String explanation, int colorIndex) {
        // Create code line panel
        LinePanel codeLine = new LinePanel(code, LINE_COLORS[colorIndex % LINE_COLORS.length]);
        
        // Create explanation panel with same height but initially empty
        JLabel explanationLabel = new JLabel(" ");  // Empty space
        explanationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        LinePanel explanationLine = new LinePanel("", LINE_COLORS[colorIndex % LINE_COLORS.length]);
        explanationLine.add(explanationLabel);
        
        // Store explanation label for later access
        explanationLabels.add(explanationLabel);
        
        // Add click listener to code line
        int currentIndex = explanationLabels.size() - 1;
        codeLine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Clear all explanations
                for (JLabel label : explanationLabels) {
                    label.setText(" ");
                }
                // Show explanation for clicked line
                explanationLabel.setText(explanation);
            }
        });
        
        // Add to panels
        codePanel.add(codeLine);
        explanationPanel.add(explanationLine);
        
        // Add small gap between lines
        codePanel.add(Box.createRigidArea(new Dimension(0, 1)));
        explanationPanel.add(Box.createRigidArea(new Dimension(0, 1)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CodeExplainer().setVisible(true);
        });
    }
}