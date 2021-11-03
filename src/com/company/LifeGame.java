package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LifeGame {
    public int[][] Board;
    private int[][] tmpBoard;
    private final int x;
    private final int y;
    private int ii;

    LifeGame(int x, int y) {
        Board = new int[x][y];
        tmpBoard = new int[x][y];
        this.x = x;
        this.y = y;
        periodicFill();
    }

    public void StartGame(int iter) throws IOException {
        print();
        iter++;
        for (int it = 1; it < iter; it++) {
            ii = it;
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    checkCell(i, j);
                }
            }
            Board = tmpBoard;
            tmpBoard = new int[x][y];
            print();
            periodicFill();
        }
    }

    boolean checkStateSafe(int xx, int yy) {
        if (xx < 0 || xx >= x || yy < 0 || yy >= y) {
            return false;
        } else return Board[xx][yy] == 1;
    }
    // [-1,-1]  [0,-1]   [1,-1]
    // [-1, 0] [xx, yy]  [1, 0]
    // [-1, 1]  [0, 1]   [1, 1]

    void checkCell(int xx, int yy) {
        int liveCellsCount = 0;
        if (checkStateSafe(xx - 1, yy - 1)) liveCellsCount++;
        if (checkStateSafe(xx, yy - 1)) liveCellsCount++;
        if (checkStateSafe(xx + 1, yy - 1)) liveCellsCount++;
        if (checkStateSafe(xx - 1, yy)) liveCellsCount++;
        if (checkStateSafe(xx + 1, yy)) liveCellsCount++;
        if (checkStateSafe(xx - 1, yy + 1)) liveCellsCount++;
        if (checkStateSafe(xx, yy + 1)) liveCellsCount++;
        if (checkStateSafe(xx + 1, yy + 1)) liveCellsCount++;

        if (liveCellsCount < 2) {
            tmpBoard[xx][yy] = 0;
        }  //underpop
        else if (liveCellsCount > 3) {
            tmpBoard[xx][yy] = 0;
        } //overcrowd
        else if (liveCellsCount == 3) {
            tmpBoard[xx][yy] = 1;
        } //born
        else if (liveCellsCount == 2) {
            tmpBoard[xx][yy] = Board[xx][yy];
        } // stay same
    }

    private void periodicFill() {
        int height = y - 1;
        int width = x - 1;
        for (int i = 0; i < y - 1; i++) {
            Board[0][height] = Board[width][height];
            Board[width - 1][height] = Board[1][height];
        }
    }

    public void fillArrayByZero() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Board[i][j] = 0;
            }
        }
    }

    private void print() throws IOException {
        BufferedImage paintImg = new BufferedImage((5 * x) - 10, (5 * y) - 10, BufferedImage.TYPE_INT_ARGB);
        Graphics g = paintImg.createGraphics();
        for (int i = 5; i < (x * 5) - 5; i += 5) {
            for (int j = 5; j < (y * 5) - 5; j += 5) {
                if (Board[i / 5][j / 5] == 1) {
                    g.setColor(new Color(0, 255, 0));
                } else {
                    g.setColor(new Color(255, 255, 255));
                }
                g.fillRect(i, j, 5, 5);
            }
        }
        ImageIO.write(paintImg, "png", new File("LifeGame/LF" + ii + ".bmp"));
        g.dispose();
    }

    public void startRuleGilder() {
        int cx = x / 2;
        int cy = y / 2;
        Board[cx][cy] = 1;
        Board[cx + 1][cy] = 1;
        Board[cx - 1][cy + 1] = 1;
        Board[cx][cy + 1] = 1;
        Board[cx + 1][cy + 2] = 1;
    }

    public void startRuleOscillator() {
        int cx = x / 2;
        int cy = y / 2;
        Board[cx - 1][cy] = 1;
        Board[cx][cy] = 1;
        Board[cx + 1][cy] = 1;
    }// change x=>y


    public static void main(String[] args) throws IOException {
        LifeGame lf = new LifeGame(10, 10);
        lf.fillArrayByZero();
        lf.startRuleOscillator();
        lf.StartGame(10);
    }
}
