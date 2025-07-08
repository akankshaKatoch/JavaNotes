public class MyThread {
    public static void main(String[] args) {
        System.out.println("Hello");
        MyRunnable runnable = new MyRunnable();

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();
    }
    
}
