package app.tool.codeanalysis.sourcecode.processors;

import app.tool.codeanalysis.sourcecode.processors.model.TreeNode;
import app.tool.codeanalysis.sourcecode.util.SourceCodeUtil;
import spoon.compiler.Environment;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.PrettyPrinter;

import java.util.*;

public class CodeScanner extends CtScanner {
    private TreeNode root = new TreeNode();
    private Deque<TreeNode> deque = new ArrayDeque<>();

    // 已展开过的方法/构造器, 防止递归调用(含this(...)转发自环)导致无限递归
    private final Set<CtMethod> visitedMethods = new HashSet<>();
    private final Set<CtConstructor> visitedConstructors = new HashSet<>();

    // 在Scanner构造器中，基于launcher的env创建autoImport打印机
    private PrettyPrinter autoImportPrinter;

    public CodeScanner(Environment env) {
        // spoon11.5 原生：自动导入模式，省略包名
        autoImportPrinter = env.createPrettyPrinterAutoImport();
    }


    @Override
    public void visitCtAssignment(CtAssignment ctAssignment) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(autoImportPrinter.prettyprint(ctAssignment));
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
        //已展开过则不再展开, 防止方法间互相递归导致无限递归
        if (!visitedMethods.add(ctMethod)) {
            return;
        }
        TreeNode treeNode = new TreeNode();

        String parentName = "";
        CtElement parent = ctMethod.getParent();
        if(parent instanceof CtClass<?>) {
            parentName = ((CtClass<?>) parent).getSimpleName();
        }else if(parent instanceof CtInterface<?>) {
            parentName = ((CtInterface<?>) parent).getSimpleName();
        }
        List<CtParameter> parameters = ctMethod.getParameters();
        String parameter = "";
        if(!parameters.isEmpty()) {
            for (CtParameter param : parameters) {
                parameter += param.getSimpleName() + ",";
            }
            parameter = parameter.substring(0, parameter.length() - 1);
        }
        treeNode.setContent(parentName + "." + ctMethod.getSimpleName() + "(" + parameter + ")");
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
        //已展开过则不再展开, 防止this(...)转发解析错误导致的自环
        if (!visitedConstructors.add(ctConstructor)) {
            return;
        }
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
        if(ctInvocation.toString().startsWith("super")
            || autoImportPrinter.prettyprint(ctInvocation).startsWith("Assert")){
            return;
        }

//        if(ctInvocation instanceof CtConstructorCall<?> ctConstructorCall) {
//            visitCtConstructorCall(ctConstructorCall);
//        }

        CtExpression target = ctInvocation.getTarget();
        if(target instanceof CtConstructorCall consCall) {
            visitCtConstructorCall(consCall);
        }
        CtExecutableReference executable = ctInvocation.getExecutable();
        CtExecutable declaration = executable.getDeclaration();
        //普通方法
        if(declaration instanceof CtMethod) {
            boolean isRecursiveCall = SourceCodeUtil.isRecursiveCall(ctInvocation);
            if(!isRecursiveCall) {
                visitCtMethod((CtMethod)declaration);
            }
        //构造方法
        } else if (declaration instanceof CtConstructor<?>) {
//            boolean isRecursiveCall = SourceCodeUtil.isRecursiveCall(ctInvocation);
//            if(!isRecursiveCall) {
                visitCtConstructor((CtConstructor) declaration);
//            }
        } else {
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
