package com.company;

/*
 *
 * The Token class uses a string and decides which token it is
 * using state machine-like statements. If the string is not matched
 * to one of the types it is returned as an identifier. This class
 * also handles the limits of the size of numbers.
 * *
  */

public class Token
{
    private TYPE type;
    private String literalValue;
    private int line_number;

    public Token(String word, int line_number) throws LexicalException
    {
        type = assignType(word);
        literalValue = word;
        this.line_number = line_number;
        System.out.println("line no" + getLineNumber());
    }

    public Token(TYPE tokenType, int line_no) throws LexicalException
    {
        type = tokenType;
        this.line_number = line_no;
        System.out.println("line no" + getLineNumber());

    }

    public Token(TYPE tokenType, String word, int line_no) throws LexicalException {
        type = tokenType;
        literalValue = word;
        this.line_number = line_no;
        System.out.println("line no" + getLineNumber());

    }

    public TYPE assignType(String sToken) throws LexicalException
    {
        if (isReservedPlusOp(sToken)) {
            return TYPE.PLUS;
        }else if (isConst(sToken)){
            return TYPE.CONSTANT;
        }else if(isVariable(sToken)) {
            return TYPE.VARIABLE;
        }else if(isType(sToken)) {
            return TYPE.TYPE;
        }else if (isFunction(sToken)) {
            return TYPE.FUNCTION;
        }else if (isReturn(sToken)) {
            return TYPE.RETURN;
        }else if (isBegin(sToken)) {
            return TYPE.BEGIN;
        }else if (isEnd(sToken)) {
            return TYPE.END;
        }else if(isSwap(sToken)) {
            return TYPE.SWAP;
        }else if(isOutput(sToken)) {
            return TYPE.OUTPUT;
        }else if (isWhile(sToken)) {
            return TYPE.WHILE;
        }else if (isDo(sToken)) {
            return TYPE.DO;
        }else if (isCase(sToken)) {
            return TYPE.CASE;
        }else if(isDots(sToken)) {
            return TYPE.DOTS;
        }else if (isOtherwise(sToken)) {
            return TYPE.OTHERWISE;
        }else if (isRepeat(sToken)){
            return TYPE.REPEAT;
        }else if (isFor(sToken)){
            return TYPE.FOR;
        }else if (isUntil(sToken)){
            return TYPE.UNTIL;
        }else if (isLoops(sToken)){
            return TYPE.LOOP;
        }else if (isPool(sToken)){
            return TYPE.POOL;
        }else if (isLessThanEqual(sToken)){
            return TYPE.LESSTHANEQUAL;
        }else if (isNotEqual(sToken)){
            return TYPE.NOTEQUAL;
        }else if (isGreaterThanEqual(sToken)){
            return TYPE.GREATERTHANEQUAL;
        }else if (isGreaterThan(sToken)){
            return TYPE.GREATERTHAN;
        }else if (isEqualToken(sToken)){
            return TYPE.EQUAL;
        }else if (isMod(sToken)){
            return TYPE.MOD;
        }else if (isRead(sToken)){
            return TYPE.READ;
        }else if (isSucc(sToken)){
            return TYPE.SUCC;
        }else if (isPred(sToken)){
            return TYPE.PRED;
        }else if (isExit(sToken)){
            return TYPE.EXIT;
        } else if (isReservedMinusOp(sToken)) {
            return TYPE.MINUS;
        }else if (isORD(sToken)) {
            return TYPE.ORD;
        }else if (isCHR(sToken)) {
            return TYPE.CHR;
        }else if(isOf(sToken)) {
            return TYPE.OF;
        }else if(isBeginBlock(sToken)){
            return TYPE.BEGIN_BLOCK;
        } else if(isReservedMultiplyOp(sToken)) {
            return TYPE.MULTIPLY;
        } else if(isReservedAssignmentOp(sToken)) {
            return TYPE.ASSIGNMENT;
        } else if (isReservedLessThanOp(sToken)) {
            return TYPE.LESSTHAN;
        } else if (isReservedOpenParen(sToken)) {
            return TYPE.OPEN_PAREN;
        } else if (isReservedClosedParen(sToken)) {
            return TYPE.CLOSED_PAREN;
        } else if (isReservedIf(sToken)) {
            return TYPE.IF;
        } else if (isReservedThen(sToken)) {
            return TYPE.THEN;
        } else if (isReservedElse(sToken)) {
            return TYPE.ELSE;
        } else if (isReservedEndIf(sToken)) {
            return TYPE.END_IF;
        } else if (isReservedNot(sToken)) {
            return TYPE.NOT;
        } else if (isReservedOr(sToken)) {
            return TYPE.OR;
        } else if (isReservedAnd(sToken)) {
            return TYPE.AND;
        } else if (isReservedComment(sToken)) {
            return TYPE.SINGLELINECOMMENT;
        } else if (isReservedForwardSlash(sToken)) {
            return TYPE.FORWARD_SLASH;
        } else if (isReservedComma(sToken)) {
            return TYPE.COMMA;
        }else if (isDot(sToken)) {
            return TYPE.DOT;
        } else if (isReservedColon(sToken)) {
            return TYPE.COLON;
        }else if (isSemiColon(sToken)) {
            return TYPE.SEMICOLON;
        }else if (isInteger(sToken)) {
            return TYPE.INTEGER;
        }else if (isString(sToken)) {
            return TYPE.STRING;
        }else if(isNewLine(sToken)){
            return TYPE.NEWLINE;
        }else if(isStart(sToken)){
            return TYPE.START;
        }else if(isChar(sToken)){
            return TYPE.CHAR;
        } else if (isIdentifier(sToken)) {
            return TYPE.IDENTIFIER;
        } else {
            throw new LexicalException("invalid literal at line" + getLineNumber() + " " + literalValue);
        }
    }

    private boolean isDot(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '.': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isBeginBlock(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '{': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isIdentifier(String string) {
        Character first_char = string.charAt(0);
        if ((first_char >= 97 && first_char <= 122) || (first_char >= 65 && first_char <= 90) || first_char == 95) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isCHR(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 3;

        char next;

        if (candidate.length() != 3) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'c':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'h':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'r':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;


            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isORD(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 3;

        char next;

        if (candidate.length() != 3) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'o':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'r':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'd':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;


            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isPred(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'p':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'r':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'd':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isSucc(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 's':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'u':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'c':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'c':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isRead(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'r':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'a':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'd':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isMod(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 3;

        char next;

        if (candidate.length() != 3) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'm':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'o':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'd':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;


            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isEqualToken(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '=': state++; break;
                        default : state = -1;
                    }
                    break;


            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;

    }

    private boolean isGreaterThan(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '>': state++; break;
                        default : state = -1;
                    }
                    break;


            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isGreaterThanEqual(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '>': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case '=': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isNotEqual(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '<': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case '>': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isLessThanEqual(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '<': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case '=': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isExit(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'x':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'i':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 't':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isPool(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'p':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'o':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'o':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'l':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isLoops(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'l':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'o':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'o':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'p':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isUntil(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 5;

        char next;

        if (candidate.length() != 5) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'u':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'n':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 't':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'i':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
                case 4:
                    switch (next) {
                        case 'l':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isFor(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 3;

        char next;

        if (candidate.length() != 3) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'f':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'o':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'r':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;


            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isRepeat(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 6;

        char   next;

        if (candidate.length()!=6){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'r': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'p': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 'a': state++; break;
                        default : state = -1;
                    }
                    break;

                case 5:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isOtherwise(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 9;

        char   next;

        if (candidate.length() != 9) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'h': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 'r': state++; break;
                        default : state = -1;
                    }
                    break;

                case 5:
                    switch ( next )
                    {
                        case 'w': state++; break;
                        default : state = -1;
                    }
                    break;

                case 6:
                    switch ( next )
                    {
                        case 'i': state++; break;
                        default : state = -1;
                    }
                    break;
                case 7:
                    switch ( next )
                    {
                        case 's': state++; break;
                        default : state = -1;
                    }
                    break;
                case 8:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isDots(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '.': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case '.': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isOf(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'f': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isCase(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'c':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'a':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 's':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isDo(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'd': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isWhile(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 5;

        char next;

        if (candidate.length() != 5) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'w':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'h':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'i':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'l':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
                case 4:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;

    }

    private boolean isOutput(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 6;

        char   next;

        if (candidate.length()!=6){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'u': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'p': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 'u': state++; break;
                        default : state = -1;
                    }
                    break;

                case 5:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;

    }

    private boolean isSwap(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 3;

        char next;

        if (candidate.length() != 3) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case ':':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case '=':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case ':':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isEnd(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 3;

        char next;

        if (candidate.length() != 3) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'n':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'd':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;


            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;

    }

    private boolean isBegin(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 5;

        char next;

        if (candidate.length() != 5) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'b':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'g':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'i':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
                case 4:
                    switch (next) {
                        case 'n':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;

    }

    private boolean isReturn(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 6;

        char next;

        if (candidate.length() != 6) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 'r':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 't':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'u':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
                case 4:
                    switch (next) {
                        case 'r':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
                case 5:
                    switch (next) {
                        case 'n':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isFunction(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 8;

        char   next;

        if (candidate.length()!=8){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'f': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'u': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'c': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

                case 5:
                    switch ( next )
                    {
                        case 'i': state++; break;
                        default : state = -1;
                    }
                    break;

                case 6:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;
                case 7:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;

            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;

    }

    private boolean isType(String candidate) {
        int START_STATE = 0;
        int TERMINAL_STATE = 4;

        char next;

        if (candidate.length() != 4) {
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++) {
            next = candidate.charAt(i);
            switch (state) {
                case 0:
                    switch (next) {
                        case 't':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 1:
                    switch (next) {
                        case 'y':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 2:
                    switch (next) {
                        case 'p':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;

                case 3:
                    switch (next) {
                        case 'e':
                            state++;
                            break;
                        default:
                            state = -1;
                    }
                    break;
            }
        }
        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isConst(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 8;

        char   next;

        if (candidate.length()!=8){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'c': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 's': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

                case 5:
                    switch ( next )
                    {
                        case 'a': state++; break;
                        default : state = -1;
                    }
                    break;

                case 6:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;
                case 7:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isVariable(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 3;

        char   next;

        if (candidate.length()!=3){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'v': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'a': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'r': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;

    }

    private boolean isStart(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 7;

        char   next;

        if (candidate.length()!=7){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'p': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'r': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'g': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 'r': state++; break;
                        default : state = -1;
                    }
                    break;

                case 5:
                    switch ( next )
                    {
                        case 'a': state++; break;
                        default : state = -1;
                    }
                    break;

                case 6:
                    switch ( next )
                    {
                        case 'm': state++; break;
                        default : state = -1;
                    }
                    break;

            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    private boolean isNewLine(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '\\': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedAnd(String candidate) {

        int START_STATE    = 0;
        int TERMINAL_STATE = 3;

        char   next;

        if (candidate.length()!=3){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'a': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'd': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedAssignmentOp(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case ':': state++; break;
                        default : state = -1;
                    }
                    break;
                case 1:
                    switch ( next )
                    {
                        case '=': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedClosedParen(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case ')': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedColon(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case ':': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isSemiColon(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case ';': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedComma(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case ',': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedComment(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '/': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case '/': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedElse(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 4;

        char   next;

        if (candidate.length()!=4){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'l': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 's': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedEndIf(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 5;

        char   next;

        if (candidate.length()!=5){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'd': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'i': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 'f': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedForwardSlash(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '/': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedIf(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'i': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'f': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isInteger(String num) throws LexicalException {
        Long lowerBound = -4294967296L;
        Long upperBound =  4294967295L;

        try {

            Long.valueOf(num).longValue();

        } catch (NumberFormatException nfe) {
            return false;
        }

        Long temp = Long.valueOf(num).longValue();
        if( !(lowerBound<=temp) || !(upperBound>=temp) ){

            throw new LexicalException("invalid literal at line " + getLineNumber() + " " + literalValue);

        }

        return true;
    }

    public boolean isString(String str) throws LexicalException {
        int length = str.length();
        if (str.charAt(0) != '"'){
            return false;
        }
        if(str.charAt(length -2) != '"'){
            return false;
        }

        for (int i= 0 ;i< length ; i++){
            if (str.charAt(0)=='"' && i<length-1){
                return false;
            }
        }
        return true;
    }

    public boolean isChar(String str) throws LexicalException {
        if (str.length() != 3) {
            return false;
        } else if (str.charAt(0) != 39) {
            return false;
        } else if (str.charAt(1) == 39) {
            return false;
        } else if (str.charAt(2) != 39) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isReservedLessThanOp(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '<': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedMinusOp(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '-': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedMultiplyOp(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '*': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedNot(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 3;

        char   next;

        if (candidate.length()!=3){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedOpenParen(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '(': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedOr(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 2;

        char   next;

        if (candidate.length()!=2){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'r': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedPlusOp(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 1;

        char   next;

        if (candidate.length()!=1){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case '+': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean isReservedThen(String candidate) {
        int START_STATE    = 0;
        int TERMINAL_STATE = 4;

        char   next;

        if (candidate.length()!=4){
            return false;
        }

        int state = START_STATE;
        for (int i = 0; i < candidate.length(); i++)
        {
            next = candidate.charAt(i);
            switch (state)
            {
                case 0:
                    switch ( next )
                    {
                        case 't': state++; break;
                        default : state = -1;
                    }
                    break;

                case 1:
                    switch ( next )
                    {
                        case 'h': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'n': state++; break;
                        default : state = -1;
                    }
                    break;
            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }

    public boolean equals(Token otherToken)
    {
        return type == otherToken.getType();
    }

    public String getValue() {
        if (literalValue == null) {
            return "value not assigned";
        } else {
            return literalValue;
        }
    }

    public TYPE getType() {
        return type;
    }

    public int typeToInt(){ return type.ordinal(); }

    public String toString(){ return literalValue; }

    public String getLineNumber() {

        return Integer.toString(line_number);
    }


    public enum TYPE {
        NEWLINE,
        START,
        VARIABLE,
        CONSTANT,
        TYPE,
        FUNCTION,
        RETURN,
        BEGIN,
        END,
        SWAP,
        ASSIGNMENT,
        OUTPUT, IF,
        THEN,
        ELSE,
        WHILE,
        DO,
        CASE,
        OF,
        DOTS,
        OTHERWISE,
        REPEAT,
        FOR,
        UNTIL,
        LOOP,
        POOL,
        EXIT,
        LESSTHANEQUAL,
        NOTEQUAL,
        LESSTHAN,
        GREATERTHANEQUAL,
        GREATERTHAN,
        EQUAL,
        MOD,
        READ,
        SUCC,
        PRED,
        CHR,
        ORD,
        EOF,
        BEGIN_BLOCK,
        COLON,
        SEMICOLON,
        DOT,
        COMMA,
        PLUS,
        MINUS,
        MULTIPLY,
        OPEN_PAREN,
        CLOSED_PAREN,
        END_IF,
        NOT,
        OR,
        AND,
        SINGLELINECOMMENT,
        MULTILINECOMMENT,
        FORWARD_SLASH,
        INTEGER,
        IDENTIFIER,
        CHAR,
        STRING
    }
}