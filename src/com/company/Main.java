package com.company;

import java.io.IOException;

/**
 * Created by Oshada on 2019-11-30.
 */

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            Scanner scanner = new Scanner("C://Users/Oshada/IdeaProjects/Compiler/src/com/company/test.txt");
            Parser parser = new Parser(scanner);
            parser.parseFile();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
