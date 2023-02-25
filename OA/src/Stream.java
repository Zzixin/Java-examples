import java.util.Scanner;
import java.util.Arrays;


public class Stream {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.println("input: ");
      int m = sc.nextInt();
      int[] num1 = new int[m];
      for (int i = 0; i < m; i++) {
         num1[i] = sc.nextInt();
      }
      System.out.println("output: ");
      System.out.println(Arrays.toString(num1));
   }
}
