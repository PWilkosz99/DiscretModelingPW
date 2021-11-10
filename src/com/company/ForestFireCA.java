package com.company;

import java.io.IOException;



public class ForestFireCA {
    public Tree[][] Forest;
    private final int x;
    private final int y;
    private double newTreeProbability; //percent
    private double newFireProbability; //percent

    private enum Tree {
        DEAD,
        BURNING,
        LIFE
    }

    ForestFireCA(int x, int y) {
        this.x = x;
        this.y = y;
    }

    ForestFireCA(int x, int y, double TP, double FP) {
        this(x,y);
        newTreeProbability=TP;
        newFireProbability=FP;
    }

    public void StartSimulation(){
        fillArrayByZero();

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

    public void afforestation(){
        //int[][] tmp = Forest.clone();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if(Forest[i][j]==Tree.DEAD){
                    if(getRandom()<20){
                        Forest[i][j]=Tree.LIFE;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

    }
}

