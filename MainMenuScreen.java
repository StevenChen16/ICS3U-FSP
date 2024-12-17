import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScreen extends JFrame {
    public MainMenuScreen() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton conceptButton = new JButton("Concepts");
        JButton activityButton = new JButton("Activity");
        JButton assessmentButton = new JButton("Assessment");

        conceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConceptScreen().setVisible(true);
                dispose();
            }
        });

        activityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActivityScreen().setVisible(true);
                dispose();
            }
        });

        assessmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AssessmentScreen().setVisible(true);
                dispose();
            }
        });

        panel.add(conceptButton);
        panel.add(activityButton);
        panel.add(assessmentButton);

        add(panel);
    }

    public void showScreen() {
        setVisible(true);
    }
}
