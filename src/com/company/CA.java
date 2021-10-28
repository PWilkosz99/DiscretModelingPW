package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CA {

    public static int convertToDecimal(Integer s0, Integer s1, Integer s2) {
        return s0 + (s1 * 2) + (s2 * 4);
    }

    public static char[] convertToBinary(int x) {
        String tmp;
        String bs = Integer.toBinaryString(x);
        while (bs.length() < 8) {
            tmp = bs;
            bs = "0";
            bs += tmp;
        }
        return bs.toCharArray();
    }

    public static Boolean checkNext(int upperValue, char[] rule) {
        return rule[upperValue] == '1';
    }

    public static void CA(int[][] Table, int ruleNumber) {
        int height, width;
        height = 10;
        width = 5; //to be changed
        char[] rule = convertToBinary(ruleNumber);
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                var d = convertToDecimal(Table[i - 1][j], Table[i][j], Table[i + 1][j]);
                if (checkNext(d, rule)) {
                    Table[i][j + 1] = 1;
                }
            }
        }
        Table = periodicFill(Table);
    }

    public static int[][] periodicFill(int[][] Table) {
        int height = 10;
        int width = 10;//tmp
        for (int i = 0; i < height; i++) {
            Table[0][height] = Table[width][height];
            Table[width - 1][height] = Table[1][height];
        }
        return Table;
    }

    public static void printCA(int Table[][]) throws IOException {
        BufferedImage paintImg = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
        Graphics g = paintImg.createGraphics();

        for (int i = 20; i < 580; i+=5) {
            for (int j = 0; j < 800; j+=5) {
//                for (int k = i*5; k < (i*5)+5; k++) {
//                    for (int l = j*5; l < (j*5)+5; l++) {
                if (Table[i/20][j/20] == 1) {
                    g.setColor(new Color(0, 0, 0));
                } else {
                    g.setColor(new Color(250, 4, 4));
                }
                g.fillRect(i, j, 5, 5);
//                    }
//                }
            }
        }
        ImageIO.write(paintImg, "png", new File("CA.bmp"));
        g.dispose();
    }

    public static void main(String[] args) throws IOException {
        int[][] tb = new int[30][40];
        tb[28][0]=1;
        printCA(tb);
    }
}
