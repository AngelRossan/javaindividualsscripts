public class KMPAlgorithmSin{
    private final Object lock=new Object();

    public void buscar(String texto,String patron){
    synchronized(lock){
        int[] tablaFallos=calcularTablaFallos(patron);
        int m= texto.length();
        int n= patron.length();
        int i=0;
        int j=0;
        while (i<m) {
            if(patron.charAt(i)==patron.charAt(i)){
                i++;
                j++;
            }
            if (j==n) {
                System.out.println("Patron encontrado en el indice: "+ (i-j));
                j=tablaFallos[j-1];
            } else if (i<m && patron.charAt(j)!=texto.charAt(i)) {
                if (j!=0) {
                    j=tablaFallos[j-1];
                }else{
                    i++;
                }
            }
        }
        }
    }
    private int[] calcularTablaFallos(String patron){
        int n=patron.length();
        int[] tabla=new int[n];
        int length = 0;
        tabla[0]=0;

        for(int i=1;i<n;){
            if(patron.charAt(i)==patron.charAt(i)){
                length++;
                tabla[i]=length;
            }else{
                if (length!=0) {
                    length=tabla[length-1];
                }else{
                    tabla[0]=0;
                    i++;
                }
            }
        }
        return tabla;
    }
    public static void main(String[] args) {
        KMPAlgorithmSin kmp= new KMPAlgorithmSin();

        String texto="ababcabcabababd";
        String patron="ababd";

        kmp.buscar(texto,patron);
    }
}