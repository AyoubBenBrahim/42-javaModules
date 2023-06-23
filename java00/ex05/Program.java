package ex05;

import java.util.Scanner;

public class Program {

    private static final String END_OF_INPUT = ".";
    private static final String[] DAY_OF_WEEK = { "MO", "TU", "WE", "TH", "FR", "SA", "SU" };

    /*
    ***********************************************************************************
    */

    public static void main(String[] args) {
        String[] studentNamesTemp = new String[10];
        int[] classScheduleTemp = new int[10];

        int size = 0;
        size = getStudentsNames(studentNamesTemp, null, 0);
        int NBR_STUD = size;

        String[] studentNames = new String[size];
        getStudentsNames(studentNamesTemp, studentNames, size);

        size = 0;
        size = getClassSchedule(classScheduleTemp, null, 0);

        int[] classSchedule = new int[size];
        getClassSchedule(classScheduleTemp, classSchedule, size);

        insertionSort(classSchedule);

        int MAX_TIME_DAY_PAIR = packIntegers(6, 31); // 223
        int[][] attendanceRecords = new int[NBR_STUD][MAX_TIME_DAY_PAIR];
        for (int i = 0; i < attendanceRecords.length; i++) {
            for (int j = 0; j < attendanceRecords[i].length; j++) {
                attendanceRecords[i][j] = -1;
            }
        }

        displayRecords(studentNames, attendanceRecords, classSchedule);
    }

    /*
    ***********************************************************************************
    */

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

    private static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
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

    private static int getStudentsNames(String[] T1, String[] T2, int size) {

        if (size == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter student names(max 10, len 10), one per line. Enter '.' when done.");

            for (int i = 0; i < 10; i++) {
                String name = scanner.nextLine();
                if (name.equals(END_OF_INPUT))
                    break;

                if (name.length() > 10 || hasSpace(name))
                    terminate();
                T1[i] = name;
                size++;
            }
            // scanner.close(); // don't close the scanner, we need it later, or the next
            // function will throw an exception
        } else
            for (int i = 0; i < size; i++)
                T2[i] = T1[i];

        return size;
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

        sc.close();
        return packIntegers(time, dayIndex);
    }

    /*
     ***************************** FUNC NO LONGER USED******************************************************
     */
    private static void checkLimitClassPerWeek(int[] classSchedule) {
        int i = 0;
        while (i < classSchedule.length && classSchedule[i] != -1)
            i++;
        if (i > 10)
            terminate();

        int[] days = new int[7];
        for (int j = 0; j < classSchedule.length; j++) {
            if (classSchedule[j] == -1)
                break;
            int T[] = unpackIntegers(classSchedule[j]);
            days[T[1]]++;
        }
        int countClass = 0;
        for (int j = 0; j < days.length; j++) {
            // if (days[j] > 10)
            // terminate();
            countClass += days[j];
        }
        System.out.println("countClass: " + countClass);
        if (countClass > 10)
            terminate();
    }
    /*
    ***********************************************************************************
    */

    private static int getClassSchedule(int[] T1, int[] T2, int numClasses) {

        if (numClasses == 0) {
            System.out.println("Enter class schedule, one per line [hour day] Enter '.' when done.");
            Scanner scanner = new Scanner(System.in);

            while (numClasses < 10) {
                String input = scanner.nextLine();
                if (input.equals(END_OF_INPUT)) {
                    break;
                }
                int dayTime = parseDayTime(input);
                if (isDuplicate(T1, dayTime))
                    terminate();
                T1[numClasses] = dayTime;
                numClasses++;
            }
        } else
            for (int i = 0; i < numClasses; i++)
                T2[i] = T1[i];
        // checkLimitClassPerWeek(classSchedule); // no need to check this, the while
        // loop above already does
        // scanner.close(); // dont close the scanner, we still need it next
        return numClasses;
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

    /*
    ***********************************************************************************
    */

    private static int getAttendanceStatus(String input) {
        if (input == null)
            return 0;
        if (input.equals("HERE"))
            return 1;
        else if (input.equals("NOT_HERE"))
            return 0;
        else
            terminate();

        return 0;
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

    private static void checkIfTimeExistInSchedule(int[] classSchedule, int time) {
        for (int i = 0; i < classSchedule.length; i++) {
            int T[] = unpackIntegers(classSchedule[i]);
            if (T[0] == time)
                return;
        }
        terminate();
    }

    /*
    ***********************************************************************************
    */

    private static void checkIfDateCorrespondToDayInSchedule(int[] classSchedule, int date) {

        for (int i = 0; i < classSchedule.length; i++) {
            int T[] = unpackIntegers(classSchedule[i]);
            if (getDayString(T[1]).equals(getDayString(date % 7)))
                return;
        }
        terminate();
    }

    /*
    ***********************************************************************************
    */

    private static void parseAttendanceRecords(int[][] attendanceRecords, String[] studentNames, int[] classSchedule,
            String inputStram) {

        Scanner scanner = new Scanner(inputStram);
        scanner.useDelimiter(" ");

        int counter = 0;
        String regex = "\\S+";
        while (scanner.findInLine(regex) != null)
            counter++;

        scanner.close();

        if (counter != 4)
            terminate();

        Scanner sc = new Scanner(inputStram);
        sc.useDelimiter(" ");

        int studentIndex = getStudentIndex(studentNames, sc.next());
        int time = isDigit(sc.next());
        int date = isDigit(sc.next());
        int attendanceStatus = getAttendanceStatus(sc.next());
        int timeDatePair = packIntegers(time, date);

        checkIfTimeExistInSchedule(classSchedule, time);
        checkIfDateCorrespondToDayInSchedule(classSchedule, date);
        if (!isInRange(date, 1, 31) || studentIndex == -1)
            terminate();

        sc.close();

        attendanceRecords[studentIndex][timeDatePair] = attendanceStatus;
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
            parseAttendanceRecords(attendanceRecords, studentNames, classSchedule, input);
        }
        scanner.close(); // close the scanner, we don't need it anymore
    }

    /*
    ***********************************************************************************
    */

    private static int countDayOccurancesInMonth(int[] schedule) {

        int counter = 0;

        for (int i = 1; i < 31; i++) {
            for (int j = 0; j < schedule.length; j++) {
                int T[] = unpackIntegers(schedule[j]);
                if (getDayString(i % 7).equals(getDayString(T[1])))
                    counter++;
            }
        }
        return counter;
    }

    /*
    ***********************************************************************************
    */
    private static String formatOutput(String[] output, int count) {
        // System.out.print(" ");
        String formattedOutput = "           ";
        for (int i = 0; i < count; i++) {
            formattedOutput += output[i];
            if (i < count - 1) {
                formattedOutput += "|";
            }
        }
        formattedOutput += "|";
        return formattedOutput;
    }

    /*
    ***********************************************************************************
    */

    public static String intToString(int num) {
        if (num == 0) {
            return "0";
        }
        String str = "";
        boolean isNegative = false;
        if (num < 0) {
            isNegative = true;
            num = -num;
        }
        while (num > 0) {
            char digit = (char) ('0' + num % 10);
            str = digit + str;
            num /= 10;
        }
        if (isNegative) {
            str = "-" + str;
        }
        return str;
    }

    /*
    ***********************************************************************************
    */

    public static void printTimetable(String[] studentNames, int[] timeDatePairOfMonth, int[][] attendanceRecords,
            int count, String[] output) {
        if (count > 0) {
            String formattedOutput = formatOutput(output, count);
            System.out.println(formattedOutput);
        }

        for (int i = 0; i < studentNames.length; i++) {
            System.out.printf("%-10s", studentNames[i]);

            for (int k = 0; k < timeDatePairOfMonth.length; k++) {
                int attendanceStatus = attendanceRecords[i][timeDatePairOfMonth[k]];

                if (attendanceStatus == -1)
                    System.out.printf("|          ");
                else if (attendanceStatus == 0)
                    System.out.printf("|        -1");
                else
                    System.out.printf("|         1");
            }

            System.out.println("|");
        }
    }

    /*
    ***********************************************************************************
    */

    private static void displayRecords(String[] studentNames, int[][] attendanceRecords, int[] classSchedule) {

        getAttendanceRecords(attendanceRecords, studentNames, classSchedule);

        int size = countDayOccurancesInMonth(classSchedule);

        int count = 0;
        String[] output = new String[size];
        int[] timeDatePairOfMonth = new int[size]; // I already stored the timeDatePair in the attendanceRecords array,
                                                   // "John 4 9 HERE" so it gonna be easy to get the timeDatePair of the
                                                   // month for students printing later

        for (int i = 1; i < 31; i++) {
            for (int j = 0; j < classSchedule.length; j++) {
                int[] timeDayPair = unpackIntegers(classSchedule[j]);
                int time = timeDayPair[0];
                int day = timeDayPair[1];

                if (getDayString(i % 7).equals(getDayString(day))) {

                    String dayOfMonth = intToString(i);

                    String delimiter = (dayOfMonth.length() == 2) ? " " : "  ";

                    timeDatePairOfMonth[count] = packIntegers(time, i);
                    output[count] = time + ":00 " + getDayString(day) + delimiter + i;

                    count++;
                }
            }
        }

        printTimetable(studentNames, timeDatePairOfMonth, attendanceRecords, count, output);
    }

    /*
    ***********************************************************************************
    */

    private static int packIntegers(int hour, int day) {
        return (hour << 5) | (day & 0x1F);

    }

    /*
    ***********************************************************************************
    */

    private static int[] unpackIntegers(int packedVal) {
        int[] unpackedInts = new int[2];

        unpackedInts[0] = (packedVal >> 5) & 0x07;
        unpackedInts[1] = packedVal & 0x1F;

        return unpackedInts;
    }
}

// Enter student names(max 10, len 10), one per line. Enter '.' when done.
// AA
// BBB
// CCCC
// DDDDD
// EEEEEEEE
// GGGGGGGGGG
// .
// Enter class schedule, one per line [hour day] Enter '.' when done.
// 2 MO
// 4 WE
// 5 TU
// 6 TU
// 1 TU
// 1 MO
// 2 WE
// .
// Enter attendance records, one per line. Enter '.' when done.
// BBB 2 28 NOT_HERE
// BBB 1 28 HERE
// BBB 4 9 NOT_HERE
// AA 5 15 HERE
// AA 1 15 NOT_HERE
// AA 5 15 HERE