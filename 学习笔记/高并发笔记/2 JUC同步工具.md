# 1 ReentrantLock

jdk基于自旋实现的可重入锁

## 1.1 reentrantLock优点：

1. tryLock 尝试获取锁，当方法带参数，则指定时间内返回，若方法不带时间参数，直接返回时候获取到锁的标志

2. lockInterupptibly  是否可以随时响应打断，lock方法只有在获取到锁后才能响应中断（synchronized不支持中断，对并发系统影响比较大）

lock.lock(); //必须等持有锁对象的线程做完事情，其他等待的线程才可以做事情。而且中途不能退出。 

lock.lockInterruptibly(); //也必须是等待持有锁对象的线程做完事情，其他线程才能做事情，但中途可以退出(被打断)。

3. 可以指定公平和非公平锁

4. cas操作（自旋），避免线程直接进入到内核的阻塞状态。synchronized升级到重量级锁后直接进入到阻塞状态。



## 1.2 reentrantLock注意点

lock方法和unlock方法必须放在try finally中，确保会释放锁，避免出现死锁现象。synchronized是在作用域方法执行完毕后自动释放锁（synchronized的优点）。



## 1.3 公平锁非公平锁原理：

1.非公平锁：锁自旋后会进入到block队列，新线程需要获取锁的时候可能正在自旋，自旋时可能直接先获取到锁，即使没自旋的线程，block队列中线程获取到锁的概率也要取决于调度器

2.公平锁：需要获取锁对象时，先看block队列中是否有等待队列，有的话直接进入等待队列，没有可以自旋。当其他线程释放锁后，将锁对象赋予block中最先等待的线程。



## 1.4 synchronized和ReentrantLock区别

1.synchronized是系统自带的，代码运行结束自动释放锁，而ReentrantLock需要手动释放锁

2.ReentrantLock支持Condition，支持唤醒不同的等待队列，synchronized不支持

3.ReentrantLock底层是cas，synchronized底层经历了4种锁状态的升级（无锁、偏向锁、自旋锁、重量级锁）



## 1.5 ReentrantReadWriteLock

当读操作明显多于写操作时，使用读写锁。

读锁：共享锁，多个不同的线程可以重入，提高了读的效率

写锁：排他锁，只有当前线程可以重入。

### 1.5.1 使用案例

```java
@Slf4j
public class Test4_ReentrantReadWriteLock {
    // 读写锁
    ReadWriteLock lock = new ReentrantReadWriteLock();

    // 读线程操作
    public void read(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            log.info("读线程");
            // 模拟读写操作耗时
            TimeUnit.SECONDS.sleep(1);
        } finally {
            lock.unlock();
        }
    }

    // 写线程操作
    public void write(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            log.info("写线程");
            TimeUnit.SECONDS.sleep(1);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 结论：当读多写少的时候，可以使用读写锁，提高效率
     * 读锁：是共享锁，当其他线程申请读的时候可以共享
     * 写锁：排他锁，其他线程申请写时，不允许其他线程操作（读操作也不允许，避免读到的值是未写完的值）
     *
     * @param args
     */
    public static void main(String[] args) {
        Test4_ReentrantReadWriteLock reentrantReadWriteLock = new Test4_ReentrantReadWriteLock();

        // 读写锁。
        // 读锁：是共享锁，当其他线程申请读的时候可以共享
        // 写锁：排他锁，其他线程申请写时，不允许其他线程操作（读操作也不允许）
        Lock readLock = reentrantReadWriteLock.lock.readLock();
        Lock writeLock = reentrantReadWriteLock.lock.writeLock();
        // 正常的lock锁是排他锁
        Lock normalLock = new ReentrantLock();
        // 启用18个线程进行读操作
        for (int i = 0; i < 18; i++) {
            new Thread(() -> {
                try {
                    // 正常的锁：读写操作都是每隔一秒输出
                    // 读锁：18个线程可以并发的读操作，提高效率
//                    reentrantReadWriteLock.read(normalLock);
                    reentrantReadWriteLock.read(readLock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 启用两个线程进行写操作
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    reentrantReadWriteLock.write(normalLock);
//                    reentrantReadWriteLock.write(writeLock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```



# 2 CountDownLatch

可等待其他线程都运行结束后才执行， 即使用lacth.await()方法需等门栓数为0才能放行

## 2.1 使用场景

可以用于多线程计算数据，最后合并计算结果的场景。

## 2.2 使用案例

```java
@Slf4j
public class Test2_CountDownLatch {

    /**
     * 结论：CountDownLatch使用场景：可等待其他线程都运行结束后才执行
     * 即使用lacth.await()方法需等门栓数为0才能放行
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        // 创建门栓
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                log.info("线程{}进行countdown操作", Thread.currentThread().getName());
                // 子线程对门栓进行减操作
                latch.countDown();
            }, String.valueOf(i + 1));
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        // 等待门栓数为0后才能放行
        latch.await();

        log.info("主线程结束");
    }
}
```

# 3 CyclicBarrier线程栅栏

所有线程都等待完成后，才执行下一步操作

## 3.1 使用场景

可以用于多线程计算数据，最后合并计算结果的场景。

## 3.2 CountDownLatch和CyclicBarrier区别

CountDownLatch 是一次性的，CyclicBarrier 是可循环利用的

## 3.3 使用案例

```java
@Slf4j
public class Test3_CyclicBarrier {

    /**
     * 结论：CyclicBarrier(循环栅栏)等待指定数量的线程完成后才执行后续操作
     *
     * @param args
     */
    public static void main(String[] args) {
        // 所有线程都等待完成后，才执行下一步操作
        // 第一个参数：要参与的线程数
        // 第二个参数：最后一个到达线程需要做的任务
        CyclicBarrier barrier = new CyclicBarrier(20, () -> log.info("线程{}到达，等待线程已满", Thread.currentThread().getName()));
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    log.info("线程{}到达栅栏", Thread.currentThread().getName());
                    // 表示当前线程已经到达栅栏,当指定数量的线程都达到后才能继续后续执行
                    barrier.await();
                    log.info("线程{}执行", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    // 表示栅栏已经被破坏，破坏的原因可能是其中一个线程 await() 时被中断或者超时
                    e.printStackTrace();
                }
            }, String.valueOf(i + 1)).start();
        }
    }
}
```

# 4 Semaphore信号量

## 4.1 使用场景

限流操作，例如有八辆车，两个收费站，八个车需要获取收费站准许后才能通过。

## 4.2 核心方法

semaphore.acquire();   获取准许，信号量减1

semaphore.release();    释放准许，信号量增1

## 4.3 注意事项

release() 方法需要放在finally中，确保会被执行到

## 4.4 使用场景

```java
@Slf4j
public class Test5_Semaphore {

    // 信号量，入参为准许的数量
    Semaphore semaphore = new Semaphore(1);

    public void run() {
        try {
            // 获取准许，获取到后，信号量减1，当信号量为0时，线程在此阻塞
            semaphore.acquire();
            log.info("线程{}开始执行", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(1);
            log.info("线程{}执行结束", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 信号量释放时，信号量自增，需放到finally中，确保会执行到
            semaphore.release();
        }
    }

    /**
     * 结论：当时限流的场景时可以使用semaphore（同时允许几个线程并发操作）
     *
     * @param args
     */
    public static void main(String[] args) {
        Test5_Semaphore test5_semaphore = new Test5_Semaphore();
        // 启动两个线程，可通过信号量为1和2，查看输出区别
        // 信号量为1：另外一个线程需要等第一个线程释放掉后才能执行，因其会阻塞到acquire()方法
        // 信号量为2：两个线程可以并发执行，两个线程都可以acquire到
        new Thread(() -> test5_semaphore.run()).start();
        new Thread(() -> test5_semaphore.run()).start();
    }
}
```

## 4.5 Semaphore与Lock的区别



# 5 ThreadLocal

## 5.1 作用

threadlocal中的变量实际是存储到了每个线程对象的threadLocals属性中（属性是个map对象，key为threadlocal对象，key是弱引用，value为存入的值），因此可以说每个线程中的变量是各自独享的



## 5.2 使用场景

每个线程需要各自的变量时或者用于线程上下文传递（当前线程set进去的，只能当前线程拿出来），可以使用threadlocal对象。如elg服务的每个线程都需要签署不同的协议，将协议放在threadlcoal中，签署后最后才从threadlocal中获取到需要推送的协议数组，然后推送出去。



## 5.3 注意

threadlocal可能存在内存泄漏，每次使用完必须调用remove()方法



## 5.4 内存泄漏原因

1、若threadlocal对象一直有强引用存在，则垃圾回收器就一直无法回收map中的对象

2、map中的value一直是强引用，如果不手动remove调，垃圾回收器没法回收value对象

![image-20201225002926186](https://cdn.jsdelivr.net/gh/lxj53/markdownPictures@main/img/20201225002933.png)

## 5.5 强软弱虚引用

### 5.5.1 强引用

强引用就是通常的引用   如：Integer i = new Integer();

### 5.5.2 软引用

当内存空间不够时，软引用对象会被垃圾回收器回收

#### 5.5.2.1 使用场景

做缓存，可以从硬盘中读取一个大文件，然后缓存到内存中，当内存够用时，就从内存中读取（速度快），当内存不够用时，jvm虚拟机可以回收（充分利用内存资源）

#### 5.5.2.2 注意

日常开发基本不用，因为一般有第三方的缓存，如redis，因此无需自行实现。

#### 5.5.2.3 使用场景

```java
@Slf4j
public class Test9_1_SoftReference {

    /**
     * 结论：软引用的对象在内存不够用时，会进行自动回收
     * 案例前提：需要手动指定堆内存大小 -Xms25m -Xmx25m
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建10M的对象
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println("创建软引用对象:" + m.get());
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("手动gc后:" + m.get());
        // 再创建10M的内存大小(应该是直接进入了老年代，导致老年代大小不够用，后期可通过jstat指令查看)
        // 导致内存不够用，回收软引用的对象
        byte[] bytesNew = new byte[1024*1024*10];
        System.out.println("创建大对象后:" + m.get());
    }

}
```



### 5.5.3 弱引用

#### 5.5.3.1 作用

弱引用的对象，遇到java虚拟机gc，对象就会被回收

#### 5.5.3.2 创建的弱引用对象，每次gc都会被回收掉，那么有什么使用场景？

用在容器中，只要弱引用的对象，有其他强引用失效，则对象就该被回收。可以看WeakHashMap

#### 5.5.3.3 使用案例

```java
@Slf4j
public class Test9_2_WeakReference {

    /**
     * 结论：弱引用对象遇到垃圾回收就会被回收
     * 注意：软、弱、虚引用都需要通过get()方法获取对象
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        WeakReference<byte[]> m = new WeakReference<>(new byte[1024*1024*10]);
        System.out.println("创建弱引用对象:" + m.get());
        System.gc();
        // 暂停一秒，确保会触发垃圾回收器回收
        TimeUnit.SECONDS.sleep(1);
        // 此时get方法得到的到null，因为会被回收掉
        System.out.println("手动gc回收后:" + m.get());
    }
}
```



### 5.5.4 虚引用

#### 5.5.4.1 作用

1、虚引用get不到对象（指的是开发人员，写jvm的底层人员还是可以获取到的）

2、当对象回收时，会被扔到队列中，可以手动从队列中取对象，从而用于回收堆外内存

#### 5.5.4.2 场景

jvm开发人员、netty使用堆外内存时，用于回收堆外内存（因为堆外的内存，垃圾回收器无法自动回收）。日常开发基本不用

#### 5.5.4.3 使用案例

```java
@Slf4j
public class Test9_3_PhantomReference {
    /**
     * 总结：虚引用可以从队列中获取对象(类似一个标志)，当收到标志后可进行相应的操作，如回收堆外内存
     * 注意：虚引用必须要和引用队列一起使用，他的get方法永远返回null (只有框架底层人员可以获取到)
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();

        // 虚引用必须要和引用队列一起使用，他的get方法永远返回null，只有框架底层人员可以获取到
        PhantomReference<byte[]> phantomReference = new PhantomReference<>(
                new byte[1024 * 1024 * 5], queue);

        System.out.println("虚引用:" + phantomReference.get());

        System.out.println("队列获取:" + queue.poll());

        // 调用gc,回收虚引用对象
        System.gc();

        // 等待一秒，确保虚引用对象会被回收
        TimeUnit.SECONDS.sleep(1);

        // 从队列中拿值，有值后，可以进行堆外内存操作
        System.out.println("gc后从队列获取:" + queue.poll());
    }
}
```



# 6 Exchanger

## 6.1 使用场景

exchanger用于线程间通信交换数据

## 6.2 注意事项

exchange()方法是阻塞方法

## 6.3 使用案例

```java
@Slf4j
public class Test6_Exchanger {

    // 定义交换的容器
    static Exchanger<String> exchanger = new Exchanger<>();

    /**
     * 结论：exchanger用于线程间通信交换数据，exchange()方法是阻塞方法
     *
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            String s = "T1";
            try {
                // 此处线程会阻塞，直到有其他线程来交换，将交换后的值返回
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程{}输出{}", Thread.currentThread().getName(), s);
        }, "t1").start();

        new Thread(() -> {
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程{}输出{}", Thread.currentThread().getName(), s);
        }, "t2").start();
    }
}
```

# 7 LockSupport

## 7.1 作用

通过park 和 unpark方法阻塞和唤醒线程，以前只能通过先获取到锁对象，然后wait 或者 await

## 7.2 区别

1. wait 和 await方法需要先获取到锁（需要在锁的代码块中调用），park方法不需要获取锁

2. unpark方法可以唤醒指定的线程，而notify/notifyAll不能唤醒指定的线程，只能唤醒阻塞队列中的随机线程/所有线程

3. unpark方法可以先与park方法方法执行（可以提前抵消一次park操作），而notify不能先于wait方法执行

## 7.3 使用案例

```java
@Slf4j
public class Test7_LockSupport {

    /**
     * 结论：LockSupport可阻塞线程和唤醒指定的线程，以前只能通过先获取到锁对象，然后wait 或者 await
     * 区别：
     * 1. wait 和 await方法需要先获取到锁（需要在锁的代码块中调用），park方法不需要获取锁
     * 2. unpark方法可以唤醒指定的线程，而notify/notifyAll不能唤醒指定的线程，只能唤醒阻塞队列中的随机线程/所有线程
     * 3.unpark方法可以先与park方法方法执行（可以提前抵消一次park操作），而notify不能先于wait方法执行
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    // 线程阻塞，等待其他线程唤醒
                    LockSupport.park();
                }
                log.info(String.valueOf(i));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        // 主线程唤醒t线程
        LockSupport.unpark(t);
    }
}
```

