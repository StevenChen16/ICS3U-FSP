// Section.java
import java.util.List;

public class Section {
    private String title;       // 章节标题
    private String description; // 章节描述
    private List<CodeExample> examples; // 示例列表
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<CodeExample> getExamples() { return examples; }
    public void setExamples(List<CodeExample> examples) { this.examples = examples; }
}