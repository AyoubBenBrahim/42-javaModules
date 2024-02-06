package fr.fortytwo.printer.app;
import fr.fortytwo.printer.logic.*;

public class Program {
    public static void main(String[] args) {

        if (args.length != 2 || args[0].length() != 5 || args[1].length() != 5 ){
            System.out.println("Usage: java -cp target --w=. --b=0 /path/it.bmp");
            System.exit(1);
        }

        if (!args[0].substring(0, 4).equals("--w=") || !args[1].substring(0, 4).equals("--b=")) { 
            System.out.println("Usage: java -cp target --w=. --b=0 /path/it.bmp");
            System.exit(1);
        }
        
        char white = args[0].substring(4).charAt(0);
        char black = args[1].substring(4).charAt(0);

        ImageHandler imgHandler = new ImageHandler(white, black);
        char[][] charArray = imgHandler.imgToCharArray();
        imgHandler.printCharArray(charArray);
    }
}