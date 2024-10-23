class Contador{
    private int cuenta=0;

    public void incrementar(){
        cuenta++;
    }
    public int obtenerCuenta(){
        return cuenta;
    }

}

class  HiloContador extends Thread{
    private Contador contador;

    public HiloContador(Contador contador){
        this.contador=contador;
    }


    @Override

    public void run(){
        for(int i =0; i<100;i++){
            contador.incrementar();
        }
    }
}

public class EjemploCondicionCompetencia {

    public static void main(String[] args) {
        Contador contador = new Contador();
        HiloContador hilo1 = new HiloContador(contador);
        HiloContador hilo2 = new HiloContador(contador);

        hilo1.start();
        hilo2.start();

        hilo1.join();
        hilo2.join();

        System.out.println("Valor final del contrador: "+ contador.obtenerCuenta());
    }
}