public class ExampleThread {
    public static void main(String[] args) {
        MyRunnable thread1 = new MyRunnable();
        MyRunnable thread2 = new MyRunnable();

        thread1.start();
        thread2.start();

    }
    
}
