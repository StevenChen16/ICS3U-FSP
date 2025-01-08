// ConceptsLoader.java
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ConceptsLoader {
    private static final String JSON_FILE = "resources/concepts.json";
    private List<Section> sections;
    
    public ConceptsLoader() {
        loadConcepts();
    }
    
    private void loadConcepts() {
        sections = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(JSON_FILE));
            JSONArray sectionsArray = (JSONArray)jsonObject.get("sections");
            
            for (Object sectionObj : sectionsArray) {
                JSONObject sectionJson = (JSONObject)sectionObj;
                Section section = new Section();
                
                section.setTitle((String)sectionJson.get("title"));
                section.setDescription((String)sectionJson.get("description"));
                
                List<CodeExample> examples = new ArrayList<>();
                JSONArray examplesArray = (JSONArray)sectionJson.get("examples");
                
                for (Object exampleObj : examplesArray) {
                    JSONObject exampleJson = (JSONObject)exampleObj;
                    CodeExample example = new CodeExample();
                    
                    example.setTitle((String)exampleJson.get("title"));
                    example.setDescription((String)exampleJson.get("description"));
                    
                    List<CodeBlock> codeBlocks = new ArrayList<>();
                    JSONArray codeBlocksArray = (JSONArray)exampleJson.get("codeBlocks");
                    
                    for (Object blockObj : codeBlocksArray) {
                        JSONObject blockJson = (JSONObject)blockObj;
                        CodeBlock block = new CodeBlock();
                        
                        block.setFileName((String)blockJson.get("fileName"));
                        block.setCode((String)blockJson.get("code"));
                        block.setExplanation((String)blockJson.get("explanation"));
                        
                        codeBlocks.add(block);
                    }
                    example.setCodeBlocks(codeBlocks);
                    examples.add(example);
                }
                section.setExamples(examples);
                sections.add(section);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Section> getSections() {
        return sections;
    }
}
