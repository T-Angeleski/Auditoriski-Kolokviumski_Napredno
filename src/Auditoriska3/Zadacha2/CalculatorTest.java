package Auditoriska3.Zadacha2;

import java.util.Scanner;

public class CalculatorTest {

    public static char getCharLower(String line) {
        if(line.trim().length() > 0) {
            return Character.toLowerCase(line.charAt(0));
        } else return '?';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());

            while(true) {
                String line = scanner.nextLine();
                char choice = getCharLower(line);
                if(choice == 'r') {
                    System.out.println(String.format("Final result = %f",
                            calculator.getResult()));
                    break;
                }

                String[] parts = line.split("\\s+"); //edno ili povekje prazni mesta
                char operator = parts[0].charAt(0); // parts[0] is string
                double value = Double.parseDouble(parts[1]);

                //treba try/catch oti vo execute frlame exception
                try {
                    String result = calculator.execute(operator, value);
                    System.out.println(result);
                    System.out.println(calculator);
                } catch (UnknownOperatorException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("(Y/N)");
            String line = scanner.nextLine();
            char choice = getCharLower(line);
            if(choice == 'n') break; // if entered No
        }
    }
}
