package ex03;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int studentsData = 0;
        int currentWeek = 1;
        while (true) {
            String weekStr = scanner.nextLine();

            if (weekStr.equals("42") || currentWeek >= 18) {
                break;
            }
            String expectedPrefix = "week " + currentWeek;

            if (!weekStr.equals(expectedPrefix)) {
                System.err.println(" week IllegalArgument");
                System.exit(-1);
            }

            int min = 9;

            boolean isError = false;

            int count = 0;
            while (scanner.hasNextInt() && count < 5) {
                int inputDigit = scanner.nextInt();
                min = (inputDigit < min) ? inputDigit : min;
                if (inputDigit < 1 || inputDigit > 9) {
                    isError = true;
                    break;
                }
                count++;
            }
            if (isError) {
                System.err.println("input digit IllegalArgument");
                System.exit(-1);
            }
            scanner.nextLine();

            studentsData = studentsData * 10 + min;
            currentWeek++;

        }
        int division = (int) Math.pow(10, currentWeek - 2);

        currentWeek = 1;
        while (division > 0) {
            System.out.print("week " + currentWeek + " ");
            int digit = studentsData / division;
            studentsData = studentsData % division;
            division = division / 10;
            for (int j = 0; j < digit; j++) {
                System.out.print("=");
            }
            System.out.println(">");
            currentWeek++;
        }
        scanner.close();

    }
}