package ManagingThreads;

public class LambdaThread {
    public static void main(String[] args) {
        String id = "1";

        //Code stored in a Runnable
        Runnable r = () -> {
            double result = 0;
            for (int i = 1; i < 100000; i++) {
                double s = Math.sqrt(1. * i);
                result = result + s;
            }
            result = result / 99999;
            System.out.println("Worker "+id+": result="+result);
        };

        //Passing r code into Thread. The code is in run() now.
        Thread t1 = new Thread(r);

        //Start and join of t1:
        t1.start();
        try{
            t1.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
