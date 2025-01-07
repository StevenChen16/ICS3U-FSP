import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AssessmentScreen extends JFrame {
    
    // Constants for window size
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    // UI Components
    private JPanel mainPanel;
    private JPanel questionPanel;
    private JPanel answerPanel;
    private JLabel questionLabel;
    private JLabel progressLabel;
    private JButton nextButton;
    private JButton submitButton;
    private JButton backButton;
    
    // Question tracking
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private JRadioButton[] answerButtons;
    
    public AssessmentScreen() {
        initializeWindow();
        loadQuestions();
        createComponents();
        setupLayout();
        showQuestion(0);
        setVisible(true);
    }
    
    private void initializeWindow() {
        setTitle("F1 Array Racing - Assessment");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set background image
        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(
                getClass().getResource("/resources/background1.jpg")
            );
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
    }
    
    private void loadQuestions() {
        questions = new ArrayList<>();
        try {
            questions = QuestionLoader.loadQuestions("resources/questions.json");
            if (questions.isEmpty()) {
                createDefaultQuestions();
            }
        } catch (Exception e) {
            e.printStackTrace();
            createDefaultQuestions();
        }
    }
    
    private void createDefaultQuestions() {
        questions.add(new Question(
            1,
            "multiple_choice",
            "array_access",
            "In F1 race analysis, we store 5 lap times in array lapTimes[]. What is correct way to access last lap time?",
            new String[]{
                "lapTimes[5]",
                "lapTimes[4]",
                "lapTimes[-1]",
                "lapTimes.last()"
            },
            1,
            "Array index starts from 0, so for array of size 5, last index is 4",
            "medium"
        ));
    }
    
    private void createComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        
        // Create question panel
        questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout(10, 10));
        questionPanel.setOpaque(false);
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(Color.WHITE);
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        
        // Create answer panel
        answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.setOpaque(false);
        
        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        nextButton = createStyledButton("Next Question");
        submitButton = createStyledButton("Submit Quiz");
        backButton = createStyledButton("Back to Main Menu");
        
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        
        // Create progress label
        progressLabel = new JLabel();
        progressLabel.setForeground(Color.WHITE);
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(220, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> handleButtonClick(text));
        return button;
    }
    
    private void setupLayout() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create a container panel for question and answer panels
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        
        // Add some vertical spacing
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Add question panel
        questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(questionPanel);
        
        // Add some vertical spacing between question and answers
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Add answer panel
        answerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(answerPanel);
        
        // Add components to main panel
        mainPanel.add(progressLabel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add button panel at bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(createNavigationPanel(), BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Set initial button states
        submitButton.setEnabled(false);
        
        add(mainPanel);
    }
    
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setOpaque(false);
        navPanel.add(backButton);
        navPanel.add(nextButton);
        navPanel.add(submitButton);
        return navPanel;
    }
    
    private void showQuestion(int index) {
        currentQuestionIndex = index;
        Question question = questions.get(index);
        
        String displayText = String.format("<html><div style='width: 400px;'>" +
            "<p style='color: #FFD700;'>Category: %s</p>" +
            "<p style='color: #FF4500;'>Difficulty: %s</p>" +
            "<p style='color: white;'>%s</p>" +
            "</div></html>",
            question.getCategory(),
            question.getDifficulty(),
            question.getQuestionText()
        );
        
        questionLabel.setText(displayText);
        
        // Clear previous answers
        answerPanel.removeAll();
        
        // Create new radio buttons for answers
        ButtonGroup group = new ButtonGroup();
        answerButtons = new JRadioButton[question.getOptions().length];
        
        for (int i = 0; i < question.getOptions().length; i++) {
            answerButtons[i] = new JRadioButton(question.getOptions()[i]);
            answerButtons[i].setForeground(Color.WHITE);
            answerButtons[i].setOpaque(false);
            group.add(answerButtons[i]);
            answerPanel.add(answerButtons[i]);
        }
        
        progressLabel.setText(String.format("Question %d of %d", index + 1, questions.size()));
        
        nextButton.setEnabled(index < questions.size() - 1);
        submitButton.setEnabled(index == questions.size() - 1);
        
        answerPanel.revalidate();
        answerPanel.repaint();
    }
    
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Next Question":
                if (isAnswerSelected()) {
                    checkAnswer();
                    showQuestion(currentQuestionIndex + 1);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Please select an answer before continuing.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }
                break;
                
            case "Submit Quiz":
                if (isAnswerSelected()) {
                    checkAnswer();
                    showResults();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Please select an answer before submitting.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }
                break;
                
            case "Back to Main Menu":
                returnToMainMenu();
                break;
        }
    }
    
    private boolean isAnswerSelected() {
        for (JRadioButton button : answerButtons) {
            if (button.isSelected()) {
                return true;
            }
        }
        return false;
    }
    
    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        for (int i = 0; i < answerButtons.length; i++) {
            if (answerButtons[i].isSelected() && i == currentQuestion.getCorrectAnswer()) {
                score++;
                break;
            }
        }
    }
    
    private void showResults() {
        double percentage = (score * 100.0) / questions.size();
        String message = String.format(
            "Quiz Complete!\n\n" +
            "Your Score: %d out of %d\n" +
            "Percentage: %.1f%%\n\n",
            score, questions.size(), percentage
        );
        
        if (percentage >= 80) {
            message += "Excellent! You have a good understanding of arrays!";
        } else if (percentage >= 60) {
            message += "Good job! But there's room for improvement.";
        } else {
            message += "You might want to review the concepts again.";
        }
        
        JOptionPane.showMessageDialog(this,
            message,
            "Quiz Results",
            JOptionPane.INFORMATION_MESSAGE);
        
        submitButton.setEnabled(false);
    }
    
    private void returnToMainMenu() {
        dispose();
        new MainMenuScreen();
    }
}