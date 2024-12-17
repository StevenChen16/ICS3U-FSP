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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JButton conceptsButton = new JButton("Concepts");
        conceptsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        conceptsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to Concepts screen
                new ConceptScreen();
            }
        });
        panel.add(conceptsButton);

        JButton activityButton = new JButton("Activity");
        activityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        activityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to Activity screen
                new ActivityScreen();
            }
        });
        panel.add(activityButton);

        JButton assessmentButton = new JButton("Assessment");
        assessmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        assessmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to Assessment screen
                new AssessmentScreen();
            }
        });
        panel.add(assessmentButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenuScreen();
    }
}
