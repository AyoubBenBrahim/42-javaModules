package ex03;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int studentsData = 0;
        int currentWeek = 1;
        while (true) {
            System.out.print("-> ");
            String weekStr = scanner.nextLine();
           
            if (weekStr.equals("42") || currentWeek >= 18) {
                break;
            }
            String expectedPrefix = "week " + currentWeek;

            if (!weekStr.equals(expectedPrefix)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            int min = 9;
            scanner.useDelimiter(" ");
            for (int i = 0; i < 5; i++) {
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                     System.out.println("input: " + input);
                    min = (input < min) ? input : min;
                    if (input > 9 || input <= 1) {
                        System.err.println("IllegalArgument");
                        System.exit(-1);
                    }
                }
                scanner.nextLine();
            }
            studentsData = studentsData * 10 + min;
            currentWeek++;
        }

        for (int i = 0; i < currentWeek; i++) {
            for (int temp = studentsData; temp > 0; temp /= 10) {
                int digit = temp % 10;
                System.out.print(currentWeek);
                for (int j = 0; j < digit; j++) {
                    System.out.print("=");
                }
                System.out.print(">");
            }
        }

        scanner.close();

    }

}