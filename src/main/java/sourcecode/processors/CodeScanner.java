package sourcecode.processors;

import sourcecode.processors.model.TreeNode;
import spoon.compiler.Environment;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtMethod;
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
        deque.peek().addChild(treeNode);
    }

    public void visitCtMethod(CtMethod ctMethod) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(ctMethod.getSimpleName());
        if(deque.isEmpty()) {
            root = treeNode;
        }else {
            deque.peek().addChild(treeNode);
        }
        deque.push(treeNode);
        super.visitCtMethod(ctMethod);
        deque.pop();
    }

    @Override
    public void visitCtLocalVariable(CtLocalVariable ctLocalVariable) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(autoImportPrinter.prettyprint(ctLocalVariable));
        deque.peek().addChild(treeNode);
        deque.push(treeNode);
        super.visitCtLocalVariable(ctLocalVariable);
    }
    @Override
    public void visitCtInvocation(CtInvocation ctInvocation) {
        TreeNode treeNode = new TreeNode();
        treeNode.setContent(autoImportPrinter.prettyprint(ctInvocation));
        deque.peek().addChild(treeNode);
        deque.push(treeNode);
        super.visitCtInvocation(ctInvocation);
        deque.pop();
    }


    public TreeNode getRoot() {
        return root;
    }

}
