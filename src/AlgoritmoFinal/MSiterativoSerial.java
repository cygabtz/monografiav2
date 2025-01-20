package AlgoritmoFinal;

public class MSiterativoSerial {
    public static void sort(int[] arr, int[] aux) {
        int length = arr.length;

        // Subarreglos de tamaño 1, 2, 4, 8, ... hasta n/2
        for (int size = 1; size < length; size *= 2) {
            for (int left = 0; left < length - size; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, length - 1);
                merge(arr, aux, left, mid, right);
            }
        }
    }

    private static void merge(int[] arr, int[] aux, int left, int mid, int right) {
        // Copiar los elementos al arreglo auxiliar
        for (int i = left; i <= right; i++) aux[i] = arr[i];

        int i = left;       // Índice del subarreglo izquierdo
        int j = mid + 1;    // Índice del subarreglo derecho
        int k = left;       // Índice del arreglo original

        // Mezclar los dos subarreglos
        while (i <= mid && j <= right) arr[k++] = (aux[i] <= aux[j])? aux[i++] : aux[j++];

        // Copiar elementos restantes del subarreglo izquierdo (si los hay)
        while (i <= mid) arr[k++] = aux[i++];

        // Los elementos restantes del subarreglo derecho ya están en su lugar
    }
}
