package TestPreliminarUmbral;

import DesarrolloAlgoritmos.Tests.MainTester;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MSiterativoParaleloSinUmbral {

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
        int [] arr = MainTester.generateArray(1000);
        for (int i : arr) System.out.print(i+", ");
        System.out.println("\n");

        int [] aux = new int[arr.length];
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        sort(arr, aux, executorService);

        for (int i : arr) System.out.print(i+", ");
    }
}
