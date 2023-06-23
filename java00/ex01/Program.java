package ex01;

import java.util.Scanner;

public class Program {

    private static void terminate() {
        System.err.println("IllegalArgument");
        System.exit(-1);
    }

    /*
    ***********************************************************************************
    */

    private static void isPrime(int n) {
        int iter = 0;

        if (n <= 1)
            terminate();

        for (int i = 2; i * i <= n; i++) {
            iter++;
            if (n % i == 0) {
                System.out.println("false " + iter);
                return;
            }
        }
        System.out.println("true " + (iter + 1));
    }

    /*
    ***********************************************************************************
    */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt())
            return;

        int input = scanner.nextInt();
        isPrime(input);
    }
}