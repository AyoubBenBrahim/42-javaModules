package ex04;

import java.util.Scanner;

public class Program {

    private static void countOccurrences(String str, int[] charCount) {
        System.out.println();
        for (int i = 0; i < charCount.length; i++) {
            charCount[i] = 0;
        }
        for (char c : str.toCharArray()) {
            if (charCount[c] < 999)
                charCount[c]++;
        }
    }

    private static void sortArray(int[] charCount, int[][] charCounts) {

        for (int i = 0; i < charCounts.length; i++) {
            charCounts[i][0] = i;
            charCounts[i][1] = charCount[i];
        }
        for (int i = 0; i < charCounts.length - 1; i++) {
            for (int j = i + 1; j < charCounts.length; j++) {
                if (charCounts[i][1] < charCounts[j][1] ||
                        (charCounts[i][1] == charCounts[j][1] && charCounts[i][0] > charCounts[j][0])) {
                    int[] temp = charCounts[i];
                    charCounts[i] = charCounts[j];
                    charCounts[j] = temp;
                }
            }
        }
    }

    private static void printHistogram(int[][] charCounts) {
        int maxCount = charCounts[0][1];

        for (int row = 10; row >= 0; row--) {

            for (int col = 0; col < 10; col++) {
                if (charCounts[col][1] * 10 / maxCount == row) {
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
        String str = scanner.nextLine();

        // Count the occurrences of each character in the string
        int[] charCount = new int[65535];

        countOccurrences(str, charCount);

        // Sort the characters based on their occurrences
        int[][] charCounts = new int[65535][2];
        sortArray(charCount, charCounts);

        printHistogram(charCounts);

        scanner.close();

    }

}
/*
  AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDWEWWKFKKDKKDSKAKLSLDKSKALLLLLLLLLLRTRTETWTWWWWWWWWWWOOOOOOO
 */

// 36
// # 35
// # #
// # # 27
// # # #
// # # #
// # # #
// # # # 14 12
// # # # # # 9
// # # # # # # 7 4
// # # # # # # # # 2 2
// D A S W L K O T E R
