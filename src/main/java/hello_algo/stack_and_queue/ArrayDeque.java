package hello_algo.stack_and_queue;

import java.util.Arrays;

public class ArrayDeque {
    int[] arr;
    int front;
    int rear;
    int queSize;

    ArrayDeque(int capacity) {
        arr = new int[capacity];
        front = rear = queSize = 0;
    }

    private int peekLast() {
        return arr[rear];
    }

    private int peekFirst() {
        return arr[front];
    }

    private void pushLast(int i) {
        if(isEmpty()) {
            arr[rear] = i;
        }else {
            rear = rear + 1;
            arr[rear % arr.length] = i;
        }
        queSize++;
    }

    private void pushFirst(int i) {
        if(isEmpty()) {
            arr[front] = i;
        }else {
            front = front - 1;
            if (front < 0) {
                front = arr.length - 1;
            }
            arr[front] = i;
        }
        queSize++;
    }

    private int popLast() {
        int val = arr[rear];
        rear = rear - 1;
        if(rear < 0) {
            rear = arr.length - 1;
        }
        queSize--;
        return val;
    }

    private int popFirst() {
        int val = arr[front];
        front = (front + 1) % arr.length;
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
        int[] res = new int[queSize];
        for (int i = 0, j = front; i < queSize; i++,j++) {
            res[i] = arr[j%arr.length];
        }
        return res;
    }

    public static void main(String[] args) {
        /* 初始化双向队列 */
        ArrayDeque deque = new ArrayDeque(10);
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
