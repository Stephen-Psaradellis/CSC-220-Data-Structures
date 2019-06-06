package prog05;

import java.util.Stack;
import prog02.GUI;
import prog02.UserInterface;

public class Parser {
    static final String OPERATORS = "()+-*/u^";
    static final int[] PRECEDENCE;
    static UserInterface ui;
    int index;
    String input;
    Stack<Double> numberStack;
    Stack<Character> operatorStack;

    public Parser() {
        input = null;
        index = 0;
        operatorStack = new Stack();
        numberStack = new Stack();
    }

    static {
        PRECEDENCE = new int[]{-1, -1, 1, 1, 2, 2, 3, 4};
        ui = new GUI();
    }

    boolean atEndOfInput() {
        while (index < input.length() && Character.isWhitespace(input.charAt(index))) {
            index++;
        }
        return index == input.length();
    }

    boolean isNumber() {
        return Character.isDigit(input.charAt(index));
    }

    double getNumber() {
        int index2 = index;
        while (index2 < input.length() && (Character.isDigit(input.charAt(index2)) || input.charAt(index2) == '.')) {
            index2++;
        }
        double d = 0.0d;
        try {
            d = Double.parseDouble(input.substring(index, index2));
        } catch (Exception e) {
            System.out.println(e);
        }
        index = index2;
        return d;
    }

    char getOperator() {
        String str = input;
        int i = index;
        index = i + 1;
        char op = str.charAt(i);
        if (OPERATORS.indexOf(op) == -1) {
            System.out.println("Operator " + op + " not recognized.");
        }
        return op;
    }

    String numberStackToString() {
        String s = "numberStack: ";
        Stack<Double> helperStack = new Stack();
        while (!numberStack.empty()) {
            helperStack.push((Double) numberStack.pop());
        }
        while (!helperStack.empty()) {
            s = new StringBuilder(String.valueOf(s)).append(" ").append(numberStack.push((Double) helperStack.pop())).toString();
        }
        return s;
    }

    String operatorStackToString() {
        String s = "operatorStack: ";
        Stack<Character> helperStack = new Stack();
        while (!operatorStack.empty()) {
            helperStack.push((Character) operatorStack.pop());
        }
        while (!helperStack.empty()) {
            s = new StringBuilder(String.valueOf(s)).append(" ").append(operatorStack.push((Character) helperStack.pop())).toString();
        }
        return s;
    }

    void displayStacks() {
        ui.sendMessage(numberStackToString() + "\n" + operatorStackToString());
    }

    double evaluate(String expr) {
        input = expr;
        index = 0;
        while (!operatorStack.empty()) {
            operatorStack.pop();
        }
        while (!numberStack.empty()) {
            numberStack.pop();
        }
        boolean previousWasNorP = false;
        while (!atEndOfInput()) {
            if (isNumber()) {
                numberStack.push(Double.valueOf(getNumber()));
                displayStacks();
                previousWasNorP = true;
            } else {
                char op = getOperator();
                if (op != '-' || previousWasNorP) {
                    processOperator(op);
                } else {
                    processOperator('u');
                }
                displayStacks();
                if (op == ')') {
                    previousWasNorP = true;
                } else {
                    previousWasNorP = false;
                }
            }
        }
        while (!operatorStack.empty()) {
            evaluateOperator();
        }
        return ((Double) numberStack.pop()).doubleValue();
    }

    int precedence(char op) {
        return PRECEDENCE[OPERATORS.indexOf(op)];
    }

    double evaluateOperator(double a, char op, double b) {
        switch (op) {
            case '*':
                return a * b;
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '/':
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                System.out.println("Unknown operator " + op);
                return 0.0d;
        }
    }

    void evaluateOperator() {
        char op = ((Character) operatorStack.pop()).charValue();
        if (op == 'u') {
            numberStack.push(Double.valueOf(-((Double) numberStack.pop()).doubleValue()));
        } else {
            double b = ((Double) numberStack.pop()).doubleValue();
            numberStack.push(Double.valueOf(evaluateOperator(((Double) numberStack.pop()).doubleValue(), op, b)));
        }
        displayStacks();
    }

    void processOperator(char op) {
        if (op == '(' || op == 'u') {
            operatorStack.push(Character.valueOf(op));
        } else if (op == ')') {
            while (((Character) operatorStack.peek()).charValue() != '(') {
                evaluateOperator();
            }
            operatorStack.pop();
        } else {
            while (!operatorStack.empty() && precedence(op) <= precedence(((Character) operatorStack.peek()).charValue())) {
                evaluateOperator();
            }
            operatorStack.push(Character.valueOf(op));
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        while (true) {
            String line = ui.getInfo("Enter arithmetic expression or cancel.");
            if (line != null) {
                try {
                    ui.sendMessage(new StringBuilder(String.valueOf(line)).append(" = ").append(parser.evaluate(line)).toString());
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                return;
            }
        }
    }
}