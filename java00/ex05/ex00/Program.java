
/*
  int nbr = 0234;

In Java, if an integer literal starts with a leading zero, it is interpreted as an octal (base 8) number.
In this example, the integer literal 0234 is an octal number equal to decimal 156.

 */

public class Program {
    public static void main(String[] args) {

        int nbr = 479598;
        int sum = 0;

        // for (int temp = nbr; temp > 0; temp /= 10) {
        // int digit = temp % 10;
        // sum += digit;
        // }

        int temp = nbr;
        int digit = temp % 10;
        sum += digit;

        temp /= 10;
        digit = temp % 10;
        sum += digit;

        temp /= 10;
        digit = temp % 10;
        sum += digit;

        temp /= 10;
        digit = temp % 10;
        sum += digit;

        temp /= 10;
        digit = temp % 10;
        sum += digit;

        temp /= 10;
        digit = temp % 10;
        sum += digit;

        System.out.println(sum);
    }
}