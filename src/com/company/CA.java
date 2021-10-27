package com.company;

import java.io.IOException;

public class CA {

    public static int convertToDecimal(Integer s0, Integer s1, Integer s2){
        return s0+(s1*2)+(s2*4);
    }

    public static char[] convertToBinary(int x){
        String tmp;
        String bs = Integer.toBinaryString(x);
        while(bs.length()<8){
            tmp = bs;
            bs="0";
            bs+=tmp;
        }
        return bs.toCharArray();
    }

    public static Boolean checkNext(int upperValue, char[] rule){
        return rule[upperValue] == '1';
    }

    public static void CA(int[][]Table, int ruleNumber){
        int length, width;
        length=10;
        width=5; //to be changed
        char[] rule = convertToBinary(ruleNumber);
        for (int i = 1; i <length; i++) {
            for (int j = 0; j < width-1; j++) {
                var d = convertToDecimal(Table[i-1][j],Table[i][j],Table[i+1][j]);
                if(checkNext(d,rule)){
                    Table[i][j+1]=1;
                }
            }
        }
    }

    //TODO: drawing

    public static void main(String[] args) throws IOException {
        System.out.println(convertToDecimal(1,1,1));
        System.out.println(convertToBinary(128));
    }
}
