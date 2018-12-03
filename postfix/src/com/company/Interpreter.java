package com.company;

public class Interpreter {
    private Parser parser;

    public Interpreter(Parser parser){
        this.parser = parser;
    }

    private Integer visit() throws Exception{
        Integer res = Integer.parseInt(parser.numbers.get(0));
        for(int i = 1; i < parser.numbers.size(); i++) {
            if (parser.ops.get(i - 1).getType() == TokenType.PLUS) {
                res += Integer.parseInt(parser.numbers.get(i));
            }
            if (parser.ops.get(i - 1).getType() == TokenType.MINUS) {
                res -= Integer.parseInt(parser.numbers.get(i));
            }
            if (parser.ops.get(i - 1).getType() == TokenType.MUL) {
                res *= Integer.parseInt(parser.numbers.get(i));
            }
            if (parser.ops.get(i - 1).getType() == TokenType.DIV) {
                res /= Integer.parseInt(parser.numbers.get(i));
            }
        }
        if (parser.numbers.size() - parser.ops.size() == 1) {
            return res;

        }
        throw new Exception("Too many operations");
    }

    public int interpret() throws Exception {
        parser.parse();
        return visit();
    }
}
