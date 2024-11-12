import java.util.concurrent.RecursiveTask;

public class SumaMatricesTask extends RecursiveTask<int[][]> {
    private static final int UMBRAL = 64; // Tamaño mínimo de bloque para dividir el trabajo
    private final int[][] matrizA;
    private final int[][] matrizB;
    private final int inicioFila;
    private final int finFila;

    public SumaMatricesTask(int[][] matrizA, int[][] matrizB, int inicioFila, int finFila) {
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.inicioFila = inicioFila;
        this.finFila = finFila;
    }

    @Override
    protected int[][] compute() {
        int numColumnas = matrizA[0].length;

        // Si el rango es menor o igual al umbral, sumar directamente
        if (finFila - inicioFila <= UMBRAL) {
            int[][] resultado = new int[finFila - inicioFila][numColumnas];
            for (int i = inicioFila; i < finFila; i++) {
                for (int j = 0; j < numColumnas; j++) {
                    resultado[i - inicioFila][j] = matrizA[i][j] + matrizB[i][j];
                }
            }
            return resultado;
        } else {
            // Dividir en dos tareas
            int mitad = (inicioFila + finFila) / 2;
            SumaMatricesTask tareaSuperior = new SumaMatricesTask(matrizA, matrizB, inicioFila, mitad);
            SumaMatricesTask tareaInferior = new SumaMatricesTask(matrizA, matrizB, mitad, finFila);

            // Ejecutar subtareas en paralelo y combinar los resultados
            tareaSuperior.fork(); // Lanza la tarea superior en paralelo
            int[][] resultadoInferior = tareaInferior.compute(); // Computa la tarea inferior en el hilo actual
            int[][] resultadoSuperior = tareaSuperior.join(); // Espera a la tarea superior

            // Combinar los resultados en una sola matriz
            int[][] resultadoFinal = new int[finFila - inicioFila][numColumnas];
            System.arraycopy(resultadoSuperior, 0, resultadoFinal, 0, resultadoSuperior.length);
            System.arraycopy(resultadoInferior, 0, resultadoFinal, resultadoSuperior.length, resultadoInferior.length);
            return resultadoFinal;
        }
    }

    // Método principal para iniciar la suma de matrices
    public static int[][] sumarMatrices(int[][] matrizA, int[][] matrizB) {
        if (matrizA.length != matrizB.length || matrizA[0].length != matrizB[0].length) {
            throw new IllegalArgumentException("Las matrices deben tener el mismo tamaño.");
        }
        SumaMatricesTask tareaPrincipal = new SumaMatricesTask(matrizA, matrizB, 0, matrizA.length);
        return tareaPrincipal.invoke(); // Ejecuta la tarea principal
    }
}
