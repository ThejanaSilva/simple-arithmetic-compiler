package org.waruna.compiler.codegen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.waruna.compiler.model.Node;

public class CodeGenerator {
    public byte[] generateBytecode(Node parseTree, String className) throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        MethodVisitor mv;

        // Define the class
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);

        // Define the main method
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        // Generate bytecode based on the parse tree
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        generateBytecodeFromTree(parseTree, mv);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();

        return cw.toByteArray();
    }

    private static void generateBytecodeFromTree(Node node, MethodVisitor methodVisitor) {
        if (node.left == null && node.right == null) {
            // It's a leaf node (NUMBER), push the value onto the stack
            methodVisitor.visitIntInsn(Opcodes.BIPUSH, Integer.parseInt(node.value));
        } else {
            // Generate bytecode for the left and right nodes
            generateBytecodeFromTree(node.left, methodVisitor);
            generateBytecodeFromTree(node.right, methodVisitor);

            // Apply the operator
            switch (node.value) {
                case "+":
                    methodVisitor.visitInsn(Opcodes.IADD);
                    break;
                case "-":
                    methodVisitor.visitInsn(Opcodes.ISUB);
                    break;
                case "*":
                    methodVisitor.visitInsn(Opcodes.IMUL);
                    break;
                case "/":
                    methodVisitor.visitInsn(Opcodes.IDIV);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown operator: " + node.value);
            }
        }
    }
}