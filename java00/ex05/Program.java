package ex05;

import java.util.Scanner;

public class Program {

    private static final String END_OF_INPUT = ".";
    private static final String[] DAY_OF_WEEK = { "MO", "TU", "WE", "TH", "FR", "SA", "SU" };

    private static int getDayIndex(String dayString) {
        for (int i = 0; i < DAY_OF_WEEK.length; i++) {
            if (DAY_OF_WEEK[i].equals(dayString))
                return i;
        }
        return -1;
    }

    /*
    ***********************************************************************************
    */

    private static String getDayString(int index) {
        if (index >= 0 && index < DAY_OF_WEEK.length) {
            return DAY_OF_WEEK[index];
        } else
            return null;
    }

    /*
    ***********************************************************************************
    */

    private static void terminate() {
        System.err.println("IllegalArgument");
        System.exit(-1);
    }

    /*
    ***********************************************************************************
    */

    public static void main(String[] args) {
        String[] studentNames = new String[10];
        int HOURS = 6;
        int DAYS = 31;
        int[] classSchedule = new int[10];
        int[][] attendanceRecords = new int[HOURS][DAYS];

        getStudentsNames(studentNames);
        getClassSchedule(classSchedule);
        getAttendanceRecords(attendanceRecords, studentNames, classSchedule);
        // displayRecords(studentNames, attendanceRecords, classSchedule);
    }

    /*
    ***********************************************************************************
    */

    private static int getStudentIndex(String[] studentNames, String student) {
        for (int i = 0; i < studentNames.length; i++) {
            if (studentNames[i] != null && studentNames[i].equals(student)) {
                return i;
            }
        }
        return -1;
    }

    /*
    ***********************************************************************************
    */

    private static boolean hasSpace(String str) {
        if (str == null) {
            return false;
        }

        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c == ' ')
                return true;
        }

        return false;
    }

    /*
    ***********************************************************************************
    */

    private static void getStudentsNames(String[] studentNames) {

        // Prompt the user to enter the student names
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student names(max 10, len 10), one per line. Enter '.' when done.");

        for (int i = 0; i < 10; i++) {
            String name = scanner.nextLine();
            if (name.equals(END_OF_INPUT))
                break;
            
            if (name.length() > 10 || hasSpace(name))
                terminate();
            studentNames[i] = name;
        }

        // scanner.close(); // don't close the scanner, we need it later, or the next
        // function will throw an exception
    }

    /*
    ***********************************************************************************
    */

    private static int isDigit(String str) {
        if (str == null)
            terminate();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] < 48 || chars[i] > 57) {
                terminate();
            }
        }
        return Integer.parseInt(str);
    }

    /*
    ***********************************************************************************
    */

    private static int parseDayTime(String inputStram) {
        Scanner scanner = new Scanner(inputStram);
        int counter = 0;
        String regex = "\\S+";
        while (scanner.findInLine(regex) != null) {
            counter++;
        }
        scanner.close();

        if (counter != 2)
            terminate();

        Scanner sc = new Scanner(inputStram);
        sc.useDelimiter(" ");
        int time = isDigit(sc.next());
        int dayIndex = getDayIndex(sc.next());

        if (!isInRange(time, 1, 6) || dayIndex == -1)
            terminate();

        // System.out.println("time: " + time + " day: " + getDayString(dayIndex) + "
        // dayIndex: " +
        // dayIndex);
        sc.close();
        return packIntegers(time, dayIndex);
    }

    /*
    ***********************************************************************************
    */

    private static void getClassSchedule(int[] classSchedule) {

        System.out.println("Enter class schedule, one per line [hour day] Enter '.' when done.");
        Scanner scanner = new Scanner(System.in);

        int numClasses = 0;
        while (numClasses < 10) {
            String input = scanner.nextLine();
            if (input.equals(END_OF_INPUT)) {
                break;
            }
            int dayTime = parseDayTime(input);
            int T[] = unpackIntegers(dayTime);
            System.out.println("time: " + T[0] + " day: " + getDayString(T[1]));
            if (isDuplicate(classSchedule, dayTime))
                terminate();
            classSchedule[numClasses] = dayTime;

            numClasses++;
        }
        // scanner.close(); // dont close the scanner, we need it later

        // for (int i = 0; i < numClasses; i++) {
        // System.out.println("[" + i + "] = " + classSchedule[i]);
        // }
    }

    /*
    ***********************************************************************************
    */

    private static boolean isDuplicate(int[] classSchedule, int dayTime) {
        for (int i = 0; i < classSchedule.length; i++) {
            if (classSchedule[i] == dayTime)
                return true;
        }
        return false;
    }

    private static boolean getAttendanceStatus(String input) {
        if (input == null)
            return false;
        if (input.equals("HERE"))
            return true;
        else if (input.equals("NOT_HERE"))
            return false;
        else
            terminate();

        return false;
    }

    /*
    ***********************************************************************************
    */

    private static boolean isInRange(int num, int lowerBound, int upperBound) {
        return (num >= lowerBound && num <= upperBound);
    }

    /*
    ***********************************************************************************
    */

    private static void parseAttendanceRecords(String inputStram, String[] studentNames) {

        Scanner scanner = new Scanner(inputStram);
        scanner.useDelimiter(" ");

        int counter = 0;
        String regex = "\\S+";
        while (scanner.findInLine(regex) != null) {
            counter++;
        }
        scanner.close();

        if (counter != 4)
            terminate();

        Scanner sc = new Scanner(inputStram);
        sc.useDelimiter(" ");

        int studentIndex = getStudentIndex(studentNames, sc.next());
        int time = isDigit(sc.next());
        int date = isDigit(sc.next());
        int attendanceStatus = getAttendanceStatus(sc.next()) ? 1 : -1;

        if (!isInRange(time, 1, 6) || !isInRange(date, 1, 31) || studentIndex == -1)
            terminate();

        sc.close();
        System.out.println("studentIndex: " + studentIndex +
                " time: " + time + " date: " + date + " attendanceStatus: " + attendanceStatus);
    }

    /*
    ***********************************************************************************
    */

    private static void getAttendanceRecords(int[][] attendanceRecords, String[] studentNames, int[] classSchedule) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter attendance records, one per line. Enter '.' when done.");

        while (true) {
            String input = scanner.nextLine();
            if (input.equals(END_OF_INPUT))
                break;

            parseAttendanceRecords(input, studentNames);
        }
        scanner.close(); // close the scanner, we don't need it anymore
    }

    /*
    ***********************************************************************************
    */

    private static void displayRecords(String[] studentNames, int[][] attendanceRecords, int[][] classSchedule) {

        // Display the attendance records in tabular form
        int numClasses = classSchedule.length;
        System.out.println("Class Schedule:");
        for (int i = 0; i < numClasses; i++) {
            int day = classSchedule[i][0];
            int time = classSchedule[i][1];
            System.out.print(time + ":00 ");
            switch (day) {
                case 1:
                    System.out.print("MO");
                    break;
                case 2:
                    System.out.print("TU");
                    break;
                case 3:
                    System.out.print("WE");
                    break;
                case 4:
                    System.out.print("TH");
                    break;
                case 5:
                    System.out.print("FR");
                    break;
                case 6:
                    System.out.print("SA");
                    break;
                case 7:
                    System.out.print("SU");
                    break;
            }
            System.out.print(" " + (i + 1) + "|");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            if (studentNames[i] != null) {
                System.out.print(studentNames[i] + " |");
                for (int j = 0; j < numClasses; j++) {
                    if (attendanceRecords[i][j] == 1) {
                        System.out.print(" HERE ");
                    } else if (attendanceRecords[i][j] == -1) {
                        System.out.print(" ABSENT ");
                    } else {
                        System.out.print("      ");
                    }
                    System.out.print("|");
                }
                System.out.println();
            }
        }

    }

    /*
    ***********************************************************************************
    */

    private static int packIntegers(int hour, int day) {
        return (hour << 5) | (day & 0x1F);

    }

    private static int[] unpackIntegers(int packedVal) {
        int[] unpackedInts = new int[2];
        unpackedInts[0] = (packedVal >> 5) & 0x07;
        unpackedInts[1] = packedVal & 0x1F;

        return unpackedInts;
    }
}