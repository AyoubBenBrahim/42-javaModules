package fr.fortytwo.printer.app;
import fr.fortytwo.printer.logic.*;

import java.io.File;

public class Program {
    public static void main(String[] args) {

        if (args.length != 3 || args[0].length() != 5 || args[1].length() != 5 ){
            System.out.println("Usage: java -cp target --w=. --b=0 /path/it.bmp");
            System.exit(1);
        }

        if (!args[0].substring(0, 4).equals("--w=") || !args[1].substring(0, 4).equals("--b=")) { 
            System.out.println("Usage: java -cp target --w=. --b=0 /path/it.bmp");
            System.exit(1);
        }

        if (args[2].charAt(0) != '/') {
            System.err.println("Error: Please specify the absolute path to the image");
            System.exit(1);
        }

        String absolutePath = args[2].substring(0);
        
        File path = new File(absolutePath);
        
        if (!path.exists()) {
            System.out.println("ERROR : path does not exist");
            System.exit(1);
        }
        
        if (!path.isFile()) {
            System.out.println("ERROR : path is not a file");
            System.exit(1);
        }
        
        char white = args[0].substring(4).charAt(0);
        char black = args[1].substring(4).charAt(0);
        String fileName = path.getName();

        ImageHandler imgHandler = new ImageHandler(white, black, absolutePath);
        char[][] charArray = imgHandler.imgToCharArray();
        imgHandler.printCharArray(charArray);
    }
}