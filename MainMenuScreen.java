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
        // 设置窗口基本属性
        setTitle("F1 Array Racing - Learn Java Arrays");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 初始化组件
        createComponents();
        
        // 设置布局
        createLayout();
        
        // 添加事件监听
        addEventListeners();
        
        // 显示窗口
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
        mainPanel.setBackground(new Color(33, 33, 33)); // F1主题深色背景
        
        // 创建标题
        titleLabel = new JLabel("F1 Array Racing");
        titleLabel.setFont(new Font("Formula1", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        
        // 创建按钮
        conceptsButton = createStyledButton("Learn Concepts");
        activityButton = createStyledButton("Practice Activities");
        assessmentButton = createStyledButton("Take Assessment");
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(220, 0, 0)); // F1红色
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
    
    private void createLayout() {
        // 使用垂直BoxLayout
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // 创建标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(33, 33, 33));
        titlePanel.add(titleLabel);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(33, 33, 33));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // 添加间距
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(conceptsButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(activityButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(assessmentButton);
        
        // 将组件添加到主面板
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(titlePanel);
        mainPanel.add(buttonPanel);
        
        // 设置组件对齐
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        conceptsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        activityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        assessmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 添加主面板到窗口
        add(mainPanel);
    }
    
    private void addEventListeners() {
        conceptsButton.addActionListener(e -> openConceptsScreen());
        activityButton.addActionListener(e -> openActivityScreen());
        assessmentButton.addActionListener(e -> openAssessmentScreen());
    }
    
    private void openConceptsScreen() {
        dispose(); // 关闭当前窗口
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