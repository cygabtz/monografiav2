package ExploracionHerramientasConcurrencia.Synchronization;

public class SynchronizedExchangerMain {
    public static void main(String[] args) {
        SynchronizedExchanger exchanger = new SynchronizedExchanger();

        Runnable runnable1 = () -> {
            for(int i=0; i<1000; i++){
                exchanger.setObject("" + i);
            }
        };

        Runnable runnable2 = () -> {
            for(int i=0; i<1000; i++){
                System.out.println(exchanger.getObject());
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();
    }
}
