import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Football_score {

   public static List<Integer> football(int[] teamA, int[] teamB){
      List<Integer> res = new ArrayList<>();
      Arrays.sort(teamA);
      // find the smallest element in A which is bigger than element in B
      for (int B : teamB){
         int left = 0, right = teamA.length - 1;
         while (left < right){
            int mid = (right - left) / 2 + left;
            if (teamA[mid] >= B){
               right = mid;
            }
            else{
               left = mid + 1;
            }
         }
         System.out.println("right: "+ right);
         if (teamA[right] <= B){
            res.add(right + 1);
         }
         else{
            res.add(right);
         }
      }

      return res;

   }

   public static void main(String[] args){
      int[] teamA = {1,2,3,5,7,8,9};
      int[] teamB = {0,2,4,10};
      List<Integer> ans = football(teamA, teamB);
      for (int i : ans){
         System.out.println(i + " ");
      }
   }
}
