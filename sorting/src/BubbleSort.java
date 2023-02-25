import java.util.Arrays;

public class BubbleSort {
    /**
     冒泡排序
     依次比较相邻的两个数，将比较大的数放到后面
     这样子第i次之后，最后i个数都是最大的，并且是顺序的.
     时间复杂度：1）最好O(n) 数据正序
               2）最差/平均 O(n2)
                数据倒序，一共n-1趟排序，每趟排序进行n-i次比较，每次比较移动记录三次
                max = 3n(n-1)/2 = O(n2)
     空间复杂度：O(1)
     稳定性： 稳定

     * @param nums 待排序数组
     */
    public static void bubble(int[] nums){
        boolean flag_change = true;
        int n = nums.length;

        for (int i = 0; i < n-1 && flag_change; i++){
            flag_change = false;
            for (int j = 0; j < n-i-1; j++){
                if (nums[j] > nums[j+1]){
                    swap(nums, j, j+1);
                    flag_change = true;
                }
            }
        }
    }

    private static void swap(int[] nums, int i, int j){
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {1,5,2,3,7,5,8,9,4,4,1,3,10,5,6,234};
        bubble(nums);
        System.out.println(Arrays.toString(nums));
    }
}
