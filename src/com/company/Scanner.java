package com.company;

/**
 * Created by Oshada on 2019-11-30.
 */

import java.io.PushbackInputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class Scanner
{
    public static final int MAX_LENGTH = 256;

    private PushbackInputStream sourceFile;
    private Token               nextToken;

    public Scanner( String filename ) throws IOException
    {
        sourceFile = new PushbackInputStream(new FileInputStream(filename));

        nextToken = null;
    }


    public Token getNextToken() throws IOException, LexicalException
    {
//        if( nextToken != null ){
//            Token tmp = nextToken;
//            nextToken = null;
//            return tmp;
//        }

        String rawToken = "";
        int nextByte = getNextByte();
        int tokenLength = 1;

        // Throw away leading whitespace
        while( isWhitespace((char) nextByte) ){
            nextByte = getNextByte();
        }
        if( nextByte == -1 || nextByte == '�'){
            return new Token(Token.TYPE.EOF);
        }
        if(nextByte == '\n'){
            return new Token(Token.TYPE.NEWLINE);
        }
        if(nextByte == '#'  ){
            char temp='j';
            while (temp!='\n')
            {
                temp = (char) sourceFile.read();
                if (temp == 65535)
                    break;
            }
            return new Token(Token.TYPE.SINGLELINECOMMENT);
        }

        if(nextByte == '{'  ){
            char temp='j';
            while (temp!='}')
            {
                temp = (char) sourceFile.read();
                if (temp == 65535)
                    break;
            }
            return new Token(Token.TYPE.MULTILINECOMMENT);
        }


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
                !isWhitespace( (char) nextByte) ) {
            if( tokenLength > MAX_LENGTH ){
                throw new LexicalException("Identifier is too long. Max identifier length: " + MAX_LENGTH);
            }

            if( nextByte != -1 || nextByte == '�'){ // EOF character
                rawToken += (char) nextByte;
                tokenLength++;
                nextByte = getNextByte();
            }
            else{
                // Found the End of FIle, return what we were reading and save an EOS Token
                return makeToken(rawToken);
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

    // Static member funtions
    public static boolean isWhitespace(char c )
    {
        return c == ' '  ||
                c == '\b' ||
                c == '\f' ||
                c == '\r' ||
                c == '\t';
    }

    public static boolean isSymbol( char c )
    {
        return  c == '=' ||
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
                c == '/';
        // Note: Comment tag is two characters and is perceived as a keyword
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
        nextToken = new Token( rawToken );
        return nextToken;
    }
}

