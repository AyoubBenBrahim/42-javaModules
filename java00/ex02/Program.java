package ex02;

import java.util.Scanner;

public class Program {

    private static int sumOfDigits(int nbr) {
        int sum = 0;

        for (int temp = nbr; temp > 0; temp /= 10) {
            int digit = temp % 10;
            sum += digit;
        }

        return sum;
    }

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int primeCounter = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                int sequence = scanner.nextInt();
                if (sequence == 42)
                    break;

                if (isPrime(sumOfDigits(sequence)))
                    primeCounter++;

            } else {
                System.err.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
        scanner.close();
        System.out.println("Count of coffee-request - " + primeCounter );
    }
}

// 12347: The sum of the digits is 17, which is a prime number.
// 23459: The sum of the digits is 23, which is a prime number.
// 34561: The sum of the digits is 19, which is a prime number.

// 45673: The sum of the digits is 25, which is not a prime number.
// 56789: The sum of the digits is 35, which is not a prime number.
// 67891: The sum of the digits is 31, which is a prime number.