package ManagingThreads;

public class ImplementingRunnable {
    public static void main(String[] args) {
        //Creation of the runnable
        CodeB myRunnable = new CodeB();
        //Creation of the thread
        Thread thread1 = new Thread(myRunnable);
    }
}
