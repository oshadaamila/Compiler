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

    private void TYPES() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.TYPE)) {
            TYPE();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("cannot Parse");
            }
            while (nextToken.getType() == Token.TYPE.IDENTIFIER) {
                TYPE();
                if (read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("cannot Parse");
                }
            }
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

    private void SUBPROGS() throws LexicalException, ParseException, IOException {
        if (nextToken.getType() != Token.TYPE.FUNCTION) {
            throw new ParseException("cannot parse");
        }
        while (nextToken.getType() == Token.TYPE.FUNCTION) {
            FCN();
        }
    }

    private void CONST() throws LexicalException, ParseException, IOException {
        NAME();
        if (!read(Token.TYPE.EQUAL)) {
            return;
        }
        LITLIST();
    }

    private void LITLIST() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.OPEN_PAREN)) {
            NAME();
            while (nextToken.getType() == Token.TYPE.COMMA) {
                NAME();
            }
            if (!read(Token.TYPE.CLOSED_PAREN)) {
                throw new ParseException("cannot parse");
            }
        } else {
            throw new ParseException("cannot parse");
        }
    }

    private void TYPE() throws LexicalException, ParseException, IOException {
        NAME();
        if (!read(Token.TYPE.EQUAL)) {
            throw new ParseException("cannot parse");
        }
        LITLIST();
    }


    private void FCN() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.FUNCTION)) {
            NAME();
            if (!read(Token.TYPE.OPEN_PAREN)) {
                throw new ParseException("cannot parse");
            }
            PARAMS();
            if (!read(Token.TYPE.CLOSED_PAREN)) {
                throw new ParseException("cannot parse");
            }
            if (!read(Token.TYPE.COLON)) {
                throw new ParseException("cannot parse");
            }
            NAME();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("cannot parse");
            }
            CONSTS();
            TYPES();
            DCLNS();
            BODY();
            NAME();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("cannot parse");
            }

        } else {
            throw new ParseException("cannot parse");

        }
    }

    private void PARAMS() throws LexicalException, ParseException, IOException {
        DCLN();
        while (read(Token.TYPE.COMMA)) {
            DCLN();
        }

    }

    private void CONSTVALUE() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case INTEGER:
                read(Token.TYPE.INTEGER);
                break;
            case CHAR:
                read(Token.TYPE.CHAR);
                break;
            case IDENTIFIER:
                NAME();
                break;
            default:
                throw new ParseException("cannot parse");
        }
    }


    private void BODY() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.BEGIN)) {
            STATEMENT();
            while (nextToken.getType() == Token.TYPE.SEMICOLON) {
                STATEMENT();
            }
            if (!read(Token.TYPE.END)) {
                throw new ParseException("cannot parse");
            }
        } else {
            throw new ParseException("cannot parse");
        }
    }


    private void DCLN() throws LexicalException, ParseException, IOException {
        NAME();
        while (!read(Token.TYPE.COLON)) {
            NAME();
            if (!read(Token.TYPE.COMMA)) {
                //throw new ParseException("cannot parse");
                return;
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

    //TODO implement 12
    private void STATEMENT() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case IDENTIFIER:
                ASSIGNMENT();
                break;
            case OUTPUT:
                read(Token.TYPE.OUTPUT);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("cannot parse");
                }
                OUTEXP();
                while (nextToken.getType() == Token.TYPE.COMMA) {
                    OUTEXP();
                }
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("cannot parse");
                }
                break;
            case IF:
                read(Token.TYPE.IF);
                EXPRESSION();
                if (!read(Token.TYPE.THEN)) {
                    throw new ParseException("cannot parse");
                }
                STATEMENT();
                if (read(Token.TYPE.THEN)) {
                    STATEMENT();
                }
                break;
            case WHILE:
                read(Token.TYPE.WHILE);
                EXPRESSION();
                if (!read(Token.TYPE.DO)) {
                    throw new ParseException("cannot parse");
                }
                STATEMENT();
                break;
            case REPEAT:
                read(Token.TYPE.REPEAT);
                STATEMENT();
                while (read(Token.TYPE.SEMICOLON)) {
                    STATEMENT();
                }
                ;
                if (!read(Token.TYPE.UNTIL)) {
                    throw new ParseException("cannot parse");
                }
                EXPRESSION();
                break;
            case FOR:
                read(Token.TYPE.FOR);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("cannot parse");
                }
                FORSTAT();
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("cannot parse");
                }
                FOREXP();
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("cannot parse");
                }
                FORSTAT();
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("cannot parse");
                }
                STATEMENT();
                break;
            case LOOP:
                read(Token.TYPE.LOOP);
                STATEMENT();
                while (read(Token.TYPE.SEMICOLON)) {
                    STATEMENT();
                }
                if (!read(Token.TYPE.UNTIL)) {
                    throw new ParseException("cannot parse");
                }
                EXPRESSION();
                break;
            case CASE:
                read(Token.TYPE.CASE);
                EXPRESSION();
                if (!read(Token.TYPE.OF)) {
                    throw new ParseException("cannot parse");
                }
                CASECLAUSES();
                OTHERWISECLAUSE();
                if (!read(Token.TYPE.END)) {
                    throw new ParseException("cannot parse");
                }
                break;
            case READ:
                read(Token.TYPE.READ);
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("cannot parse");
                }
                NAME();
                while (read(Token.TYPE.COMMA)) {
                    NAME();
                }
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("cannot parse");
                }
                break;
            case EXIT:
                read(Token.TYPE.EXIT);
                break;
            case RETURN:
                read(Token.TYPE.RETURN);
                EXPRESSION();
                break;
            case BEGIN:
                BODY();
                break;
            default:
                return;
        }
    }

    private void CASECLAUSES() {
    }

    private void FOREXP() {
    }

    private void FORSTAT() {
    }

    private void EXPRESSION() {
    }


    private void ASSIGNMENT() {
    }

    private void OUTEXP() {

    }

    private void OTHERWISECLAUSE() {
    }
}

