package com.company;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestPostfix {
    @Test
    public void postfix_1() throws Exception {
        Lexer lexer = new Lexer ("2 3 *");
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        assertEquals(interpreter.interpret(), 6);
    }

    @Test
    public void postfix_2() throws Exception {
        Lexer lexer = new Lexer ("2 4 9 - +");
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        assertEquals(interpreter.interpret(), 7);
    }

    @Test
    public void postfix_3() throws Exception {
        Lexer lexer = new Lexer ("2 3 2 * + 2 -");
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        assertEquals(interpreter.interpret(), 6);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void postfix_4() throws Exception {
        Lexer lexer = new Lexer ("2 3 4 - + /");
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        thrown.expect(Exception.class);
        interpreter.interpret();
        thrown = ExpectedException.none();
    }
}
