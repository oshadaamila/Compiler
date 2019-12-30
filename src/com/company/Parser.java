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
        System.out.println("PARSING COMPLETED");


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
//                throw new ParseException("Parse error occured at line "+nextToken.getLineNumber()+" "+nextToken.getValue());
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
            BODY();
            NAME();
            if (!read(Token.TYPE.DOT)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    } else {
        throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
    }
}

    private void NAME() throws LexicalException, ParseException, IOException {
        if (!read(Token.TYPE.IDENTIFIER)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }

    private void CONSTS() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.CONSTANT)) {
            //here we assumes that each constant is seperated by a comma
            while (nextToken.getType() != Token.TYPE.SEMICOLON) {
                CONST();
                if (!read(Token.TYPE.COMMA)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
            }
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
        } else {
            return;
        }
    }

    private void TYPES() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.TYPE)) {
            TYPE();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            while (nextToken.getType() == Token.TYPE.IDENTIFIER) {
                TYPE();
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
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
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            while (nextToken.getType() == Token.TYPE.IDENTIFIER) {
                DCLN();
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
            }

        } else {
            return;
        }
    }

    private void SUBPROGS() throws LexicalException, ParseException, IOException {
        if (nextToken.getType() != Token.TYPE.FUNCTION) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
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

    //corrected
    private void LITLIST() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.OPEN_PAREN)) {
            NAME();
            while (read(Token.TYPE.COMMA)) {
                NAME();
            }
            if (!read(Token.TYPE.CLOSED_PAREN)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }

    private void TYPE() throws LexicalException, ParseException, IOException {
        NAME();
        if (!read(Token.TYPE.EQUAL)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
        LITLIST();
    }


    private void FCN() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.FUNCTION)) {
            NAME();
            if (!read(Token.TYPE.OPEN_PAREN)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            PARAMS();
            if (!read(Token.TYPE.CLOSED_PAREN)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            if (!read(Token.TYPE.COLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            NAME();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            CONSTS();
            TYPES();
            DCLNS();
            BODY();
            NAME();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }

        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());

        }
    }

    private void PARAMS() throws LexicalException, ParseException, IOException {
        DCLN();
        while (read(Token.TYPE.SEMICOLON)) {
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
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }


    private void BODY() throws LexicalException, ParseException, IOException {
        if (read(Token.TYPE.BEGIN)) {
            STATEMENT();
            while (read(Token.TYPE.SEMICOLON)) {
                STATEMENT();
            }
            if (!read(Token.TYPE.END)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }


    //CORRECTED
    private void DCLN() throws LexicalException, ParseException, IOException {
        NAME();
        if (read(Token.TYPE.COLON)) {
            NAME();
            return;
        } else if (nextToken.getType() == Token.TYPE.COMMA) {
            while (read(Token.TYPE.COMMA)) {
                NAME();
            }
            if (!read(Token.TYPE.COLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            NAME();
            return;
        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
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
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                OUTEXP();
                while (read(Token.TYPE.COMMA)) {
                    OUTEXP();
                }
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                break;
            case IF:
                read(Token.TYPE.IF);
                EXPRESSION();
                if (!read(Token.TYPE.THEN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                STATEMENT();
                if (read(Token.TYPE.ELSE)) {
                    STATEMENT();
                }
                break;
            case WHILE:
                read(Token.TYPE.WHILE);
                EXPRESSION();
                if (!read(Token.TYPE.DO)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
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
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                break;
            case FOR:
                read(Token.TYPE.FOR);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                FORSTAT();
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                FOREXP();
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                FORSTAT();
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
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
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                break;
            case CASE:
                read(Token.TYPE.CASE);
                EXPRESSION();
                if (!read(Token.TYPE.OF)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                CASECLAUSES();
                OTHERWISECLAUSE();
                if (!read(Token.TYPE.END)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                break;
            case READ:
                read(Token.TYPE.READ);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                NAME();
                while (read(Token.TYPE.COMMA)) {
                    NAME();
                }
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
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

    //CORRECTED
    private void CASECLAUSES() throws LexicalException, ParseException, IOException {
        CASECLAUSE();
        if (!read(Token.TYPE.SEMICOLON)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
        while (nextToken.getType() == Token.TYPE.INTEGER || nextToken.getType() == Token.TYPE.CHAR ||
                nextToken.getType() == Token.TYPE.IDENTIFIER) {
            CASECLAUSE();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
        }
    }

    private void CASECLAUSE() throws LexicalException, ParseException, IOException {
        CASEEXPRESSION();
        while (read(Token.TYPE.COMMA)) {
            CASEEXPRESSION();
        }
        if (!read(Token.TYPE.COLON)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
        STATEMENT();

    }

    private void FOREXP() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case MINUS:
                EXPRESSION();
                break;
            case PLUS:
                EXPRESSION();
                break;
            case NOT:
                EXPRESSION();
                break;
            case EOF:
                EXPRESSION();
                break;
            case IDENTIFIER:
                EXPRESSION();
                break;
            case INTEGER:
                EXPRESSION();
                break;
            case CHAR:
                EXPRESSION();
                break;
            case OPEN_PAREN:
                EXPRESSION();
                break;
            case SUCC:
                EXPRESSION();
                break;
            case PRED:
                EXPRESSION();
                break;
            case CHR:
                EXPRESSION();
                break;
            case ORD:
                EXPRESSION();
                break;
            default:
                return;
        }

    }

    private void FORSTAT() throws LexicalException, ParseException, IOException {
        if (nextToken.getType() == Token.TYPE.IDENTIFIER) {
            ASSIGNMENT();
        } else {
            return;
        }
    }

    private void EXPRESSION() throws LexicalException, ParseException, IOException {
        TERM();
        switch (nextToken.getType()) {
            case LESSTHANEQUAL:
                read(Token.TYPE.LESSTHANEQUAL);
                TERM();
                break;
            case LESSTHAN:
                read(Token.TYPE.LESSTHAN);
                TERM();
                break;
            case GREATERTHANEQUAL:
                read(Token.TYPE.GREATERTHANEQUAL);
                TERM();
                break;
            case GREATERTHAN:
                read(Token.TYPE.GREATERTHAN);
                TERM();
                break;
            case EQUAL:
                read(Token.TYPE.EQUAL);
                TERM();
                break;
            case NOTEQUAL:
                read(Token.TYPE.NOTEQUAL);
                TERM();
                break;
        }
    }

    private void TERM() throws LexicalException, ParseException, IOException {
        FACTOR();
        while (read(Token.TYPE.PLUS) || read(Token.TYPE.MINUS) ||
                read(Token.TYPE.OR)) {
            FACTOR();
        }
    }

    private void FACTOR() throws LexicalException, ParseException, IOException {
        PRIMARY();
        while (read(Token.TYPE.MULTIPLY) || read(Token.TYPE.FORWARD_SLASH) ||
                read(Token.TYPE.AND) || read(Token.TYPE.MOD)) {
            PRIMARY();
        }
    }

    private void PRIMARY() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case MINUS:
                read(Token.TYPE.MINUS);
                PRIMARY();
                break;
            case PLUS:
                read(Token.TYPE.PLUS);
                PRIMARY();
                break;
            case NOT:
                read(Token.TYPE.NOT);
                PRIMARY();
                break;
            case EOF:
                read(Token.TYPE.EOF);
                break;
            case IDENTIFIER:
                NAME();
                if (read(Token.TYPE.OPEN_PAREN)) {
                    EXPRESSION();
                    while (read(Token.TYPE.COMMA)) {
                        EXPRESSION();
                    }
                    if (!read(Token.TYPE.CLOSED_PAREN)) {
                        throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                    }
                }
                break;
            case INTEGER:
                read(Token.TYPE.INTEGER);
                break;
            case CHAR:
                read(Token.TYPE.CHAR);
                break;
            case OPEN_PAREN:
                read(Token.TYPE.OPEN_PAREN);
                EXPRESSION();
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                break;
            case SUCC:
                read(Token.TYPE.SUCC);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                break;
            case PRED:
                read(Token.TYPE.PRED);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                break;
            case CHR:
                read(Token.TYPE.CHR);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                break;
            case ORD:
                read(Token.TYPE.ORD);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                break;
            default:
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }


    private void ASSIGNMENT() throws LexicalException, ParseException, IOException {
        NAME();
        switch (nextToken.getType()) {
            case ASSIGNMENT:
                read(Token.TYPE.ASSIGNMENT);
                EXPRESSION();
                break;
            case SWAP:
                read(Token.TYPE.SWAP);
                NAME();
                break;
        }
    }

    private void OUTEXP() throws ParseException, IOException, LexicalException {
        switch (nextToken.getType()) {
            case STRING:
                STRINGNODE();
                break;
            case MINUS:
                EXPRESSION();
                break;
            case PLUS:
                EXPRESSION();
                break;
            case NOT:
                EXPRESSION();
                break;
            case EOF:
                EXPRESSION();
                break;
            case IDENTIFIER:
                EXPRESSION();
                break;
            case INTEGER:
                EXPRESSION();
                break;
            case CHAR:
                EXPRESSION();
                break;
            case OPEN_PAREN:
                EXPRESSION();
                break;
            case SUCC:
                EXPRESSION();
                break;
            case PRED:
                EXPRESSION();
                break;
            case CHR:
                EXPRESSION();
                break;
            case ORD:
                EXPRESSION();
                break;
            default:
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }

    }

    private void STRINGNODE() throws LexicalException, ParseException, IOException {
        if (!read(Token.TYPE.STRING)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }

    private void OTHERWISECLAUSE() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case OTHERWISE:
                read(Token.TYPE.OTHERWISE);
                STATEMENT();
                break;
            default:
                return;
        }
    }

    private void CASEEXPRESSION() throws LexicalException, ParseException, IOException {
        CONSTVALUE();
        //TODO : SCANNER DOESNOT DIFFERENTIATE DOTS
        if (read(Token.TYPE.DOTS)) {
            CONSTVALUE();
        }
    }


}
