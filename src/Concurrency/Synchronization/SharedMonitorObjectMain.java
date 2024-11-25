package Concurrency.Synchronization;

public class SharedMonitorObjectMain {
    public static void main(String[] args) {
        Object monitor = new Object();

        //sm1 and sm2 are objects from different instances but its methods are
        //synchronized to the same monitor

        SharedMonitorObject sm1 = new SharedMonitorObject(monitor);
        SharedMonitorObject sm2 = new SharedMonitorObject(monitor);

        //Cannot be called at the same time from different threads.
        sm1.incCounter();
        sm2.incCounter();

    }
}
