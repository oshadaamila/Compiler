package com.company;

import java.io.IOException;



/**
 * Created by Oshada on 2019-12-20.
 */
public class Parser {
    protected Scanner scanner;
    String parsedString;
    Token nextToken;
    private Token.TYPE type;


    public Parser(Scanner scanner) throws IOException, LexicalException {
        this.scanner = scanner;
        this.parsedString = "";
        this.nextToken = scanner.getNextValidToken();

    }

    public void parseFile() throws IOException, LexicalException, ParseException {
        this.start();


    }

//    private void start() throws IOException, LexicalException, ParseException {
//        int n = 1;
//        type = nextToken.getType();
//        switch (type) {
//            case BEGIN:
//                read(Token.TYPE.BEGIN);
//                start();
//                read(Token.TYPE.END);
//                //add build tree function here
//                break;
//            case IDENTIFIER:
//                read(Token.TYPE.IDENTIFIER);
//                read(Token.TYPE.ASSIGNMENT);
//                E();
//                read(Token.TYPE.SEMICOLON);
//                break;
//            default:
//                throw new ParseException("Cannot Parse");
//
//        }
//
//    }
//
//    private void E() throws IOException, LexicalException, ParseException {
//        T();
//        while (type == Token.TYPE.PLUS) {
//            read(Token.TYPE.PLUS);
//            T();
//        }
//    }
//
//    private void T() throws IOException, LexicalException, ParseException {
//        P();
//        if (type == Token.TYPE.MULTIPLY) {
//            read(Token.TYPE.MULTIPLY);
//            T();
//        }
//    }
//
//    private void P() throws IOException, LexicalException, ParseException {
//        switch (type) {
//            case OPEN_PAREN:
//                read(Token.TYPE.OPEN_PAREN);
//                E();
//                read(Token.TYPE.CLOSED_PAREN);
//                break;
//            case IDENTIFIER:
//                read(Token.TYPE.IDENTIFIER);
//                read(Token.TYPE.END);
//                break;
//        }
//    }
private void start() throws LexicalException, ParseException, IOException {
    if (read(Token.TYPE.START)) {
        NAME();
        if (read(Token.TYPE.COLON)) {
            CONSTS();
            TYPES();
            DCLNS();
            SUBPROGS();
            NAME();
            if (!read(Token.TYPE.DOT)) {
                throw new ParseException("cannot parse");
            }
        } else {
            throw new ParseException("cannot parse");
        }
    } else {
        throw new ParseException("cannot parse");
    }
}

    private void NAME() throws LexicalException, ParseException, IOException {
        if (!read(Token.TYPE.IDENTIFIER)) {
            throw new ParseException("cannot parse");
        }
    }

    private void CONSTS() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.CONSTANT)) {
            //here we assumes that each constant is seperated by a comma
            while (nextToken.getType() != Token.TYPE.SEMICOLON) {
                CONST();
                if (!read(Token.TYPE.COMMA)) {
                    throw new ParseException("cannot parse");
                }
            }
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("cannot parse");
            }
        } else {
            return;
        }
    }

    // TODO: 2019-12-26 implement this 3
    private void TYPES() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.TYPE)) {

        } else {
            return;
        }
    }

    private void DCLNS() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.VARIABLE)) {
            DCLN();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("cannot Parse");
            }
            while (nextToken.getType() == Token.TYPE.IDENTIFIER) {
                DCLN();
                if (read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("cannot Parse");
                }
            }

        } else {
            return;
        }
    }

    // TODO: 2019-12-26 implement this 5
    private void SUBPROGS() {
    }

    // TODO: 2019-12-26 implement this 6
    private void CONST() {
    }


    private void DCLN() throws LexicalException, ParseException, IOException {
        NAME();
        while (!read(Token.TYPE.COLON)) {
            NAME();
            if (!read(Token.TYPE.COMMA)) {
                throw new ParseException("cannot parse");
            }
        }
        NAME();
    }

    private boolean read(Token.TYPE type) throws IOException, LexicalException, ParseException {

        if (nextToken.getType() == type) {
            this.parsedString = this.parsedString + nextToken.getValue();
            nextToken = scanner.getNextValidToken();
            this.type = nextToken.getType();
            return true;
        } else {
            return false;
        }
    }
}

