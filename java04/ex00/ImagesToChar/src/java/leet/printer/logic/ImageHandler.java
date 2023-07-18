package leet.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHandler {

    private  static char dot;
    private  static char zero;
    private String imgPath;
    private static BufferedImage img;

    public ImageHandler(char dot, char zero, String imgPath) {
        this.dot = dot;
        this.zero = zero;
        this.imgPath = imgPath;

        try {
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    // private static void imgHandler(String[] args) {
    //     dot = args[0].charAt(0);
    //     zero = args[1].charAt(0);
        
    // }

    public char[][]  imgToCharArray() {
        int width = img.getWidth();
        int height = img.getHeight();
        char[][] charArray = new char[height][width];
        for (int i = 0; i < height; i++) {
            charArray[i] = new char[width];
            for (int j = 0; j < width; j++) {
                int rgb = img.getRGB(j, i);
                if (rgb == Color.BLACK.getRGB()) {
                    charArray[i][j] = zero;
                } else if (rgb == Color.WHITE.getRGB()){
                    charArray[i][j] = dot;
                }
            }
        }
        return charArray;
    }

    public void printCharArray(char[][] charArray) {
        for (char[] chars : charArray) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }   
}
