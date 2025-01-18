// Question.java
public class Question {
    private int id;
    private String type;
    private String category;
    private String questionText;
    private String[] options;
    private int correctAnswer;
    private String explanation;
    private String difficulty;
    
    // Constructor
    public Question(int id, String type, String category, String questionText, 
                   String[] options, int correctAnswer, String explanation, String difficulty) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.difficulty = difficulty;
    }
    
    // Getters
    public int getId() { return id; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public String getExplanation() { return explanation; }
    public String getDifficulty() { return difficulty; }
}