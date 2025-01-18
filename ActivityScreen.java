import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ActivityScreen.class);
    
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
        tabbedPane.addTab("Bubble Sort Visualization", createSortingPanel());
        
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
        
        // 添加教学说明
        JLabel instructionLabel = new JLabel(
            "<html><h3>Array Operations with Lap Times</h3>" +
            "This activity demonstrates key array operations:<br>" +
            "• Storing multiple values in an array<br>" +
            "• Iterating through array elements<br>" +
            "• Finding minimum and maximum values<br>" +
            "• Calculating statistics (mean, median, standard deviation)<br><br>" +
            "Enter 5 lap times below (in seconds) and see the analysis:</html>"
        );
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建输入区域
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 5));
        lapTimeFields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel("Lap " + (i + 1) + ":");
            label.setPreferredSize(new Dimension(50, 25));
            lapTimeFields[i] = new JTextField(10);
            fieldPanel.add(label);
            fieldPanel.add(lapTimeFields[i]);
            inputPanel.add(fieldPanel);
        }
        inputPanel.setMaximumSize(new Dimension(400, 200));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建图表面板
        JPanel chartPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                // 保持16:9的宽高比
                int width = getParent() != null ? getParent().getWidth() - 40 : 600;
                return new Dimension(width, (int)(width * 0.5625));
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLapTimeChart(g);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        chartPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 分析按钮
        JButton analyzeButton = new JButton("Analyze Lap Times");
        analyzeButton.setBackground(new Color(220, 0, 0));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 分析结果区域
        analysisResult = new JTextArea(10, 40);
        analysisResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(analysisResult);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 添加分析按钮事件
        analyzeButton.addActionListener(e -> performDetailedAnalysis(chartPanel));
        
        // 组装面板
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(chartPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(analyzeButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(scrollPane);
        
        return panel;
    }

    private void performDetailedAnalysis(JPanel chartPanel) {
        try {
            // 获取并验证输入
            double[] lapTimes = new double[5];
            for (int i = 0; i < 5; i++) {
                String input = lapTimeFields[i].getText().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all lap times");
                }
                lapTimes[i] = Double.parseDouble(input);
                if (lapTimes[i] <= 0) {
                    throw new IllegalArgumentException("Lap times must be positive");
                }
            }
            
            // 计算统计数据
            double[] sortedTimes = Arrays.copyOf(lapTimes, lapTimes.length);
            Arrays.sort(sortedTimes);
            
            double fastestLap = sortedTimes[0];
            double slowestLap = sortedTimes[sortedTimes.length - 1];
            double medianLap = sortedTimes[sortedTimes.length / 2];
            
            double sum = Arrays.stream(lapTimes).sum();
            double mean = sum / lapTimes.length;
            
            double variance = Arrays.stream(lapTimes)
                .map(t -> Math.pow(t - mean, 2))
                .sum() / lapTimes.length;
            double stdDev = Math.sqrt(variance);
            
            // 格式化结果输出
            DecimalFormat df = new DecimalFormat("#.###");
            StringBuilder result = new StringBuilder();
            result.append("Detailed Lap Time Analysis:\n\n");
            result.append("Basic Statistics:\n");
            result.append("• Fastest Lap: ").append(df.format(fastestLap)).append("s\n");
            result.append("• Slowest Lap: ").append(df.format(slowestLap)).append("s\n");
            result.append("• Median Lap: ").append(df.format(medianLap)).append("s\n");
            result.append("• Average Lap: ").append(df.format(mean)).append("s\n\n");
            
            result.append("Consistency Analysis:\n");
            result.append("• Standard Deviation: ").append(df.format(stdDev)).append("s\n");
            result.append("• Time Spread: ").append(df.format(slowestLap - fastestLap)).append("s\n");
            result.append("• Consistency Rating: ");
            
            // 根据标准差评定一致性
            if (stdDev < 0.5) result.append("Excellent!");
            else if (stdDev < 1.0) result.append("Good");
            else if (stdDev < 1.5) result.append("Fair");
            else result.append("Needs Improvement");
            
            analysisResult.setText(result.toString());
            
            // 重绘图表
            chartPanel.repaint();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid numbers for all lap times",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void drawLapTimeChart(Graphics g) {
        // 确保有输入数据
        if (lapTimeFields[0].getText().isEmpty()) return;
        
        try {
            // 获取数据
            double[] lapTimes = new double[5];
            for (int i = 0; i < 5; i++) {
                lapTimes[i] = Double.parseDouble(lapTimeFields[i].getText().trim());
            }
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 获取画布尺寸并计算合适的padding
            int canvasWidth = getWidth();
            int canvasHeight = getHeight();
            int padding = Math.min(Math.min(canvasWidth, canvasHeight) / 10, 30); // 动态计算padding，但不超过30
            
            // 计算实际可用的绘图区域
            // int width = canvasWidth - (padding * 2);
            // int height = canvasHeight - (padding * 2);
            int width = 700;
            int height = 350;
            // System.out.println(width + " " + height);
            logger.info(width + " " + height);
            
            // 清除背景
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, canvasWidth, canvasHeight);
            
            // 计算数据范围
            double minTime = Arrays.stream(lapTimes).min().getAsDouble();
            double maxTime = Arrays.stream(lapTimes).max().getAsDouble();
            double range = maxTime - minTime + 2;
            
            // 设置字体大小
            int fontSize = Math.min(height / 15, 12); // 根据高度调整字体大小，但不超过12
            g2.setFont(new Font("Arial", Font.PLAIN, fontSize));
            
            // 绘制网格
            g2.setColor(new Color(240, 240, 240));
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{5}, 0));
            for (int i = 1; i < 5; i++) {
                int x = padding + (width * i / 4);
                g2.drawLine(x, padding, x, height + padding);
            }
            
            // 绘制坐标轴
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            g2.drawLine(padding, height + padding, width + padding, height + padding); // X轴
            g2.drawLine(padding, padding, padding, height + padding); // Y轴
            
            // 绘制数据点和连线
            int[] xPoints = new int[5];
            int[] yPoints = new int[5];
            int pointSize = Math.min(width / 30, 6); // 动态调整点的大小
            
            for (int i = 0; i < 5; i++) {
                yPoints[i] = padding + (height * i / 4);
                xPoints[i] = (int)(padding + ((lapTimes[i] - minTime) / range * width));
                
                // 绘制数据点
                g2.setColor(new Color(220, 0, 0));
                g2.fillOval(xPoints[i] - pointSize/2, yPoints[i] - pointSize/2, pointSize, pointSize);
                
                // 绘制时间标签
                String timeStr = String.format("%.2f", lapTimes[i]);
                g2.setColor(Color.BLACK);
                FontMetrics fm = g2.getFontMetrics();
                int labelX = xPoints[i] + pointSize;
                int labelY = yPoints[i] + fontSize/2;
                
                // 确保标签不会超出画布
                if (labelX + fm.stringWidth(timeStr) > width + padding) {
                    labelX = xPoints[i] - fm.stringWidth(timeStr) - pointSize;
                }
                g2.drawString(timeStr, labelX, labelY);
                
                // 绘制Lap标签
                String lapLabel = "Lap " + (i+1);
                g2.drawString(lapLabel, padding - fm.stringWidth(lapLabel) - 5, yPoints[i] + fontSize/2);
                
                // 绘制连线
                if (i > 0) {
                    g2.setColor(new Color(220, 0, 0));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawLine(xPoints[i-1], yPoints[i-1], xPoints[i], yPoints[i]);
                }
            }
            
            // 绘制X轴刻度
            DecimalFormat df = new DecimalFormat("#.0");
            int tickCount = 5;
            for (int i = 0; i <= tickCount; i++) {
                double value = minTime + (range * i / tickCount);
                String label = df.format(value);
                FontMetrics fm = g2.getFontMetrics();
                int x = padding + (width * i / tickCount);
                // 确保刻度标签不会重叠
                if (i < tickCount) {
                    g2.drawString(label, x - fm.stringWidth(label)/2, 
                        height + padding + fontSize + 2);
                }
            }
            
        } catch (NumberFormatException e) {
            // 忽略无效输入
        }
    }

    private JPanel createPositionTrackerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 添加教学说明
        JLabel instructionLabel = new JLabel(
            "<html><h3>Race Position Tracking System</h3>" +
            "This activity demonstrates array manipulation and 2D arrays:<br>" +
            "• Tracking multiple values in an array<br>" +
            "• Updating array elements<br>" +
            "• Maintaining position history (2D array)<br>" +
            "• Validating array operations<br><br>" +
            "Enter positions (1-20) for each driver and track their progress:</html>"
        );
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建输入区域
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 5));
        positionFields = new JTextField[5];
        String[] drivers = {"Hamilton", "Verstappen", "Leclerc", "Norris", "Russell"};
        for (int i = 0; i < 5; i++) {
            JPanel driverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel driverLabel = new JLabel(drivers[i] + ":");
            driverLabel.setPreferredSize(new Dimension(80, 25));
            positionFields[i] = new JTextField(5);
            driverPanel.add(driverLabel);
            driverPanel.add(positionFields[i]);
            inputPanel.add(driverPanel);
        }
        inputPanel.setMaximumSize(new Dimension(400, 200));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建位置显示面板
        JPanel trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTrackPositions(g);
            }
        };
        trackPanel.setPreferredSize(new Dimension(600, 300));
        trackPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        trackPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 更新按钮
        updateButton = new JButton("Update Positions");
        updateButton.setBackground(new Color(220, 0, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 位置历史显示
        positionDisplay = new JLabel();
        positionDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane historyPane = new JScrollPane(positionDisplay);
        historyPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        historyPane.setPreferredSize(new Dimension(600, 150));
        
        // 添加更新按钮事件
        updateButton.addActionListener(e -> updatePositionsWithAnimation(trackPanel));
        
        // 组装面板
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(trackPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(updateButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(historyPane);
        
        return panel;
    }

    private void updatePositionsWithAnimation(JPanel trackPanel) {
        try {
            // 获取并验证输入
            int[] newPositions = new int[5];
            for (int i = 0; i < 5; i++) {
                String input = positionFields[i].getText().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all positions");
                }
                newPositions[i] = Integer.parseInt(input);
                if (newPositions[i] < 1 || newPositions[i] > 20) {
                    throw new IllegalArgumentException("Positions must be between 1 and 20");
                }
            }
            
            // 检查重复位置
            Set<Integer> positionSet = new HashSet<>();
            for (int pos : newPositions) {
                if (!positionSet.add(pos)) {
                    throw new IllegalArgumentException("Each position must be unique");
                }
            }
            
            // 保存当前位置用于动画
            int[] oldPositions = new int[5];
            for (int i = 0; i < 5; i++) {
                try {
                    oldPositions[i] = Integer.parseInt(positionFields[i].getText().trim());
                } catch (NumberFormatException e) {
                    oldPositions[i] = newPositions[i];
                }
            }
            
            // 创建动画
            Timer timer = new Timer(50, null);
            final int[] step = {0};
            final int steps = 20; // 总动画步数
            
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (step[0] < steps) {
                        // 计算当前位置
                        int[] currentPositions = new int[5];
                        for (int i = 0; i < 5; i++) {
                            double progress = (double)step[0] / steps;
                            currentPositions[i] = (int)(oldPositions[i] + (newPositions[i] - oldPositions[i]) * progress);
                        }
                        
                        // 更新显示
                        drawPositionsWithAnimation(trackPanel.getGraphics(), currentPositions, oldPositions, newPositions);
                        step[0]++;
                    } else {
                        // 动画结束，更新最终位置
                        ((Timer)e.getSource()).stop();
                        updatePositionHistory(newPositions);
                        trackPanel.repaint();
                    }
                }
            });
            
            timer.start();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid numbers for all positions",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void drawPositionsWithAnimation(Graphics g, int[] currentPositions, int[] oldPositions, int[] newPositions) {
        if (g == null) return;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 清除画面
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        // 计算赛道尺寸
        Dimension size = getSize();
        int padding = 40;
        int availableWidth = size.width - (2 * padding);
        int availableHeight = size.height - (2 * padding);
        
        // 保持16:9的宽高比
        double aspectRatio = 16.0 / 9.0;
        int trackWidth, trackHeight;
        
        if (availableWidth / aspectRatio <= availableHeight) {
            trackWidth = availableWidth;
            trackHeight = (int)(trackWidth / aspectRatio);
        } else {
            trackHeight = availableHeight;
            trackWidth = (int)(trackHeight * aspectRatio);
        }
        
        // 计算居中位置
        int startX = (size.width - trackWidth) / 2;
        int startY = (size.height - trackHeight) / 2;
        
        // 绘制赛道
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(startX, startY, trackWidth, trackHeight, 20, 20);
        
        // 绘制位置标记
        String[] drivers = {"HAM", "VER", "LEC", "NOR", "RUS"};
        Color[] driverColors = {
            new Color(0, 210, 255),
            new Color(6, 0, 239),
            new Color(220, 0, 0),
            new Color(255, 135, 0),
            new Color(0, 210, 255)
        };
        
        for (int i = 0; i < 5; i++) {
            // 计算位置 - 使用新的startX
            double x = startX + (trackWidth * (currentPositions[i] - 1) / 19.0);
            double y = startY + trackHeight / 2.0;
            
            // 绘制车辆标记
            int markerSize = Math.min(trackHeight / 10, 30);
            g2.setColor(driverColors[i]);
            g2.fillOval((int)x - markerSize/2, (int)y - markerSize/2, markerSize, markerSize);
            
            // 绘制车手代号
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, markerSize/2));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(drivers[i]);
            g2.drawString(drivers[i], (int)x - textWidth/2, (int)y + fm.getAscent()/2 - 2);
            
            // 绘制位置变化指示器
            if (oldPositions[i] != newPositions[i]) {
                String change = (newPositions[i] < oldPositions[i] ? "▲" : "▼");
                g2.setColor(newPositions[i] < oldPositions[i] ? Color.GREEN : Color.RED);
                g2.drawString(change, (int)x - 5, (int)y - markerSize/2 - 5);
            }
        }
    }

    private void updatePositionHistory(int[] newPositions) {
        // 更新历史记录显示
        StringBuilder history = new StringBuilder("<html>");
        history.append("<h3>Position History:</h3>");
        history.append("<table border='0' cellpadding='3'>");
        history.append("<tr><th>Driver</th><th>Current</th><th>Change</th></tr>");
        
        String[] drivers = {"Hamilton", "Verstappen", "Leclerc", "Norris", "Russell"};
        int[] previousPositions = new int[5];
        
        // 获取上一次的位置
        for (int i = 0; i < 5; i++) {
            try {
                String prevPos = positionFields[i].getText().trim();
                previousPositions[i] = prevPos.isEmpty() ? newPositions[i] : Integer.parseInt(prevPos);
            } catch (NumberFormatException e) {
                previousPositions[i] = newPositions[i];
            }
        }
        
        // 生成历史记录
        for (int i = 0; i < 5; i++) {
            history.append("<tr>");
            history.append("<td>").append(drivers[i]).append("</td>");
            history.append("<td align='center'>P").append(newPositions[i]).append("</td>");
            
            // 新的计算逻辑: 新位置 - 旧位置
            int change = newPositions[i] - previousPositions[i];
            String changeText;
            String color;
            if (change < 0) { // 位置上升(数字变小)显示为正数
                changeText = "+" + (-change);
                color = "green";
            } else if (change > 0) { // 位置下降(数字变大)显示为负数
                changeText = String.valueOf(-change);
                color = "red";
            } else {
                changeText = "=";
                color = "gray";
            }
            history.append("<td style='color: ").append(color).append("'>")
                .append(changeText).append("</td>");
            history.append("</tr>");
        }
        
        history.append("</table></html>");
        positionDisplay.setText(history.toString());
    }

    private JPanel createSortingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 添加说明标签
        JLabel instructionLabel = new JLabel(
            "<html><h3>Bubble Sort Visualization</h3>" +
            "Enter lap times below and watch how bubble sort organizes them from fastest to slowest.<br>" +
            "This demonstrates how arrays can be sorted step by step.<br><br>" +
            "Example code:<br>" +
            "<pre>for (int i = 0; i < array.length - 1; i++) {<br>" +
            "    for (int j = 0; j < array.length - i - 1; j++) {<br>" +
            "        if (a2rray[j] > array[j + 1]) {<br>" +
            "            // swap elements<br>" +
            "            double temp = array[j];<br>" +
            "            array[j] = array[j + 1];<br>" +
            "            array[j + 1] = temp;<br>" +
            "        }<br>" +
            "    }<br>" +
            "}</pre></html>"
        );
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建输入区域
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 5));
        JTextField[] timeFields = new JTextField[6];
        for (int i = 0; i < 6; i++) {
            inputPanel.add(new JLabel("Lap " + (i + 1) + " Time:"));
            timeFields[i] = new JTextField(10);
            inputPanel.add(timeFields[i]);
        }
        inputPanel.setMaximumSize(new Dimension(300, 200));
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建排序过程显示区域
        JPanel sortingVisualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 将在后面实现绘制逻辑
            }
        };
        sortingVisualPanel.setPreferredSize(new Dimension(600, 200));
        sortingVisualPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        sortingVisualPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建控制按钮
        JButton sortButton = new JButton("Start Sorting");
        sortButton.setBackground(new Color(220, 0, 0));
        sortButton.setForeground(Color.WHITE);
        sortButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 创建日志区域
        JTextArea logArea = new JTextArea(8, 40);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // 添加排序按钮事件
        sortButton.addActionListener(e -> {
            try {
                // 获取输入的圈速
                double[] laptimes = new double[6];
                for (int i = 0; i < 6; i++) {
                    laptimes[i] = Double.parseDouble(timeFields[i].getText().trim());
                }
                
                // 开始排序动画
                startSortingAnimation(laptimes, sortingVisualPanel, logArea);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, 
                    "Please enter valid numbers for all lap times!", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // 组装面板
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(sortingVisualPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(sortButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(scrollPane);
        
        return panel;
    }

    private void startSortingAnimation(double[] laptimes, JPanel visualPanel, JTextArea logArea) {
        // 复制数组以保留原始数据
        double[] array = Arrays.copyOf(laptimes, laptimes.length);
        Timer timer = new Timer(1000, null);
        
        final int[] i = {0};
        final int[] j = {0};
        
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i[0] < array.length - 1) {
                    if (j[0] < array.length - i[0] - 1) {
                        // 执行一次比较
                        if (array[j[0]] > array[j[0] + 1]) {
                            // 交换元素
                            double temp = array[j[0]];
                            array[j[0]] = array[j[0] + 1];
                            array[j[0] + 1] = temp;
                            
                            // 记录交换操作
                            logArea.append(String.format(
                                "Swapped %.2fs with %.2fs\n", 
                                temp, 
                                array[j[0]]
                            ));
                        }
                        
                        // 更新显示
                        updateSortingVisual(visualPanel, array, j[0], j[0] + 1);
                        j[0]++;
                    } else {
                        i[0]++;
                        j[0] = 0;
                    }
                } else {
                    // 排序完成
                    ((Timer)e.getSource()).stop();
                    logArea.append("\nSorting completed!\n");
                    updateSortingVisual(visualPanel, array, -1, -1);
                }
            }
        });
        
        timer.start();
    }

    private void updateSortingVisual(JPanel panel, double[] array, int pos1, int pos2) {
        Graphics2D g = (Graphics2D)panel.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 清除面板
        g.setColor(panel.getBackground());
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        
        // 计算显示参数
        int width = panel.getWidth() / array.length - 10;
        int maxHeight = panel.getHeight() - 40;
        
        // 找出最大值用于归一化
        double maxValue = Arrays.stream(array).max().getAsDouble();
        
        // 绘制每个元素
        for (int i = 0; i < array.length; i++) {
            int x = i * (width + 10) + 10;
            int height = (int)(array[i] / maxValue * maxHeight);
            int y = panel.getHeight() - height - 20;
            
            // 设置颜色
            if (i == pos1 || i == pos2) {
                g.setColor(new Color(220, 0, 0));  // 当前比较的元素用红色
            } else {
                g.setColor(new Color(33, 33, 33));
            }
            
            // 绘制柱形
            g.fillRect(x, y, width, height);
            
            // 绘制数值
            g.setColor(Color.BLACK);
            g.drawString(String.format("%.2f", array[i]), x, panel.getHeight() - 5);
        }
    }

    private void drawTrackPositions(Graphics g) {
        try {
            // 获取当前位置
            int[] positions = new int[5];
            for (int i = 0; i < 5; i++) {
                String posText = positionFields[i].getText().trim();
                positions[i] = posText.isEmpty() ? 0 : Integer.parseInt(posText);
            }
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 计算赛道尺寸
            Dimension size = getSize();
            int padding = 40;
            // int availableWidth = size.width - (2 * padding);
            // int availableHeight = size.height - (2 * padding);
            int availableHeight=400;
            int availableWidth=700;

            logger.info("Available height: " + availableHeight);
            logger.info("Available width: " + availableWidth);
            
            // 目标宽高比 16:9
            double targetAspectRatio = 16.0 / 9.0;
            
            // 计算缩放后的尺寸
            int trackWidth, trackHeight;
            double availableAspectRatio = (double) availableWidth / availableHeight;
            
            if (availableAspectRatio > targetAspectRatio) {
                // 受高度限制
                trackHeight = availableHeight;
                trackWidth = (int) (trackHeight * targetAspectRatio);
            } else {
                // 受宽度限制
                trackWidth = availableWidth;
                trackHeight = (int) (trackWidth / targetAspectRatio);
            }
            
            // 计算居中位置
            int startX = padding + (availableWidth - trackWidth) / 2;
            int startY = padding + (availableHeight - trackHeight) / 2;
            
            // 绘制赛道外框
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(startX, startY, trackWidth, trackHeight, 20, 20);
            
            // 只有当所有位置都有效时才绘制
            if (Arrays.stream(positions).allMatch(p -> p > 0 && p <= 20)) {
                String[] drivers = {"HAM", "VER", "LEC", "NOR", "RUS"};
                Color[] driverColors = {
                    new Color(0, 210, 255),  // Mercedes Blue
                    new Color(6, 0, 239),    // Red Bull Blue
                    new Color(220, 0, 0),    // Ferrari Red
                    new Color(255, 135, 0),  // McLaren Orange
                    new Color(0, 210, 255)   // Mercedes Blue
                };
                
                for (int i = 0; i < 5; i++) {
                    // 计算位置
                    double x = startX + (trackWidth * (positions[i] - 1) / 19.0);
                    double y = startY + trackHeight / 2.0;
                    
                    // 调整标记大小以适应缩放后的赛道
                    int markerSize = Math.min(trackHeight / 12, 20);
                    
                    // 绘制车辆标记
                    g2.setColor(driverColors[i]);
                    g2.fillOval((int)x - markerSize/2, (int)y - markerSize/2, markerSize, markerSize);
                    
                    // 绘制车手代号
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, markerSize/2));
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(drivers[i]);
                    g2.drawString(drivers[i], (int)x - textWidth/2, (int)y + fm.getAscent()/2 - 2);
                }
            }
        } catch (NumberFormatException e) {
            // 忽略无效输入
        }
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBackground(new Color(220, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> returnToMainMenu());
        return backButton;
    }
    
    private void returnToMainMenu() {
        dispose();
        new MainMenuScreen();
    }
}