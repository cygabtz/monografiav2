package ManagingThreads;

public class JoiningThreads {
    public static void main(String[] args) {
        System.out.print("Thread1: ");
        Thread1 t1 = new Thread1("Thread01");
        t1.start();
        try{
            //Join makes the main thread wait until t1 thread finishes
            t1.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(t1.getName() + ": result=" + t1.getResult());
    }
}
