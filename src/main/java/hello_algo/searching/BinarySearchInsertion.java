package hello_algo.searching;

public class BinarySearchInsertion {

    private static int binarySearchInsertion(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        int m = 0;
        while(i <= j) {
            m = i + (j - i) / 2;
            if(nums[m] > target) {
                j = m - 1;
            }else if(nums[m] < target) {
                i = m + 1;
            }else {
                j = m - 1;
            }
        }
        return i;
    }

    private static int binarySearchInsertionSimple(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        int m = 0;
        while(i <= j) {
            m = i + (j - i) / 2;
            if(nums[m] > target) {
                j = m -1;
            }else if(nums[m] < target) {
                i = m + 1;
            }else {
                return m;
            }
        }

        return i;
    }

    public static void main(String[] args) {
        // 无重复元素的数组
        int[] nums = { 1, 3, 6, 8, 12, 15, 23, 26, 31, 35 };
        System.out.println("\n数组 nums = " + java.util.Arrays.toString(nums));
        // 二分查找插入点
        for (int target : new int[] { 6, 9 }) {
            int index = binarySearchInsertionSimple(nums, target);
            System.out.println("元素 " + target + " 的插入点的索引为 " + index);
        }

        // 包含重复元素的数组
        nums = new int[] { 1, 3, 6, 6, 6, 6, 6, 10, 12, 15 };
        System.out.println("\n数组 nums = " + java.util.Arrays.toString(nums));
        // 二分查找插入点
        for (int target : new int[] { 2, 6, 20 }) {
            int index = binarySearchInsertion(nums, target);
            System.out.println("元素 " + target + " 的插入点的索引为 " + index);
        }
    }
}
