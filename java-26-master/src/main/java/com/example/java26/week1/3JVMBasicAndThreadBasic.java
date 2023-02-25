package com.example.java26.week1;

import java.util.concurrent.CountDownLatch;

/**
 *  Stack
 *      thread 1 to 1 stack
 *
 *  Heap
 *      Object / instance
 *      [ eden area ][s0][s1]  young gen (minor gc) STW
 *      [                   ]  old gen   (major gc) CMS / mark and sweep / G1
 *
 *      CMS
 *      1. initial mark (STW)
 *      2. concurrent mark
 *      3. final mark (STW)
 *      4. concurrent remove
 *
 *      G1
 *      [][][Y][Y][][O][][]
 *      [][][][][O][][][H]
 *      [][][][O][][O][][]
 *      [][][][][][][][]
 *      [][][][][][][][]
 *
 *
 *      full gc = minor + major gc
 *
 *      out of memory
 *          1. restart application
 *          2. use diff reference type
 *              StrongReference
 *              SoftReference
 *              WeakReference
 *              PhantomReference + Reference Queue
 *          3. change jvm parameters
 *          4. heap dump + analyze memory leak
 *              JProfiler / Memory Analyzer / Java Mission Control
 *              static ConcurrentHashMap
 *              connection
 *
 *      stack over flow
 *     *     *     *     *     *     *     *     *     *     *     *     *     *
 *  Thread
 *                      Blocked (enter / exit consume CPU resource)
 *                      |
 *    sleep()     -    [runnable  ->  running]  - wait() / await() / LockSupport - Wait
 *                                       |
 *                                    terminate
 *
 *                   int num = 1
 *                T1 -> get lock -> if(num is odd)  -> print / i++
 *
 *                T2 -> get lock ->  if(num is even) -> print
 *
 *                blocking queue
 *        producer -> [][][][][] -> consumer
 *
 *  synchronized + wait() / notify()
 *
 *  T1: Obj1 -> wait() -> T1
 *  T2: Obj2 -> wait() -> T2
 *
 *  T1: Obj1 -> wait() -> T1
 *  T2: Obj1 -> wait() -> T1 -> T2
 */
class SynchronizedTest {
    public synchronized void func1() {
//        synchronized (this) {
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        }
    }


    public synchronized static void func2() {
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{
        SynchronizedTest s1 = new SynchronizedTest();
        SynchronizedTest s2 = new SynchronizedTest();
        Thread t1 = new Thread(() -> s1.func1());
        Thread t2 = new Thread(() -> func2());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

/**
 *
 *      CPU1            CPU2           CPU3
 *      L1              L1              L1
 *      L2              L2              L2
 *      L3              L3              L3
 *
 *
 *          Main memory  x = 12
 *
 *    volatile int x = 10;
 *    synchronized void increment() {
 *        /
 *        . x = 10
 *        .
 *        .  x *= 10
 *        .
 *        .  x -= 10
 *        /
 *    }
 *
 *
 *    void print() {
 *        System.out.println(x);
 *    }
 *
 *    main() {
 *      T1 : increment()
 *      T2 : increment()
 *      T3 : print()
 *    }
 *
 *    T1 increment() {
 *        x++;
 *    }
 *
 *    T2 increment() {
 *
 *    }
 *
 *
 */

/**
 *   int x = 10;
 *   T1 -> x++
 *   T2 -> x++
 *   T3 -> x = 12
 *   *    *    *   *    * *   *    * *   *    *
 *  volatile
        1. happen before rule
 *              --------- --------- --------- > timeline
 *              get  write(x = 10)   get    get
 *      2. read from main memory (visibility)
 */
class VolatileTest1 {
    private static volatile int x = 10;

    public static void inc() {
        /**
         * x = 10
         */
        x = 20;
        /**
         * x = 20
         */
    }
}

class VolatileTest2 {
    private static int a = 0;
    private static boolean b = true;

    public static void update() {
        //hoist b = false; reorder
        a = 10;
        b = false;

        /**
         *   if(b == false)
         *      100% a==10
         */
    }

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> update());
        Thread t2 = new Thread(() -> {
            if(b == false) {
                System.out.println(a); //a = 10 / a = 0
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}


class VolatileTest3 {
    private volatile static boolean flag = true;


    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            while(flag) {}
            System.out.println("print t1 out of while loop: " + flag);
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {}
            flag = false;
            System.out.println("t2 flag: " + flag);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

/**
 *  ConcurrentHashMap
 *  public void update() {
 *      put(k1, v1);
 *                               -> get(k1) = v1 , get(k2) = null
 *      put(k2, v2);
 *  }
 *
 *  ConcurrentHashMap
 *  public void update() {
 *      for(List<Integer> id: stus) { //10 students
 *          map.put(id, v);
 *      }
 *      t1: map.put(id1, v);
 *      t2: map.put(id2, v);
 *      t3: map.put(id3, v);
 *      ..
 *      t10: map.put(id10, v);
 *  }
 *
 *  public void get() {
 *      map.iterator() => getAll() -> 5 entries
 *  }
 *
 *  HashMap
 *  public synchronized void update() {
 *      put(k1, v1);
 *      put(k2, v2);
 *      put(k1, v2);
 *      put(k1, v3);
 *  }
 *  public synchronized get() {
 *
 *  }
 *                           -> get(k1) = v1 , get(k2) = v2
 *
 * concurrent hashmap
 * public void update() {
 *   cMap.compute(k, () -> {});
 * }
 */

/**
 *  synchronized / volatile / CAS
 *                          volatile / CAS
 *                            /
 *          AbstractQueuedSynchronized
 *          /                   \
 *  ReentrantLock       CountDownLatch ..
 *        +
 *   Condition
 *       /
 *  blocking queue
 *      /+thread
 *   Thread Pool (diff thread pools + how to decide thread number /count)
 *
 *                        synchronized / volatile / CAS
 *                           |                  \
 *                     concurrent hashmap       copy on write list
 *
 *
 *
 *                                  synchronized
 *                         /             \               \
 *                       hashtable   string buffer       vector
 *
 *
 *                              CAS  / volatile
 *                                  |
 *                               Atomic Library
 *
 *
 */

class SynchronizedNonSyncValue1 {
    private static boolean flag = false;
    public static synchronized void func1() {
        while(!flag) {

        }
        System.out.println("break");
    }

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> func1());
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println(flag);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

class SynchronizedNonSyncValue2 {
    private static int v = 0;
    private static CountDownLatch latch = new CountDownLatch(1);

    public synchronized static void inc() {
        for(int i = 0; i < 10000; i++) {
            v++;
            if(i == 50) {
                latch.countDown();
            }
        }
    }
    /*
        Non-volatile changes won't be visible is not 100% true; it just no guarantee
        On the X86, caches are always coherent. So if CPU1 executes a store on address A and CPU2 has the cache line containing A, the cache line is invalidated on CPU2 before the store can commit to the the L1D on CPU1. So if CPU2 wants to load A after the cache line has been invalidated, it will run into a coherence miss and first needs to get the cache line in e.g. shared or exclusive state before it can read A. And as consequence it will see the newest value of A.
        So volatile loads and stores have no influence on caches being coherent. On the X86 it will not happen that on CPU2 the old value is loaded for A, after CPU1 committed A to the L1D.
        The primary purpose of volatile is to prevent reordering with respect to other loads and stores to other addresses.
        https://stackoverflow.com/questions/63874879/why-non-volatile-variable-is-updated-on-cpu-shared-cache
        https://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html
        https://gee.cs.oswego.edu/dl/jmm/cookbook.html

        java memory model
            cpu could copy values from main memory to cpu local caches(L1, L2, L3), and values are not visible to other cpus

            volatile
                a. happen before (by using barrier)
                   prevent reordering (by using barrier)
                b. visibility (always read from main memory, update main memory when change the value)
            synchronized
                a. happen before
                b. put monitor on object (scope is object)
                c. write cpu cache back to main memory
                d. need to put thread into blocked status, and need extra time on thread scheduling
            CAS
                a. atomic instruction, very fast
                sometimes faster than synchronized


        x86 processor
            (not 100% sure) memory barrier will flush or drain dirty data / cpu caches to main memory
            volatile
                a. mfence instruction / or other instructions (StoreLoad / full barrier)
                   prevent reordering / happen before
                b. (not 100% sure) x86 follows cache coherence protocol, any changes to cache (volatile or non-volatile variables)
                   will invalidate related cache of other Cpus
                c. cpu will cache volatile values until other cpu send invalidation signal, then it will fetch newest value into cpu local cache.
            synchronized
                a. use "Lock" prefix instruction to lock cache (StoreLoad / full barrier)
            CAS
                a. use "Lock" prefix instruction to lock cache (StoreLoad / full barrier)
    */
    public static void print() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(v);
    }

    public static void main(String[] args) throws Exception {
        Thread t2 = new Thread(() -> inc());
        Thread t1 = new Thread(() -> print());

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}