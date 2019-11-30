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

    public enum TYPE {
        NEWLINE,
        START,
        VARIABLE,
        PLUS,
        MINUS,
        MULTIPLY,
        ASSIGNMENT,
        LESSTHAN,
        OPEN_PAREN,
        CLOSED_PAREN,
        IF,
        THEN,
        ELSE,
        END_IF,
        NOT,
        OR,
        AND,
        SINGLELINECOMMENT,
        MULTILINECOMMENT,
        FORWARD_SLASH,
        COMMA,
        BOOLEAN,
        TRUE,
        FALSE,
        COLON,
        INTEGER,
        PRINT,
        IDENTIFIER,
        NUMBER,
        EOS
    }

    public Token(String word) throws LexicalException
    {
        type = assignType(word);
        literalValue = word;
    }

    public Token(TYPE tokenType) throws LexicalException
    {
        type = tokenType;
    }

    public static TYPE assignType(String sToken) throws LexicalException
    {
        if (isReservedPlusOp(sToken)) {
            return TYPE.PLUS;
        }else if(isVariable(sToken)){
            return TYPE.VARIABLE;
        } else if (isReservedMinusOp(sToken)) {
            return TYPE.MINUS;
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
        } else if (isReservedBoolean(sToken)) {
            return TYPE.BOOLEAN;
        } else if (isReservedTrue(sToken)) {
            return TYPE.TRUE;
        } else if (isReservedFalse(sToken)) {
            return TYPE.FALSE;
        } else if (isReservedColon(sToken)) {
            return TYPE.COLON;
        } else if (isReservedInteger(sToken)) {
            return TYPE.INTEGER;
        } else if (isReservedPrint(sToken)) {
            return TYPE.PRINT;
        } else if (isNumber(sToken)) {
            return TYPE.NUMBER;
        }else if(isNewLine(sToken)){
            return TYPE.NEWLINE;
        }else if(isStart(sToken)){
               return TYPE.START;
        }else{
            return TYPE.IDENTIFIER;
        }
    }

    private static boolean isVariable(String candidate) {
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

    private static boolean isStart(String candidate) {
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

    private static boolean isNewLine(String candidate) {
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


    public static boolean isReservedAnd(String candidate) {

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

    public static boolean isReservedAssignmentOp(String candidate) {
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


    public static boolean isReservedBoolean(String candidate) {
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
                        case 'b': state++; break;
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
                        case 'o': state++; break;
                        default : state = -1;
                    }
                    break;

                case 3:
                    switch ( next )
                    {
                        case 'l': state++; break;
                        default : state = -1;
                    }
                    break;

                case 4:
                    switch ( next )
                    {
                        case 'e': state++; break;
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

            }
        }

        if ( state == TERMINAL_STATE )
            return true;
        else
            return false;
    }


    public static boolean isReservedClosedParen(String candidate) {
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


    public static boolean isReservedColon(String candidate) {
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


    public static boolean isReservedComma(String candidate) {
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


    public static boolean isReservedComment(String candidate) {
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


    public static boolean isReservedElse(String candidate) {
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


    public static boolean isReservedEndIf(String candidate) {
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


    public static boolean isReservedFalse(String candidate) {
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
                        case 'f': state++; break;
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
                        case 'l': state++; break;
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


    public static boolean isReservedForwardSlash(String candidate) {
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


    public static boolean isReservedIf(String candidate) {
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


    public static boolean isReservedInteger(String candidate) {
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
                        case 'i': state++; break;
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
                        case 't': state++; break;
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
                        case 'g': state++; break;
                        default : state = -1;
                    }
                    break;

                case 5:
                    switch ( next )
                    {
                        case 'e': state++; break;
                        default : state = -1;
                    }
                    break;

                case 6:
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


    public static boolean isReservedLessThanOp(String candidate) {
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


    public static boolean isReservedMinusOp(String candidate) {
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


    public static boolean isReservedMultiplyOp(String candidate) {
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


    public static boolean isReservedNot(String candidate) {
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


    public static boolean isReservedOpenParen(String candidate) {
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


    public static boolean isReservedOr(String candidate) {
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


    public static boolean isReservedPlusOp(String candidate) {
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


    public static boolean isReservedPrint(String candidate) {
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
                        case 'i': state++; break;
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

                case 4:
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


    public static boolean isReservedThen(String candidate) {
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


    public static boolean isReservedTrue(String candidate) {
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
                        case 'r': state++; break;
                        default : state = -1;
                    }
                    break;

                case 2:
                    switch ( next )
                    {
                        case 'u': state++; break;
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

    public static boolean isNumber(String num) throws LexicalException
    {
        Long lowerBound = -4294967296L;
        Long upperBound =  4294967295L;

        try {

            Long.valueOf(num).longValue();

        } catch (NumberFormatException nfe) {
            return false;
        }

        Long temp = Long.valueOf(num).longValue();
        if( !(lowerBound<=temp) || !(upperBound>=temp) ){

            throw new LexicalException("Number out of range");
        }

        return true;
    }

    public boolean equals(Token otherToken)
    {
        return type == otherToken.getType();
    }

    public String getValue(){ return literalValue; }
    public TYPE getType(){ return type; }
    public int typeToInt(){ return type.ordinal(); }
    public String toString(){ return literalValue; }
}