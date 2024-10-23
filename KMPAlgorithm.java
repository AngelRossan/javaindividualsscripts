public class KMPAlgorithm {

    // Función para calcular el array LPS (Longest Prefix Suffix)
    private static void computeLPSArray(String pattern, int[] lps) {
        int length = 0; // Longitud del prefijo más largo
        lps[0] = 0; // LPS para el primer carácter es siempre 0
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1]; // Volver al último prefijo
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    // Función para buscar el patrón en el texto usando KMP
    public static void KMPSearch(String text, String pattern) {
        int[] lps = new int[pattern.length()];
        computeLPSArray(pattern, lps);

        int i = 0; // Índice para el texto
        int j = 0; // Índice para el patrón

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                System.out.println("Patrón encontrado en el índice: " + (i - j));
                j = lps[j - 1]; // Continuar buscando
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1]; // Usar LPS para evitar retroceder en text
                } else {
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        
        KMPSearch(text, pattern);
    }
}