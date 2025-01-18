// CodeExample.java
import java.util.List;
import java.util.Map;

public class CodeExample {
    private String title;           // 示例标题
    private String description;     // 示例描述
    private List<CodeBlock> codeBlocks;  // 代码块列表
    private Map<String, String> tooltips; // 悬浮提示映射
    
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