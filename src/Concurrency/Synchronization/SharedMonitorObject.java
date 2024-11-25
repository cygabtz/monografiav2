package Concurrency.Synchronization;

public class SharedMonitorObject {
    private Object monitor = null;
    private int counter = 0;

    SharedMonitorObject(Object monitor){
        if(monitor==null){
            throw new IllegalArgumentException();
        }
        this.monitor = monitor;
    }

    public void incCounter(){
        synchronized (this.monitor){
            this.counter++;
        }
    }
}
