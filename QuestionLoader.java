// QuestionLoader.java
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionLoader {
    public static List<Question> loadQuestions(String filename) {
        List<Question> questions = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filename));
            JSONArray questionArray = (JSONArray) jsonObject.get("questions");
            
            for (Object obj : questionArray) {
                JSONObject questionObj = (JSONObject) obj;
                JSONArray optionsArr = (JSONArray) questionObj.get("options");
                String[] options = new String[optionsArr.size()];
                for (int i = 0; i < optionsArr.size(); i++) {
                    options[i] = (String) optionsArr.get(i);
                }
                
                questions.add(new Question(
                    ((Long) questionObj.get("id")).intValue(),
                    (String) questionObj.get("type"),
                    (String) questionObj.get("category"),
                    (String) questionObj.get("questionText"),
                    options,
                    ((Long) questionObj.get("correctAnswer")).intValue(),
                    (String) questionObj.get("explanation"),
                    (String) questionObj.get("difficulty")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
}