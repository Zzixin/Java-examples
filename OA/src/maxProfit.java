import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class maxProfit {

   public static int maxPro(int costPerCut, int salePrice, List<Integer> lengths){
      int maxProfit = 0;
      int maxlen = 0;
      for (int i : lengths){
         maxlen = Math.max(maxlen, i);
      }
      for(int len = 1; len <= maxlen; len++){
         int totalcuts = 0, totalpieces = 0;
         for (int rod : lengths){
            int cut = (rod - 1) / len;
            int piece = rod / len;
            if (piece * len * salePrice - cut * costPerCut > 0) {
               totalcuts += cut;
               totalpieces += piece;
            }
         }
         maxProfit = Math.max(maxProfit, totalpieces * len * salePrice - totalcuts * costPerCut);
      }
      return maxProfit;
   }

   public static void main(String[] args){
      System.out.println(maxPro(1, 10, Arrays.asList(26, 103, 59)));
   }

}
