import javax.swing.*;
import java.awt.*;

public class ConceptScreen extends JPanel {
    public ConceptScreen() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Concepts: Java Arrays and Array Operations");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JTextArea conceptText = new JTextArea();
        conceptText.setText("Array Theory:\n\n" +
                "An array is a data structure that can hold multiple values of the same type. " +
                "Each value is stored at a specific index, starting from 0.\n\n" +
                "Example:\n" +
                "int[] numbers = {1, 2, 3, 4, 5};\n\n" +
                "Array Operations:\n\n" +
                "1. Accessing elements:\n" +
                "int firstNumber = numbers[0]; // 1\n\n" +
                "2. Modifying elements:\n" +
                "numbers[1] = 10; // {1, 10, 3, 4, 5}\n\n" +
                "3. Iterating through an array:\n" +
                "for (int i = 0; i < numbers.length; i++) {\n" +
                "    System.out.println(numbers[i]);\n" +
                "}\n\n" +
                "4. Array length:\n" +
                "int length = numbers.length; // 5\n\n" +
                "5. Copying an array:\n" +
                "int[] copy = Arrays.copyOf(numbers, numbers.length);\n\n" +
                "6. Sorting an array:\n" +
                "Arrays.sort(numbers); // {1, 3, 4, 5, 10}\n\n" +
                "7. Searching an array:\n" +
                "int index = Arrays.binarySearch(numbers, 4); // 2\n\n" +
                "Visual Demonstrations:\n\n" +
                "1. Array Initialization:\n" +
                "[1, 2, 3, 4, 5]\n\n" +
                "2. Array Modification:\n" +
                "[1, 10, 3, 4, 5]\n\n" +
                "3. Array Sorting:\n" +
                "[1, 3, 4, 5, 10]\n\n" +
                "4. Array Searching:\n" +
                "Index of 4: 2\n\n");
        conceptText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(conceptText);
        add(scrollPane, BorderLayout.CENTER);
    }
}
