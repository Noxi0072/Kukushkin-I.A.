package ru.Vsuet.mtx;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculate calculator = new Calculate();
        HistoryManager historyManager = new HistoryManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите математическое выражение (или 'quit' для завершения): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                break;
            }
            double result = calculator.evaluateExpression(input);
            System.out.println("Результат: " + result);

            historyManager.saveExpression(input, result);
        }
        List<String> history = historyManager.loadHistory();
        System.out.println("История вычислений:");
        for (String entry : history) {
            System.out.println(entry);
        }
    }
}
