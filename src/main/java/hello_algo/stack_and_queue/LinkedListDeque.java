package hello_algo.stack_and_queue;

import java.util.Arrays;

class ListNode {
    int val;
    ListNode next;
    ListNode prev;
    ListNode(int val) {
        this.val = val;
        next = prev = null;
    }
}

public class LinkedListDeque {
    ListNode front, rear;
    int queSize = 0;

    private int peekLast() {
        return rear.val;
    }

    private int peekFirst() {
        return front.val;
    }

    private void pushFirst(int i) {
        ListNode listNode = new ListNode(i);
        if(isEmpty()) {
            front = rear = listNode;
        }else{
            listNode.next = front;
            front.prev = listNode;
            front = listNode;
        }
        queSize++;
    }

    private void pushLast(int i) {
        ListNode listNode = new ListNode(i);
        if(isEmpty()) {
            front = rear = listNode;
        }else{
            rear.next = listNode;
            listNode.prev = rear;
            rear = listNode;
        }
        queSize++;
    }

    private int popLast() {
        int val = rear.val;
        ListNode listNode = rear.prev;
        listNode.next = null;
        rear = listNode;
        queSize--;
        return val;
    }

    private int popFirst() {
        int val = front.val;
        ListNode listNode = front.next;
        listNode.prev = null;
        front = listNode;
        queSize--;
        return val;
    }

    private int size() {
        return queSize;
    }

    private boolean isEmpty() {
        return queSize == 0;
    }

    private int[] toArray() {
        int[] arr = new int[queSize];
        ListNode listNode = front;
        for(int i = 0; i < queSize; i++) {
            arr[i] = listNode.val;
            listNode = listNode.next;
        }
        return arr;
    }


    public static void main(String[] args) {
        /* 初始化双向队列 */
        LinkedListDeque deque = new LinkedListDeque();
        deque.pushLast(3);
        deque.pushLast(2);
        deque.pushLast(5);
        System.out.println("双向队列 deque = " + Arrays.toString(deque.toArray()));

        /* 访问元素 */
        int peekFirst = deque.peekFirst();
        System.out.println("队首元素 peekFirst = " + peekFirst);
        int peekLast = deque.peekLast();
        System.out.println("队尾元素 peekLast = " + peekLast);

        /* 元素入队 */
        deque.pushLast(4);
        System.out.println("元素 4 队尾入队后 deque = " + Arrays.toString(deque.toArray()));
        deque.pushFirst(1);
        System.out.println("元素 1 队首入队后 deque = " + Arrays.toString(deque.toArray()));

        /* 元素出队 */
        int popLast = deque.popLast();
        System.out.println("队尾出队元素 = " + popLast + "，队尾出队后 deque = " + Arrays.toString(deque.toArray()));
        int popFirst = deque.popFirst();
        System.out.println("队首出队元素 = " + popFirst + "，队首出队后 deque = " + Arrays.toString(deque.toArray()));

        /* 获取双向队列的长度 */
        int size = deque.size();
        System.out.println("双向队列长度 size = " + size);

        /* 判断双向队列是否为空 */
        boolean isEmpty = deque.isEmpty();
        System.out.println("双向队列是否为空 = " + isEmpty);
    }

}
