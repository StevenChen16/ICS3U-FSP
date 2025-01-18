import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.*;

public class CustomCodePanel extends JPanel {
    private JTextPane codePane;
    private JLabel explanationLabel;
    private Map<Integer, String> lineExplanations;
    private SimpleAttributeSet defaultStyle;
    private SimpleAttributeSet highlightStyle;
    private int currentHighlightedLine = -1;
    
    public CustomCodePanel() {
        setLayout(new BorderLayout());
        
        // Initialize text styles
        defaultStyle = new SimpleAttributeSet();
        StyleConstants.setFontFamily(defaultStyle, "Monospaced");
        StyleConstants.setFontSize(defaultStyle, 14);
        
        highlightStyle = new SimpleAttributeSet(defaultStyle);
        StyleConstants.setBackground(highlightStyle, new Color(232, 242, 254));
        
        // Initialize components
        initComponents();
        
        // Initialize line explanations
        lineExplanations = new HashMap<>();
    }
    
    private void initComponents() {
        // Create code text pane
        codePane = new JTextPane() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                drawLineNumbers(g);
            }
        };
        codePane.setEditable(false);
        codePane.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        // Add mouse listener to handle line clicks
        codePane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleCodePaneClick(e);
            }
        });
        
        // Create explanation label
        explanationLabel = new JLabel();
        explanationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        explanationLabel.setVerticalAlignment(JLabel.TOP);
        explanationLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Using JSplitPane to display code and explanation side by side
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(codePane),
            new JScrollPane(explanationLabel)
        );
        splitPane.setResizeWeight(0.5);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void drawLineNumbers(Graphics g) {
        // Draw line numbers for code pane
        Container parent = codePane.getParent();
        if (parent instanceof JViewport) {
            Rectangle clip = g.getClipBounds();
            int startOffset = codePane.viewToModel(new Point(clip.x, clip.y));
            int endOffset = codePane.viewToModel(new Point(clip.x, clip.y + clip.height));
            
            while (startOffset <= endOffset) {
                try {
                    Rectangle rect = codePane.modelToView(startOffset);
                    int lineNumber = codePane.getDocument().getDefaultRootElement()
                        .getElementIndex(startOffset) + 1;
                    
                    // Draw line number
                    g.setColor(Color.GRAY);
                    g.drawString(String.valueOf(lineNumber), 5, rect.y + rect.height - 2);
                    
                    startOffset = Utilities.getRowEnd(codePane, startOffset) + 1;
                } catch (BadLocationException e) {
                    break;
                }
            }
        }
    }
    
    private void handleCodePaneClick(MouseEvent e) {
        int pos = codePane.viewToModel(e.getPoint());
        if (pos >= 0) {
            Element root = codePane.getDocument().getDefaultRootElement();
            int line = root.getElementIndex(pos) + 1;
            
            // Clear previous highlight
            clearHighlight();
            
            // Highlight the clicked line
            highlightLine(line);
            
            // Update explanation label
            updateExplanation(line);
        }
    }
    
    private void clearHighlight() {
        if (currentHighlightedLine != -1) {
            try {
                Element root = codePane.getDocument().getDefaultRootElement();
                Element elem = root.getElement(currentHighlightedLine - 1);
                StyledDocument doc = (StyledDocument) codePane.getDocument();
                doc.setCharacterAttributes(elem.getStartOffset(),
                    elem.getEndOffset() - elem.getStartOffset(), defaultStyle, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void highlightLine(int line) {
        try {
            Element root = codePane.getDocument().getDefaultRootElement();
            Element elem = root.getElement(line - 1);
            StyledDocument doc = (StyledDocument) codePane.getDocument();
            doc.setCharacterAttributes(elem.getStartOffset(),
                elem.getEndOffset() - elem.getStartOffset(), highlightStyle, true);
            currentHighlightedLine = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setContent(String code, Map<Integer, String> explanations) {
        codePane.setText(code);
        lineExplanations = explanations;
        currentHighlightedLine = -1;
        explanationLabel.setText("");
    }
    
    private void updateExplanation(int line) {
        String explanation = lineExplanations.get(line);
        if (explanation != null) {
            explanationLabel.setText("<html>" + explanation + "</html>");
        } else {
            explanationLabel.setText("");
        }
    }
}