// CodeBlock.java
public class CodeBlock {
    private String fileName;    // 代码所属文件
    private String code;        // 代码内容
    private String explanation; // 对应的解释
    
    // Getters and setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}