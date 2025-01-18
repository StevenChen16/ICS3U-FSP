// CodeExample.java
import java.util.List;
import java.util.Map;

public class CodeExample {
    private String title;           // Example title
    private String description;     // Example description
    private List<CodeBlock> codeBlocks;  // List of code blocks
    private Map<String, String> tooltips; // Map of tooltips
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<CodeBlock> getCodeBlocks() { return codeBlocks; }
    public void setCodeBlocks(List<CodeBlock> codeBlocks) { this.codeBlocks = codeBlocks; }
    public Map<String, String> getTooltips() { return tooltips; }
    public void setTooltips(Map<String, String> tooltips) { this.tooltips = tooltips; }
}