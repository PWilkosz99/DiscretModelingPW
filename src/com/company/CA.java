package com.company;

import java.io.IOException;

public class CA {

    public static int convertToDecimal(Character s0, Character s1, Character s2){
        return Character.getNumericValue(s0)+(Character.getNumericValue(s1)*2)+(Character.getNumericValue(s2)*4);
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

    public static void main(String[] args) throws IOException {
        System.out.println(convertToDecimal('1','1','1'));
        System.out.println(convertToBinary(128)[0]);
    }
}
