import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private Stack<Double> values;
    private Stack<Character> operations;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        currentInput = new StringBuilder();
        values = new Stack();
        operations = new Stack();
        
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5, 5, 5));
        
        String[] buttonLabels = {
            "7", "8", "9", "/", "C",
            "4", "5", "6", "*", "√",
            "1", "2", "3", "-", "^",
            "0", ".", "=", "+", "log",
            "sin", "cos", "tan", "(", ")"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            panel.add(button);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(display, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("\\d") || command.equals(".")) {
            currentInput.append(command);
            display.setText(currentInput.toString());
        } else if (command.equals("C")) {
            currentInput.setLength(0);
            display.setText("");
        } else if (command.equals("=")) {
            try {
                double result = evaluateExpression(currentInput.toString());
                display.setText(String.valueOf(result));
                currentInput.setLength(0);
                currentInput.append(result);
            } catch (Exception ex) {
                display.setText("Error");
                currentInput.setLength(0);
            }
        } else if (command.equals("√")) {
            try {
                double result = Math.sqrt(Double.parseDouble(currentInput.toString()));
                display.setText(String.valueOf(result));
                currentInput.setLength(0);
                currentInput.append(result);
            } catch (Exception ex) {
                display.setText("Error");
                currentInput.setLength(0);
            }
        } else if (command.equals("log")) {
            try {
                double result = Math.log10(Double.parseDouble(currentInput.toString()));
                display.setText(String.valueOf(result));
                currentInput.setLength(0);
                currentInput.append(result);
            } catch (Exception ex) {
                display.setText("Error");
                currentInput.setLength(0);
            }
        } else if (command.equals("sin") || command.equals("cos") || command.equals("tan")) {
            try {
                double result = 0;
                double value = Math.toRadians(Double.parseDouble(currentInput.toString()));
                if (command.equals("sin")) result = Math.sin(value);
                if (command.equals("cos")) result = Math.cos(value);
                if (command.equals("tan")) result = Math.tan(value);
                display.setText(String.valueOf(result));
                currentInput.setLength(0);
                currentInput.append(result);
            } catch (Exception ex) {
                display.setText("Error");
                currentInput.setLength(0);
            }
        } else {
            currentInput.append(" ").append(command).append(" ");
            display.setText(currentInput.toString());
        }
    }

    private double evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");
        for (String token : tokens) {
            if (isNumber(token)) {
                values.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                while (!operations.isEmpty() && hasPrecedence(token.charAt(0), operations.peek())) {
                    values.push(applyOperation(operations.pop(), values.pop(), values.pop()));
                }
                operations.push(token.charAt(0));
            } else if (token.equals("(")) {
                operations.push('(');
            } else if (token.equals(")")) {
                while (operations.peek() != '(') {
                    values.push(applyOperation(operations.pop(), values.pop(), values.pop()));
                }
                operations.pop();
            }
        }

        while (!operations.isEmpty()) {
            values.push(applyOperation(operations.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(char op) {
        return op == '+' || op == '-' || op == '*' || op == '/' || op == '^';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        if (op1 == '^' && (op2 != '^')) return false;
        return true;
    }

    private double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '^': return Math.pow(a, b);
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ScientificCalculator calculator = new ScientificCalculator();
                calculator.setVisible(true);
            }
        });
    }
}
