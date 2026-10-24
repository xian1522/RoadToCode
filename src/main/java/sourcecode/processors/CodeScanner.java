package sourcecode.processors;

import sourcecode.processors.model.TreeNode;
import sourcecode.util.SourceCodeUtil;
import spoon.compiler.Environment;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtConstructor;
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
        deque.push(treeNode);
        CtExpression assignment = ctAssignment.getAssignment();
        if(assignment instanceof CtInvocation) {
            visitCtInvocation((CtInvocation)assignment);
        }
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
    public void visitCtConstructor(CtConstructor ctConstructor) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(ctConstructor.getSimpleName());
        treeNode.setType("CtConstructor");
        if(deque.isEmpty()) {
            root = treeNode;
        }else {
            deque.peek().addChild(treeNode);
        }
        deque.push(treeNode);
        super.visitCtConstructor(ctConstructor);
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

        CtExpression target = ctInvocation.getTarget();
        if(target instanceof CtConstructorCall consCall) {
            visitCtConstructorCall(consCall);
        }

        CtExecutableReference executable = ctInvocation.getExecutable();
        CtExecutable declaration = executable.getDeclaration();
        if(declaration instanceof CtMethod) {
            boolean isRecursiveCall = SourceCodeUtil.isRecursiveCall(ctInvocation);
            if(!isRecursiveCall) {
                visitCtMethod((CtMethod)declaration);
            }
        }else {
            TreeNode treeNode = new TreeNode();
            treeNode.setContent(autoImportPrinter.prettyprint(ctInvocation));
            treeNode.setType("CtInvocation");
            deque.peek().addChild(treeNode);
            deque.push(treeNode);
            System.out.println("ctInvocation: " + treeNode.getContent());
            super.visitCtInvocation(ctInvocation);
            deque.pop();
        }
    }
    @Override
    public void visitCtConstructorCall(CtConstructorCall ctConstructorCall) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(autoImportPrinter.prettyprint(ctConstructorCall));
        treeNode.setType("CtConstructorCall");

        CtExecutableReference executable = ctConstructorCall.getExecutable();
        CtConstructor constructor = (CtConstructor)executable.getDeclaration();
        //JDK jar包中的构造器无法获取,等于null
        if(constructor != null) {
            deque.peek().addChild(treeNode);
            deque.push(treeNode);
            visitCtConstructor(constructor);
            System.out.println("ctInvocation: " + treeNode.getContent());
            deque.pop();
        }
    }


    public TreeNode getRoot() {
        return root;
    }

}
