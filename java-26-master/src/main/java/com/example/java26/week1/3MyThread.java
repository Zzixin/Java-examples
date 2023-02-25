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
 *       GC记一下概念，parallel，new
 *       CMS:
 *       1. initial mark (STW)
 *       2. concurrent mark
 *       3. final mark (to make sure every execution is fine.
 *       4. concurrent remove
 *
 *       [xxx][   STW   ][xxx][    STW   ]
 *       CMS尝试吧STW分开
 *
 *       G1:
 *       [][][][][][][][]
 *       [][][][][][][][]
 *       [][][][][][][][]
 *       may contain young gen, old gen, big object, small object
 *       会回收BLOCK,YOUNG GEN, OLD GEN, OBJECT，将所有东西DOWN到另一个BLOCK,再清空BLOCK
 *       每次会拿起一定量的OLD GEN
 *
 *
 *       full gc = minor + major gc
 *
 *       old gen is full, CMS will be triggered -> out of memory
 *
 *       out of memor：
 *          1. restart application
 *          2. use different reference type
 *                   StrongReference 一旦创建，就不会被回收。内存再满都不会
 *                   SoftReference JVM快满了，即将out of memory,优先回收所有 soft reference指向的instance
 *                   WeakReference 并不知道什么时候被回收，如果被JVM扫到，就继续存在。不确定的instance
 *                   PhantomReference + Reference Queue 记录被回收的一个状态，不想加载非常大的INSTANCE,当一个大的INSTANCE被
 *                                                       加载回收了，才会继续加载第二个
 *          3. change jvm parameters
 *          4. 啥的都没有办法改变了
 *             heap dump + analyze memory leak
 *             HEAP DUMP - 知道里面有什么OBJECT, INSTANCE
 *             用于解析heap dump: JProfiler / Memory Analyzer / Java Mission Control
 *             ANALYZE MEMORY LEAK - 有些资源理应被回收，但是实际上并没有被回收
 *                   eg: static ConcurrentHashMap, 它越来越大，HEAP空间慢慢变小，但是并不会触发GC，
 *          5. connection, connection会一直存在如果不进行操作
 *             like buffer read, finally we need to close the connection
 *             note: Don't write anything in the finalize() function! It only make sense to jvm
 *
 *
 *       stack over flow
 *          一旦内存达到了上限，会出现STACK OVER FLOW。写recursion时候可能出现
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
 * Thread
 *    every thread is independent
 *    every several threads share data, must ensure thread safe
 *       using synchronized, (read and write both need to synchronized)
 *       using volatile, better to add synchronized to all methods(get, print...)
 *
 *    extends, overwrite, create
 *             Blocked (enter / exit consume CPU resource) 一旦进入BLOCKED状态，不会消耗CPU资源，不会让它做事情
 *             /
 *  sleep() -  [runnable -> running] - wait() / await() / LockSupport - Wait
 *                            /
 *                         Terminate
 *      对于进入BLOCK,我们不能手动操作，是CPU自己操作的
 *      对于进入WAIT()，我们可以手动操作
 *      当进入到WAIT，线程都有一定可能自动醒来
 *      进入到sleep,也不会消耗CPU资源
 *      使用WAIT, CPU会释放锁
 *      SLEEP: 不想释放锁，又不想让线程继续运行。还要再干些什么的时候
 *      WAIT:不想让线程继续运行了,不会在增加负荷在锁上
 *                int run
 *           T1 -> get lock -> if (num is odd) -> print / i++
 *           T2 -> get lock -> if (num is even) -> print
 *
 *           blocking queue (here can use wait)
 *      producer -> [][][][][] -> consumer
 *
 *       unfair log - 插队到第一个运行，lock
 *
 *
 *
 * synchronized + wait() / notify()
 *       加锁  moniter
 *       对象/scope/target：Object/instance !!!!!!!important
 */
class SynchronizedTest {
   public void func1(){
      synchronized (this) {
         // 只有一个对象能拿到锁？
         try {
            Thread.sleep((3000));
            System.out.println((Thread.currentThread()));
         } catch (Exception e){
            e.printStackTrace();
         }
      }
   }

//   // 这样子写和上面的相同，就是SYNCHRONIZED放在BLOCK里还可以在上下加一些东西
//   public synchronized void func1(){
//         try {
//            Thread.sleep((3000));
//            System.out.println((Thread.currentThread()));
//         } catch (Exception e){
//            e.printStackTrace();
//         }
//      }
//   }

//   // 并不确定哪一个THREAD会先进入BLOCK,
//   public static void main(String[] args)  throws  Exception{
//      SynchronizedTest s = new SynchronizedTest();
//      Thread t1 = new Thread(() -> s.func1());
//      Thread t2 = new Thread(() -> s.func1());
//      t1.start();
//      t2.start();
//      t1.join();
//      t2.join();
//   }

   // class object
   public synchronized static void func2() {
      try {
         Thread.sleep((3000));
         System.out.println((Thread.currentThread()));
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * T1: Obj1 -> wait() -> T1
    * T2: Obj2 -> wait() -> T2
    *
    * T1: Obj1 -> wait() -> T1
    * T2: Obj1 -> wait() -> T1 -> T2
    */
   public static void main(String[] args)  throws  Exception{
      SynchronizedTest s1 = new SynchronizedTest();
      SynchronizedTest s2 = new SynchronizedTest();
      //用FUNC2，两个THREAD是同时执行的
      Thread t1 = new Thread(() -> s1.func1());
      Thread t2 = new Thread(() -> s2.func1());
//      Thread t1 = new Thread(() -> s1.func2());
//      Thread t2 = new Thread(() -> s2.func2());
      t1.start();
      t2.start();
      t1.join();
      t2.join();
   }
}

 /**
 * volatile
  * when only get() without write(), volatile is very good to ensure the consistency, avoid reorder
  *   1. happen before rule
  *            ------- ------ ------ > timeline
  *            get  write(x = 10) get  get
  *      the get after write can definitely get the latest x (the x after write operation)
  *   2. Thread safe ? No!
  *            CPU: read x, update x, write x
  *            CPU2: read x, up
  *   3. read from main memory (visibility)
  *   一旦一个数据在一个CPU中被改变，会通知其他的CPU数据INVALID, 进而更新数据
  * add volatile, to ensure the current x is the latest
  * volatile int x = 10;
  * // add volatile, 在这个时间点上读到的数据都是最新的数据
  *
 *
 */
// not thread safe
 class VolatileTest {
    private static volatile int x = 10;
    public static void inc() {
       x++; // has several operations, not thread safe
       x = 20; // don't mind what x was, can seem as thread safe
    }
 }

 class VolatileTest2 {
    private static int a = 0;
    private static boolean b = false;

    // 在没有锁的情况下，UPDATE并不一定会在thread之前运行
    public static void update() {
       // hoist b = false; reorder drain memory
       a = 10;
       // barrier 如果B加了volatile,前后都会有barrier，就不会出现A=0的情况
       b = false;
       // barrier
       /**
        *  if (b == false)   100% sure -> a == 10 (using volatile)
        */
    }
    public static void main(String[] args) {
       // Thread t1 = new Thread(() -> update());
       Thread t1 = new Thread(() -> update());
       Thread t2 = new Thread(() -> {
          if (b == false) {
             System.out.println(a); // a = 18 / a = 0
             // 如果没有加VOLATILE BOOLEAN B = FALSE, 那么可能会先运行B = false，
             // 然后T2直接运行(B== FALSE), 此时如果A还没有赋值，PRINT结果就是0
          }
       });
    }
 }

class VolatileTest3 {
    private static boolean flag = true; // non thread safe
    private static volatile boolean flag2 = true; // thread safe, once flag is changed, everyone knows
    Thread t1 = new Thread(() -> {
       while (flag) {

       }
    })
}

/**
 * ConcurrentHashMap: thread safe
 * // only to make sure single put/get is thread safe, once combined together, not thread safe!
 * public void update() {
 *    put(k1, v1);
 *                          -> get(k1) = v1 , get(k2) = null // not matter when implement, need most resent data (current data)
 *    put(k2, v2);
 * }
 *
 *
 * ConcurrentHashMap
 * public void update() {
 *    for(List<Integer> id : students) { // 10 students
 *       map.put(id, v);
 *    }
 *    t1: map.put(id1, v);
 *    t2: map.put(id2, v);
 *    t3: map.put(id3, v);
 *    ....
 *    t10: map.put(id10, v);
 * }
 * // don't when users will get(), not sure if the data is written to CPU when user read
 * // to ensure get() is after the whole update() operation, we need to add synchronized
 * public void get() {
 *    map.iterator() => getAll() -> 10 entries
 * }
 *
 *
 * HashMap
 * public synchronized void update() {
 *    put(k1, v1);
 *    put(k2, v2);
 *    put (k1, v2);
 *    put(k1, v3);
 *    //将上述操作视作一个整体，不能简单地用VOLATILE
 * }
 * public synchronized get() {
 *
 * }
 *                           -> get(k1) = v1, get(k2) = v2
 *
 *
 *  concurrent hashmap
 *  // every methods is thread safe, combine them together may not be thread safe
 *  public void update() { // update - not thread safe
 *     int cnt = cMap.getOrDefault(k, v);//get - thread safe
 *     // there is blank time between the codes, not sure what will happen
 *     // to make thread safe, add synchronized or use compute
 *     // cMap.compute(k, () -> {});
 *     cMap.put(k, cnt + 1); // put - thread safe
 *  }
 */


/**
 *       CPU1
 * synchronize block每次只让一个CPU读取代码
 *    int x = 10; // no Volatile
 *    synchronized void increment() {
 *       x++;  =>  x = 11
 *                         T3: print() : x = 11 ?
 *       // no volatile: T3 is not sure, when print() may be executed before x++ and x won't be wrote to CPU until the block is end
 *       // if volatile is enabled, x == 1
 *    }
 *
 *    // if print() is synchronized, no need to add volatile
 *    // if print() is not synchronized, need to add volatile
 *    void print() {
 *       System.out.println(x);
 *    }
 *    // 问题：顺序不确定，print 不一定会最后运行
 *    // T3 can first sleep, then read the data
 *    {
 *       T1: increment();
 *       T2: increment();
 *       T3: print();
 *    }
 */
// Java Memory Model

/**
 * synchronized / volatile/ CAS
 *
 *       AbstractQueueSynchronized (based on  volatile/ CAS)
 *          - ReentrantLock + condition
 *             - blocking queue + thread
 *                - Thread Pool
 *          - CountDownLatch
 *
 *       synchronized / volatile / CAS
 *          /                 \
 *     concurrent hashmap     copy on write list
 *
 * synchronized
 *    hashtable
 *    string buffer
 *    vector
 *
 * CAS/volatile
 *    Atomic Library
 *
 *
 * message queue
 * producer -> blocking queue -> consumer
 */







