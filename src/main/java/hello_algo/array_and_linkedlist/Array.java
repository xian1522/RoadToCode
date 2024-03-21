package hello_algo.array_and_linkedlist;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 数据结构-数组
 *
 */
public class Array {
    /**
     * 随机访问
     */
    static int randomAccess(int[] nums) {
        int i = ThreadLocalRandom.current().nextInt(0, nums.length);
        return nums[i];
    }

    /**
     * 增加元素
     * @param nums
     * @param num
     * @param index
     */
    static void insert(int[] nums, int num, int index) {
        for(int i= nums.length-1; i>index; i--) {
            nums[i] = nums[i-1];
        }
        nums[index] = num;
    }

    /**
     * 删除元素
     * @param nums
     * @param index
     */
    static void remove(int[] nums, int index) {
        for(int i=index; i<nums.length-1; i++) {
            nums[i] = nums[i+1];
        }
    }

    /**
     * 遍历数组
     * @param nums
     */
    static void traverse(int[] nums) {
        for(int num: nums) {
            System.out.println(num);
        }
    }

    /**
     * 查询数组
     * @param nums
     * @param target
     * @return
     */
    static int find(int[] nums, int target) {
        for(int i=0; i<nums.length; i++) {
            if(nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    static int[] extend(int[] nums, int length) {
        int[] newNums = new int[nums.length + length];
        for(int i=0; i<nums.length; i++) {
            newNums[i] = nums[i];
        }
        return newNums;
    }


    public static void main(String[] args) {
        /* 初始化数组 */
        int[] arr = new int[5];
        System.out.println("数组 arr = " + Arrays.toString(arr));
        int[] nums = { 1, 3, 2, 5, 4 };
        System.out.println("数组 nums = " + Arrays.toString(nums));

        /* 随机访问 */
        int randomNum = randomAccess(nums);
        System.out.println("在 nums 中获取随机元素 " + randomNum);

        /* 长度扩展 */
        nums = extend(nums, 3);
        System.out.println("将数组长度扩展至 8 ，得到 nums = " + Arrays.toString(nums));

        /* 插入元素 */
        insert(nums, 6, 3);
        System.out.println("在索引 3 处插入数字 6 ，得到 nums = " + Arrays.toString(nums));

        /* 删除元素 */
        remove(nums, 2);
        System.out.println("删除索引 2 处的元素，得到 nums = " + Arrays.toString(nums));

        /* 遍历数组 */
        traverse(nums);

        /* 查找元素 */
        int index = find(nums, 3);
        System.out.println("在 nums 中查找元素 3 ，得到索引 = " + index);
    }

}
