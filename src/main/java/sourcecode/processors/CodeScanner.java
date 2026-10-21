package sourcecode.processors;

import sourcecode.processors.model.TreeNode;
import spoon.compiler.Environment;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.PrettyPrinter;

import java.util.ArrayDeque;
import java.util.Deque;

public class CodeScanner extends CtScanner {
    private TreeNode root = new TreeNode();
    private Deque<TreeNode> deque = new ArrayDeque<>();

    // 在Scanner构造器中，基于launcher的env创建autoImport打印机
    private PrettyPrinter autoImportPrinter;

    public CodeScanner(Environment env) {
        // spoon11.5 原生：自动导入模式，省略包名
        autoImportPrinter = env.createPrettyPrinterAutoImport();
    }


    @Override
    public void visitCtAssignment(CtAssignment ctAssignment) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(ctAssignment.toString());
        treeNode.setType("CtAssignment");
        System.out.println("ctAssignment: " + treeNode.getContent());
        deque.peek().addChild(treeNode);
        deque.pop();
    }

    public void visitCtMethod(CtMethod ctMethod) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(ctMethod.getSimpleName());
        treeNode.setType("CtMethod");
        if(deque.isEmpty()) {
            root = treeNode;
        }else {
            deque.peek().addChild(treeNode);
        }
        System.out.println("ctMethod: " + treeNode.getContent());
        deque.push(treeNode);
        super.visitCtMethod(ctMethod);
        deque.pop();
    }

    @Override
    public void visitCtLocalVariable(CtLocalVariable ctLocalVariable) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(autoImportPrinter.prettyprint(ctLocalVariable));
        treeNode.setType("CtLocalVariable");
        deque.peek().addChild(treeNode);
        deque.push(treeNode);
        super.visitCtLocalVariable(ctLocalVariable);
        deque.pop();
    }
    @Override
    public void visitCtInvocation(CtInvocation ctInvocation) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(autoImportPrinter.prettyprint(ctInvocation));
        treeNode.setType("CtInvocation");
        CtExecutableReference executable = ctInvocation.getExecutable();
        CtExecutable declaration = executable.getDeclaration();
        if(declaration instanceof CtMethod) {
            visitCtMethod((CtMethod)declaration);
        }else {
            deque.peek().addChild(treeNode);
            deque.push(treeNode);
            System.out.println("ctInvocation: " + treeNode.getContent());
            super.visitCtInvocation(ctInvocation);
            deque.pop();
        }
    }


    public TreeNode getRoot() {
        return root;
    }

}
