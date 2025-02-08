package DesarrolloAlgoritmos.Tests;

import DesarrolloAlgoritmos.MergeSortRecursivoParalelo_V0;
import DesarrolloAlgoritmos.MergeSortRecursivoParalelo_V1;
import DesarrolloAlgoritmos.MergeSortRecursivoSerial_V1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.concurrent.ForkJoinPool;

public class MainTester {

    public interface SortFunction {
        void sort(int[] array);
    }

    public static void test(SortFunction function, int size, String csvName) throws IOException {
        //Generar el array de longitudes. Un total de 9.
        int[] sizes = new int[size];
        for (int i = 0; i<sizes.length; i++){
            sizes[i] = (int) Math.pow(10, i+1);
        }
        System.out.println("SIZES SUCCESSFULLY GENERATED");

        int reps = 10;
        long[][] results = new long[sizes.length][reps];

        //Benchmark
        for (int i = 0; i<sizes.length; i++){
            for (int j = 0; j<reps; j++){
                int [] array = generateArray(sizes[i]);

                long startTime = System.nanoTime();
                function.sort(array);
                long endTime = System.nanoTime();

                results [i][j] = endTime - startTime;

                System.out.println("Array with size " + sizes[i] + " successfully sorted in " + results[i][j]);
            }
        }
        System.out.println("BENCHMARK SUCCESSFULLY");

        //Calcular promedios
        long [] averages = new long[sizes.length];
        for (int i = 0; i<sizes.length; i++){
            for (int j = 0; j<reps; j++){
                averages[i] = results[i][j];
            }
            averages[i] /= reps;
        }
        System.out.println("AVERAGES SUCCESSFUL");

        writeResultsToCSV(sizes, averages, csvName);
        System.out.println("RESULT WROTE SUCCESSFULLY");

        System.out.println("RESULTS");
        System.out.println("Averages: "+ Arrays.toString(averages));
    }

    public static int[] generateArray(int size){
        int [] array = new int[size];
        long seed = 6180339887L;
        SplittableRandom random = new SplittableRandom(seed);

        //Todos los arrays constan de una misma secuencia
        for (int i = 0; i<array.length; i++){
            array[i] = random.nextInt(1000); //De 0 a 999
        }
        return array;
    }

    public static void writeResultsToCSV(int[] sizes, long[] averages, String csvName) throws IOException{
        try (FileWriter writer = new FileWriter(csvName + "_Benchmark.csv")){
            writer.append("Longitud colección,Promedio ejecución (ns)\n");
            for (int i = 0; i<sizes.length; i++){
                writer.append(sizes[i] + "," + averages[i] + "\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        test((array -> MergeSortRecursivoSerial.sort(array, 0, array.length-1)),
//                7, "MSRSv1");
        test(array -> MergeSortRecursivoSerial_V1.sort(array, array.length), 7, "MSRSv2");

        test(array -> {
            final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);
            MergeSortRecursivoParalelo_V0 task = new MergeSortRecursivoParalelo_V0(array, array.length);
            forkJoinPool.invoke(task);
        }, 7, "MSPRv1");

        test(array -> {
            final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);
            MergeSortRecursivoParalelo_V1 task = new MergeSortRecursivoParalelo_V1(array, array.length);
            forkJoinPool.invoke(task);
        }, 7, "MSPRv2");
    }
}
