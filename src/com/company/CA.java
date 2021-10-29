package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
        Character tmpc;
        char[] ctab = bs.toCharArray();
        for (int i = 0; i < 4; i++) {
            tmpc=ctab[i];
            ctab[i]=ctab[7-i];
            ctab[7-i]=tmpc;
        }
        return ctab;
    }

    public static Boolean checkNext(int upperValue, char[] rule) {
        return rule[upperValue] == '1';
    }

    public static int[][] CA(int[][] Table, int ruleNumber) {
        int height, width;
        int[][] res = Table;
        height = 40;
        width = 30;
        char[] rule = convertToBinary(ruleNumber);
        for (int k = 0; k < 30; k++) {
            for (int j = 0; j < height-1; j++) {
                for (int i = 1; i < width-1; i++) {
                    var d = convertToDecimal(Table[i - 1][j], Table[i][j], Table[i + 1][j]);
                    if (checkNext(d, rule)) {
                        res[i][j + 1] = 1;
                    }
                }
            }
            res = periodicFill(res);
            Table = res;
        }
        return Table;
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

    public static void printCA(int[][] Table) throws IOException {
        BufferedImage paintImg = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
        Graphics g = paintImg.createGraphics();

        for (int i = 20; i < 580; i += 5) {
            for (int j = 0; j < 800; j += 5) {
                if (Table[i / 20][j / 20] == 1) {
                    g.setColor(new Color(255, 0, 0));
                } else {
                    g.setColor(new Color(0, 0, 0));
                }
                g.fillRect(i, j, 5, 5);
            }
        }
        ImageIO.write(paintImg, "png", new File("CA.bmp"));
        g.dispose();
    }

    public static void main(String[] args) throws IOException {
        int[][] tb = new int[30][40]; // main board [1:28], rest work as periodic cells
        tb[14][0] = 1;
        tb = CA(tb, 250);
        printCA(tb);
    }
}
