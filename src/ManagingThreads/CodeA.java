package ManagingThreads;

public class CodeA extends Thread{
    public void run(){
        //Code that will be broken
        for(int i = 0; i<=5; i++){
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
