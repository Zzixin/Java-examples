// package com.example.java26.week1;

import java.util.*;

/**
 * array
 */

class ArrayTest {
   public static void main(String[] args) {
      int[] arr1 = {1, 2, 3};
      int[] arr2 = new int[]{1, 2, 3};
      Object[] arr3 = new Object[10]; // need t know length, max = Integer.MAX_VALUE
      List<Integer>[] graph = new List[10];
      for(int i = 0; i < graph.length; i++) {
         graph[i] = new ArrayList<>();
      }

      // reverse string
      String str = "";
      char[] chs = str.toCharArray(); // a new charArray compare to the original string


   }

   public static String reverse (String str) {
      char[] chs = str.toCharArray();
      for (int i = 0, j = chs.length - 1; i < j; i++,j++) {
         char ch = chs[i];
         chs[i] = chs[j];
         chs[j] = ch;
      }
      return new String(chs); // not modify the original string
   }
}

/**
 * ArrayList when initialize arraylist must specify the data type
 *      add(), not considering expanding the size, very fast, O(1)
 *      delete element:
 *      eg: [3][1][5][4][7][8][][][][][], delete 1
 *      answer: swap the target element with the last one, and delete the last one to make it faster
 *
 * LinkedList using Node 双指针
 *       using node pointer, try to be more specific
 *
 * using ArrayList:
 *       get by index
 *       if using arraylist to add element by index, every element behind have to move behind
 * using LinkedList
 *       add(index, element): add by index
 */

/**
 * HashMap - key-value pair <K, V>, key - immutable
 *       basic structure:
 *          like an node array, with linkedList node or red black tree node inside
 *          [LinkedList][][][] or [Red black Tree]
 *          if the length of the bucket is too long, linkedlist will convert to red black tree
 *       put(key, value)
 *       get(key)
 *
 *       time complexity: O(1)
 *
 * hashcode & hashvalue
 * id -> hash(id), shuffle the id set, a good hash function can put keys in each buckets evenly
 *
 * why hashcode is int
 *       if long，linkedlist will get larger, 搜索时间变长, not balanced
 *       tc depends on hashcode
 */
class Day2Student{
   private int id;
   // command + n, to get some functions

   public Day2Student(int id) {
      this.id = id;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Day2Student that = (Day2Student) o;
      return id == that.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }

   @Override
   public String toString() {
      return "Day2Student{" +
            "id=" + id +
            '}';
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getId() {
      return id;
   }
}

class HashMapTest {
   public static void main(String[] args) {
      // generally s1.hashCode != s2.hashCode
      // s1 == s2 false, they are two different instances,
      Day2Student s1 = new Day2Student(1);
      Day2Student s2 = new Day2Student(1);
      Map<Day2Student, Integer> map = new HashMap<>();
      map.put(s1, 1); // s1 will first go to the bucket1
      // null without hashCode and equals
      // null without equals
      // 1 with hasCode and equals
      System.out.println(map.get(s2));

      s1.setId(2); // still i bucket1
      System.out.println(map.get(s1)); // will go to the other bucket
   }
}

/**
 * hashset
 *       implemented as a hashmap, with a new object() served as value
 */

/**
 * TreeMap
 * key in ascending order
 */
class TreeMapTest{
   public static void main(String[] args) {
      Day2Student s3 = new Day2Student(1);
      // lambda function
      TreeMap<Day2Student, Integer> map = new TreeMap<>((s1, s2) -> s1.getId() - s2.getId());
      map.put(s3, 1);
      System.out.println((map.get(s3)));
   }
}

/**
 * loop throught map
 */
class ForLoopMap {
   public static void main(String[] args) {
      Map<Integer, Integer> m = new HashMap<>();
      m.put(1, 1);
      m.put(2, 2);
      m.put(3, 3);
      // using for loop map.entry, don't do any modifications, otherwise will throw error
      for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
         System.out.println((entry));
      }
      for(int key : m.keySet()) {
         System.out.println(key);
      }
      for(int val: m.values()) {
         System.out.println(val);
      }
      Iterator<Map.Entry<Integer, Integer>> itr = m.entrySet().iterator();
      while (itr.hasNext()) {
         System.out.println(itr.next());
         // cannot use remove
      }

      m.entrySet().forEach(System.out::println);
      m.entrySet().stream().forEach((System.out::println));
   }
}

/**
 * Stack - ArrayDeque
 * Queue - linkedlist
 */
class DequeTest {
   public static void main(String[] args) {
      Deque<Integer> stack = new ArrayDeque<>();
      Deque<Integer> stack2 = new LinkedList<>();
   }
}

/**
 * heap.offer() / poll()
 * everytime will reorder, not ordered other than root
 */
class PriorityQueueTest {
   public static void main(String[] args) {
      PriorityQueue<Integer> heap = new PriorityQueue<>((v1, v2) -> v1 - v2); // min heap, root is min
      PriorityQueue<Integer> heap2 = new PriorityQueue<>((v1, v2) -> v2 - v1); // maxheap
      PriorityQueue<Day2Student> heap3 = new PriorityQueue<>((v1, v2) -> v1.getId() - v2.getId());
      Day2Student s1 = new Day2Student(2);
      Day2Student s2 = new Day2Student(3);
      Day2Student s3 = new Day2Student(4);
      heap3.offer(s1);
      heap3.offer(s2);
      heap3.offer(s3);
      // heap will not change, only offer / poll will reorgornize
      s1.setId(20);

      PriorityQueue<Map.Entry<Integer, Integer>> heap4 = new PriorityQueue<>((e1, e2) -> {
         // here we cannot use ==, better to choose .equal or convert to integer
         if (e1.getValue() == e2.getValue()) {
            return -1;
         }
         else {
            return 1;
         }
      });

   }
}


/**
 * Iterator
 * when using iterator, 默认已经不会再修改了
 */
class IteratorTest {
   public static void main(String[] args) {
      List<Integer> list = new ArrayList<>();
      list.add(5);
      list.add(4);
      list.add(3);
      list.add(2);
      for(int v : list) {
         list.add(v + 2);
      }
      Iterator<Integer> itr = list.iterator();
      while(itr.hasNext()) {
         itr.next();
         list.add(5); // modify the list while using iterator, 会报错 exception or error?
      }
      // can modify the list
      for (int i = 0, len = list.size(); i < len; i++) {
         list.add(5);
      }
   }
}

/**
 *
 * thread vs process
 * thread - working unit
 * thread 是cpu schedule的最小单位
 * process - 自成的空间，thread USE process中的resource
 * Process 是resource allocation的最小单位
 * process 间资源不共享
 */
class TreadExample {
   public static void main(String[] args) throws Exception {
      System.out.println(Thread.currentThread());
      Thread t1 = new MyThread();
      Thread t2 = new Thread(() ->System.out.println(Thread.currentThread()));
      t1.start();
      t2.start();
      t1.join();
      t2.join();

   }
}
class MyThread extends Thread {
   @Override
   public void run() {
      System.out.println(Thread.currentThread());
   }
}
/**
 * jvm
 *
 * Stack
 *       thread 1 to 1 stack
 *       push method frame to stack
 *       recursion use stack
 *       every thread has its own stack
 *
 * Heap
 *       Object / instance use heap
 *       instance is stored in heap
 *    how to recycle?
 *       we can not recycle instances in java manually
 *       most instances and gc() is in young generation and cannot survive when young gen ends
 *       [ ede area ][s0][s1]   young generation (minor gc) STW-Stop The World
 *       // STW - the whole jvm stop and 标记要回收的object/instance
 *       // STW 越频繁，程序表现越差，频繁trigger gc
 *       // S0清空，将东西存储到S1；S1清空，将东西存储到S0;WHY, to let min instances go to old generation
 *       [  old generation    ] old generation (major gc) CMS / mark and sweep
 *
 *       CMS - current mark and sweep, try to decrease STW
 *       当空间太琐碎，不太能存放big object, we will use mark and sweep
 *
 *       full gc = minor + major gc
 *
 */

/**
 * CUP1   ----> time line
 *       T1 ----        ----
 *       T2     ----
 *       T3                 ----
 *       T4         ----
 *       T5
 * to make sure there is certain order for the threads, we need to add lock
 *  - multi-thread
 */

/**
 * tomorrow
 * thread and process is done today!
 *
 * memory leak
 * out of memory
 * stack over flow
 *
 * synchronized
 * volatile
 * CAS
 */























