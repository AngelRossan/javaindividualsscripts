class Impresora {
    synchronized void imprimir(String trabajo){
        System.out.println("Imprimiendo: "+ trabajo);
        try {
                Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Trabajo Completado: "+trabajo);
    }
}


class Usuario extends Thread{
    private Impresora impresora;
    private String trabajo;

    Usuario(Impresora impresora, String trabajo){
        this.impresora=impresora;
        this.trabajo=trabajo;
    }
    public void run(){
        impresora.imprimir(trabajo);
    }
    
}

public class SistemaImpresion {
    public static void main(String[] args) {
        Impresora impresora=new Impresora();
    
        Usuario usuario1=new Usuario(impresora,"Documento1");
        Usuario usuario2=new Usuario(impresora,"Documento2");
        Usuario usuario3=new Usuario(impresora,"Documento3");

        usuario1.start();
        usuario2.start();
        usuario3.start();
        try{
            usuario1.join();
            usuario2.join();
            usuario3.join();
            
        }catch(InterruptedException e){
            System.out.println(e);
        }
        System.out.println("Todos los trabajos han sido procesados");
    }
}