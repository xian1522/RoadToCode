package hello_algo.stack_and_queue;

import hello_algo.ListNode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LinkedListQueue {

    private ListNode front;
    private ListNode rear;
    private int size;

    private void push(int i) {
        ListNode listNode = new ListNode(i);
        if(isEmpty()) {
            front = listNode;
            rear = listNode;
            front.next = rear;
        }else {
            rear.next = listNode;
            rear = listNode;
        }
        size++;
    }

    private int peek() {
        if(front == null) {
            throw new IndexOutOfBoundsException("---");
        }
        return front.val;
    }

    private int pop() {
        ListNode temp = front.next;
        int val = front.val;
        front.next = null;
        front = temp;
        size--;
        return val;
    }

    private int[] toArray() {
        int[] arr = new int[size];
        ListNode listNode = front;
        for (int i = 0; i < size; i++) {
            arr[i] = listNode.val;
            listNode = listNode.next;
        }
        return arr;
    }

    private int size() {
        return size;
    }

    private boolean isEmpty() {
        return size == 0;
    }


    public static void main(String[] args) {
        /* 初始化队列 */
        LinkedListQueue queue = new LinkedListQueue();

        /* 元素入队 */
        queue.push(1);
        queue.push(3);
        queue.push(2);
        queue.push(5);
        queue.push(4);
        System.out.println("队列 queue = " + Arrays.toString(queue.toArray()));

        /* 访问队首元素 */
        int peek = queue.peek();
        System.out.println("队首元素 peek = " + peek);

        /* 元素出队 */
        int pop = queue.pop();
        System.out.println("出队元素 pop = " + pop + "，出队后 queue = " + Arrays.toString(queue.toArray()));

        /* 获取队列的长度 */
        int size = queue.size();
        System.out.println("队列长度 size = " + size);

        /* 判断队列是否为空 */
        boolean isEmpty = queue.isEmpty();
        System.out.println("队列是否为空 = " + isEmpty);
    }

}
