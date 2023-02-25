import java.util.Arrays;

public class InsertionSort {
    /**
     选择排序
     将数组分为已排序部分和未排序部分。初始值已排序部分只有第一个元素
     从第2位开始遍历(for)，将此元素和前面已排好序的元素比较(for)，找到合适的位置插入
     前半部分已排好序，后半部分未排好序（对比冒泡排序）

     时间复杂度 1）最好O(n)
              2) 最差/平均O(n2)
     空间复杂度 O(1)
     稳定性：稳定

     * @param nums
     */

    public static void insertion(int[] nums){
        for (int i = 1, j; i<nums.length; i++){
            int temp_num = nums[i];
            //将第i位元素和前面排好序的比较，当其大于nums[j]的时候，就是插入位置
            for (j = i-1; j>=0 && temp_num<nums[j]; j--){
                nums[j+1] = nums[j];
            }
            nums[j+1] = temp_num;
        }
    }

    public static void main(String[] args){
        int[] nums = {1,5,2,3,7,5,8,9,4,4,1,3,10,5,6,234};
        insertion(nums);
        System.out.println(Arrays.toString(nums));
    }
}
