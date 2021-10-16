package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int[][] getBitArray() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("Mapa_MD_no_terrain_low_res_Gray.txt")));
        int rows = 330;
        int columns = 600;
        int[][] bitArray = new int[rows][columns];
        while (sc.hasNextLine()) {
            for (int i = 0; i < bitArray.length; i++) {
                String[] line = sc.nextLine().trim().split("\t");
                for (int j = 0; j < line.length; j++) {
                    bitArray[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        System.out.println(Arrays.deepToString(bitArray));
        return bitArray;
    }

    static int[][] Brightening(int[][] bits, int b) {
        int maxvalue = 255;

        for (int i = 0; i < 330; i++) {
            for (int j = 0; j < 600; j++) {
                var a = bits[i][j] + b;
                if (a < 0) {
                    bits[i][j] = 0;
                } else if (a > maxvalue) {
                    bits[i][j] = maxvalue;
                } else {
                    bits[i][j] = a;
                }
            }
        }
        return bits;
    }

    static int[][] Binarization(int[][] bits, int val) {
        for (int i = 0; i < 330; i++) {
            for (int j = 0; j < 600; j++) {
                if (bits[i][j] >= val) {
                    bits[i][j] = 255;
                } else {
                    bits[i][j] = 0;
                }
            }
        }
        return bits;
    }

    static void makeHistogram(int[][] bits) {
        int[] values = new int[256];
        for (int i = 0; i < 330; i++) {
            for (int j = 0; j < 600; j++) {
                values[bits[i][j]]++;
            }
        }

        for (int i = 0; i < 255; i++) {
            System.out.println(values[i]);
        }
    }

    static void saveToTxtFile(int[][] bits) {
        try {
            PrintWriter writer = new PrintWriter("res.txt");
            for (int i = 0; i < 330; i++) {
                for (int j = 0; j < 600; j++) {
                    writer.print(bits[i][j] + " ");
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void saveToBmp(int[][] bits) throws IOException {
        BufferedImage paintImg = new BufferedImage(600, 330, BufferedImage.TYPE_INT_ARGB);
        Graphics g = paintImg.createGraphics();

        for (int dy = 0; dy < 600; dy++) {
            for (int dx = 0; dx < 330; dx++) {

                g.setColor(new Color(bits[dx][dy], bits[dx][dy], bits[dx][dy]));
                g.fillRect(dx, dy, 1, 1);
            }
        }
        ImageIO.write(paintImg, "png", new File("output.bmp"));
        g.dispose();

    }

    public static void main(String[] args) throws IOException {
        var a = getBitArray();
        a = Binarization(a, 220);
        System.out.println(Arrays.deepToString(a));
        //makeHistogram(a);
        //saveToTxtFile(a);
        saveToBmp(a);
    }
}
