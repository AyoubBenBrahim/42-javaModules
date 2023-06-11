package ex02;

public class Program {
    public static void main(String[] args) {
        
        sumOfDigits();

    }

    private static void sumOfDigits() {
        int nbr = 998877;
        int sum = 0;

        for (int temp = nbr; temp > 0; temp /= 10) {
            int digit = temp % 10;
            sum += digit;
        }

        System.out.println("Sum of digits: " + sum);
    }
}
