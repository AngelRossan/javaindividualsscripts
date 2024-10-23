public class EsperaOcupada {

    private static volatile boolean flag=false;
    private static int sharedResource=0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Process(1));
        Thread thread2 = new Thread(new Process(2));

        thread1.start();
        thread2.start();
    }
}