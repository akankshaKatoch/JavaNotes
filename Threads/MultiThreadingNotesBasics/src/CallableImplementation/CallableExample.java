import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<String> callable1 = new MyCallable("Task 1");
        Callable<String> callable2 = new MyCallable("Task 2");

        try {

            Future<String> future1 = executor.submit(callable1);
            Future<String> future2 = executor.submit(callable2);

            System.out.println("Resut from first task:");
            System.out.println(future1.get());

            System.out.println("Resut from second task:");
            System.out.println(future2.get());


        } catch (InterruptedException | ExecutionException e){
            System.out.println("Task execution interrupted: " + e.getMessage());

        } finally {
            executor.shutdown();
        }
    }
    
}
