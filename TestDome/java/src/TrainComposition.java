import java.util.ArrayDeque;
import java.util.Deque;

public class TrainComposition {
   Deque<Integer> train = new ArrayDeque<>();

   public void attachWagonFromLeft(int wagonId) {
      train.addFirst(wagonId);
   }

   public void attachWagonFromRight(int wagonId) {
      train.addLast(wagonId);
   }

   public int detachWagonFromLeft() {
      return train.removeFirst();
   }

   public int detachWagonFromRight() {
      return train.removeLast();
   }

   public static void main(String[] args) {
      TrainComposition train = new TrainComposition();
      train.attachWagonFromLeft(7);
      train.attachWagonFromLeft(13);
      System.out.println(train.detachWagonFromRight()); // 7
      System.out.println(train.detachWagonFromLeft()); // 13
   }
}