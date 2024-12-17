import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivityScreen extends JPanel {
    public ActivityScreen() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Activity: Practice Array Operations");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JTextArea activityText = new JTextArea();
        activityText.setText("Practice Exercises:\n\n" +
                "1. Create an array of integers with 5 elements.\n" +
                "2. Modify the second element to 10.\n" +
                "3. Sort the array in ascending order.\n" +
                "4. Find the index of the number 4 in the array.\n\n" +
                "Enter your code below and click 'Run' to see the result.\n");
        activityText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(activityText);
        add(scrollPane, BorderLayout.CENTER);

        JTextArea codeInput = new JTextArea();
        JScrollPane codeScrollPane = new JScrollPane(codeInput);
        add(codeScrollPane, BorderLayout.SOUTH);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeInput.getText();
                // Implement code execution and feedback system here
                JOptionPane.showMessageDialog(null, "Code executed: " + code);
            }
        });
        add(runButton, BorderLayout.EAST);
    }
}
