package hello_algo.array_and_linkedlist;

import hello_algo.ListNode;
import hello_algo.PrintUtil;

public class LinkedList {

    public static void main(String[] args) {
        /* 初始化链表 */
        // 初始化各个节点
        ListNode n0 = new ListNode(1);
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(5);
        ListNode n4 = new ListNode(4);
        // 构建节点之间的引用
        n0.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        System.out.println("初始化的链表为");
        PrintUtil.printLinkedList(n0);

        /* 插入节点 */
        insert(n0, new ListNode(0));
        System.out.println("插入节点后的链表为");
        PrintUtil.printLinkedList(n0);

        /* 删除节点 */
        remove(n0);
        System.out.println("删除节点后的链表为");
        PrintUtil.printLinkedList(n0);

        /* 访问节点 */
        ListNode node = access(n0, 3);
        System.out.println("链表中索引 3 处的节点的值 = " + node.val);

        /* 查找节点 */
        int index = find(n0, 2);
        System.out.println("链表中值为 2 的节点的索引 = " + index);
    }


    private static int find(ListNode n0, int i) {
        int index = 0;
        while (n0 != null) {
            if(n0.val == i) {
                return index;
            }
            index++;
            n0 = n0.next;
        }

        return -1;
    }

    private static ListNode access(ListNode n0, int i) {
        int count = 0;
        while (n0 != null) {
            if(count == i) {
                return n0;
            }
            count ++;
            n0 = n0.next;
        }
        return null;
    }

    private static void remove(ListNode n0) {
        if(n0.next == null) {
            return;
        }
        ListNode del = n0.next;
        ListNode next = del.next;
        n0.next = next;
    }

    private static void insert(ListNode n0, ListNode listNode) {
        ListNode temp = n0.next;
        n0.next = listNode;
        listNode.next = temp;
    }
}
