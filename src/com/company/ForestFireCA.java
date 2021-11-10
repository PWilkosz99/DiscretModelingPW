package com.company;

import java.io.IOException;

public class ForestFireCA {
    public int[][] Forest;
    private final int x;
    private final int y;

    ForestFireCA(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void fillArrayByZero() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Forest[i][j] = 0;
            }
        }
    }

    private int getRandom() {
        return (int) (Math.random() * 100); //0-99
    }

    public static void main(String[] args) throws IOException {
    }
}

