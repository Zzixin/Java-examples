import java.util.Arrays;

/**
 * refer: https://www.cnblogs.com/chengxiao/p/6129630.html
 */

public class HeapSort {
    /**
     * 堆排序
     * 顺序-大顶堆；倒序-小顶堆
     *
     * @param nums array to be sorted
     */

    public static void heapsort(int[] nums){
        int n = nums.length;
        //1. 构建大顶堆
        for (int i = n/2-1; i >= 0; i--){ //从第一个非叶子节点开始，即n/2-1
            AdjustHeap(nums, i, n); //从第一个非叶子节点开始从下到上，从右到左调整结构
        }

        //2. 调整堆结构，交换堆顶和末尾元素，使得后半部分数组有序
        for (int j=n-1; j>=0; j--){
            swap(nums, 0, j); //交换
            AdjustHeap(nums, 0, j); //重新调整
        }

    }

    /**
     * 调整大顶堆
     * 从i节点开始，向下遍历i的子节点们，将i放到合适的位置，途中也会移动其他节点
     * @param nums 当前未排序部分数组
     * @param i 当前节点index
     * @param len 未排序部分数组长度
     */
    public static void AdjustHeap(int[] nums, int i, int len){
        int cur_node = nums[i]; //保存节点i
        for (int k = i*2+1; k < len; k = k*2+1){ //从i节点的左子节点开始
            if (k+1 < len && nums[k]<nums[k+1]){ //如果左子节点小于右子节点，k指向右子节点
                k++;
            }

            if (nums[k] > cur_node){ //若子节点大于父节点（即最开始的节点i），将子节点值赋给父节点
                //swap(nums, k, (k-1)/2); //这里用swap会有三次赋值，可以不用
                nums[i] = nums[k];
                i = k; //将i指向cur_node下一个可能的位置
            } else {
                break;
            }
        }
        nums[i] = cur_node;
    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args){
        int[] nums = {1,5,2,3,7,5,8,9,4,4,1,3,10,5,6,234};
        heapsort(nums);
        System.out.println(Arrays.toString(nums));
    }

}
