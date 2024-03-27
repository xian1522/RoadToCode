package hello_algo;

import hello_algo.array_and_linkedlist.LinkedList;
import hello_algo.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Trunk {
    Trunk prev;
    String str;

    Trunk(Trunk prev, String str) {
        this.prev = prev;
        this.str = str;
    }
};

public class PrintUtil {

    public static void printLinkedList(ListNode listNode) {
        List<String> list = new ArrayList<>();
        ListNode temp = listNode;
        while (temp != null){
            list.add(String.valueOf(temp.val));
            temp = temp.next;
        }
        System.out.println(String.join("->", list));
    }

    public static void printTree(TreeNode root) {
        printTree(root, null, false);
    }

    /* Print a binary tree */
    public static void printTree(TreeNode root, Trunk prev, boolean isRight) {
        if (root == null) {
            return;
        }

        String prev_str = "    ";
        Trunk trunk = new Trunk(prev, prev_str);

        printTree(root.right, trunk, true);

        if (prev == null) {
            trunk.str = "———";
        } else if (isRight) {
            trunk.str = "/———";
            prev_str = "   |";
        } else {
            trunk.str = "\\———";
            prev.str = prev_str;
        }

        showTrunks(trunk);
        System.out.println(" " + root.val);

        if (prev != null) {
            prev.str = prev_str;
        }
        trunk.str = "   |";

        printTree(root.left, trunk, false);
    }

    public static void showTrunks(Trunk p) {
        if (p == null) {
            return;
        }

        showTrunks(p.prev);
        System.out.print(p.str);
    }

    public static void printHeap(Queue<Integer> queue) {
        List<Integer> list = new ArrayList<>(queue);
        System.out.print("堆的数组表示：");
        System.out.println(list);
        System.out.println("堆的树状表示：");
        TreeNode root = TreeNode.listToTree(list);
        printTree(root);
    }
}
