package ex05;

import java.util.Scanner;

public class Program {

    private static final String[] DAY_OF_WEEK = { "MO", "TU", "WE", "TH", "FR", "SA", "SU" };

    private static int getDayIndex(String dayString) {
        for (int i = 0; i < DAY_OF_WEEK.length; i++) {
            if (DAY_OF_WEEK[i].equals(dayString))
                return i;

        }
        return -1;
    }

    private static String getDayString(int index) {
        if (index >= 0 && index < DAY_OF_WEEK.length) {
            return DAY_OF_WEEK[index];
        } else
            return null;
    }

    private static void terminate() {
        System.err.println("IllegalArgument");
        System.exit(-1);
    }

    public static void main(String[] args) {
        String[] studentNames = new String[10];
        int HOURS = 6;
        int DAYS = 31;
        int[] classSchedule = new int[10];
        int[][] attendanceRecords = new int[HOURS][DAYS];

        getStudentsNames(studentNames);
        getClassSchedule(classSchedule);
        // getAttendanceRecords(attendanceRecords, studentNames, classSchedule);
        // displayRecords(studentNames, attendanceRecords, classSchedule);
    }

    private static int getStudentIndex(String[] studentNames, String student) {
        for (int i = 0; i < studentNames.length; i++) {
            if (studentNames[i] != null && studentNames[i].equals(student)) {
                return i;
            }
        }
        return -1;
    }

    private static void getStudentsNames(String[] studentNames) {

        // Prompt the user to enter the student names
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student names(max 10), one per line. Enter '.' when done.");

        for (int i = 0; i < 10; i++) {
            String name = scanner.nextLine();
            if (name.equals(".")) {
                break;
            }
            studentNames[i] = name;
        }
        // for (int i = 0; i < 10; i++)
        // if (studentNames[i] != null)
        // System.out.println("[" + i + "] = " + studentNames[i]);

    }

    public static boolean isDigit(String str) {
        if (str == null)
         return false;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] < 48 || chars[i] > 57) {
                return false;
            }
        }
        return true;
    }

    private static int getDayTime(String inputStreaString) {
        Scanner scanner = new Scanner(inputStreaString);
        scanner.useDelimiter(" ");
        int time = -1;
        int dayIndex = -1;

        int countr = 0;
        boolean isError = false;
        while (scanner.hasNext() && countr < 2) {
            String _time = scanner.next();
            if (!isDigit(_time)) {
                isError = true;
                break;
            }
            time = Integer.parseInt(_time);
            if (!scanner.hasNext())
                break;
            String day = scanner.next();
            dayIndex = getDayIndex(day);

            // System.out.println("time: " + time + " day: " + day + " dayIndex: " +
            // dayIndex);

            if (time < 1 || time > 6 || dayIndex == -1) {
                isError = true;
                break;
            }
            countr += 2;
        }

        scanner.close();
        if (isError || countr != 2 || scanner.hasNext())
            terminate();

        return combineHourDay(time, dayIndex);
    }

    private static void getClassSchedule(int[] classSchedule) {

        // Prompt the user to enter the class schedule
        System.out.println("Enter class schedule, one per line [hour day]. Enter '.' when done.");
        Scanner scanner = new Scanner(System.in);

        scanner.useDelimiter(" ");

        int numClasses = 0;
        while (numClasses < 10) {

            String input = scanner.nextLine();
            if (input.equals(".")) {
                break;
            }
            classSchedule[numClasses] = getDayTime(input);
            numClasses++;
        }
        scanner.close();

        for (int i = 0; i < numClasses; i++) {
            System.out.println("[" + i + "] = " + classSchedule[i]);
        }
    }

    public static boolean getAttendanceStatus(String input) {
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

    private static void parseAttendanceRecords(String inputStreaString, String[] studentNames) {

        Scanner scanner = new Scanner(inputStreaString);
        scanner.useDelimiter(" ");

        int counter = 0;
        boolean isError = false;
        String student = null;
        int studentIndex = -1;
        int dayTime = -1;
        int dayDate = -1;
        String _time = null;
        String _date = null;
        String _status = null;
        int status = -1;
        while (scanner.hasNext() && counter < 4) {

            student = scanner.next();
            studentIndex = getStudentIndex(studentNames, student);
            _time = scanner.next();

             _date = scanner.next();
            
            _status = scanner.next();

            // counter += ;
        }

        if (isError || counter != 4 || scanner.hasNext())
            terminate();

        if (studentIndex == -1 || !isDigit(_time) || !isDigit(_date) || !getAttendanceStatus(_status) )
            terminate();

        dayTime = Integer.parseInt(_time);
        dayDate = Integer.parseInt(_date);
        status = getAttendanceStatus(_status) ? 1 : -1;

        if (dayTime < 1 || dayTime > 6 || dayDate < 1 || dayDate > 31)
            terminate();

        System.out.println("student: " + student + " dayTime: " + dayTime + " dayDate: " + dayDate + " status: "
                + status);

    }

    private static void getAttendanceRecords(int[][] attendanceRecords, String[] studentNames, int[][] classSchedule) {

        System.out.println("Enter attendance records, one per line. Enter '.' when done.");
        Scanner scanner = new Scanner(System.in);

        scanner.useDelimiter(" ");

        int numClasses = 0;

        while (true) {

            String input = scanner.nextLine();
            if (input.equals(".")) {
                break;
            }
            parseAttendanceRecords(input, studentNames);

        }
        scanner.close();

    }

    // private static void getAttendanceRecords(int[][] attendanceRecords, String[]
    // studentNames, int[][] classSchedule) {

    // Scanner scanner = new Scanner(System.in);
    // int numClasses = 0;
    // // Prompt the user to record attendance for each class
    // System.out.println("Enter attendance records, one per line. Enter '.' when
    // done.");
    // while (true) {
    // String input = scanner.nextLine();
    // if (input.equals(".")) {
    // break;
    // }
    // String[] parts = input.split(" ");
    // String name = parts[0];
    // int day = Integer.parseInt(parts[1]);
    // int time = Integer.parseInt(parts[2]);
    // String status = parts[3];
    // // Find the student index
    // int studentIndex = -1;
    // for (int i = 0; i < 10; i++) {
    // if (studentNames[i] != null && studentNames[i].equals(name)) {
    // studentIndex = i;
    // break;
    // }
    // }
    // if (studentIndex != -1) {
    // // Find the class index
    // int classIndex = -1;
    // for (int i = 0; i < numClasses; i++) {
    // if (classSchedule[i][0] == day && classSchedule[i][1] == time) {
    // classIndex = i;
    // break;
    // }
    // }
    // if (classIndex != -1) {
    // attendanceRecords[studentIndex][classIndex] = status.equals("HERE") ? 1 : -1;
    // } else {
    // System.out.println("Invalid class schedule. Please try again.");
    // }
    // } else {
    // System.out.println("Invalid student name. Please try again.");
    // }
    // }
    // }

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

    private static int combineHourDay(int hour, int day) {
        return (hour << 5) | (day & 0x1F);
    }
}