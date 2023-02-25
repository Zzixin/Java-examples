import java.util.HashMap;
import java.util.Map;

public class Palindrome_subseq {

   public static int count(String str){
      int n = str.length();
      int[][] count1 = new int[4][n]; // 01, 10, 11, 00 front
      int[][] count2 = new int[4][n]; // 01, 10, 11, 00 rear
      int num1 = 0, num0 = 0;
      for (int i = 0; i < n; i++){
         if (str.charAt(i) == '0'){
            count1[1][i] = num1;
            count1[3][i] = num0;
            num0++;
         }
         else{
            count1[0][i] = num0;
            count1[2][i] = num1;
            num1++;
         }
         count1[1][i] += (i > 0 ? count1[1][i - 1]:0);
         count1[3][i] += (i > 0 ? count1[3][i - 1]:0);
         count1[0][i] += (i > 0 ? count1[0][i - 1]:0);
         count1[2][i] += (i > 0 ? count1[2][i - 1]:0);
      }
      num1 = 0; num0 = 0;
      //int[][] count2 = new int[4][n]; // 01, 10, 11, 00 rear
      for (int i = n - 1; i >= 0; i--){
         if (str.charAt(i) == '0'){
            count2[1][i] = num1;
            count2[3][i] = num0;
            num0++;
         }
         else{
            count2[0][i] = num0;
            count2[2][i] = num1;
            num1++;
         }
         count2[1][i] += (i < n - 1 ? count2[1][i + 1]:0);
         count2[3][i] += (i < n - 1 ? count2[3][i + 1]:0);
         count2[0][i] += (i < n - 1 ? count2[0][i + 1]:0);
         count2[2][i] += (i < n - 1 ? count2[2][i + 1]:0);
      }
      int count = 0;
      for (int i = 2; i < n - 2; i++){
         for (int j = 0; j < 4; j++){
            count += count1[j][i - 1] * count2[j][i + 1];
            count %= 1e9+7;
         }
      }
      return count;
   }


   public static void main(String[] args){
      String test1 = "010110";
      System.out.println(count(test1));
   }

}
