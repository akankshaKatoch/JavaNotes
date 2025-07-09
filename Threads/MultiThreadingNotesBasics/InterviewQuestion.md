# Java Multithreading Interview Questions

---

## Question 1: What is the difference between `start()` and `run()` methods?

**Answer:**  
- The `start()` method begins thread execution and internally calls the `run()` method.  
- The `run()` method contains the code to be executed by the thread.  
- Directly calling `run()` does **not** create a new thread; it executes in the current thread.

**Example:**

```java
class MyThread extends Thread { 
    public void run() { 
        System.out.println("Thread running: " + Thread.currentThread().getName()); 
    } 
} 

public class Main { 
    public static void main(String[] args) { 
        MyThread t1 = new MyThread(); 
        t1.start();  // Starts a new thread 

        MyThread t2 = new MyThread(); 
        t2.run();    // Runs in the main thread 
    } 
}
```

> **Note:** Always use `start()` to utilize multithreading. Calling `run()` directly will not create a new thread.

---

## Question 2: Can we call the `start()` method twice on the same Thread object?

**Answer:**  
No, calling `start()` twice on the same Thread object will throw an `IllegalThreadStateException`.  
A thread that has completed execution cannot be restarted.

**Example:**

```java
public class TestThread extends Thread { 
    public void run() { 
        System.out.println("Thread is running..."); 
    } 

    public static void main(String[] args) { 
        TestThread t = new TestThread(); 
        t.start(); // Works fine 
        t.start(); // Throws IllegalThreadStateException 
    } 
}
```

---

## Question 3: What is thread safety and how can it be achieved?

**Answer:**  
Thread safety means code functions correctly during simultaneous execution by multiple threads.  
It can be achieved through:
- Synchronization
- Immutable objects
- Concurrent collections
- Atomic variables
- Thread-local variables

---

## Question 4: What happens if an exception occurs in a thread's `run()` method?

**Answer:**  
If an uncaught exception occurs in a thread's `run()` method, the thread terminates.  
The exception does **not** propagate to the parent thread and does **not** affect other threads.

**Example:**

```java
class MyThread extends Thread { 
    public void run() { 
        try { 
            throw new RuntimeException("Exception in thread"); 
        } catch (Exception e) { 
            System.out.println("Caught exception in thread: " + e.getMessage()); 
        } 
    } 
} 

public class Main { 
    public static void main(String[] args) { 
        MyThread t = new MyThread(); 
        t.start(); // Starts a separate thread 
        System.out.println("Main thread is running"); 
    } 
}
```

**Explanation:**  
- Calling `t.run();` executes in the main thread, so exceptions are thrown in the main thread.
- Calling `t.start();` runs the thread separately; exceptions terminate only that thread.

**Sample Output:**
```
Main thread is running 
Exception in thread "Thread-0" java.lang.RuntimeException: Exception in thread 
    at MyThread.run(Main.java:4) 
    at java.base/java.lang.Thread.run(Thread.java:833)
```

---

## Question 5: What's the difference between `sleep()` and `wait()`?

**Answer:**  
- `sleep()` pauses the current thread for a specified time **without releasing locks**.
- `wait()` causes the current thread to wait until another thread calls `notify()` or `notifyAll()` on the same object, and **releases the lock** on the object.

### Example 1: `sleep()`

```java
class SleepExample { 
    public static void main(String[] args) { 
        System.out.println("Thread is going to sleep..."); 
        try { 
            Thread.sleep(2000); // Sleep for 2 seconds 
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 
        System.out.println("Thread woke up after sleeping."); 
    } 
}
```

**Output:**
```
Thread is going to sleep...
(Thread pauses for 2 seconds)
Thread woke up after sleeping.
```

> **Note:** `sleep()` does **not** release any locks. Other threads cannot access synchronized resources held by the sleeping thread.

---

### Example 2: `wait()` and `notify()`

```java
class SharedResource { 
    synchronized void waitExample() { 
        System.out.println(Thread.currentThread().getName() + " is waiting..."); 
        try { 
            wait(); // Releases the lock and waits 
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        } 
        System.out.println(Thread.currentThread().getName() + " resumed after notify."); 
    } 

    synchronized void notifyExample() { 
        System.out.println("Notifying a waiting thread..."); 
        notify(); // Wakes up one waiting thread 
    } 
} 

public class WaitNotifyExample { 
    public static void main(String[] args) { 
        SharedResource shared = new SharedResource(); 

        // Thread 1 (Waits) 
        Thread t1 = new Thread(() -> shared.waitExample(), "Thread-1"); 
        // Thread 2 (Notifies after 2 seconds) 
        Thread t2 = new Thread(() -> { 
            try { 
                Thread.sleep(2000); // Ensure Thread-1 goes to wait state 
                shared.notifyExample(); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        }, "Thread-2"); 

        t1.start(); 
        t2.start(); 
    } 
}
```

**Output:**
```
Thread-1 is waiting...
Notifying a waiting thread...
Thread-1 resumed after notify.
```

**Explanation:**  
- `wait()` releases the lock, allowing other threads to acquire it.
- `notify()` wakes up one waiting thread, which resumes after reacquiring the lock.

> If `notifyAll()` is used, all waiting threads are notified, but only one acquires the lock first.

---

## Question 6: What is the `Callable` interface, and how does it differ from `Runnable`?

**Answer:**  
- `Callable` is a functional interface introduced in Java 5.
- `Callable.call()` can return a result and throw checked exceptions.
- `Runnable.run()` returns void and cannot throw checked exceptions.
- `Callable` works with `Future` objects for asynchronous result handling.

---

## Question 7: Can you use `Callable` with standard `Thread` objects?

**Answer:**  
No, you cannot directly use `Callable` with the `Thread` class.  
`Callable` is designed for use with the `ExecutorService` framework.  
However, you can adapt a `Callable` to work with `Thread` by wrapping it in a `Runnable`:

```java
class MyRunnable implements Runnable { 
    private Callable<Integer> callable; 
    public MyRunnable(Callable<Integer> callable) { 
        this.callable = callable; 
    } 

    public void run() { 
        try { 
            System.out.println(callable.call()); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}
```

---
