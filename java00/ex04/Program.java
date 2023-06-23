package ex04;

import java.util.Scanner;

public class Program {

    private static void countOccurrences(String str, int[][] charCounts) {
        System.out.println();

        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (charCounts[c][1] < 999) {
                charCounts[c][0] = c;
                charCounts[c][1]++;
            }
        }
    }

    private static void initTopCountsFromCharCounts(int[][] charCounts, int[][] TopCountsArray) {
        // Initialize the top counts array with the first 10 elements from the
        // charCounts array
        for (int i = 0; i < TopCountsArray.length; i++) {
            TopCountsArray[i][0] = charCounts[i][0];
            TopCountsArray[i][1] = charCounts[i][1];
        }
    }

    private static void sortArray(int[][] charCounts) {
        for (int i = 0; i < charCounts.length - 1; i++) {
            for (int j = i + 1; j < charCounts.length; j++) {
                if (charCounts[i][1] < charCounts[j][1] ||
                        (charCounts[i][1] == charCounts[j][1] && charCounts[i][0] > charCounts[j][0])) {
                    int tempChar = charCounts[i][0];
                    int tempCount = charCounts[i][1];
                    charCounts[i][0] = charCounts[j][0];
                    charCounts[i][1] = charCounts[j][1];
                    charCounts[j][0] = tempChar;
                    charCounts[j][1] = tempCount;
                }
            }
        }
    }

    private static void compareCountsToTop(int[][] charCounts, int[][] TopCountsArray) {
        // Iterate over the remaining elements in the charCounts array and compare them
        // to the top counts array
        for (int i = TopCountsArray.length; i < charCounts.length; i++) {
            int count = charCounts[i][1];
            if (count > TopCountsArray[TopCountsArray.length - 1][1]) {
                TopCountsArray[TopCountsArray.length - 1][0] = charCounts[i][0];
                TopCountsArray[TopCountsArray.length - 1][1] = count;
                sortArray(TopCountsArray);
            }
        }
    }

    private static void printHistogram(int[][] charCounts) {
        int maxCount = charCounts[0][1];

        for (int row = 10; row >= 0; row--) {

            for (int col = 0; col < 10; col++) {
                if (charCounts[col][1] * 10 / maxCount == row && charCounts[col][1] > 0) {
                    System.out.printf("%3d ", charCounts[col][1]);
                } else if (charCounts[col][1] * 10 / maxCount > row) {
                    System.out.print("  # ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();

        }

        for (int i = 0; i < 10; i++) {
            if (charCounts[i][1] > 0) {
                System.out.printf("%3c ", charCounts[i][0]);
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine() == false)
            return ;
        String str = scanner.nextLine();
        if (str.length() == 0)
            return ;
        

        int[][] charCounts = new int[65535][2];
        /*
         * use this website to check occurrences of each character
         * https://www.browserling.com/tools/letter-frequency
         */
        countOccurrences(str, charCounts);

        int[][] TopCountsArray = new int[10][2];
        initTopCountsFromCharCounts(charCounts, TopCountsArray);

        sortArray(TopCountsArray);

        compareCountsToTop(charCounts, TopCountsArray);

        // Print the top 10 counts and their corresponding characters
        /*
         * for (int i = 0; i < TopCountsArray.length; i++) {
         * char c = (char) TopCountsArray[i][0];
         * int count = TopCountsArray[i][1];
         * System.out.println(c + ": " + count);
         * }
         */

        
        printHistogram(TopCountsArray);

        scanner.close();

    }

}
