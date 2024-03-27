package hello_algo.tree;

import hello_algo.PrintUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayBinaryTree {
    private List<Integer> tree;

    public ArrayBinaryTree(List<Integer> arr) {
        tree = new ArrayList<>(arr);
    }

    public int size() {
        return tree.size();
    }

    public Integer val(int i) {
        if(i < 0 || i >= size()) {
            return null;
        }
        return tree.get(i);
    }

    public Integer left(int i) {
        return 2 * i + 1;
    }
    public Integer right(int i) {
        return 2 * i + 2;
    }
    public Integer parent(int i) {
        return (i - 1) / 2;
    }

    public List<Integer> levelOrder() {
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < tree.size(); i++) {
            Integer val = val(i);
            if(val != null) {
                res.add(val);
            }
        }
        return res;
    }

    private void dfs(int i, String order, List<Integer> res) {
        Integer root = val(i);
        if(root == null) {
            return;
        }
        if(order.equals("pre")) {
            res.add(root);
        }
        dfs(left(i), order, res);
        if(order.equals("in")) {
            res.add(root);
        }
        dfs(right(i), order, res);
        if(order.equals("post")) {
            res.add(root);
        }
    }
    public List<Integer> preOrder() {
        List<Integer> res = new ArrayList<>();
        dfs(0, "pre", res);
        return res;
    }

    public List<Integer> inOrder() {
        List<Integer> res = new ArrayList<>();
        dfs(0, "in", res);
        return res;
    }

    public List<Integer> postOrder() {
        List<Integer> res = new ArrayList<>();
        dfs(0, "post", res);
        return res;
    }


    public static void main(String[] args) {
        // 初始化二叉树
        // 这里借助了一个从数组直接生成二叉树的函数
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, null, 6, 7, 8, 9, null, null, 12, null, null, 15);

        TreeNode root = TreeNode.listToTree(arr);
        System.out.println("\n初始化二叉树\n");
        System.out.println("二叉树的数组表示：");
        System.out.println(arr);
        System.out.println("二叉树的链表表示：");
        PrintUtil.printTree(root);

        // 数组表示下的二叉树类
        ArrayBinaryTree abt = new ArrayBinaryTree(arr);

        // 访问节点
        int i = 1;
        Integer l = abt.left(i);
        Integer r = abt.right(i);
        Integer p = abt.parent(i);
        System.out.println("\n当前节点的索引为 " + i + " ，值为 " + abt.val(i));
        System.out.println("其左子节点的索引为 " + l + " ，值为 " + (l == null ? "null" : abt.val(l)));
        System.out.println("其右子节点的索引为 " + r + " ，值为 " + (r == null ? "null" : abt.val(r)));
        System.out.println("其父节点的索引为 " + p + " ，值为 " + (p == null ? "null" : abt.val(p)));

        // 遍历树
        List<Integer> res = abt.levelOrder();
        System.out.println("\n层序遍历为：" + res);
        res = abt.preOrder();
        System.out.println("前序遍历为：" + res);
        res = abt.inOrder();
        System.out.println("中序遍历为：" + res);
        res = abt.postOrder();
        System.out.println("后序遍历为：" + res);
    }



}
