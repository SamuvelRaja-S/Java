import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttractiveFormDesign extends JFrame {

    public AttractiveFormDesign() {
        setTitle("Attractive Form Design");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Create a title label
        JLabel titleLabel = new JLabel("Student Application Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 10, 20, 10);
        panel.add(titleLabel, constraints);

        // Create labels and text fields
        String[] labels = {"Name:", "Email:", "Phone:", "Address:"};
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            constraints.gridx = 0;
            constraints.gridy = i + 1;
            constraints.gridwidth = 1;
            constraints.insets = new Insets(5, 10, 5, 5);
            panel.add(label, constraints);

            JTextField textField = new JTextField(20);
            constraints.gridx = 1;
            constraints.insets = new Insets(5, 5, 5, 10);
            panel.add(textField, constraints);
        }

        // Create a Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(51, 153, 255)); // Customize the button color
        submitButton.setForeground(Color.WHITE); // Set text color
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle form submission
                JOptionPane.showMessageDialog(AttractiveFormDesign.this, "Form submitted!");
            }
        });
        constraints.gridx = 0;
        constraints.gridy = labels.length + 1;
        constraints.gridwidth = 2;
        panel.add(submitButton, constraints);

        add(panel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttractiveFormDesign form = new AttractiveFormDesign();
            form.setVisible(true);
        });
    }
}
