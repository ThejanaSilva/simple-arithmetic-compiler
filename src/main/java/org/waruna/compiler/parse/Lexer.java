package org.waruna.compiler.parse;

import org.waruna.compiler.model.Token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public List<Token> tokenize(String expression) {
        List<Token> tokens = new ArrayList<>();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                tokens.add(new Token("NUMBER", String.valueOf(c)));
            } else if (Character.isWhitespace(c)) {
                continue; // Skip whitespace
            } else if (c == '+' || c == '-' || c == '*' || c == '/') { // Fill logic for -, *, /{
                tokens.add(new Token("OPERATOR", String.valueOf(c)));
            } else {
                throw new RuntimeException("Invalid character found: " + c);
            }
        }
        return tokens;
    }
}
