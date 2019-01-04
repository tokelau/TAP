package interpreter;

import java.util.Arrays;
import java.util.List;

public class Interpreter implements NodeVisitor {
    private Parser parser;

    public Interpreter(Parser parser){
        this.parser = parser;
    }

    @Override
    public float visit(Node node) throws Exception {
        if (node.getClass().equals(BinOp.class)){
            return visitBinOp(node);
        }
        else if (node.getClass().equals(Number.class)){
            return visitNumber(node);
        }
        else if (node.getClass().equals(UnaryOp.class)){
            return visitUnaryOp(node);
        }
        throw new Exception("Interpreter error!");
    }

    public float visitBinOp(Node node) throws Exception {
//        System.out.println("visit BinOp");
        BinOp binop = (BinOp) node;
        if (binop.getOp().getType().equals(TokenType.PLUS)){
            return visit(binop.getLeft()) + visit(binop.getRight());
        }
        else if (binop.getOp().getType().equals(TokenType.MINUS)){
            return visit(binop.getLeft()) - visit(binop.getRight());
        }
        else if (binop.getOp().getType().equals(TokenType.DIV)){
            return visit(binop.getLeft()) / visit(binop.getRight());
        }
        else if (binop.getOp().getType().equals(TokenType.MUL)){
            return visit(binop.getLeft()) * visit(binop.getRight());
        }
        throw new Exception("BinOp error");
    }

    public float visitNumber(Node node){
//        System.out.println("visit Number");
        Number number = (Number) node;
        return Float.parseFloat(number.getToken().getValue());
    }

    public float visitUnaryOp(Node node) throws Exception {
//        System.out.println("visit UnaryOp");
        UnaryOp op = (UnaryOp) node;
        if (op.getToken().getType().equals(TokenType.PLUS)){
            return +visit(op.getExpr());
        }
        else if (op.getToken().getType().equals(TokenType.MINUS)){
            return -visit(op.getExpr());
        }
        throw new Exception("UnaryOp error!");
    }

    public void interpret() throws Exception {
        List<Variable> trees = Arrays.asList();
        trees = parser.parse();
//        System.out.println(tree);
        for (int i = 0; i < trees.size(); i++) {
            System.out.println(String.format("%s = %s", trees.get(i).getName(), visit(trees.get(i).value)));
        }
//        return visit(tree);
    }

    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer("BEGIN " +
                "a := -2 +-+--------- 2 * (2 + 2); " +
                "b := 34 / 2 + 5;" +
                "BEGIN " +
                    "k := 45;" +
                " END;"+
                "END.");
//        Lexer lexer = new Lexer("BEGIN " +
//                "END.");
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        interpreter.interpret();
//        System.out.println(interpreter.interpret());
    }

}
