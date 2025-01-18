// CodeBlock.java
public class CodeBlock {
    private String fileName;    // The file that the code belongs to
    private String code;        // Content of the coding block
    private String explanation; // Explanation of the coding block
    
    // Getters and setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}