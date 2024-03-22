package hello_algo.stack_and_queue;

import hello_algo.ListNode;

import java.util.Arrays;
import java.util.List;

/**
 * 栈：特殊的数组或者链表
 */
public class LinkedListStack {
    int size = 0;
    ListNode head;

    private int[] toArray() {
        if(size == 0) {
            return null;
        }
        int[] arr = new int[size];
        ListNode temp = head;
        for(int i=0; i < size; i++) {
            arr[i] = temp.val;
            temp = temp.next;
        }
        return arr;
    }

    private void push(int i) {
        ListNode listNode = new ListNode(i);
        listNode.next = head;
        head = listNode;

        size++;
    }

    private int peek() {
        if(isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return head.val;
    }

    private int pop() {
        int val = head.val;
        ListNode listNode = head.next;
        head = listNode;
        size--;
        return val;
    }

    private int size() {
        return size;
    }

    private boolean isEmpty() {
        return head == null;
    }

    public static void main(String[] args) {
        /* 初始化栈 */
        LinkedListStack stack = new LinkedListStack();

        /* 元素入栈 */
        stack.push(1);
        stack.push(3);
        stack.push(2);
        stack.push(5);
        stack.push(4);
        System.out.println("栈 stack = " + Arrays.toString(stack.toArray()));

        /* 访问栈顶元素 */
        int peek = stack.peek();
        System.out.println("栈顶元素 peek = " + peek);

        /* 元素出栈 */
        int pop = stack.pop();
        System.out.println("出栈元素 pop = " + pop + "，出栈后 stack = " + Arrays.toString(stack.toArray()));

        /* 获取栈的长度 */
        int size = stack.size();
        System.out.println("栈的长度 size = " + size);

        /* 判断是否为空 */
        boolean isEmpty = stack.isEmpty();
        System.out.println("栈是否为空 = " + isEmpty);
    }

}
