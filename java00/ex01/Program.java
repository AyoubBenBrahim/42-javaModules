package ex01;

import java.util.Scanner;

public class Program {

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

    private static void isPrime(double n) {
        int iter = 0;
        if (n <= 1) {
            System.out.println("IllegalArgument");
            System.exit(-1);
            return;
        }
        for (int i = 2; i <= ft_sqrt(n); i++) {
            iter++;
            if (n % i == 0) {
                System.out.println("false " + iter);
                return;
            }
        }
        System.out.println("true " + (iter + 1));
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("-> ");
        double input = scanner.nextDouble();
        isPrime(input);
    }

}