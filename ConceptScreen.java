import javax.swing.*;
import java.awt.*;

public class ConceptScreen extends JFrame {
    public ConceptScreen() {
        setTitle("Concepts");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("F1-Themed Array Concepts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea conceptText = new JTextArea();
        conceptText.setText("1D Arrays:\n" +
                "Represent an F1 team's race times over multiple laps.\n" +
                "Example:\n" +
                "int[] lapTimes = {90, 88, 87, 89, 91};\n\n" +
                "2D Arrays:\n" +
                "Represent lap times for multiple drivers across different races.\n" +
                "Example:\n" +
                "int[][] raceTimes = {\n" +
                "    {90, 88, 87, 89, 91},\n" +
                "    {92, 89, 88, 90, 93},\n" +
                "    {91, 87, 86, 88, 90}\n" +
                "};\n\n" +
                "Array Operations:\n" +
                "Calculate averages (e.g., average lap time), find the fastest lap, or sort lap times.\n" +
                "Example:\n" +
                "int sum = 0;\n" +
                "for (int time : lapTimes) {\n" +
                "    sum += time;\n" +
                "}\n" +
                "double average = sum / (double) lapTimes.length;\n" +
                "System.out.println(\"Average lap time: \" + average);");
        conceptText.setEditable(false);
        conceptText.setLineWrap(true);
        conceptText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(conceptText);
        scrollPane.setPreferredSize(new Dimension(580, 300));

        panel.add(titleLabel);
        panel.add(scrollPane);

        add(panel);
    }

    public void showScreen() {
        setVisible(true);
    }
}
