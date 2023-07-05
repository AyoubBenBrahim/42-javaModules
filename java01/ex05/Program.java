package java01.ex05;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Menu menu = null;
        if (args.length == 1 && args[0].equals("--profile=dev")) {
            menu = new Menu(true);
            // menu.launch();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                menu.printMenu();
                menu.dispatcher(scanner.nextInt());
                System.out.println("---------------------------------------------------------");
            }
        } else if (args.length == 1 && args[0].equals("--profile=production")) {
            menu = new Menu(false);
            // menu.launch();
        } else {
            System.err.println("illegal argument");
            System.exit(-1);
        }
    }
}
