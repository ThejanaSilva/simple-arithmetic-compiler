package org.waruna.compiler.parse;

import java.util.List;
import java.util.Stack;

import org.waruna.compiler.model.Node;
import org.waruna.compiler.model.Token;

public class Parser {

    public Node parse(List<Token> tokens) {
        Stack<Node> values = new Stack<>();
        Stack<String> operators = new Stack<>();
        for (Token token : tokens) {
            if (token.type.equals("NUMBER")) {
                values.push(new Node(token.value));
            }  else { // OPERATOR
                while (!operators.empty() && precedence(operators.peek())>= precedence(token.value)) {
                    addNode(operators, values);
                }
                operators.push(token.value);
            }
        }
        while (!operators.empty()) {
            addNode(operators, values);
        }
        return values.pop();
    }

    private int precedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        }
        return 2; // *, /
    }

    private void addNode(Stack<String> operators, Stack<Node> values) {
        String op = operators.pop();
        Node right = values.pop();
        Node left = values.pop();
        values.push(new Node(op, left, right));
    }

}