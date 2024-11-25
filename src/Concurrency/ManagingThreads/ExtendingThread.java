package Concurrency.ManagingThreads;

public class ExtendingThread {
    public static void main(String[] args){
        //Java runs the code in ThreadA in a new thread.
        //All threads executing at same time
        //No specific order
        for (int i = 0; i<5; i++){
            CodeA thread1 = new CodeA();
            thread1.start();
        }
    }
}
