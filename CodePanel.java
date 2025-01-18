// CodePanel.java
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;

public class CodePanel extends JPanel {
    private JPanel codePanel;
    private JPanel explanationPanel;
    private ArrayList<JLabel> explanationLabels;
    private static final Color[] LINE_COLORS = {
        new Color(100, 196, 255, 40),  // Williams blue (transparent)
        new Color(182, 186, 189, 40),  // Haas grey (transparent)
        new Color(82, 226, 82, 40),    // Kick Sauber green (transparent)
        new Color(255, 135, 188, 40)   // Alpine pink (transparent)
    };

    private static final Color HOVER_COLOR = new Color(230, 230, 230);
    private static final int LINE_HEIGHT = 30;

    public CodePanel() {
        setLayout(new GridLayout(1, 2, 10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Initialize code and explanation panels
        codePanel = new JPanel();
        explanationPanel = new JPanel();
        explanationLabels = new ArrayList<>();
        
        // Set layouts
        codePanel.setLayout(new BoxLayout(codePanel, BoxLayout.Y_AXIS));
        explanationPanel.setLayout(new BoxLayout(explanationPanel, BoxLayout.Y_AXIS));
        
        // Add padding
        codePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        explanationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create scroll panes
        JScrollPane codeScroll = new JScrollPane(codePanel);
        JScrollPane explanationScroll = new JScrollPane(explanationPanel);
        
        // Syncronize vertical scrolling
        codeScroll.getVerticalScrollBar().setModel(
            explanationScroll.getVerticalScrollBar().getModel()
        );
        
        // Set scroll bar policies
        codeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        explanationScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        add(codeScroll);
        add(explanationScroll);
    }
    
    private class LinePanel extends JPanel {
        private Color defaultBackground;
        
        public LinePanel(String text, Color bgColor) {
            setLayout(new BorderLayout());
            setBackground(bgColor);
            defaultBackground = bgColor;
            
            JLabel label = new JLabel(text);
            label.setFont(new Font("Monospaced", Font.PLAIN, 14));
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            add(label, BorderLayout.WEST);
            
            // Set line height
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
        
        // Create explanation label
        JLabel explanationLabel = new JLabel(" ");
        explanationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        LinePanel explanationLine = new LinePanel("", LINE_COLORS[colorIndex % LINE_COLORS.length]);
        explanationLine.add(explanationLabel);
        
        // Store explanation label
        explanationLabels.add(explanationLabel);
        
        // Add click listener to show explanation
        int currentIndex = explanationLabels.size() - 1;
        codeLine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Clear previous explanations
                for (JLabel label : explanationLabels) {
                    label.setText(" ");
                }
                // Display current explanation
                explanationLabel.setText(explanation);
            }
        });
        
        // Create line separators
        codePanel.add(codeLine);
        explanationPanel.add(explanationLine);
        
        // Add spacing
        codePanel.add(Box.createRigidArea(new Dimension(0, 1)));
        explanationPanel.add(Box.createRigidArea(new Dimension(0, 1)));
    }
    
    public void setContent(CodeBlock codeBlock, Map<String, String> tooltips) {
        // Clear previous content
        codePanel.removeAll();
        explanationPanel.removeAll();
        explanationLabels.clear();
        
        // Split code and explanation into lines
        String[] codeLines = codeBlock.getCode().split("\n");
        String[] explanationLines = codeBlock.getExplanation().split("\n");
        
        // Add file name
        addCodeLine("// File: " + codeBlock.getFileName(), "", 0);
        addCodeLine("", "", 0);  // Empty line
        
        // Add code lines with explanations
        for (int i = 0; i < codeLines.length; i++) {
            String explanation = i < explanationLines.length ? explanationLines[i] : "";
            addCodeLine(codeLines[i], explanation, i % LINE_COLORS.length);
        }
        
        // Refresh panel
        revalidate();
        repaint();
    }
}