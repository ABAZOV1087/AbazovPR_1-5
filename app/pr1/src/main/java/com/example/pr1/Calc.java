package com.example.pr1;

import java.util.Scanner;

public class Calc {
    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);
        String command;
        System.out.println("=== KONSOLNYJ KALKULYATOR ===");
        do {
            System.out.print("\nUkazhite dejstvie (+, -, *, /, ^ dlya kvadrata, q dlya vyhoda): ");
            command = inputReader.next().trim();
            if (command.equalsIgnoreCase("q")) {
                System.out.println("Rabota kalkulyatora zavershena.");
                break;
            }
            if (command.equals("^") || command.equalsIgnoreCase("sq")) {
                System.out.print("Vvedite chislo dlya vozvedeniya v kvadrat: ");
                while (!inputReader.hasNextDouble()) {
                    System.out.print("Nekorrektnyj vvod! Vvedite chislo: ");
                    inputReader.next();
                }
                double val = inputReader.nextDouble();
                printResult(square(val));
                continue;
            }
            if (!command.equals("+") && !command.equals("-") && !command.equals("*") && !command.equals("/")) {
                System.out.println("Neizvestnaya komanda! Dostupny: +, -, *, /, ^, q.");
                continue;
            }
            System.out.print("Vvedite pervyj operand: ");
            while (!inputReader.hasNextDouble()) {
                System.out.print("Nekorrektnyj vvod! Vvedite chislo: ");
                inputReader.next();
            }
            double firstOperand = inputReader.nextDouble();
            System.out.print("Vvedite vtoroj operand: ");
            while (!inputReader.hasNextDouble()) {
                System.out.print("Nekorrektnyj vvod! Vvedite chislo: ");
                inputReader.next();
            }
            double secondOperand = inputReader.nextDouble();
            calculate(firstOperand, secondOperand, command.charAt(0));
        } while (true);
        inputReader.close();
    }

    public static double add(double x, double y) {
        return x + y;
    }

    public static double subtract(double x, double y) {
        return x - y;
    }

    public static double multiply(double x, double y) {
        return x * y;
    }

    public static double divide(double x, double y) {
        if (y == 0.0) {
            System.out.println("[Oshibka]: Delenie na nol' nevozmozhno!");
            return Double.NaN;
        }
        return x / y;
    }

    public static double square(double x) {
        return x * x;
    }

    public static void calculate(double a, double b, char operator) {
        double computedValue;
        switch (operator) {
            case '+':
                computedValue = add(a, b);
                printResult(computedValue);
                break;
            case '-':
                computedValue = subtract(a, b);
                printResult(computedValue);
                break;
            case '*':
                computedValue = multiply(a, b);
                printResult(computedValue);
                break;
            case '/':
                computedValue = divide(a, b);
                if (!Double.isNaN(computedValue)) {
                    printResult(computedValue);
                }
                break;
            default:
                System.out.println("[Oshibka]: Nepodderzhivaemyj operator.");
        }
    }

    public static void printResult(double value) {
        System.out.printf(">>> Itogovoe znachenie: %.4f%n", value);
    }
}
