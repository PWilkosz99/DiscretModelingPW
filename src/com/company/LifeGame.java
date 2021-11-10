package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class LifeGame {
    public int[][] Board;
    private int[][] tmpBoard;
    private final int x;
    private final int y;
    private final int cx;
    private final int cy;
    private int ii;

    LifeGame(int x, int y) {
        Board = new int[x][y];
        tmpBoard = new int[x][y];
        this.x = x;
        this.y = y;
        cx=x/2;
        cy=y/2;
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
        for (int i = 0; i < y; i++) {
            Board[0][i] = Board[x - 2][i];
            Board[x - 1][i] = Board[1][i];
        }
        for (int i = 0; i < x; i++) {
            Board[i][0] = Board[i][x - 2];
            Board[i][x - 1] = Board[i][1];
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
                if (Board[x - (i / 5)][y - (j / 5)] == 1) {
                    g.setColor(new Color(0, 255, 0));
                } else {    
                    g.setColor(new Color(255, 0, 0));
                }
                g.fillRect(i, j, 5, 5);
            }
        }
        ImageIO.write(paintImg, "png", new File("LifeGame/LF" + ii + ".bmp"));
        g.dispose();
    }

    public void startRuleGilder() {
        Board[cx][cy] = 1;
        Board[cx + 1][cy] = 1;
        Board[cx - 1][cy + 1] = 1;
        Board[cx][cy + 1] = 1;
        Board[cx + 1][cy + 2] = 1;
    }

    public void startRuleOscillator() {
        Board[cx - 1][cy] = 1;
        Board[cx][cy] = 1;
        Board[cx + 1][cy] = 1;
    }

    public void startRuleBeeHive(){
        Board[cx-1][cy]=1;
        Board[cx+2][cy]=1;
        Board[cx][cy+1]=1;
        Board[cx+1][cy+1]=1;
        Board[cx][cy-1]=1;
        Board[cx+1][cy-1]=1;
    }

    public void startRuleRandom(){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Board[i][j]=(int)Math.round(Math.random());
            }
        }
    }

    public void startRuleOwn(Map.Entry<Integer, Integer>[] values){
        for (Map.Entry<Integer, Integer> v: values) {
            Board[v.getKey()][v.getValue()]=1;
        }
    }

    public static void main(String[] args) throws IOException {
        LifeGame lf = new LifeGame(90, 90);
        lf.fillArrayByZero();
        lf.startRuleRandom();
        lf.StartGame(10);
    }
}
