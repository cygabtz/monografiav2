package DesarrolloAlgoritmos.Tests;

import DesarrolloAlgoritmos.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SplittableRandom;
import java.util.concurrent.ForkJoinPool;

public class FinalMainTester {

    private static final int[] ARRAY_SIZES = generateSizes(10, 1_000_000, 20);
    private static final int NUM_EXECUTIONS = 50;
    private static final SplittableRandom RANDOM = new SplittableRandom();
    private static final String OUTPUT_FILE = "resultados_completos.csv";
    private static FileWriter promedios;

    public static void main(String[] args) throws IOException {
        // Configurar el ForkJoinPool para implementaciones paralelas
        int numCores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool customPool = new ForkJoinPool(numCores);
        System.out.println("Thread Pools configurada con "+numCores+ " cores.");

        // Crear archivo de salida
        try (FileWriter writer = new FileWriter(OUTPUT_FILE)) {
            //Archivo general
            writer.write("Algoritmos;Tamaños;Tiempos;Promedios\n");
            System.out.println("CSV creado.");
            //Archivo de solo los promedios
            promedios = new FileWriter("Promedios.csv");
            promedios.write("Tamaños;MSRS;MSIS;MSRP;MSIP;MSIP_Optimizado\n");
            //BufferedWritter mejor

            // Testear cada algoritmo
            for (int i = 0; i<ARRAY_SIZES.length; i++) {
                int size = ARRAY_SIZES[i];
                System.out.println("Iteración  " + i + " con tamaño " + size);
                int[] originalArray = generateArray(size);
                promedios.write(size + ";");
                System.out.println("Array generado con tamaño " + size);


                testAlgorithm("MSRS", originalArray, writer, size,
                        array -> MergeSortRecursivoSerial_V1.sort(array, array.length));
                testAlgorithm("MSIS", originalArray, writer, size, MergeSortIterativoSerial::sort);

                testAlgorithm("MSRP", originalArray, writer, size, (arr) -> {
                    MergeSortRecursivoParalelo_V1 task = new MergeSortRecursivoParalelo_V1(arr, arr.length);
                    customPool.invoke(task);
                });

//                testAlgorithm("MSIP", originalArray, writer, size, MergeSortIterativoParalelo::sort);
                testAlgorithm("MSIP_Optimizado", originalArray, writer, size,
                        MergeSortIterativoParalelo_V1::sort);
                promedios.write("\n");
            }
        }

        System.out.println("TEST FINALIZADO. Resultados completos en " + OUTPUT_FILE);
    }

    private static void testAlgorithm(String algorithm,
                                      int[] originalArray,
                                      FileWriter writer,
                                      int size,
                                      SortAlgorithm sortAlgorithm) throws IOException {
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
        System.out.println("Promedio del " + algorithm + ": " + averageTime);
        // Escribir resultados
        writer.write(algorithm + ";" + size + ";" + arrayToString(executionTimes) + ";" + averageTime + "\n");
        promedios.write(Long.toString(averageTime) + ";");
        System.out.println("Resultados del " + algorithm + " escritos en " + OUTPUT_FILE);
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

        for (int i = 0; i<times.length; i++) {
            sum += times[i];
            if (times[i] < min) min = times[i];
            if (times[i] > max) max = times[i];
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

