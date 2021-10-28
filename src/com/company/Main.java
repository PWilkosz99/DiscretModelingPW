package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

import static java.lang.Math.ceil;

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
        return bitArray;
    }

    static int[][] Brightening(int[][] bits, int b) {
        int maxvalue = 255;

        for (int i = 0; i < 330; i++) {
            for (int j = 0; j < 600; j++) {
                var a = bits[i][j] + b;
                if (a < 0) {
                    bits[i][j] = 0;
                } else bits[i][j] = Math.min(a, maxvalue);
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

    static boolean checkBounds(int[][] bits, int x, int y, boolean isEr){
        if(x<0||y<0||x>=330||y>=600){
            return false;
        }else{
            boolean res = bits[x][y] < 200;
            if(isEr){
                return !res;
            }else{
                return res;
            }
        }
    }

    static int[][] Dilatation(int[][] bits) {
        int[][] res = new int[330][600];
        for (int i = 0; i < 330; i++) {
            for (int j = 0; j < 600; j++) {
                if (checkBounds(bits, i - 1, j - 1, false)) {
                    res[i][j] = 0;
                } else if (checkBounds(bits, i, j - 1, false)) {
                    res[i][j] = 0;
                } else if (checkBounds(bits, i + 1, j - 1, false)) {
                    res[i][j] = 0;
                } else if (checkBounds(bits, i - 1, j, false)) {
                    res[i][j] = 0;
                } else if (checkBounds(bits, i + 1, j, false)) {
                    res[i][j] = 0;
                } else if (checkBounds(bits, i - 1, j + 1, false)) {
                    res[i][j] = 0;
                } else if (checkBounds(bits, i, j + 1, false)) {
                    res[i][j] = 0;
                } else if (checkBounds(bits, i + 1, j + 1, false)) {
                    res[i][j] = 0;
                }else{
                    res[i][j] = 255;
                }
            }
        }
        return res;
    }

    static int[][] Erosion(int[][] bits) {
        int[][] res = new int[330][600];
        for (int i = 0; i < 330; i++) {
            for (int j = 0; j < 600; j++) {
                if (checkBounds(bits, i - 1, j - 1, true)) {
                    res[i][j] = 255;
                } else if (checkBounds(bits, i, j - 1, true)) {
                    res[i][j] = 255;
                } else if (checkBounds(bits, i + 1, j - 1, true)) {
                    res[i][j] = 255;
                } else if (checkBounds(bits, i - 1, j, true)) {
                    res[i][j] = 255;
                } else if (checkBounds(bits, i + 1, j, true)) {
                    res[i][j] = 255;
                } else if (checkBounds(bits, i - 1, j + 1, true)) {
                    res[i][j] = 255;
                } else if (checkBounds(bits, i, j + 1, true)) {
                    res[i][j] = 255;
                } else if (checkBounds(bits, i + 1, j + 1, true)) {
                    res[i][j] = 255;
                }else{
                    res[i][j] = 0;
                }
            }
        }
        return res;
    }

    static void makeHistogram(int[][] bits) throws IOException {
        int[] values = new int[256];
        for (int i = 0; i < 330; i++) {
            for (int j = 0; j < 600; j++) {
                values[bits[i][j]]++;
            }
        }

        int maxval = -1;

        for (int i = 0; i < 255; i++) {
            if (values[i] > maxval) {
                maxval = values[i];
            }
        }

        double[] perc = new double[256];

        for (int i = 0; i < 256; i++) {
            perc[i] = (1.0 * values[i] / maxval) * 100;
            System.out.println(perc[i]);
        }

        BufferedImage paintImg = new BufferedImage(256, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = paintImg.createGraphics();

        for (int dx = 0; dx < 256; dx++) {
            int black = (int) ceil(perc[dx]);
            for (int dy = 0; dy < black; dy++) {

                g.setColor(new Color(1, 1, 1));
                g.fillRect(dx, dy, 1, 1);
            }
        }
        ImageIO.write(paintImg, "png", new File("histogram.bmp"));
        g.dispose();

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


        for (int dx = 0; dx < 330; dx++) {
            for (int dy = 0; dy < 600; dy++) {
                g.setColor(new Color(bits[dx][dy], bits[dx][dy], bits[dx][dy]));
                g.fillRect(dx, dy, 1, 1);
            }
        }
        ImageIO.write(paintImg, "png", new File("output.bmp"));
        g.dispose();

    }

    static boolean checkBound(int x, int y) {
        return x >= 0 && x <= 300 && y >= 0 && y <= 600;
    }

    static int normalizeRGB(int val) {
        if (val > 0 && val < 256) {
            return val;
        } else if (val < 0) {
            return 0;
        } else {
            return 255;
        }
    }


    static int[][] lowPassFilter(int[][] bits) {
        int[][] weights = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};

        return useFilter(bits, weights);
    }


    static int[][] highPassFilter(int[][] bits) {
        int[][] weights = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};

        return useFilter(bits, weights);
    }

    static int[][] gaussianFilter(int[][] bits) {
        int[][] weights = {{1, 4, 1}, {4, 32, 4}, {1, 4, 1}};

        return useFilter(bits, weights);
    }

    private static int[][] useFilter(int[][] bits, int[][] weights) {
        int weightSum;
        int[][] res = new int[330][600];
        int sum = 0;

        for (int dx = 0; dx < 330; dx++) {
            for (int dy = 0; dy < 600; dy++) {
                sum = 0;
                weightSum = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (checkBound(dy + i, dx + j)) {
                            sum += bits[dy + i][dx + j] * weights[i + 1][j + 1];
                            weightSum += weights[i + 1][j + 1];
                        }
                    }
                }
                if (weightSum != 0) {
                    res[dx][dy] = normalizeRGB(sum / weightSum);
                } else {
                    res[dx][dy] = normalizeRGB(sum);
                }
            }
        }
        return res;
    }


    public static void main(String[] args) throws IOException {
        var a = getBitArray();
        a = Binarization(a, 200);
        //a = Dilatation(a);
        a = Erosion(a);
        //a = highPassFilter(a);
        //a = lowPassFilter(a);
        //System.out.println(Arrays.deepToString(a));
        //makeHistogram(a);
        //saveToTxtFile(a);
        saveToBmp(a);
    }
}
