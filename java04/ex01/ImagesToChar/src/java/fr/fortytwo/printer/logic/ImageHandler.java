package fr.fortytwo.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHandler {

    private static char white;
    private static char black;
    private static String imgPath = "/resources/image.bmp";
    private static BufferedImage img;

    public ImageHandler(char white, char black) {
        this.white = white;
        this.black = black;

        try {
            img = ImageIO.read(ImageHandler.class.getResource(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[][] imgToCharArray() {
        int width = img.getWidth();
        int height = img.getHeight();
        char[][] charArray = new char[height][width];
        for (int i = 0; i < height; i++) {
            charArray[i] = new char[width];
            for (int j = 0; j < width; j++) {
                int rgb = img.getRGB(j, i);
                if (rgb == Color.BLACK.getRGB()) {
                    charArray[i][j] = black;
                } else if (rgb == Color.WHITE.getRGB()) {
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