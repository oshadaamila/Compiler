package com.company;

import java.io.IOException;



/**
 * Created by Oshada on 2019-12-20.
 */
public class Parser {
    protected Scanner scanner;
    String parsedString;
    Token currentToken;
    boolean parse;
    private Token.TYPE type;


    public Parser(Scanner scanner) throws IOException, LexicalException {
        this.scanner = scanner;
        this.parsedString = "";
        this.currentToken = scanner.getNextToken();
        this.parse = false;

    }

    public void parseFile() throws IOException, LexicalException, ParseException {
        this.start();
        if (parse) {
            System.out.println("parse successs");
        } else {
            System.out.printf("parse unsuccess");
        }

    }

    private void start() throws IOException, LexicalException, ParseException {
        int n = 1;
        type = currentToken.getType();
        switch (type) {
            case BEGIN:
                read(Token.TYPE.BEGIN);
                start();
                read(Token.TYPE.END);
                //add build tree function here
                break;
            case IDENTIFIER:
                read(Token.TYPE.IDENTIFIER);
                read(Token.TYPE.ASSIGNMENT);
                E();
                read(Token.TYPE.SEMICOLON);
                break;
            default:
                throw new ParseException("Cannot Parse");

        }

    }

    private void E() throws IOException, LexicalException, ParseException {
        T();
        while (type == Token.TYPE.PLUS) {
            read(Token.TYPE.PLUS);
            T();
        }
    }

    private void T() throws IOException, LexicalException, ParseException {
        P();
        if (type == Token.TYPE.MULTIPLY) {
            read(Token.TYPE.MULTIPLY);
            T();
        }
    }

    private void P() throws IOException, LexicalException, ParseException {
        switch (type) {
            case OPEN_PAREN:
                read(Token.TYPE.OPEN_PAREN);
                E();
                read(Token.TYPE.CLOSED_PAREN);
                break;
            case IDENTIFIER:
                read(Token.TYPE.IDENTIFIER);
                read(Token.TYPE.END);
                break;
        }
    }


    private void read(Token.TYPE type) throws IOException, LexicalException, ParseException {

        if (currentToken.getType() == type) {
            this.parsedString = this.parsedString + currentToken.getValue();
            currentToken = scanner.getNextToken();
            this.type = currentToken.getType();
            parse = true;
            return;
        } else {
            parse = false;
            return;
        }
    }
}
