import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

// ConceptsScreen.java
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
        
        // 创建主面板
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // 创建标签页面板
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        createTabs();
        
        // 创建返回按钮
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> returnToMainMenu());
        backButton.setBackground(new Color(220, 0, 0));
        backButton.setForeground(Color.WHITE);
        
        // 创建一个用于包含tabbedPane的面板
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // 创建滚动面板
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 增加滚动速度
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        // 添加组件到主面板
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
    }
    
    private void createTabs() {
    for (Section section : loader.getSections()) {
        // 创建主面板，使用BoxLayout
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 创建标题Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 设置主标题
        JLabel titleLabel = new JLabel(section.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        
        // 设置描述文本
        JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" + 
                                    section.getDescription() + "</div></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(descLabel);
        
        sectionPanel.add(titlePanel);
        
        // 处理每个示例
        for (CodeExample example : section.getExamples()) {
            sectionPanel.add(Box.createVerticalStrut(30));
            
            // 示例标题面板
            JPanel exampleTitlePanel = new JPanel();
            exampleTitlePanel.setLayout(new BoxLayout(exampleTitlePanel, BoxLayout.Y_AXIS));
            exampleTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // 示例标题
            JLabel exampleTitle = new JLabel(example.getTitle());
            exampleTitle.setFont(new Font("Arial", Font.BOLD, 20));
            exampleTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            exampleTitlePanel.add(exampleTitle);
            exampleTitlePanel.add(Box.createVerticalStrut(5));
            
            // 示例描述
            JLabel exampleDesc = new JLabel("<html><div style='text-align: center;'>" + 
                                          example.getDescription() + "</div></html>");
            exampleDesc.setFont(new Font("Arial", Font.PLAIN, 14));
            exampleDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
            exampleTitlePanel.add(exampleDesc);
            
            sectionPanel.add(exampleTitlePanel);
            
            // 添加代码面板
            for (CodeBlock codeBlock : example.getCodeBlocks()) {
                sectionPanel.add(Box.createVerticalStrut(10));
                CodePanel codePanel = new CodePanel();
                codePanel.setContent(codeBlock, example.getTooltips());
                codePanel.setMaximumSize(new Dimension(FRAME_WIDTH - 100, 400));
                sectionPanel.add(codePanel);
            }
        }
        
        // 创建滚动面板并设置策略
        JScrollPane scrollPane = new JScrollPane(sectionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);  // 移除滚动面板边框
        
        tabbedPane.addTab(section.getTitle(), scrollPane);
    }
}

    
    private void createLayout() {
        // 由于我们已经在主面板中设置了滚动条，这里直接添加主面板
        add(mainPanel);
        // 设置窗口最小尺寸，以确保可以看到滚动条
        setMinimumSize(new Dimension(800, 600));
    }
    
    
    private void returnToMainMenu() {
        dispose();
        new MainMenuScreen();
    }
}