import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team_Formation {
   private static int count = 0;
   public static int countTeams(int[] skills, int minPlayers, int minLevel, int maxLevel){
      List<Integer> match = new ArrayList<>();
      for (int i : skills){
         if (i >= minLevel && i <= maxLevel){
            match.add(i);
         }
      }
      return count;
   }

   public static void backtrack(List<Integer> num, int depth, int minPlayers, int tmp){
      // > 是因为要把最后一种情况记录下来，然后到for循环那儿终止
      if (depth > num.size()){
         return;
      }

      if (tmp >= minPlayers){
         count++;
      }

      for (int i = depth; i < num.size(); i++){
         tmp++;
         backtrack(num, i + 1, minPlayers, tmp);
         tmp--;
      }

   }

   public static void main(String[] args){
      Integer[] n = new Integer[]{1,2,3,4};
      List<Integer> num = Arrays.asList(n);

      backtrack(num, 0, 3, 0);
      System.out.println(count);
   }
}
