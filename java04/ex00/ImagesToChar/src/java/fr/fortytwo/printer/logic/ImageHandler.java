package fr.fortytwo.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHandler {

    private  static char white;
    private  static char black;
    private String imagePath;
    private static BufferedImage imgBuffer;

    public ImageHandler(char white, char black, String imagePath) {
        this.white = white;
        this.black = black;
        this.imagePath = imagePath;

        try {
            imgBuffer = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[][]  imgToCharArray() {
        int width = imgBuffer.getWidth();
        int height = imgBuffer.getHeight();
        char[][] charArray = new char[height][width];
        for (int i = 0; i < height; i++) {
            charArray[i] = new char[width];
            for (int j = 0; j < width; j++) {
                int rgb = imgBuffer.getRGB(j, i);
                if (rgb == Color.BLACK.getRGB()) {
                    charArray[i][j] = black;
                } else if (rgb == Color.WHITE.getRGB()){
                    charArray[i][j] = white;
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
