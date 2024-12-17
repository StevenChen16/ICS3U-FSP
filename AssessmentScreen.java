import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssessmentScreen extends JPanel {
    public AssessmentScreen() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Assessment: Test Your Knowledge");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JTextArea assessmentText = new JTextArea();
        assessmentText.setText("Multiple-Choice Questions:\n\n" +
                "1. What is the index of the first element in an array?\n" +
                "a) 0\n" +
                "b) 1\n" +
                "c) -1\n" +
                "d) None of the above\n\n" +
                "2. How do you find the length of an array?\n" +
                "a) array.size()\n" +
                "b) array.length\n" +
                "c) array.length()\n" +
                "d) array.size\n\n" +
                "Basic Coding Challenge:\n\n" +
                "Write a method to reverse an array of integers.\n\n" +
                "Enter your code below and click 'Submit' to see the result.\n");
        assessmentText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(assessmentText);
        add(scrollPane, BorderLayout.CENTER);

        JTextArea codeInput = new JTextArea();
        JScrollPane codeScrollPane = new JScrollPane(codeInput);
        add(codeScrollPane, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeInput.getText();
                // Implement code execution and grading system here
                JOptionPane.showMessageDialog(null, "Code submitted: " + code);
            }
        });
        add(submitButton, BorderLayout.EAST);
    }
}
