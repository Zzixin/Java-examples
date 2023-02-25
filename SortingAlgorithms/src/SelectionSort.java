import java.util.Arrays;

public class SelectionSort {
    /**
     选择排序
     可以将数组分成已排序部分和未排序部分
     共需要遍历n-1次，最后一个数不用遍历，直接就是最大的
     每次遍历在未排序部分中选取最小的数，将其和未排序部分的第一个数交换

     时间复杂度 O(n2)
     空间复杂度 O(1)
     稳定性：不稳定 eg 6、7、6、2、8 第一次排序会将第一个6和2交换，这样子两个6的顺序就变了

     * @param nums  array to be sorted
     */
    public static void selection(int[] nums){
        int n = nums.length;
        for (int i = 0; i < n-1; i++){
            int min_index = i;
            for (int j = i; j < n; j++){
                if (nums[j] < nums[min_index]){
                    min_index = j;
                }
            }
            swap(nums, i, min_index);
        }
    }

    private static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args){
        int[] nums = {1,5,2,3,7,5,8,9,4,4,1,3,10,5,6,234};
        selection(nums);
        System.out.println(Arrays.toString(nums));
    }
}
