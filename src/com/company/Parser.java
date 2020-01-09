package com.company;

import java.io.*;
import java.util.Stack;


/**
 * Created by Oshada on 2019-12-20.
 */
public class Parser {
    protected Scanner scanner;
    String parsedString;
    Token nextToken;
    Stack<Node> stack;
    String astFilePath;
    private Token.TYPE type;
    private int testInt = 1;

    public Parser(Scanner scanner, String astFilePath) throws IOException, LexicalException {
        this.scanner = scanner;
        this.parsedString = "";
        this.nextToken = scanner.getNextValidToken();
        stack = new Stack<>();
        this.astFilePath = astFilePath;

    }

    public void parseFile() throws IOException, LexicalException, ParseException {
        this.start();
        try {
            File fout = new File(astFilePath);
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            traverseTree(stack.pop(), 0, bw);
            bw.close();
        } catch (IOException e) {
            System.out.println("cannot create file, permission denied");
        }
        System.out.println("PARSING COMPLETED");

    }

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
                buildTree("program", 7);
            } else {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }

    private void NAME() throws LexicalException, ParseException, IOException {
        String val = nextToken.getValue();
        if (!read(Token.TYPE.IDENTIFIER)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
        buildTree(val, 0);
        buildTree("<identifier>", 1);

    }

    //TODO for single case of consrs
    private void CONSTS() throws LexicalException, ParseException, IOException {
        int childCount = 0;
        if (read(Token.TYPE.CONSTANT)) {
            //here we assumes that each constant is seperated by a comma
            while (nextToken.getType() != Token.TYPE.SEMICOLON) {
                CONST();
                childCount = childCount + 1;
                if (!read(Token.TYPE.COMMA)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
            }
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            buildTree("consts", childCount);
        } else {
            buildTree("consts", 0);
            return;
        }
    }

    private void TYPES() throws LexicalException, ParseException, IOException {
        int childCount = 1;
        if (read(Token.TYPE.TYPE)) {
            TYPE();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            while (nextToken.getType() == Token.TYPE.IDENTIFIER) {
                TYPE();
                childCount = childCount + 1;
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
            }
            buildTree("types", childCount);
        } else {
            buildTree("types", 0);
            return;
        }
    }

    private void DCLNS() throws LexicalException, ParseException, IOException {
        int childCount = 1;
        if (read(Token.TYPE.VARIABLE)) {
            DCLN();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            while (nextToken.getType() == Token.TYPE.IDENTIFIER) {
                childCount = childCount + 1;
                DCLN();
                if (!read(Token.TYPE.SEMICOLON)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
            }
            buildTree("dclns", childCount);
        } else {
            buildTree("dclns", 0);
            return;
        }
    }

    private void SUBPROGS() throws LexicalException, ParseException, IOException {
        if (nextToken.getType() != Token.TYPE.FUNCTION) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
        int childCount = 0;
        while (nextToken.getType() == Token.TYPE.FUNCTION) {
            FCN();
            childCount = childCount + 1;
        }
        buildTree("subprogs", childCount);
    }

    private void CONST() throws LexicalException, ParseException, IOException {
        NAME();
        if (!read(Token.TYPE.EQUAL)) {
            //TODO check whether this is executing, if not try changing this into an error
            return;
        }
        LITLIST();
        buildTree("const", 2);
    }

    //corrected
    private void LITLIST() throws LexicalException, ParseException, IOException {
        int childCount = 1;
        if (read(Token.TYPE.OPEN_PAREN)) {
            NAME();
            while (read(Token.TYPE.COMMA)) {
                NAME();
                childCount = childCount + 1;
            }
            if (!read(Token.TYPE.CLOSED_PAREN)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            buildTree("lit", childCount);
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
        buildTree("type", 2);
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
            buildTree("fcn", 8);
        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());

        }
    }

    private void PARAMS() throws LexicalException, ParseException, IOException {
        int childCount = 1;
        DCLN();
        while (read(Token.TYPE.SEMICOLON)) {
            DCLN();
            childCount = childCount + 1;
        }
        buildTree("params", childCount);

    }

    private void CONSTVALUE() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case INTEGER:
                buildTree(nextToken.getValue(), 0);
                buildTree("<integer>", 1);
                read(Token.TYPE.INTEGER);
                break;
            case CHAR:
                buildTree(nextToken.getValue(), 0);
                buildTree("<char>", 1);
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
        int childCount = 1;
        if (read(Token.TYPE.BEGIN)) {
            STATEMENT();
            while (read(Token.TYPE.SEMICOLON)) {
                childCount = childCount + 1;
                STATEMENT();
            }
            if (!read(Token.TYPE.END)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            buildTree("block", childCount);
        } else {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }

    //CORRECTED
    private void DCLN() throws LexicalException, ParseException, IOException {
        int childCount = 1;
        NAME();
        if (read(Token.TYPE.COLON)) {
            NAME();
            buildTree("var", 2);
            return;
        } else if (nextToken.getType() == Token.TYPE.COMMA) {
            while (read(Token.TYPE.COMMA)) {
                NAME();
                childCount = childCount + 1;
            }
            if (!read(Token.TYPE.COLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
            NAME();
            childCount = childCount + 1;
            buildTree("var", childCount);
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

    private void STATEMENT() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case IDENTIFIER:
                ASSIGNMENT();
                break;
            case OUTPUT:
                int childCount = 1;
                read(Token.TYPE.OUTPUT);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                OUTEXP();
                while (read(Token.TYPE.COMMA)) {
                    childCount = childCount + 1;
                    OUTEXP();
                }
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                buildTree("output", childCount);
                break;
            case IF:
                int childCountIf = 2;
                read(Token.TYPE.IF);
                EXPRESSION();
                if (!read(Token.TYPE.THEN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                STATEMENT();
                if (read(Token.TYPE.ELSE)) {
                    STATEMENT();
                    childCountIf = childCountIf + 1;
                }
                buildTree("if", childCountIf);
                break;
            case WHILE:
                read(Token.TYPE.WHILE);
                EXPRESSION();
                if (!read(Token.TYPE.DO)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                STATEMENT();
                buildTree("while", 2);
                break;
            case REPEAT:
                int childCountRepeat = 1;
                read(Token.TYPE.REPEAT);
                STATEMENT();
                while (read(Token.TYPE.SEMICOLON)) {
                    STATEMENT();
                    childCountRepeat = childCountRepeat + 1;
                }
                if (!read(Token.TYPE.UNTIL)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                childCountRepeat = childCountRepeat + 1;
                buildTree("repeat", childCountRepeat);
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
                buildTree("for", 4);
                break;
            case LOOP:
                int childCountLoop = 1;
                read(Token.TYPE.LOOP);
                STATEMENT();
                while (read(Token.TYPE.SEMICOLON)) {
                    STATEMENT();
                    childCountLoop = childCountLoop + 1;
                }
                if (!read(Token.TYPE.UNTIL)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                EXPRESSION();
                childCountLoop = childCountLoop + 1;
                buildTree("loop", childCountLoop);
                break;
            case CASE:
                read(Token.TYPE.CASE);
                int childCountCase = 1;
                EXPRESSION();
                if (!read(Token.TYPE.OF)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                childCountCase = childCountCase + CASECLAUSES();
                childCountCase = childCountCase + OTHERWISECLAUSE();
                if (!read(Token.TYPE.END)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                buildTree("case", childCountCase);
                break;
            case READ:
                int childCountRead = 1;
                read(Token.TYPE.READ);
                if (!read(Token.TYPE.OPEN_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                NAME();
                while (read(Token.TYPE.COMMA)) {
                    NAME();
                    childCountRead = childCountRead + 1;
                }
                if (!read(Token.TYPE.CLOSED_PAREN)) {
                    throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                }
                buildTree("read", childCountRead);
                break;
            case EXIT:
                read(Token.TYPE.EXIT);
                buildTree("exit", 0);
                break;
            case RETURN:
                read(Token.TYPE.RETURN);
                EXPRESSION();
                buildTree("return", 1);
                break;
            case BEGIN:
                BODY();
                break;
            default:
                buildTree("<null>", 0);
                return;
        }
    }

    //CORRECTED
    private int CASECLAUSES() throws LexicalException, ParseException, IOException {
        int childCount = 1;
        CASECLAUSE();
        if (!read(Token.TYPE.SEMICOLON)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
        while (nextToken.getType() == Token.TYPE.INTEGER || nextToken.getType() == Token.TYPE.CHAR ||
                nextToken.getType() == Token.TYPE.IDENTIFIER) {
            childCount = childCount + 1;
            CASECLAUSE();
            if (!read(Token.TYPE.SEMICOLON)) {
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
            }
        }
        return childCount;
    }

    private void CASECLAUSE() throws LexicalException, ParseException, IOException {
        int childCount = 1;
        CASEEXPRESSION();
        while (read(Token.TYPE.COMMA)) {
            childCount = childCount + 1;
            CASEEXPRESSION();
        }
        if (!read(Token.TYPE.COLON)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
        STATEMENT();
        childCount = childCount + 1;
        buildTree("case_clause", childCount);
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
                buildTree("true", 0);
                return;
        }

    }

    private void FORSTAT() throws LexicalException, ParseException, IOException {
        if (nextToken.getType() == Token.TYPE.IDENTIFIER) {
            ASSIGNMENT();

        } else {
            buildTree("<null>", 0);
            return;
        }
    }

    private void EXPRESSION() throws LexicalException, ParseException, IOException {
        TERM();
        switch (nextToken.getType()) {
            case LESSTHANEQUAL:
                read(Token.TYPE.LESSTHANEQUAL);
                TERM();
                buildTree("<=", 2);
                break;
            case LESSTHAN:
                read(Token.TYPE.LESSTHAN);
                TERM();
                buildTree("<", 2);
                break;
            case GREATERTHANEQUAL:
                read(Token.TYPE.GREATERTHANEQUAL);
                TERM();
                buildTree(">=", 2);
                break;
            case GREATERTHAN:
                read(Token.TYPE.GREATERTHAN);
                TERM();
                buildTree(">", 2);
                break;
            case EQUAL:
                read(Token.TYPE.EQUAL);
                TERM();
                buildTree("=", 2);
                break;
            case NOTEQUAL:
                read(Token.TYPE.NOTEQUAL);
                TERM();
                buildTree("<>", 2);
                break;
        }
    }

    //Corrected
    private void TERM() throws LexicalException, ParseException, IOException {
        if (nextToken.getType() == Token.TYPE.PLUS || nextToken.getType() == Token.TYPE.MINUS || nextToken.getType() == Token.TYPE.OR) {
            if (nextToken.getType() == Token.TYPE.PLUS) {
                read(Token.TYPE.PLUS);
                FACTOR();
                buildTree("+", 2);
                if (nextToken.getType() == Token.TYPE.PLUS || nextToken.getType() == Token.TYPE.MINUS || nextToken.getType() == Token.TYPE.OR) {
                    TERM();
                }
            } else if (nextToken.getType() == Token.TYPE.MINUS) {
                read(Token.TYPE.MINUS);
                FACTOR();
                buildTree("-", 2);
                if (nextToken.getType() == Token.TYPE.PLUS || nextToken.getType() == Token.TYPE.MINUS || nextToken.getType() == Token.TYPE.OR) {
                    TERM();
                }
            } else {
                read(Token.TYPE.OR);
                FACTOR();
                buildTree("or", 2);
                if (nextToken.getType() == Token.TYPE.PLUS || nextToken.getType() == Token.TYPE.MINUS || nextToken.getType() == Token.TYPE.OR) {
                    TERM();
                }
            }
        } else {
            FACTOR();
            if (nextToken.getType() == Token.TYPE.PLUS || nextToken.getType() == Token.TYPE.MINUS || nextToken.getType() == Token.TYPE.OR) {
                TERM();
            }
        }
    }

    //corrected
    private void FACTOR() throws LexicalException, ParseException, IOException {
        if (nextToken.getType() == Token.TYPE.MULTIPLY || nextToken.getType() == Token.TYPE.FORWARD_SLASH || nextToken.getType()
                == Token.TYPE.AND || nextToken.getType() == Token.TYPE.MOD) {
            if (nextToken.getType() == Token.TYPE.MULTIPLY) {
                read(Token.TYPE.MULTIPLY);
                PRIMARY();
                buildTree("*", 2);
                if (nextToken.getType() == Token.TYPE.MULTIPLY || nextToken.getType() == Token.TYPE.FORWARD_SLASH || nextToken.getType()
                        == Token.TYPE.AND || nextToken.getType() == Token.TYPE.MOD) {
                    FACTOR();
                }
            } else if (nextToken.getType() == Token.TYPE.FORWARD_SLASH) {
                read(Token.TYPE.FORWARD_SLASH);
                PRIMARY();
                buildTree("/", 2);
                if (nextToken.getType() == Token.TYPE.MULTIPLY || nextToken.getType() == Token.TYPE.FORWARD_SLASH || nextToken.getType()
                        == Token.TYPE.AND || nextToken.getType() == Token.TYPE.MOD) {
                    FACTOR();
                }
            } else if (nextToken.getType() == Token.TYPE.AND) {
                read(Token.TYPE.AND);
                PRIMARY();
                buildTree("and", 2);
                if (nextToken.getType() == Token.TYPE.MULTIPLY || nextToken.getType() == Token.TYPE.FORWARD_SLASH || nextToken.getType()
                        == Token.TYPE.AND || nextToken.getType() == Token.TYPE.MOD) {
                    FACTOR();
                }
            } else {
                read(Token.TYPE.MOD);
                PRIMARY();
                buildTree("mod", 2);
                if (nextToken.getType() == Token.TYPE.MULTIPLY || nextToken.getType() == Token.TYPE.FORWARD_SLASH || nextToken.getType()
                        == Token.TYPE.AND || nextToken.getType() == Token.TYPE.MOD) {
                    FACTOR();
                }
            }
        } else {
            PRIMARY();
            if (nextToken.getType() == Token.TYPE.MULTIPLY || nextToken.getType() == Token.TYPE.FORWARD_SLASH || nextToken.getType()
                    == Token.TYPE.AND || nextToken.getType() == Token.TYPE.MOD) {
                FACTOR();
            }
        }
    }

    private void PRIMARY() throws LexicalException, ParseException, IOException {
        switch (nextToken.getType()) {
            case MINUS:
                read(Token.TYPE.MINUS);
                PRIMARY();
                buildTree("-", 1);
                break;
            case PLUS:
                read(Token.TYPE.PLUS);
                PRIMARY();
                buildTree("+", 1);
                break;
            case NOT:
                read(Token.TYPE.NOT);
                PRIMARY();
                buildTree("not", 1);
                break;
            case EOF:
                read(Token.TYPE.EOF);
                buildTree("eof", 0);
                break;
            case IDENTIFIER:
                int childCount = 1;
                NAME();
                if (read(Token.TYPE.OPEN_PAREN)) {
                    childCount = childCount + 1;
                    EXPRESSION();
                    while (read(Token.TYPE.COMMA)) {
                        childCount = childCount + 1;
                        EXPRESSION();
                    }
                    if (!read(Token.TYPE.CLOSED_PAREN)) {
                        throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
                    }
                    buildTree("call", childCount);
                }
                break;
            case INTEGER:
                buildTree(nextToken.getValue(), 0);
                buildTree("<integer>", 1);
                read(Token.TYPE.INTEGER);
                break;
            case CHAR:
                buildTree(nextToken.getValue(), 0);
                buildTree("<char>", 1);
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
                buildTree("succ", 1);
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
                buildTree("pred", 1);
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
                buildTree("chr", 1);
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
                buildTree("ord", 1);
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
                buildTree("assign", 2);
                break;
            case SWAP:
                read(Token.TYPE.SWAP);
                NAME();
                buildTree("swap", 2);
                break;
        }
    }

    private void OUTEXP() throws ParseException, IOException, LexicalException {
        switch (nextToken.getType()) {
            case STRING:
                STRINGNODE();
                buildTree("string", 1);
                break;
            case MINUS:
                EXPRESSION();
                buildTree("integer", 1);
                break;
            case PLUS:
                EXPRESSION();
                buildTree("integer", 1);
                break;
            case NOT:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case EOF:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case IDENTIFIER:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case INTEGER:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case CHAR:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case OPEN_PAREN:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case SUCC:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case PRED:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case CHR:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            case ORD:
                EXPRESSION();
                buildTree("integer", 1);

                break;
            default:
                throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }

    }

    //TODO add buildTree method
    private void STRINGNODE() throws LexicalException, ParseException, IOException {
        if (!read(Token.TYPE.STRING)) {
            throw new ParseException("Parse error occured at line " + nextToken.getLineNumber() + " " + nextToken.getValue());
        }
    }

    private int OTHERWISECLAUSE() throws LexicalException, ParseException, IOException {
        int childCount = 0;
        switch (nextToken.getType()) {
            case OTHERWISE:
                read(Token.TYPE.OTHERWISE);
                STATEMENT();
                buildTree("otherwise", 1);
                childCount = childCount + 1;
                break;
            default:
                break;
        }
        return childCount;
    }

    private void CASEEXPRESSION() throws LexicalException, ParseException, IOException {
        CONSTVALUE();
        if (read(Token.TYPE.DOTS)) {
            CONSTVALUE();
            buildTree("..", 2);
        }
    }

    private void buildTree(String functionName, int num_of_children) {
        //System.out.println(functionName + "(" + Integer.toString(num_of_children) + ")");
        //testInt = testInt + 1;
        Node p = null;
        for (int i = 1; i <= num_of_children; i = i + 1) {
            Node c = stack.pop();
            c.right = p;
            p = c;
        }
        stack.push(new Node(functionName + "(" + Integer.toString(num_of_children) + ")", p, null));
    }

    private void traverseTree(Node root, int dotCount, BufferedWriter bw) throws IOException {

        if (root == null) {
            return;
        } else {
            for (int i = 0; i < dotCount; i++) {
                bw.write(". ");
            }
            bw.write(root.getVal());
            bw.newLine();
            traverseTree(root.getLeft(), dotCount + 1, bw);
            traverseTree(root.getRight(), dotCount, bw);

        }
    }
}
