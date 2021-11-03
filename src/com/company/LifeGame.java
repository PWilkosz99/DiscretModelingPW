package com.company;

public class LifeGame {
    int[][] Board;
    int x;
    int y;

    LifeGame(int x, int y) {
        Board = new int[x][y];
        this.x = x;
        this.y = y;
    }

    void StartGame() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

            }
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

    void checkCell(int xx, int yy){
        int liveCellsCount=0;
        if(checkStateSafe(xx-1,yy-1)) liveCellsCount++;
        if(checkStateSafe(xx,yy-1)) liveCellsCount++;
        if(checkStateSafe(xx+1,yy-1)) liveCellsCount++;
        if(checkStateSafe(xx-1,yy)) liveCellsCount++;
        if(checkStateSafe(xx+1,yy)) liveCellsCount++;
        if(checkStateSafe(xx-1,yy+1)) liveCellsCount++;
        if(checkStateSafe(xx,yy+1)) liveCellsCount++;
        if(checkStateSafe(xx+1,yy+1)) liveCellsCount++;

        int cellState=Board[xx][yy];

        if((cellState==0)&&(liveCellsCount==3)){
            Board[xx][yy]=1;
        }else if((cellState==1)&&(liveCellsCount==2||liveCellsCount==3)){
            Board[xx][yy]=1;
        }else if((cellState==1)&&liveCellsCount>3){
            Board[xx][yy]=0;
        }else if((cellState==1)&&liveCellsCount<2){
            Board[xx][yy]=0;
        }
    }


    public static void main(String[] args) {
        System.out.println("a");
    }
}
