package Concurrency.Synchronization;

public class SynchronizedExchanger {
    protected Object object = null;

    //All methods are linked to an instance of SynchronizedExchanger (non-static)
    //Only one thread can call any of the synchronized methods of this class.
    public synchronized void setObject(Object o){
        this.object = o;
    }

    public synchronized Object getObject(){
        return this.object;
    }

    public void setObj(Object o){
        //Allows to choose the instance of the class to be linked (the monitor).
        synchronized (this){
            this.object = o;
        }
    }

    public Object getObj(){
        synchronized (this){
            return this.object;
        }
    }
}
