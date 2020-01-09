package com.company;

import java.io.File;
import java.io.IOException;

/**
 * Created by Oshada on 2019-11-30.
 */

public class winzigc {

    public static void main(String[] args) {
        // write your code here
        String arg0 = args[0];
        String programFilePath = "";
        String astFileName = "";
        if (arg0.equals("-ast")) {
            try {
                int i = 1;
                while (!args[i].equals(">")) {
                    programFilePath = programFilePath + args[i];
                    i = i + 1;
                }
                astFileName = args[i + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("invalid arguments,please check the arguments in the correct format -ast path_to_program>ast_tree_file_name");
            }
        } else {
            System.out.println("invalid arguments,please add the arguments in the format -ast path_to_program>ast_tree_file_name");
            return;
        }
        try {
            File test = new File(programFilePath);
            if (test.exists()) {
                Scanner scanner = new Scanner(programFilePath);
                Parser parser = new Parser(scanner, createASTFilePath(programFilePath, astFileName));
                parser.parseFile();
            } else {
                System.out.println("provided file does not exist in the file system");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexicalException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static String createASTFilePath(String programFilePath, String astFileName) {
        String astFilePath = "";
        String[] folders = programFilePath.split("/");
        for (int i = 0; i < folders.length - 1; i++) {
            astFilePath = astFilePath + folders[i] + "/";
        }
        return astFilePath + astFileName;
    }
}
