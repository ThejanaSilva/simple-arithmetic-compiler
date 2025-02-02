package org.waruna.compiler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.waruna.compiler.codegen.CodeGenerator;
import org.waruna.compiler.model.Node;
import org.waruna.compiler.model.Token;
import org.waruna.compiler.parse.Lexer;
import org.waruna.compiler.parse.Parser;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java -jar simple-arithmatic-compiler <expression>");
            return;
        }
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.tokenize(args[0]);
        Parser parser = new Parser();
        Node parseTree = parser.parse(tokens);
        CodeGenerator codeGenerator = new CodeGenerator();
        byte[] bytecode = codeGenerator.generateBytecode(parseTree, "MyProgram");
        saveClassToFile(bytecode, "MyProgram.class");
    }

      public static void saveClassToFile(byte[] bytecode, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(bytecode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}