package com.company;

/**
 * Created by Oshada on 2019-11-30.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class Scanner
{
    public static final int MAX_LENGTH = 256;

    private PushbackInputStream sourceFile;
    private Token               nextToken;
    private int line_count;

    public Scanner( String filename ) throws IOException
    {
        sourceFile = new PushbackInputStream(new FileInputStream(filename));

        nextToken = null;
        line_count = 1;
    }

    // Static member funtions
    public static boolean isWhitespace(char c) {
        return c == ' ' ||
                c == '\b' ||
                c == '\f' ||
                c == '\r' ||
                c == '\t';
    }

    public static boolean isSymbol(char c) {
        return c == '=' ||
                c == '{' ||
                c == ':' ||
                c == ';' ||
                c == '.' ||
                c == ',' ||
                c == '(' ||
                c == ')' ||
                c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/' ||
                c == '<' ||
                c == '>';
        // Note: Comment tag is two characters and is perceived as a keyword
    }

    public Token getNextToken() throws IOException, LexicalException
    {

        String rawToken = "";
        int nextByte = getNextByte();
        int tokenLength = 1;

        // Throw away leading whitespace
        while( isWhitespace((char) nextByte) ){
            nextByte = getNextByte();
        }
        if( nextByte == -1 || nextByte == '�'){
            return new Token(Token.TYPE.EOF, line_count);
        }
        if(nextByte == '\n'){
            line_count = line_count + 1;
            return new Token(Token.TYPE.NEWLINE, line_count);


        }
        if(nextByte == '#'  ){
            char temp='j';
            while (temp!='\n')
            {
                line_count = line_count + 1;
                temp = (char) sourceFile.read();
                if (temp == 65535)
                    break;
            }
            return new Token(Token.TYPE.SINGLELINECOMMENT, line_count);
        }

        if(nextByte == '{'  ){
            char temp='j';
            while (temp!='}')
            {
                temp = (char) sourceFile.read();
                if (temp == '\n') {
                    line_count = line_count + 1;
                }
                if (temp == 65535)
                    break;
            }
            return new Token(Token.TYPE.MULTILINECOMMENT, line_count);
        }

        if (nextByte == '"') {
            String temp = Character.toString('"');
            char next = (char) sourceFile.read();
            while (next != '"' && next != -1 && next != 65535) {
                temp = temp + Character.toString(next);
                next = (char) sourceFile.read();
            }
            if (next == '"') {
                //a string identified
                temp = temp + Character.toString('"');
                return new Token(Token.TYPE.STRING, temp, line_count);
            } else {
                //not a string
                throw new LexicalException("Syntax error: String cannot be parsed");
            }
        }
        // checks for symbols and operators
        if( isSymbol( (char) nextByte) ){
            char temp = (char)sourceFile.read();
            if (temp == '='){
                return makeToken(String.valueOf((char)nextByte)+String.valueOf(temp));
            }else{
                String stringByte =  Character.toString((char)nextByte);
                sourceFile.unread(temp);
                return makeToken(stringByte);
            }

        }


        while( !isSymbol( (char) nextByte) &&
                !isWhitespace((char) nextByte) && nextByte != '\n') {
            if( tokenLength > MAX_LENGTH ){
                throw new LexicalException("Identifier is too long. Max identifier length: " + MAX_LENGTH);
            }
            if (nextByte == '"') {
                throw new LexicalException("illegal string found");
            }

            if( nextByte != -1 || nextByte == '�'){ // EOF character
                rawToken += (char) nextByte;
                tokenLength++;
                nextByte = getNextByte();
            }
            else{
                // Found the End of FIle, return what we were reading and save an EOS Token
                return new Token(Token.TYPE.EOF, line_count);
            }
        }

        // If we reach here we found a symbol signifying a new token, so we must first replace it in the stream
        sourceFile.unread( nextByte );
        return makeToken(rawToken);
    }

    public int getNextByte() throws IOException
    {
        return sourceFile.read();
    }

//    public boolean isComment (char c) throws IOException
//    {
//        char temp = (char)sourceFile.read();
//        if ((c=='/') && temp=='/')
//        {
//            sourceFile.unread(temp);
//            return true;
//        }
//        sourceFile.unread(temp);
//        return false;
//    }

    private Token makeToken( String rawToken ) throws LexicalException
    {
        nextToken = new Token(rawToken, line_count);
        return nextToken;
    }

    public Token getNextValidToken() throws IOException, LexicalException {
        Token nextValidToken = this.getNextToken();
        while (nextValidToken.getType() == Token.TYPE.SINGLELINECOMMENT || nextValidToken.getType() == Token.TYPE.MULTILINECOMMENT || nextValidToken.getType() == Token.TYPE.NEWLINE) {
            nextValidToken = this.getNextToken();
        }
        return nextValidToken;
    }
}

