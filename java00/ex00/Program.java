
public class Program {
    public static void main(String[] args) {
        
        sumOfDigits();

    }

    private static void sumOfDigits() {
        int nbr = 45673;
        int sum = 0;

        for (int temp = nbr; temp > 0; temp /= 10) {
            int digit = temp % 10;
            sum += digit;
        }

        System.out.println(sum);
    }
}

// 198131
// -> 12901212
// -> 11122
// 42