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
        
        // 初始化面板
        codePanel = new JPanel();
        explanationPanel = new JPanel();
        explanationLabels = new ArrayList<>();
        
        // 设置布局
        codePanel.setLayout(new BoxLayout(codePanel, BoxLayout.Y_AXIS));
        explanationPanel.setLayout(new BoxLayout(explanationPanel, BoxLayout.Y_AXIS));
        
        // 添加边距
        codePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        explanationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // 创建滚动面板
        JScrollPane codeScroll = new JScrollPane(codePanel);
        JScrollPane explanationScroll = new JScrollPane(explanationPanel);
        
        // 同步滚动
        codeScroll.getVerticalScrollBar().setModel(
            explanationScroll.getVerticalScrollBar().getModel()
        );
        
        // 设置滚动策略
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
            
            // 设置固定高度
            setPreferredSize(new Dimension(Integer.MAX_VALUE, LINE_HEIGHT));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, LINE_HEIGHT));
            setMinimumSize(new Dimension(10, LINE_HEIGHT));
            
            // 添加悬停效果
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
        // 创建代码行面板
        LinePanel codeLine = new LinePanel(code, LINE_COLORS[colorIndex % LINE_COLORS.length]);
        
        // 创建解释面板
        JLabel explanationLabel = new JLabel(" ");
        explanationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        LinePanel explanationLine = new LinePanel("", LINE_COLORS[colorIndex % LINE_COLORS.length]);
        explanationLine.add(explanationLabel);
        
        // 存储解释标签
        explanationLabels.add(explanationLabel);
        
        // 添加点击监听器
        int currentIndex = explanationLabels.size() - 1;
        codeLine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 清除所有解释
                for (JLabel label : explanationLabels) {
                    label.setText(" ");
                }
                // 显示当前行的解释
                explanationLabel.setText(explanation);
            }
        });
        
        // 添加到面板
        codePanel.add(codeLine);
        explanationPanel.add(explanationLine);
        
        // 添加行间距
        codePanel.add(Box.createRigidArea(new Dimension(0, 1)));
        explanationPanel.add(Box.createRigidArea(new Dimension(0, 1)));
    }
    
    public void setContent(CodeBlock codeBlock, Map<String, String> tooltips) {
        // 清空现有内容
        codePanel.removeAll();
        explanationPanel.removeAll();
        explanationLabels.clear();
        
        // 分割代码成行
        String[] codeLines = codeBlock.getCode().split("\n");
        String[] explanationLines = codeBlock.getExplanation().split("\n");
        
        // 添加文件名
        addCodeLine("// File: " + codeBlock.getFileName(), "", 0);
        addCodeLine("", "", 0);  // 空行
        
        // 添加代码行
        for (int i = 0; i < codeLines.length; i++) {
            String explanation = i < explanationLines.length ? explanationLines[i] : "";
            addCodeLine(codeLines[i], explanation, i % LINE_COLORS.length);
        }
        
        // 刷新UI
        revalidate();
        repaint();
    }
}