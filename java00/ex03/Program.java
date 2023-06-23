package ex03;

import java.util.Scanner;

public class Program {

    private static void terminate() {
        System.err.println("IllegalArgument");
        System.exit(-1);
    }

    private static long powerOfTen(int exponent) {
        if (exponent < 0)
            terminate();

        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= 10;
        }
        return result;
    }

    private static long getStudentsGrades(String digitsStream, long studentsData) {

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
        if (isError || digitCount != 5 || scanner.hasNext())
            terminate();

        scanner.close();

        return (studentsData * 10 + minGrade);
    }

    private static void chartSudentsGrades(long studentsData, int totalWeeks) {
        int weekCounter = 1;
        // int division = (int) Math.pow(10, totalWeeks - 2);
        long division = powerOfTen(totalWeeks - 2);
        while (division > 0) {
            System.out.print("Week " + weekCounter + " ");
            int digit = (int)(studentsData / division);
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
        long studentsData = 0; // shit i used int instead of long, and alwys got this wrong "studentsData = studentsData * 10 + minGrade" when reaching big number
        int currentWeek = 1;
        while (true) {
            String weekStr = scanner.nextLine();

            if (weekStr.equals("42") || currentWeek > 18) {
                break;
            }
            String expectedPrefix = "Week " + currentWeek;

            if (!weekStr.equals(expectedPrefix))
                terminate();

            String digitsStream = scanner.nextLine();

            studentsData = getStudentsGrades(digitsStream, studentsData);
            currentWeek++;
        }

        scanner.close();

        chartSudentsGrades(studentsData, currentWeek);
    }

}

// Week 1
// 4 5 2 4 2
// Week 2
// 7 7 7 7 6
// Week 3
// 4 3 4 9 8
// Week 4
// 9 9 4 6 7
// 42
// --------------
// Week 1
// 5 6 7 8 9
// Week 2
// 5 6 7 8 9
// Week 3
// 5 6 7 8 9
// Week 4
// 5 6 7 8 9
// Week 5
// 5 6 7 8 9
// Week 6
// 5 6 7 8 9
// Week 7
// 5 6 7 8 9
// Week 8
// 5 6 7 8 9
// Week 9
// 5 6 7 8 9
// Week 10
// 5 6 7 8 9
// Week 11
// 5 6 7 8 9
// Week 12
// 5 6 7 8 9
// Week 13
// 5 6 7 8 9
// Week 14
// 5 6 7 8 9
// Week 15
// 5 6 7 8 9
// Week 16
// 5 6 7 8 9
// Week 17
// 5 6 7 8 9
// 42


