package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;


public class ForestFireCA {
    public Tree[][] Forest;
    private final int x;
    private final int y;
    private double newTreeProbability; //percent
    private double newFireProbability; //percent
    private double propagationRatio; //percent
    private int ii;
    private Byte[][] burningTime;
    private Direction direction;

    private enum Tree {
        DEAD,
        BURNING,
        LIFE,
        EXCLUDED
    }

    public enum Direction {
        North,
        South,
        West,
        East
    }

    ForestFireCA(int x, int y) {
        this.x = x;
        this.y = y;
        Forest = new Tree[x][y];
    }

    ForestFireCA(int x, int y, double TP, double FP, double P, Direction dir) {
        this(x, y);
        newTreeProbability = TP;
        newFireProbability = FP;
        propagationRatio = P;
        burningTime = new Byte[x][y];
        direction=dir;
    }

    public void StartSimulation(int iter) throws IOException {
        fillArrayByZero();
        getExclusions();
        afforestation(25);
        ii = 0;
        print();
        for (int i = 1; i < iter; i++) {
            selfImmolation();
            ii = i;
            print();
            neighborImmolation();
            buringProcess();
            afforestation();
        }
    }

    public void fillArrayByZero() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Forest[i][j] = Tree.DEAD;
                burningTime[i][j] = -1;
            }
        }
    }

    private int getRandom() {
        return (int) (Math.random() * 1000); //0-999
    }

    private double getRandomD() {
        return  (Math.random() * 1000.0); //0-999.9
    }

    private void getExclusions() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("Mapa_MD_no_terrain_low_res_Gray.txt")));
        int rows = 330;
        int columns = 600;
        int[][] bitArray = new int[rows][columns];
        while (sc.hasNextLine()) {
            for (int i = 0; i < bitArray.length; i++) {
                String[] line = sc.nextLine().trim().split("\t");
                for (int j = 0; j < line.length; j++) {
                    if (Integer.parseInt(line[j]) < 165) {
                        Forest[j][i] = Tree.EXCLUDED;
                    } else {
                        Forest[j][i] = Tree.DEAD;
                    }
                }
            }
        }
    }

    public void afforestation(double prob) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (Forest[i][j] == Tree.DEAD) {
                    if (getRandom() < 10 * prob) {
                        Forest[i][j] = Tree.LIFE;
                    }
                }
            }
        }
    }

    public void afforestation() {
        afforestation(newTreeProbability);
    }

    public void selfImmolation() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (Forest[i][j] == Tree.LIFE) {
                    if (getRandomD() < 10.0 * newFireProbability) {
                        Forest[i][j] = Tree.BURNING;
                        burningTime[i][j] = 0;
                    }
                }
            }
        }
    }

    boolean checkStateSafe(int xx, int yy) {
        if (xx < 0 || xx >= x || yy < 0 || yy >= y) {
            return false;
        } else return Forest[xx][yy] == Tree.BURNING;
    }

    private void neighborImmolation() {
        Tree[][] tmp = new Tree[x][y];
        int N, S, W, E;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (Forest[i][j] == Tree.LIFE) {
                    N = S = W = E = 0;
                    if (checkStateSafe(i - 1, j - 1)) {
                        N++;
                        W++;
                    }
                    if (checkStateSafe(i, j - 1)) {
                        N++;
                    }
                    if (checkStateSafe(i + 1, j - 1)) {
                        W++;
                        E++;
                    }
                    if (checkStateSafe(i - 1, j)) {
                        W++;
                    }
                    if (checkStateSafe(i + 1, j)) {
                        E++;
                    }
                    if (checkStateSafe(i - 1, j + 1)) {
                        W++;
                        S++;
                    }
                    if (checkStateSafe(i, j + 1)) {
                        S++;
                    }
                    if (checkStateSafe(i + 1, j + 1)) {
                        S++;
                        E++;
                    }

                    switch (direction){
                        case North:
                            N*=1.5;
                            S*=0.5;
                            break;
                        case South:
                            N*=0.5;
                            S*=1.5;
                            break;
                        case West:
                            W*=1.5;
                            E*=0.5;
                            break;
                        case East:
                            W*=0.5;
                            E*=1.5;
                            break;
                    }

                    if (getRandom() < (N+S+W+E) * propagationRatio * 10) {//without wind burningNeighboursCount * propagationRatio * 10
                        tmp[i][j] = Tree.BURNING;
                        burningTime[i][j] = 0;
                    } else {
                        tmp[i][j] = Forest[i][j];
                    }
                } else {
                    tmp[i][j] = Forest[i][j];
                }
            }
        }
        Forest = tmp.clone();
    }

    private void buringProcess() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (burningTime[i][j] > 3) {
                    Forest[i][j] = Tree.DEAD;
                    burningTime[i][j] = -1;
                } else if (burningTime[i][j] >= 0) {
                    burningTime[i][j]++;
                }
            }
        }
    }

    private void print() throws IOException {
        BufferedImage paintImg = new BufferedImage((5 * x), (5 * y), BufferedImage.TYPE_INT_ARGB);
        Graphics g = paintImg.createGraphics();
        for (int i = 5; i < (x * 5); i += 5) {
            for (int j = 5; j < (y * 5); j += 5) {
                if (Forest[x - (i / 5)][y - (j / 5)] == Tree.LIFE) {
                    g.setColor(new Color(5, 229, 5));
                } else if (Forest[x - (i / 5)][y - (j / 5)] == Tree.BURNING) {
                    g.setColor(new Color(255, 0, 0));
                } else if (Forest[x - (i / 5)][y - (j / 5)] == Tree.EXCLUDED) {
                    g.setColor(new Color(16, 140, 187));
                } else {
                    g.setColor(new Color(0, 0, 0));
                }
                g.fillRect(i, j, 5, 5);
            }
        }
        ImageIO.write(paintImg, "png", new File("Forest/F" + ii + ".bmp"));
        g.dispose();
    }

    public static void main(String[] args) throws IOException {
        ForestFireCA forest = new ForestFireCA(600, 330, 2.5, 0.005, 40, Direction.North);
        forest.StartSimulation(50);
        forest.print();
    }
}

