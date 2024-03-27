package hello_algo.heap;

import hello_algo.PrintUtil;

import java.util.*;

/**
 * 大顶堆
 */
public class MaxHeap {
    List<Integer> maxHeap;

    MaxHeap(List<Integer> list) {
        maxHeap = new ArrayList<>();
        for(Integer val : list) {
            maxHeap.add(val);
            shiftUp(size() - 1 );
        }
    }

    public int left(int i) {
        return 2 * i + 1;
    }
    public int right(int i) {
        return 2 * i + 2;
    }

    public int parent(int i) {
        return (i - 1) / 2;
    }

    public int peek() {
        return maxHeap.get(0);
    }
    public int size() {
        return maxHeap.size();
    }

    public void shiftUp(int i) {
        while (true) {
            int p = parent(i);
            if(p < 0 || maxHeap.get(p) >= maxHeap.get(i)) {
                break;
            }
            swap(i, p);
            i = p;
        }
    }
    public void push(int val) {
        maxHeap.add(val);
        shiftUp(size() - 1);
    }

    private void swap(int i, int j) {
        int temp = maxHeap.get(j);
        maxHeap.set(j, maxHeap.get(i));
        maxHeap.set(i, temp);
    }

    public int pop() {
        swap(0, size() - 1);
        int val = maxHeap.remove(size() -1);
//        shiftUp(size() - 1);
        return val;
    }

    public void shiftDown(int i) {
        while (true) {
            int l = left(i);
            int r = right(i);
            int ma = i;
            if (i < size() && maxHeap.get(l) > maxHeap.get(ma)) {
                ma = l;
            }
            if (i < size() && maxHeap.get(r) > maxHeap.get(ma)) {
                ma = r;
            }
            if(ma == i) {
                break;
            }
            swap(ma, i);
            i = ma;
        }
    }

    /* 打印堆（二叉树） */
    public void print() {
        Queue<Integer> queue = new PriorityQueue<>((a, b) -> { return b - a; });
        queue.addAll(maxHeap);
        PrintUtil.printHeap(queue);
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    public static void main(String[] args) {
        /* 初始化大顶堆 */
        MaxHeap maxHeap = new MaxHeap(Arrays.asList(9, 8, 6, 6, 7, 5, 2, 1, 4, 3, 6, 2));
        System.out.println("\n输入列表并建堆后");
        maxHeap.print();

        /* 获取堆顶元素 */
        int peek = maxHeap.peek();
        System.out.format("\n堆顶元素为 %d\n", peek);

        /* 元素入堆 */
        int val = 7;
        maxHeap.push(val);
        System.out.format("\n元素 %d 入堆后\n", val);
        maxHeap.print();

        /* 堆顶元素出堆 */
        peek = maxHeap.pop();
        System.out.format("\n堆顶元素 %d 出堆后\n", peek);
        maxHeap.print();

        /* 获取堆大小 */
        int size = maxHeap.size();
        System.out.format("\n堆元素数量为 %d\n", size);

        /* 判断堆是否为空 */
        boolean isEmpty = maxHeap.isEmpty();
        System.out.format("\n堆是否为空 %b\n", isEmpty);
    }
}
