package ManagingThreads;

public class Thread1 extends Thread{
    private double result;
    Thread1(String name){
        super(name);
    }
    @Override
    public void run() {
        for (int i = 1; i < 100000; i++) {
            double s = Math.sqrt(1. * i);
            result = result + s;
        }
        result = result / 99999;
    }
    public double getResult(){
        return result;
    }
}
