package org.waruna.compiler.parse;

import org.waruna.compiler.model.Token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public List<Token> tokenize(String expression){
        List<Token> tokens = new ArrayList<>();
        for (char c : expression.toCharArray()){
            if (Character.isDigit(c)){
                tokens.add(new Token("Number",String.valueOf(c)));
            } else if (c == '+')
            {
            tokens.add(new Token("OPERATOR", String.valueOf(c)));
            }
        }
        return tokens;
    }

}
