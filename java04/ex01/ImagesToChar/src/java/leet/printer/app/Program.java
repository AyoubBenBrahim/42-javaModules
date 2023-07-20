package leet.printer.app;
import leet.printer.logic.*;

public class Program {
    public static void main(String[] args) {

        // System.out.println("agrs.length = " + args.length);

        if (args.length != 2 || args[0].length() != 1 || args[1].length() != 1){
            System.out.println("Usage: java -cp target Main . 0");
            System.exit(1);
        }
        ImageHandler imgHandler = new ImageHandler(args[0].charAt(0), args[1].charAt(0));
        char[][] charArray = imgHandler.imgToCharArray();
        imgHandler.printCharArray(charArray);
    }
}