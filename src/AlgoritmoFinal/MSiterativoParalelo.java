package AlgoritmoFinal;

import Algoritmos.MergeSortIterativoSerial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class MSiterativoParalelo {
    private static final int THRESHOLD = 8192;

    public static void sort(int[] arr, int[] aux, ExecutorService executor) {
        int length = arr.length;
        List<Future<?>> futures = new ArrayList<>();

        for (int size = 1; size < length; size *= 2) {

            for (int left = 0; left < length - size; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, length - 1);
                int finalLeft = left; //El resto son efectivamente finales
                futures.add(executor.submit(() -> merge(arr, aux, finalLeft, mid, right)));
            }
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            futures.clear();

        }
        executor.shutdown();
    }

    private static void merge(int[] arr, int[] aux, int left, int mid, int right) {
        // Copiar los elementos al arreglo auxiliar
        for (int i = left; i <= right; i++) aux[i] = arr[i];

        int i = left;       // Índice del subarreglo izquierdo
        int j = mid + 1;    // Índice del subarreglo derecho
        int k = left;       // Índice del arreglo original

        // Mezclar los dos subarreglos
        while (i <= mid && j <= right) arr[k++] = (aux[i] <= aux[j]) ? aux[i++] : aux[j++];

        // Copiar elementos restantes del subarreglo izquierdo (si los hay)
        while (i <= mid) arr[k++] = aux[i++];

        // Los elementos restantes del subarreglo derecho ya están en su lugar
    }

    public static void main(String[] args) {

        //Comparación entre el MSinterativoParalelo con merge() unificado y sin unificar

        int[] array = Algoritmos.Tests.MainTester.generateArray(10000);
        int[] aux = new int[array.length];
        System.out.println("Array original:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
        int[] array2 = array.clone();

        System.out.print("\n\nTesting MSrecursivoParalelo unified");
        long startTime = System.nanoTime();
        //sort(array, aux);
        long endTime = System.nanoTime();

        System.out.println("\n\nArray ordenado:");
        for (int num : array) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println("Time taken: " + (endTime - startTime));


        System.out.print("\n\nTesting MSrecursivoParalelo non-unified");

        startTime = System.nanoTime();
        Algoritmos.MergeSortIterativoParalelo_V1.sort(array2);
        endTime = System.nanoTime();
        System.out.println("\n\nArray ordenado:");
        for (int num : array2) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println("Time taken: " + (endTime - startTime));

        //La versión unificada parece ligeramente más lenta
    }
}
