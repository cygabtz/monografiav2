package DetUmbral;

import AlgoritmoFinal.MSrecursivoParalelo;

import java.io.FileWriter;
import java.io.IOException;

import java.util.SplittableRandom;
import java.util.concurrent.ForkJoinPool;

public class TestPrueba{
    private static long[][] results;
    private static final int MAX_SIZE = 1_000_000;

    public static void main(String[] args) throws IOException{
        results = new long[9][30];
        for (int size=10, i=0; size<=MAX_SIZE; size*=10, i++){
            int[] array = generateArray(size);
            long[] times = new long[10];
            long totalTime = 0;

            for (int threshold = 2, j=0; threshold<=Math.pow(2, 29); threshold*=2, j++){
                if (size < threshold){
                    results[i][j] = -1;
                    break;
                } else {
                    for (int trial = 0; trial<10; trial++){
                        //Inicialización piscina y tarea
                        final ForkJoinPool forkJoinPool =
                                new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);
                        final MSrecursivoParalelo task =
                                new MSrecursivoParalelo(array, new int[array.length], 0, array.length-1);

                        //Llamada al Garbage Collector
                        System.gc();
                        //Benchmark
                        long start = System.nanoTime();
                        forkJoinPool.invoke(task);
                        long end = System.nanoTime();

                        long avg = end-start;
                        times[trial] = avg;
                        totalTime+=avg;
                    }

                    //Encontrar máximo y mínimo
                    long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
                    for (long l : times) if (l > max) max = l;
                    for (long p : times) if (p < min) min = p;

                    //Calcular el promedio
                    results[i][j] = (totalTime - min - max) / 8;
                }
            }
        }
        writeResults();
    }
    public static void writeResults() throws IOException{
        try (FileWriter writer = new FileWriter("results.csv")) {
            // Escribir encabezados writer.append("Threshold\\Size");
            for (int size = 10; size <= MAX_SIZE; size *= 10) {
                writer.append(",").append(String.valueOf(size));
            }
            writer.append("\n");
            // Escribir datos
            for (int threshold = 2, i = 0; threshold <= Math.pow(2, 29); threshold *= 2, i++){
                writer.append(String.valueOf(threshold));
                for (int j = 0; j < results.length; j++){
                    writer.append(",").append(String.valueOf(results[j][i]));
                }
                writer.append("\n");
            }
        }
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
}
