package java01.ex05;

import java.util.Scanner;

public class Program {

    private static void _exit() {
        System.out.println("Invalid Args...");
        System.exit(0);
    }

    public static void main(String[] args) {
        Menu menu = null;
        if (args.length == 1) {
            if (args[0].equals("--profile=dev"))
                menu = new Menu(true);
            else if (args[0].equals("--profile=production"))
                menu = new Menu(false);
            else
                _exit();

            while (true) {
                Scanner scanner = new Scanner(System.in);
                menu.printMenu();

                String[] choice = scanner.nextLine().split(" ");

                if (choice.length != 1) {
                    System.err.println("Invalid Choice, Try Again\n");
                    continue;
                }
                Integer choiceInt = 0;
                try {
                    choiceInt = Integer.parseInt(choice[0]);
                } catch (NumberFormatException e) {
                    System.err.println("Enter a valid Integer");
                    scanner.nextLine();
                }
                if (choiceInt < 1 || choiceInt > 7) {
                    System.err.println("Invalid Choice, 1...7 \n");
                    continue;
                }
                menu.dispatcher(choiceInt);

                System.out.println("---------------------------------------------------------");
            }
        } else
            _exit();
    }
}
