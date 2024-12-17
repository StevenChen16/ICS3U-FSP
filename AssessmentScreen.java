import javax.swing.*;
import java.awt.*;

public class AssessmentScreen extends JFrame {
    public AssessmentScreen() {
        setTitle("Assessment");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("F1-Themed Quiz");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea assessmentText = new JTextArea();
        assessmentText.setText("Conceptual Questions:\n" +
                "1. Define the purpose of arrays using examples like lap time storage.\n" +
                "2. Explain the difference between 1D and 2D arrays.\n\n" +
                "Code Completion:\n" +
                "Complete the following code to calculate average lap times:\n" +
                "int[] lapTimes = {90, 88, 87, 89, 91};\n" +
                "int sum = 0;\n" +
                "for (int time : lapTimes) {\n" +
                "    sum += time;\n" +
                "}\n" +
                "double average = sum / (double) lapTimes.length;\n" +
                "System.out.println(\"Average lap time: \" + average);\n\n" +
                "Debugging:\n" +
                "Fix the following code that processes array data (e.g., lap times):\n" +
                "int[] lapTimes = {90, 88, 87, 89, 91};\n" +
                "int fastestLap = lapTimes[0];\n" +
                "for (int i = 1; i < lapTimes.length; i++) {\n" +
                "    if (lapTimes[i] < fastestLap) {\n" +
                "        fastestLap = lapTimes[i];\n" +
                "    }\n" +
                "}\n" +
                "System.out.println(\"Fastest lap time: \" + fastestLap);");
        assessmentText.setEditable(false);
        assessmentText.setLineWrap(true);
        assessmentText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(assessmentText);
        scrollPane.setPreferredSize(new Dimension(580, 300));

        panel.add(titleLabel);
        panel.add(scrollPane);

        add(panel);
    }

    public void showScreen() {
        setVisible(true);
    }
}
