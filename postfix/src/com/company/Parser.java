package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
    public List<String> numbers;
    public List<Token> ops;

    public Parser(Lexer lexer) throws Exception {
        this.lexer = lexer;
        currentToken = this.lexer.nextToken();
        numbers = new ArrayList<>();
        ops = new ArrayList<>();
    }

    private void checkTokenType(TokenType type) throws Exception {
//        System.out.println(currentToken.getType());
        if (currentToken.getType() == type){
            currentToken = lexer.nextToken();
        }
        else {
            throw new Exception("Parser error");
        }
    }

    public void parse() throws Exception {
        while (currentToken.getType() != TokenType.EOL) {
            if (currentToken.getType() == TokenType.INTEGER) {
                numbers.add(currentToken.getValue());
                checkTokenType(TokenType.INTEGER);
            }
            else if (currentToken.getType() != TokenType.EOL) {
                ops.add(currentToken);
                checkTokenType(currentToken.getType());
            }
        }
    }

}
