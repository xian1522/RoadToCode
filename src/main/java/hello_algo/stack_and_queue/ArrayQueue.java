package hello_algo.stack_and_queue;

import java.util.Arrays;

public class ArrayQueue {

    private int[] arr;
    private int front;
    private int queSize;

    public ArrayQueue(int capacity) {
        arr = new int[capacity];
        front = queSize = 0;
    }

    private int peek() {
        return arr[front];
    }

    private int size() {
        return queSize;
    }

    private boolean isEmpty() {
        return queSize == 0;
    }

    private void push(int i) {
        int rear = (front + queSize)%arr.length;
        arr[rear] = i;
        queSize ++;
    }

    private int pop() {
        int val = arr[front];
        front = (front + 1)%arr.length;
        queSize--;
        return val;
    }

    private int[] toArray() {
        int[] temp = new int[queSize];
        for (int i = 0, j = front; i < queSize; i++, j++) {
            temp[i] = arr[j%arr.length];
        }
        return temp;
    }

    public static void main(String[] args) {
        /* 初始化队列 */
        int capacity = 10;
        ArrayQueue queue = new ArrayQueue(capacity);

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

        /* 测试环形数组 */
        for (int i = 0; i < 10; i++) {
            queue.push(i);
            queue.pop();
            System.out.println("第 " + i + " 轮入队 + 出队后 queue = " + Arrays.toString(queue.toArray()));
        }
    }
}
