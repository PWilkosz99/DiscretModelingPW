package com.company;

import java.io.IOException;

public class ForestFireCA {
    public int[][] Forest;
    private final int x;
    private final int y;
    private double newTreeProbability; //percent
    private double newFireProbability; //percent

    ForestFireCA(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    ForestFireCA(int x, int y, double TP, double FP) {
        this(x,y);
        newTreeProbability=TP;
        newFireProbability=FP;
    }

    public void fillArrayByZero() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Forest[i][j] = 0;
            }
        }
    }

    private int getRandom() {
        return (int) (Math.random() * 1000); //0-999
    }

    public static void main(String[] args) throws IOException {

    }
}

