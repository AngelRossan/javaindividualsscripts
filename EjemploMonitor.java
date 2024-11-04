class MonitorDemos{
    private int counter=0;

    synchronized void incrementar(){
        counter ++;
        System.out.println("Contador: "+counter);
    }

    synchronized int obtenerValor(){
        return counter;
    }
}

class HiloIncrementar extends Thread{
    private MonitorDemos monitor;

    HiloIncrementar(MonitorDemos monitor){
        this. monitor=monitor;
    }
    public void run(){
        for(int i=0; i<5; i++){
            monitor.incrementar();
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                // TODO: handle exception
                System.err.println(e);
            }
        }
    }
}

/**
 * EjemploMonitor
 */
public class EjemploMonitor {

    public static void main(String[] args) {
        MonitorDemos monitor=new MonitorDemos();
        HiloIncrementar hilo1= new HiloIncrementar(monitor);
        HiloIncrementar hilo2= new HiloIncrementar(monitor);

        hilo1.start();
        hilo2.start();

        try {
            hilo1.join();
            hilo2.join();
        } catch (Exception e) {
        System.out.println(e);
        }

    }
    
}