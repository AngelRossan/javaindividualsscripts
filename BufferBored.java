import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BufferBored {
    // Clase Buffer
    public static class Buffer {
        private final List<Integer> buffer;
        private final int capacidad;

        public Buffer(int capacidad) {
            this.buffer = new LinkedList<>();
            this.capacidad = capacidad;
        }

        // Método sincronizado para agregar elementos al buffer
        public synchronized void producir(int elemento) throws InterruptedException {
            while (buffer.size() == capacidad) {
                wait(); // Espera si el buffer está lleno
            }
            buffer.add(elemento);
            System.out.println("Producido: " + elemento);
            notifyAll(); // Notifica a los consumidores que hay un nuevo elemento
        }

        // Método sincronizado para quitar elementos del buffer
        public synchronized int consumir() throws InterruptedException {
            while (buffer.isEmpty()) {
                wait(); // Espera si el buffer está vacío
            }
            int elemento = buffer.remove(0);
            System.out.println("Consumido: " + elemento);
            notifyAll(); // Notifica a los productores que hay espacio disponible
            return elemento;
        }
    }

    // Clase Productor
    public static class Productor implements Runnable {
        private final Buffer buffer;

        public Productor(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            Random random = new Random();
            try {
                while (true) {
                    int numero = random.nextInt(100); // Genera un número aleatorio
                    buffer.producir(numero);
                    Thread.sleep(random.nextInt(100)); // Espera aleatoria para simular tiempo de producción
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Clase Consumidor
    public static class Consumidor implements Runnable {
        private final Buffer buffer;

        public Consumidor(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int numero = buffer.consumir();
                    // Procesamiento del número consumido
                    System.out.println("Procesado: " + numero);
                    Thread.sleep(100); // Espera para simular tiempo de procesamiento
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Método principal
    public static void main(String[] args) {
        Buffer buffer = new Buffer(10); // Tamaño máximo del buffer

        // Crear y ejecutar hilos productores
        Thread productor1 = new Thread(new Productor(buffer));
        Thread productor2 = new Thread(new Productor(buffer));

        // Crear y ejecutar hilos consumidores
        Thread consumidor1 = new Thread(new Consumidor(buffer));
        Thread consumidor2 = new Thread(new Consumidor(buffer));

        productor1.start();
        productor2.start();
        consumidor1.start();
        consumidor2.start();
    }
}
