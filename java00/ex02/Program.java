package ex02;

import java.util.Scanner;

public class Program {

    private static double sumOfDigits(int nbr) {
        int sum = 0;

        for (int temp = nbr; temp > 0; temp /= 10) {
            int digit = temp % 10;
            sum += digit;
        }

        return sum;
    }

    private static double ft_sqrt(double X) {
        for (int i = 1; i < X; ++i) {
            int p = i * i;
            if (p == X) {
                // perfect square
                return i;
            }
            if (p > X) {
                // found left part of decimal
                return ft_sqrt(X, i - 1, i);
            }
        }
        return Double.NaN;
    }

    private static double ft_sqrt(double X, double low, double high) {
        double mid = (low + high) / 2;
        double p = mid * mid;

        if (p == X || Double.toString(p).length() >= 16) {
            return mid;
        }
        if (p < X) {
            return ft_sqrt(X, mid, high);
        }
        return ft_sqrt(X, low, mid);
    }

    private static boolean isPrime(double n) {

        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= ft_sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;

    }

    public static void main(String[] args) {
        endlessSequence();
    }

  private static void endlessSequence() {
    Scanner scanner = new Scanner(System.in);
    int primeCounter = 0;
    while (true) {
        System.out.print("-> ");
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
    System.out.println("Count of coffee-request : " + primeCounter);
}
}

// 12347: The sum of the digits is 17, which is a prime number.
// 23459: The sum of the digits is 23, which is a prime number.
// 34561: The sum of the digits is 19, which is a prime number.

// 45673: The sum of the digits is 25, which is not a prime number.
// 56789: The sum of the digits is 35, which is not a prime number.
// 67891: The sum of the digits is 31, which is a prime number.