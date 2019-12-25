package com.company;

import java.io.IOException;

/**
 * Created by Oshada on 2019-11-30.
 */

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            Scanner scanner = new Scanner("D:/Campus/Sem 8/Compiler Design/Project/test.txt");
            int i = 0;
            while (i < 10) {
                Token a = scanner.getNextToken();
                System.out.println(a.getValue() + " " + a.getType());
                i = i+1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexicalException e) {
            e.printStackTrace();
        }

    }
}
