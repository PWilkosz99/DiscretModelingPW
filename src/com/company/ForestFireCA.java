package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ForestFireCA {
    public Tree[][] Forest;
    private final int x;
    private final int y;
    private double newTreeProbability; //percent
    private double newFireProbability; //percent
    private int ii;

    private enum Tree {
        DEAD,
        BURNING,
        LIFE
    }

    ForestFireCA(int x, int y) {
        this.x = x;
        this.y = y;
        Forest = new Tree[x][y];
    }

    ForestFireCA(int x, int y, double TP, double FP) {
        this(x, y);
        newTreeProbability = TP;
        newFireProbability = FP;
    }

    public void StartSimulation() throws IOException {
        fillArrayByZero();
        afforestation(5);
        ii=0;
        print();
        for (int i = 1; i < 10; i++) {
            selfImmolation();
            ii=i;
            print();
            afforestation();
        }
    }

    public void fillArrayByZero() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Forest[i][j] = Tree.DEAD;
            }
        }
    }

    private int getRandom() {
        return (int) (Math.random() * 1000); //0-999
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

    public void afforestation(){
        afforestation(newTreeProbability);
    }

    public void selfImmolation(){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (Forest[i][j] == Tree.LIFE) {
                    if (getRandom() < 10 * newFireProbability) {
                        Forest[i][j] = Tree.BURNING;
                    }
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
        ForestFireCA forest = new ForestFireCA(500, 500, 0.5, 2);
        forest.StartSimulation();
        forest.print();
    }
}

