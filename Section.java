// Section.java
import java.util.List;

public class Section {
    private String title;       // Section title
    private String description; // Section description
    private List<CodeExample> examples; // Examples in this section
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<CodeExample> getExamples() { return examples; }
    public void setExamples(List<CodeExample> examples) { this.examples = examples; }
}