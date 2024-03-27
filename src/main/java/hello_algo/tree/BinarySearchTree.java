package hello_algo.tree;

import hello_algo.PrintUtil;

public class BinarySearchTree {
    private TreeNode root;

    public BinarySearchTree() {
        root = null;
    }

    public TreeNode getRoot() {
        return  root;
    }

    public TreeNode search(int num) {

        TreeNode temp = root;
        while (temp != null && temp.val > num) {
            temp = temp.left;
        }
        while (temp != null && temp.val < num) {
            temp = temp.right;
        }
        return temp;
    }

    public void insert(int num) {
        if(root == null) {
            root = new TreeNode(num);
            return;
        }
        TreeNode cur = root , pre = null;
        while (cur != null) {
            if(cur.val == num) {
                return;
            }
            pre = cur;
            if(cur.val > num) {
                cur = cur.left;
            }else if(cur.val < num) {
                cur = cur.right;
            }
        }
        cur = new TreeNode(num);
        if(pre.val > num) {
            pre.left = cur;
        }else if(pre.val < num) {
            pre.right = cur;
        }
    }

    public void remove(int num) {
        if(root.val == num) {
            root = null;
        }
        TreeNode cur = root, pre = null;
        while (cur != null) {
            if(cur.val == num) {
                break;
            }
            pre = cur;
            if(cur.val > num) {
                cur = cur.left;
            }else if(cur.val < num) {
                cur = cur.right;
            }
        }
        if(cur.right == null || cur.left == null) {
            TreeNode child = cur.right == null ? cur.left : cur.right;
            if(cur != root) {
                if(pre.left == cur) {
                    pre.left = child;
                }else if(pre.right == cur){
                    pre.right = child;
                }
            }else {
                root = child;
            }
        }else {
            TreeNode tempNode = cur.right;
            while (tempNode.left != null) {
                tempNode = tempNode.left;
            }
            remove(tempNode.val);
            cur.val = tempNode.val;
        }
    }

    public static void main(String[] args) {
        /* 初始化二叉搜索树 */
        BinarySearchTree bst = new BinarySearchTree();
        // 请注意，不同的插入顺序会生成不同的二叉树，该序列可以生成一个完美二叉树
        int[] nums = { 8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15 };
        for (int num : nums) {
            bst.insert(num);
        }
        System.out.println("\n初始化的二叉树为\n");
        PrintUtil.printTree(bst.getRoot());

        /* 查找节点 */
        TreeNode node = bst.search(7);
        System.out.println("\n查找到的节点对象为 " + node + "，节点值 = " + node.val);

        /* 插入节点 */
        bst.insert(16);
        System.out.println("\n插入节点 16 后，二叉树为\n");
        PrintUtil.printTree(bst.getRoot());

        /* 删除节点 */
        bst.remove(1);
        System.out.println("\n删除节点 1 后，二叉树为\n");
        PrintUtil.printTree(bst.getRoot());
        bst.remove(2);
        System.out.println("\n删除节点 2 后，二叉树为\n");
        PrintUtil.printTree(bst.getRoot());
        bst.remove(4);
        System.out.println("\n删除节点 4 后，二叉树为\n");
        PrintUtil.printTree(bst.getRoot());
    }
}
