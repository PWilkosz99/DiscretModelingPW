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
    }

    public void StartGame(int iter) throws IOException {
        for (int it = 0; it < iter; it++) {
            ii = it;
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    checkCell(i, j);
                }
            }
            Board = tmpBoard;
            print();
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

        int cellState = Board[xx][yy];

        if ((cellState == 0) && (liveCellsCount == 3)) {
            tmpBoard[xx][yy] = 1;
        } else if ((cellState == 1) && (liveCellsCount == 2 || liveCellsCount == 3)) {
            tmpBoard[xx][yy] = 1;
        } else if ((cellState == 1) && liveCellsCount > 3) {
            tmpBoard[xx][yy] = 0;
        } else if ((cellState == 1) && liveCellsCount < 2) {
            tmpBoard[xx][yy] = 0;
        } else {
            tmpBoard[xx][yy] = 0;
        }
    }

    private void print() throws IOException {
        BufferedImage paintImg = new BufferedImage(5 * x, 5 * y, BufferedImage.TYPE_INT_ARGB);
        Graphics g = paintImg.createGraphics();
        for (int i = 5; i < (x * 5) - 5; i += 5) {
            for (int j = 0; j < y * 5; j += 5) {
                if (Board[i/5][j/5] == 1) {
                    g.setColor(new Color(255, 0, 0));
                } else {
                    g.setColor(new Color(0, 0, 0));
                }
                g.fillRect(i, j, 5, 5);
            }
        }
        ImageIO.write(paintImg, "png", new File("LifeGame/LF" + ii + ".bmp"));
        g.dispose();
    }

    public static void main(String[] args) throws IOException {
        LifeGame lf = new LifeGame(90, 90);
        lf.StartGame(5);
        //lf.print();
    }
}
