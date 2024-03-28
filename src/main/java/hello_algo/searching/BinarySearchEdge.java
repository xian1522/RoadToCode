package hello_algo.searching;

public class BinarySearchEdge {

    private static int binarySearchRightEdge(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        int m = i + (j - i) / 2;
        return 0;
    }

    private static int binarySearchLeftEdge(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        int m = i + (j - i) / 2;
        while (i <= j) {

        }
        return 0;
    }

    public static void main(String[] args) {
        // 包含重复元素的数组
        int[] nums = { 1, 3, 6, 6, 6, 6, 6, 10, 12, 15 };
        System.out.println("\n数组 nums = " + java.util.Arrays.toString(nums));

        // 二分查找左边界和右边界
        for (int target : new int[] { 6, 7 }) {
            int index = binarySearchLeftEdge(nums, target);
            System.out.println("最左一个元素 " + target + " 的索引为 " + index);
            index = binarySearchRightEdge(nums, target);
            System.out.println("最右一个元素 " + target + " 的索引为 " + index);
        }
    }


}
