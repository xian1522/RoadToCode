package hello_algo.sorting;

import javax.sound.sampled.EnumControl;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {

    /**
     * 1.选择排序: 开启一个循环，每轮从未排序区间选择最小的元素，将其放到已排序区间的末尾
     */
    private static void selectionSort(int[] nums) {
        for(int i = 0; i < nums.length - 1; i++){
           for(int j = i + 1; j < nums.length; j++) {
               int k = i;
               if(nums[k] > nums[j]) {
                  k = j;
               }
               int temp = nums[i];
               nums[i] = nums[k];
               nums[k] = temp;
           }
        }
    }
    /**
     * 2.冒泡排序
     */
    private static void bubbleSort(int[] nums) {
        for(int i = nums.length - 1; i > 0; i--) {
            for(int j = 0; j < i; j++) {
                if(nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    private static void bubbleSortWithFlag(int[] nums) {
        for(int i = nums.length - 1; i > 0; i--) {
            boolean flag = false;
            for(int j = 0; j < i; j++) {
                if(nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    flag = true;
                }
            }
            if(!flag){
                break;
            }
        }
    }
    /*
     3.插入排序
    */
    private static void insertionSort(int[] nums) {
        for(int i = 1; i < nums.length; i++) {
            int base = nums[i];
            for(int j = i - 1; j >= 0; j--) {
                if(nums[j] > base) {
                    nums[j+1] = nums[j];
                    nums[j] = base;
                }else {
                    break;
                }
            }
        }
    }

    /**
     * 快速排序
     */
    private static void quickSort(int[] nums, int i, int j) {
        if(i >= j) {
            return;
        }
        int partition = partition(nums, i, j);
        quickSort(nums, i, partition - 1);
        quickSort(nums, partition + 1, j);
    }

    static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    private static int partition(int[] nums, int left, int right){
        int i = left, j = right;
        while (i < j){
            while (i < j && nums[left] <= nums[j]) j--;
            while (i < j && nums[left] >= nums[i]) i++;

            swap(nums, i, j);
        }
        swap(nums, i, left);
        return i;
    }

    private static void mergeSort(int[] nums, int left, int right) {
        if(left >= right){
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);

        merge(nums, left, mid, right);
    }

    private static void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if(nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            }else {
                temp[k++] = nums[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= right){
            temp[k++] = nums[j++];
        }
        for(k = 0; k < temp.length; k++ ) {
            nums[left + k] = temp[k];
        }
    }

    public static void heapSort(int[] nums) {
        for(int i = nums.length / 2 - 1; i >= 0; i--) {
            shiftDown(nums, nums.length, i);
        }

        for(int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            shiftDown(nums, i, 0);
        }
    }
    private static void shiftDown(int[] nums, int n ,int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int max = i;

            if(left < n && nums[max] < nums[left]) {
                max = left;
            }
            if(right < n && nums[max] < nums[right]) {
                max = right;
            }
            if(max == i){
                return;
            }

            int temp = nums[i];
            nums[i] = nums[max];
            nums[max] = temp;
            i = max;
        }
    }

    private static void bucketSort(float[] nums) {
        int k = nums.length / 2;
        List<List<Float>> buckets = new ArrayList<>();
        for(int i = 0; i < k; i++) {
            buckets.add(new ArrayList<>());
        }

        for(float num : nums) {
            int i = (int)(k * num);
            buckets.get(i).add(num);
        }
        for(List<Float> bucket : buckets) {
            Collections.sort(bucket);
        }

        int i = 0;
        for(List<Float> bucket : buckets) {
            for(Float num : bucket) {
                nums[i++] = num;
            }
        }
    }

    
    public static void main(String[] args) {
        int[] nums = { 4, 1, 3, 1, 5, 2 };
        selectionSort(nums);
        System.out.println("选择排序完成后 nums = " + Arrays.toString(nums));

        int[] nums1 = { 4, 1, 3, 1, 5, 2 };
        bubbleSort(nums1);
        System.out.println("冒泡排序完成后 nums = " + Arrays.toString(nums1));

        int[] nums1_1 = { 4, 1, 3, 1, 5, 2 };
        bubbleSortWithFlag(nums1_1);
        System.out.println("冒泡排序完成后 nums1 = " + Arrays.toString(nums1_1));

        int[] nums2 = { 4, 1, 3, 1, 5, 2 };
        insertionSort(nums2);
        System.out.println("插入排序完成后 nums = " + Arrays.toString(nums2));

        int[] nums3 = { 2, 4, 1, 0, 3, 5 };
        quickSort(nums3, 0, nums3.length - 1);
        System.out.println("快速排序完成后 nums = " + Arrays.toString(nums3));

        /* 归并排序 */
        int[] nums4 = { 7, 3, 2, 6, 0, 1, 5, 4 };
        mergeSort(nums4, 0, nums4.length - 1);
        System.out.println("归并排序完成后 nums = " + Arrays.toString(nums4));

        int[] nums5 = { 4, 1, 3, 1, 5, 2 };
        heapSort(nums5);
        System.out.println("堆排序完成后 nums = " + Arrays.toString(nums5));

        float[] nums6 = { 0.49f, 0.96f, 0.82f, 0.09f, 0.57f, 0.43f, 0.91f, 0.75f, 0.15f, 0.37f };
        bucketSort(nums6);
        System.out.println("桶排序完成后 nums = " + Arrays.toString(nums6));
    }

}
