package hello_algo.stack_and_queue;

import hello_algo.array_and_linkedlist.ArrayList;

import java.util.Arrays;
import java.util.Stack;

public class ArrayListStack {

    private ArrayList arrayList;

    public ArrayListStack () {
        this.arrayList = new ArrayList();
    }

    private int[] toArray() {
        return arrayList.toArray();
    }

    private void push(int i) {
        arrayList.add(i);
    }

    private int peek() {
        return arrayList.get(size() - 1);
    }

    private int pop() {
        int num = peek();
        arrayList.remove(size() - 1);
        return num;
    }

    private int size() {
        return arrayList.size();
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    public static void main(String[] args) {
        /* 初始化栈 */
        ArrayListStack stack = new ArrayListStack();

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
