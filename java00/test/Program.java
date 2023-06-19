package test;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        twoVars();
    //    threeVars();
    }

    private static void twoVars() {
        // Scanner scanner = new Scanner(System.in);

        // // Get input for A and B
        // System.out.print("Enter A (1-6): ");
        // int A = scanner.nextInt();
        // System.out.print("Enter B (1-7): ");
        // int B = scanner.nextInt();

        // // Store A and B in a single variable
        // int combined = (A << 3) | B;

        // // Retrieve A and B from the combined variable
        // int retrievedA = (combined >> 3) & 0x07;
        // int retrievedB = combined & 0x07;

        // // Print the retrieved values for confirmation
        // System.out.println("Combined: " + combined);
        // System.out.println("Retrieved A: " + retrievedA);
        // System.out.println("Retrieved B: " + retrievedB);


    Scanner scanner = new Scanner(System.in);

    // Get input for A and B
    System.out.print("Enter A (1-6): ");
    int A = scanner.nextInt();
    System.out.print("Enter B (1-31): ");
    int B = scanner.nextInt();

    // Store A and B in a single variable
    int combined = (A << 5) | (B & 0x1F);

    // Retrieve A and B from the combined variable
    int retrievedA = (combined >> 5) & 0x07;
    int retrievedB = combined & 0x1F;

    // Print the retrieved values for confirmation
    System.out.println("Combined: " + combined);
    System.out.println("Retrieved A: " + retrievedA);
    System.out.println("Retrieved B: " + retrievedB);



    }

    private static void threeVars() {

         Scanner scanner = new Scanner(System.in);

        // Prompt user to insert variable A
        System.out.print("Enter variable A (between 1 and 6): ");
        int varA = scanner.nextInt();

        // Validate input for variable A
        if (varA < 1 || varA > 6) {
            System.out.println("Invalid input for variable A.");
            return;
        }

        // Prompt user to insert variable B
        System.out.print("Enter variable B (between 1 and 7): ");
        int varB = scanner.nextInt();

        // Validate input for variable B
        if (varB < 1 || varB > 7) {
            System.out.println("Invalid input for variable B.");
            return;
        }

        // Combine variable A and B into a single integer
        int combinedAB = (varA << 3) | (varB - 2);

        // Prompt user to insert variable C
        System.out.print("Enter variable C (between 1 and 31): ");
        int varC = scanner.nextInt();

        // Validate input for variable C
        if (varC < 1 || varC > 31) {
            System.out.println("Invalid input for variable C.");
            return;
        }

        // Combine variables A, B, and C into a single integer
        int combinedABC = (varA << 11) | (varB << 8) | varC;

        // Retrieve variables A, B, and C from the combined integer
        int recoveredA = (combinedABC >> 11) & 0x3F;
        int recoveredB = ((combinedABC >> 8) & 0x7);
        int recoveredC = combinedABC & 0x1F;

        // Print out the recovered variables
        System.out.println("Recovered variables:");
        System.out.println("A: " + recoveredA);
        System.out.println("B: " + recoveredB);
        System.out.println("C: " + recoveredC);

    }
}