package com.example.java26.week1;

import java.util.*;

/**
 *  array
 */
class ArrayTest {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = new int[]{1, 2, 3};
        Object[] arr3 = new Object[10];
        List<Integer>[] graph = new List[10];
        for(int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        String str = "";
        char[] chs = str.toCharArray();
    }

    public static String reverse(String str) {
        char[] chs = str.toCharArray();
        for(int i = 0, j = chs.length - 1; i < j; i++, j--) {
            char ch = chs[i];
            chs[i] = chs[j];
            chs[j] = ch;
        }
        return new String(chs);
    }
}
/**
 *  ArrayList
 *      get by index
 *  LinkedList
 *      add(index, element)
 */

/**
 *  HashMap<K, V>
 *
 *      [LinkedList][][][][][][][Red black tree]
 *
 *      put(key, value)
 *      get(key)
 *
 *      time complexity : O(1)
 *
 *  id -> hash(id)
 */
class Day2Student {
    private int id;

    public Day2Student(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
class HashMapTest {
    public static void main(String[] args) {
        //s1.hashCode == s2.hashCode
        //s1.equals(s2) == true
        Day2Student s1 = new Day2Student(1);
//        Day2Student s2 = new Day2Student(1);
        Map<Day2Student, Integer> map = new HashMap<>();
        map.put(s1, 1);
        s1.setId(10);
        /**
         *  id = 1 -> hash <- == id = 2
         */
        System.out.println(map.get(s1));
    }
}

/**
 *  TreeMap
 */
class TreeMapTest {
    public static void main(String[] args) {
        Day2Student day2Student = new Day2Student(1);
        TreeMap<Day2Student, Integer> map = new TreeMap<>((s1, s2) -> s1.getId() - s2.getId());
        map.put(day2Student, 1);
        System.out.println(map.get(day2Student));
    }
}

/**
 *  loop through map
 */
class ForLoopMap {
    public static void main(String[] args) {
        Map<Integer, Integer> m = new HashMap<>();
        m.put(1, 1);
        m.put(2, 2);
        m.put(3, 3);
//        for(Map.Entry<Integer, Integer> entry: m.entrySet()) {
//            System.out.println(entry);
//        }
//        for(int key: m.keySet()) {
//            System.out.println(key);
//        }
//        for(int val: m.values()) {
//            System.out.println(val);
//        }
//        Iterator<Map.Entry<Integer, Integer>> itr = m.entrySet().iterator();
//        while(itr.hasNext()) {
//            System.out.println(itr.next());
//        }
//        m.entrySet().forEach(System.out::println);
//        m.entrySet().stream().forEach(System.out::println);
    }
}
/**
 * Stack
 * Queue
 */
class DequeTest {
    public static void main(String[] args) {
        Deque<Integer> q = new LinkedList<>();
    }
}
/**
 *  heap.offer() / poll()
 */
class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<Integer> heap = new PriorityQueue<>((v1, v2) -> v2 - v1);
//        PriorityQueue<Day2Student> heap = new PriorityQueue<>((v1, v2) -> v2.getId() - v1.getId());
//        Day2Student s1 = new Day2Student(2);
//        Day2Student s2 = new Day2Student(3);
//        Day2Student s3 = new Day2Student(4);
//        heap.offer(s1);
//        heap.offer(s2);
//        heap.offer(s3);
//        s1.setId(10);
//        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((e1, e2) -> {
//            Integer v1 = e1.getValue();
//        });
    }
}

/**
 *  iterator + concurrent modification exception
 */
class IteratorTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        for(int i = 0, len = list.size(); i < len; i++) {
            list.add(5);
        }
        System.out.println(list);
//        for(int v: list) {
//            list.add(v + 2);
//        }
        Iterator<Integer> itr = list.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next());
            itr.remove();
        }
    }
}

/**
 *  thread vs process
 *
 */
class ThreadExample {
    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread());
        Thread t1 = new MyThread();
        Thread t2 = new Thread(() -> System.out.println(Thread.currentThread()));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

/**
 *  CPU1            ---> TIME LINE
 *      T1  -----           ------
 *      T2       -----
 *      T3                        -------
 *      T4             -----
 *      T5                               ------
 *
 */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread());
    }
}
/**
 *  Jvm
 *  Stack
 *      thread 1 to 1 stack
 *
 *  Heap
 *      Object / instance
 *      [ eden area ][s0][s1]  young gen (minor gc) STW
 *      [                   ]  old gen   (major gc) CMS / mark and sweep / G1
 *
 *      full gc = minor + major gc
 *
 *  memory leak
 *  out of memory
 *  stack over flow
 *
 *  synchronized
 *  volatile
 *  CAS
 */