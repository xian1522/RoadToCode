package hello_algo.searching;

public class BinarySearch {

    private static int binarySearchLCRO(int[] nums, int target) {
        int i = 0, j = nums.length;
        while (i < j) {
            int m = i + (j - i) / 2;
            if(nums[m] > target) {
                j = m ;
            }else if(nums[m] < target) {
                i = m + 1;
            }else {
                return m;
            }
        }
        return -1;
    }

    private static int binarySearch(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int m = i + (j - i) / 2;
            if(nums[m] > target) {
                j = m - 1;
            }else if(nums[m] < target) {
                i = m + 1;
            }else {
                return m;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int target = 4;
        int[] nums = { 1, 3, 6, 8, 12, 15, 23, 26, 31, 35 };

        /* 二分查找（双闭区间） */
        int index = binarySearch(nums, target);
        System.out.println("目标元素 " + target + " 的索引 = " + index);

        /* 二分查找（左闭右开区间） */
        index = binarySearchLCRO(nums, target);
        System.out.println("目标元素 " + target + " 的索引 = " + index);
    }
}
