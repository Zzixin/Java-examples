package com.example.java26.week1;

/**
 *  CAS: compare and swap
 *  faster than synchronized, 消耗CPU资源，但不会对CPU进行调度。thread in block list
 *  atomic integer很快
 *
 *  like an instruction, can only be executed once
 *
 *  CAS(obj reference address, field reference, expected value, new value)
 *  tell obj which field to choose
 *
 *  // thread safe, 是一个操作, an atomic instruction
 *  if(obj.field == expected value) {
 *      obj.field = new value;
 *      return true // if success
 *  } else {
 *      return false;
 *  }
 *
 *  Atomic Library
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. volatile int id + synchronized increment()
 *      synchronized: 只有同一个thread可以访问/改变这段代码
 *      volatile: 读取时most recent
 * 2. synchronized increment() + synchronized get()
 * 3. atomic integer - faster than synchronized
 */
class IncrementIdGenerator {
    // no need to add volatile, we won't modify id
    // volatile只对instance有作用，no effects on the attributes of instance
    private final AtomicInteger id; // thread safe

    public IncrementIdGenerator() {
        this(0);
    }

    public IncrementIdGenerator(int val) {
        this.id = new AtomicInteger(val);
    }

    public void increment() {
        id.incrementAndGet();
    }

    public int get() {
        return id.get();
    }
}
/**
 * thread safe library / function
 * synchronized 可以重复加锁
 *  synchronized
 *      1. unfair lock, can not achieve fair lock
 *      2. obj -> waiting, only 1 waiting list
 *         notifyAll()
 *         notify() don't know which thread is awakened
 *      3. try synchronized(obj)  cannot not leave once in the list, 没有时间戳设定
 *      4. func1() {
 *              synchronized() {
 *              // only have effect in this block
 *
 *              }
 *         }
 *
 *         // CANNOT release synchronized() of func1 in func2
 *         func2() {
 *             release
 *         }
 *
 *  ReentrantLock {
 *  上面synchronized不能实现的reentrantLock可以实现
 *  has good performance as synchronized, but has more functions
 *      state = 0 : unlock
 *      state >= 1 : locked
 *      current thread owner : thread reference
 *      condition : waiting list
 *
 *      lock() / unlock()
 *      tryLock(time)
 * }
 *
 * AbstractQueuedSynchronizer : Queue(LinkedList)  // a thread LinkedList
 *      fair lock - 先进队列，再去拿锁 Node(T1) -> Node(T2) -> Node(T3)
 *                                              同时指向tail node
 *                                              只能有一个运行成功，其他返回FALSE,在进入一个新的循环
 *                                          <-  N5   CAS(tail, expected Node(T3), N5)
 *      Node(T1) <-> Node(T2) <-> Node(T3)  <-  N4   CAS(tail, expected Node(T3), N4)
 *      head                      tail      <-  N6   CAS(tail, expected Node(T3), N6)
 *
 *      why infinite for loop? sometimes unfair lock, a new thread will get the lock.
 *
 * FV lock = new ReentrantLock();
 * lock.newCondition(); // get a waiting list
 * lock.newCondition();
 *     *     *     *     *     *     *     *     *     *     *     *     *
 *  ReentrantLock:
 *  BlockingQueue
 *     producer -> [][][][][][][][][][] -> consumer
 *     producer: 往QUEUE里塞东西的thread
 *
 *    consumer poll() {
 *        lock();
 *        while(queue is empty) {
 *            current thread / consumer await();
 *        }
 *        take(e)
 *        notify producer
 *        // synchronized cannot notify a specific producer
 *        unlock();
 *    }
 *
 *
 *    producer offer(e) {
 *        lock();
 *        while(queue is full) {
 *            current thread / producer await();
 *        }
 *        add(e) into queue
 *        notify consumer
 *        unlock();
 *    }
 */
// no need to add volatile, all codes are in the lock()
class MyBlockingQueue<T> {

    private final Object[] queue;
    private int size;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fullWaitingList = lock.newCondition();
    private final Condition emptyWaitingList = lock.newCondition();
    private int startIndex;
    private int endIndex;

    public MyBlockingQueue(int size) {
        this.queue = new Object[size];
    }

    public T poll() throws Exception {
        lock.lock();
        // lock() cannot be put in try, once there's an exception, 就可能没加上锁，error happened in finally
        try {
            while(size == 0) {
                emptyWaitingList.await();
            }
            Object res = queue[startIndex++];
            if(startIndex == queue.length) {
                startIndex = 0;
            }
            size--;
            fullWaitingList.signal();
            return (T)res;
        } finally {
            lock.unlock();
        }
    }

    public void offer(T ele) throws Exception {
        lock.lock();
        try {
            while(size == queue.length) {
                fullWaitingList.await();
            }
            queue[endIndex++] = ele;
            if(endIndex == queue.length) {
                endIndex = 0;
            }
            emptyWaitingList.signal();
            size++;
        } finally {
            lock.unlock();
        }
    }
}
/**
 *  How to manage threads? --- Thread Pool,不会被回收，只能手动shutdown
 *  Thread Pool (diff thread pools + how to decide thread number /count)
 *  ThreadPoolExecutor(
 *      // thread poll size [corePollSize, maxPollSize]
 *      int corePoolSize, // 至少维持这些个THREADS
 *      int maxPoolSize,
 *      int idleTime, // idleTime里没反应就移除
 *      TimeUnit unit, // 时间单位，hour/second
 *      BlockQueue queue, // put tasks in blockQueue
 *      ThreadFactory factory //
 *  )
 *
 *     producer -> ([][][][][][] blocking queue  ->  worker thread) Thread pool
 *
 *     1. fixedThreadPool : core == max
 *     2. cachedThreadPool: core != max
 *     3. scheduledThreadPool: delayedWorkQueue
 *       thread1
 *       thread2(wait 3s) -> [task1(3s)][task3(5s)][task5(t3)]
 *       after 3s, thread2 is awakened to do task1
 *       after 2s, the other thread is awakened to do task3
 *       thread3
 *
 *       time wheel
 *       时间在哪里，就执行什么任务，P1移动，就把当前MINUTE里的任务DOWN GRADE 到SECOND里面
 *       [0][1][2][..][59] minute
 *             |
 *            p1
 *
 *       [0][1][2][..][59] second
 *        |
 *       p2
 *
 *  ForkJoinPool (stealing algorithm) java7, big task split->into many small tasks
 *  用在小的TASK,同时又可以分成一个个小的SUBTASK.
 *  如果每个TASK运行时间很长，又没有办法将TASK通过代码分成小的SUBTASK, thread pool execute is enough
 *
 *     [][][][][][][][][][][] -> T1 [x1][x2][x3][x4]
 *                               T2 [][][][] T2 will steal some small tasks like x4, then return the result to T1
 *    *    *    *    *    *    *    *    *    *    *    *    *    *    *
 * thread pool thread number
 *    CPU / service / calculator based task :  core number + 1
 *    (IO + service) task : n * ( 1 / (1 - IO percentage)) + 1        n: # of cores
 *    50%   50%
 *
 *    situations may vary in the reality, we can just use cash thread poll
 */
class ThreadPoolTest {
    // put different in the same pool
    private final static ExecutorService pool = Executors.newCachedThreadPool();

    public static void get() throws Exception {
        // if ExecutorService pool is here, the pool is only for get(), too many pools

        // future:当拿到结果之后，才会继续运行
        // future: like a listener
        // callable has a return value
        Future<Integer> f = pool.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        });

        // 上面程序运行时，下面的代码也会被block
        System.out.println(f.isDone()); // false
        // 谁调用了GET, 谁就会被BLOCK
        int res = f.get();
        System.out.println(res);
    }

    public static void main(String[] args) throws Exception {
        get();
        get();
    }
}

/**
 *  deadlock
 *
 *      T1 : holding lock A {
 *          try to lock B {
 *
 *          }
 *      }
 *
 *      T2 : holding lock B {
 *          try to lock A {
 *
 *          }
 *      }
 *
 *   how to solve?
 *   1. timeout, try lock
 *   2. lock in order
 *   3. look up table // make notes, t1-A, t2-B, looking up the locks being held
 */

/**
 * synchronized / ReentrantLock 只允许一个线程访问一个block
 *  semaphore (permit)  让多个线程访问BLOC，同一时间可以有permit个线程持有锁
 *      example
 *          permit = 4
 *          T1 acquire permit = 3
 *          T2 acquire permit = 2
 *          T2 release permit = 3
 *      when permit = 0, waitinglock()
 *  countdownlatch
 *
 *  悲观锁 Pessimistic lock 上面讲的都是
 */
class CountDownLatchDemo {
    private static final CountDownLatch latch = new CountDownLatch(2);
    public synchronized static void func1() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println(Thread.currentThread() + ", count down");
    }
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" this is t1");
        });
        Thread t2 = new Thread(() -> func1());
        Thread t3 = new Thread(() -> func1());
        t1.start();
        t2.start();
        t3.start();

        t1.join();
    }
}
/**
 *
 *  cyclicbarrier
 *    *    *    *    *    *    *    *    *    *    *    *    *
 *
 *      read lock + write lock == share lock + exclusive lock
 *
 *      share lock only blocks ex lock (exclusive lock)
 *      ex lock blocks share lock + ex clock
 *
 *       *    *    *    *    *    *    *    *    *    *    *
 *
 *   abstract queued synchronized
 *   how to impl reentrant lock
 *   x86 / hardware
 *   how does g1 work
 *   treemap source code
 *
 *    *    *    *    *    *    *    *    *    *
 *    Jan 2nd
 *     1. java 8 stream api / parallel stream / completable future
 *     2. sql / index...
 */
