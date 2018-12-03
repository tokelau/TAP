package com.company;

public class Lexer {
    private String text;
    private int pos = 0;
    private Character currentChar;

    public Lexer(String text){
        this.text = text;
        currentChar = text.charAt(pos);
    }

    public Token nextToken() throws Exception {

        while (currentChar != null) {

            if (Character.isSpaceChar(currentChar)) {
                skip();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.INTEGER, integer());
            }
            if (currentChar == '+') {
                forward();
                return new Token(TokenType.PLUS, "+");
            }
            if (currentChar == '-') {
                forward();
                return new Token(TokenType.MINUS, "-");
            }
            if (currentChar == '*') {
                forward();
                return new Token(TokenType.MUL, "*");
            }
            if (currentChar == '/') {
                forward();
                return new Token(TokenType.DIV, "/");
            }
            if (currentChar.equals('.')) {
                return new Token(TokenType.EOL, null);
            }
//            System.out.println(currentChar);
            throw new Exception("Parser error");
        }
        return new Token(TokenType.EOL, null);
    }

    private void forward(){
        pos += 1;
        if (pos > text.length()-1){
            currentChar = null;
        }
        else{
            currentChar = text.charAt(pos);
        }
    }

    private void skip(){
        while ((currentChar != null) && Character.isSpaceChar(currentChar)){
            forward();
        }
    }

    private String integer(){
        String result = "";
        while ((currentChar != null) && (Character.isDigit(currentChar))){
            result += currentChar;
            forward();
        }
        return result;
    }

}
