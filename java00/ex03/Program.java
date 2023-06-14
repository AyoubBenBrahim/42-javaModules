package ex03;

import java.util.Scanner;

public class Program {

    private static int getStudentsGrades(String digitsStream, int studentsData) {

        Scanner scanner = new Scanner(digitsStream);
        int digitCount = 0;
        int minGrade = 9;
        scanner.useDelimiter(" ");
        boolean isError = false;
        while (scanner.hasNext() && digitCount < 5) {
            if (scanner.hasNextInt()) {
                int digit = scanner.nextInt();
                minGrade = (digit < minGrade) ? digit : minGrade;
                if (digit < 1 || digit > 9) {
                    isError = true;
                    break;
                }
            } else {
                isError = true;
                break;
            }
            digitCount++;
        }
        if (isError || digitCount != 5 || scanner.hasNext()) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }

        scanner.close();

        return (studentsData * 10 + minGrade);
    }

    private static void chartSudentsGrades(int studentsData, int totalWeeks) {
        int weekCounter = 1;
        int division = (int) Math.pow(10, totalWeeks - 2);
        while (division > 0) {
            System.out.print("week " + weekCounter + " ");
            int digit = studentsData / division;
            studentsData = studentsData % division;
            division = division / 10;
            for (int j = 0; j < digit; j++) {
                System.out.print("=");
            }
            System.out.println(">");
            weekCounter++;
        }
    }

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
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            String digitsStream = scanner.nextLine();
            studentsData = getStudentsGrades(digitsStream, studentsData);
            currentWeek++;
        }

        scanner.close();

        chartSudentsGrades(studentsData, currentWeek);
    }

}