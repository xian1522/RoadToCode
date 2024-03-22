package hello_algo.array_and_linkedlist;

import java.util.Arrays;

public class ArrayList {
    public int size = 0;
    public int capacity = 10;
    public int[] nums = new int[capacity];
    public int expendratio = 2;

    public void set(int index, int i1) {
        if(index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        nums[index] = i1;
    }

    public int get(int index) {
        if(index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        return nums[index];
    }

    public void remove(int index) {
        if(index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        for(int i = index; i < size; i++) {
            nums[i] = nums[i+1];
        }
        size-- ;
    }

    public void insert(int index, int i1) {
        if(index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        for(int i = size - 1; i >= index; i--){
            nums[i+1] = nums[i];
        }
        nums[index] = i1;
        size++;
        expend();
    }

    public int size() {
        return size;
    }

    private int capacity() {
        return capacity;
    }

    public int[] toArray() {
        int[] rsNums = new int[size];
        for(int i = 0; i < size; i++){
            rsNums[i] = nums[i];
        }
        return rsNums;
    }

    public void add(int i) {
        expend();
        nums[size] = i;
        size++;
    }

    private void expend() {
        if(size == capacity) {
            capacity = capacity * expendratio;
            int[] newNums = new int[capacity];
            for(int i=0; i<nums.length; i++) {
                newNums[i] = nums[i];
            }
            nums = newNums;
        }
    }

    public static void main(String[] args) {
        /* 初始化列表 */
        ArrayList nums = new ArrayList();
        /* 在尾部添加元素 */
        nums.add(1);
        nums.add(3);
        nums.add(2);
        nums.add(5);
        nums.add(4);
        System.out.println("列表 nums = " + Arrays.toString(nums.toArray()) +
                " ，容量 = " + nums.capacity() + " ，长度 = " + nums.size());

        /* 在中间插入元素 */
        nums.insert(3, 6);
        System.out.println("在索引 3 处插入数字 6 ，得到 nums = " + Arrays.toString(nums.toArray()));

        /* 删除元素 */
        nums.remove(3);
        System.out.println("删除索引 3 处的元素，得到 nums = " + Arrays.toString(nums.toArray()));

        /* 访问元素 */
        int num = nums.get(1);
        System.out.println("访问索引 1 处的元素，得到 num = " + num);

        /* 更新元素 */
        nums.set(1, 0);
        System.out.println("将索引 1 处的元素更新为 0 ，得到 nums = " + Arrays.toString(nums.toArray()));

        /* 测试扩容机制 */
        for (int i = 0; i < 10; i++) {
            // 在 i = 5 时，列表长度将超出列表容量，此时触发扩容机制
            nums.add(i);
        }
        System.out.println("扩容后的列表 nums = " + Arrays.toString(nums.toArray()) +
                " ，容量 = " + nums.capacity() + " ，长度 = " + nums.size());
    }


}
