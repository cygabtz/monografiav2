package Algoritmos.Tests;

import Algoritmos.MergeSortIterativoParalelo;
import Algoritmos.MergeSortIterativoSerial;
import Algoritmos.MergeSortRecursivoParalelo_V2;
import Algoritmos.MergeSortRecursivoSerial_V2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SplittableRandom;
import java.util.concurrent.ForkJoinPool;

public class TestMergeSort {

    private static final int[] ARRAY_SIZES = generateSizes(100, 1_000_000, 20);
    private static final int NUM_EXECUTIONS = 10;
    private static final SplittableRandom RANDOM = new SplittableRandom();
    private static final String OUTPUT_FILE = "merge_sort_results.csv";

    public static void main(String[] args) throws IOException {
        // Configurar el ForkJoinPool para implementaciones paralelas
        int numCores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool customPool = new ForkJoinPool(numCores);

        // Crear archivo de salida
        try (FileWriter writer = new FileWriter(OUTPUT_FILE)) {
            writer.write("Algorithm,Array Size,Execution Times,Average Time\n");

            // Probar cada algoritmo
            for (int size : ARRAY_SIZES) {
                int[] originalArray = generateArray(size);

                testAlgorithm("MSRS", originalArray, writer, size,
                        array -> MergeSortRecursivoSerial_V2.sort(array, array.length));
                testAlgorithm("MSIS", originalArray, writer, size, MergeSortIterativoSerial::sort);

                testAlgorithm("MSRP", originalArray, writer, size, (arr) -> {
                    MergeSortRecursivoParalelo_V2 task = new MergeSortRecursivoParalelo_V2(arr, arr.length);
                    customPool.invoke(task);
                });

                testAlgorithm("MSIP", originalArray, writer, size, MergeSortIterativoParalelo::sort);
            }
        }

        System.out.println("Test completado. Resultados guardados en " + OUTPUT_FILE);
    }

    private static void testAlgorithm(String algorithm, int[] originalArray, FileWriter writer, int size, SortAlgorithm sortAlgorithm) throws IOException {
        long[] executionTimes = new long[NUM_EXECUTIONS];

        // Medir tiempos de ejecución
        for (int i = 0; i < NUM_EXECUTIONS; i++) {
            int[] arrayCopy = originalArray.clone(); // Clonar el array para cada ejecución

            System.gc(); // Forzar garbage collection

            long startTime = System.nanoTime();
            sortAlgorithm.sort(arrayCopy);
            long endTime = System.nanoTime();

            executionTimes[i] = endTime - startTime;
        }

        // Calcular promedio sin mejor y peor tiempos
        long averageTime = calculateAverage(executionTimes);

        // Escribir resultados
        writer.write(algorithm + "," + size + "," + arrayToString(executionTimes) + "," + averageTime + "\n");
    }

    private static int[] generateArray(int size) {
        return RANDOM.ints(size, 0, Integer.MAX_VALUE).toArray();
    }

    private static int[] generateSizes(int start, int end, int count) {
        int[] sizes = new int[count];
        double factor = Math.pow((double) end / start, 1.0 / (count - 1));

        for (int i = 0; i < count; i++) {
            sizes[i] = (int) Math.round(start * Math.pow(factor, i));
        }

        return sizes;
    }

    private static long calculateAverage(long[] times) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        long sum = 0;

        for (long time : times) {
            sum += time;
            if (time < min) min = time;
            if (time > max) max = time;
        }

        return (sum - min - max) / (times.length - 2);
    }

    private static String arrayToString(long[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) sb.append(";");
        }
        return sb.toString();
    }

    @FunctionalInterface
    interface SortAlgorithm {
        void sort(int[] array);
    }
}

