package interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private Lexer lexer;
    private Token currentToken;
    private List<Variable> vars;

    public Parser(Lexer lexer) throws Exception {
        this.lexer = lexer;
        currentToken = this.lexer.nextToken();
        vars = Arrays.asList();
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

    private Node factor() throws Exception {
        Token token = currentToken;
        if (token.getType().equals(TokenType.PLUS)){
            checkTokenType(TokenType.PLUS);
            return new UnaryOp(token, factor());
        }
        else if (token.getType().equals(TokenType.MINUS)){
            checkTokenType(TokenType.MINUS);
            return new UnaryOp(token, factor());
        }
        else if (token.getType().equals(TokenType.INTEGER)) {
            checkTokenType(TokenType.INTEGER);
            return new Number(token);
        }
        else if (token.getType().equals(TokenType.LPAREN)) {
            checkTokenType(TokenType.LPAREN);
            Node node = expr();
            checkTokenType(TokenType.RPAREN);
            return node;
        }
        else if (token.getType().equals(TokenType.BEGIN)) {
            checkTokenType(TokenType.BEGIN);
            Node node = expr();
            checkTokenType(TokenType.END);
            return node;
        }
        else if (token.getType().equals(TokenType.LINEEND)) {
            Node node = expr();
            checkTokenType(TokenType.LINEEND);
            return node;
        }
//        else if (token.getType().equals(TokenType.VAR)) {
//            String varName = token.getValue();
//            checkTokenType(TokenType.VAR);
//            if (token.getType().equals(TokenType.EQU)) {
//                Variable var = new Variable(varName);
//                var.setValue(expr());
//                vars.add(var);
//
//            }
//        }
//        System.out.println(token.getType());
//        System.out.println(token.getValue());
        throw new Exception("Factor error");
    }

    private Node term() throws Exception {
        List<TokenType> ops = Arrays.asList(TokenType.DIV, TokenType.MUL);
        Node result = factor();
        while (ops.contains(currentToken.getType())){
            Token token = currentToken;
            if (token.getType() == TokenType.MUL){
                checkTokenType(TokenType.MUL);
            }
            else if(token.getType() == TokenType.DIV){
                checkTokenType(TokenType.DIV);
            }
            result = new BinOp(result, token, factor());
        }
        return result;
    }

    public Node expr() throws Exception {
        List<TokenType> ops = Arrays.asList(TokenType.PLUS, TokenType.MINUS);
        Node result = term();
        while (ops.contains(currentToken.getType())){
            Token token = currentToken;
            if (token.getType() == TokenType.PLUS){
                checkTokenType(TokenType.PLUS);
            }
            else if (token.getType() == TokenType.MINUS){
                checkTokenType(TokenType.MINUS);
            }
            result = new BinOp(result, token, term());
        }
        return result;
    }

    public List<Variable> line() throws Exception {
        List<Variable> vars = new ArrayList<>();
//            System.out.println(currentToken.getType());
        if (currentToken.getType() == TokenType.BEGIN) {
            checkTokenType(TokenType.BEGIN);
            while (currentToken.getType() != TokenType.END && currentToken.getType() != TokenType.EOL) {
                if(currentToken.getType() == TokenType.VAR) {
                    String varName = currentToken.getValue();
                    checkTokenType(TokenType.VAR);
                    if(currentToken.getType() == TokenType.EQU) {
                        checkTokenType(TokenType.EQU);
                        Variable var = new Variable(varName);
                        var.setValue(expr());
                        vars.add(var);
                    }
                    checkTokenType(TokenType.LINEEND);
                } else if (currentToken.getType() == TokenType.BEGIN) {
                    vars.addAll(line());
                }
                else {
                    System.out.println(currentToken.getType());
                    throw new Exception("Parser Error");
                }
            }
            if (currentToken.getType() == TokenType.EOL) {
                throw new Exception("Parser Error");
            }
        }
        if (!vars.isEmpty()) {
            return vars;
        }
        throw new Exception("No variables");
    }

    public List<Variable> parse() throws Exception {
        return line();
    }


    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer("2 + (2 * -3) * 4");
        Parser parser = new Parser(lexer);
        System.out.println(parser.parse());
    }

}
