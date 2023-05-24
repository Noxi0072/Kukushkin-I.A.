package ru.Vsuet.mtx;

import java.util.*;

public class Calculate {

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '|' || c == '%';
    }
    
    private double executeOperation(double num1, double num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            case '%':
                return num1 % num2;
            case '^':
                return Math.pow(num1, num2);
            case '|':
                return Math.sqrt(num1 * num1 + num2 * num2);
            default:
                throw new IllegalArgumentException("Неверный оператор: " + operator);
        }
    }

    public double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (Character.isDigit(currentChar)) {
                StringBuilder numBuilder = new StringBuilder();
                numBuilder.append(currentChar);
                while (i + 1 < expression.length() && Character.isDigit(expression.charAt(i + 1))) {
                    numBuilder.append(expression.charAt(i + 1));
                    i++;
                }

                double number = Double.parseDouble(numBuilder.toString());
                numbers.push(number);
                operators.push(currentChar);
            } else if (currentChar == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    char operator = operators.pop();
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();
                    double result = executeOperation(num1, num2, operator);
                    numbers.push(result);
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop();
                }
            } else if (isOperator(currentChar)) {
                while (!operators.isEmpty() && getOperatorPriority(operators.peek()) >= getOperatorPriority(currentChar)) {
                    char operator = operators.pop();
                    double num2 = numbers.pop();
                    double num1 = numbers.pop();
                    double result = executeOperation(num1, num2, operator);
                    numbers.push(result);
                }
                operators.push(currentChar);
            }
        }
        while (!operators.isEmpty()) {
            char operator = operators.pop();
            double num2 = numbers.pop();
            double num1 = numbers.pop();
            double result = executeOperation(num1, num2, operator);
            numbers.push(result);
        }
        return numbers.pop();
    }

    private int getOperatorPriority(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
            case '|':
                return 3;
            default:
                return 0;
        }
    }
}
