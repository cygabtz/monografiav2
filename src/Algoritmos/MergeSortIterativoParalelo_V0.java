package Algoritmos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSortIterativoParalelo_V0 {

    public static void sort(int[] array) {
        int n = array.length;
        int[] aux = new int[n];
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int size = 1; size < n; size *= 2) {
            for (int left = 0; left < n - size; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                int finalLeft = left; //El resto son efectivamente finales
                Future<?> future = executor.submit(() -> merge(array, aux, finalLeft, mid, right));
                try {
                    future.get(); // Esperar a que la tarea termine
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();
    }

    private static void merge(int[] arr, int[] aux, int left, int mid, int right) {
        System.arraycopy(arr, left, aux, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (aux[i] <= aux[j]) {
                arr[k++] = aux[i++];
            } else {
                arr[k++] = aux[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = aux[i++];
        }
    }

}
