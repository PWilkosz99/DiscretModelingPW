package com.company;

import java.io.Console;
import java.io.IOException;

public class CA {

    public static int convertToDecimal(Character s0, Character s1, Character s2){
        return Character.getNumericValue(s0)+(Character.getNumericValue(s1)*2)+(Character.getNumericValue(s2)*4);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(convertToDecimal('1','1','1'));
    }
}
