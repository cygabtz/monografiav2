package DetUmbral;

import AlgoritmoFinal.MSrecursivoParalelo;
import org.openjdk.jmh.annotations.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SplittableRandom;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;


public class ThreshBenchmark {
    private static long[][] results;
    private int[] array;

    @Setup(Level.Trial)
    public void setUp() throws IOException {
        array = generateArray(1000);
        results = new long[30][9];
        for (int size=10, i=0; size<=1_000_000_000; size*=10, i++){
            int[] array = generateArray(size);
            long[] times = new long[10];
            long totalTime = 0;

            for (int threshold = 2, j=0; threshold<=Math.pow(2, 29); threshold*=2, j++){
                if (size < threshold){
                    results[i][j] = -1;
                    break;
                } else {
                    for (int trial = 0; trial<10; trial++){

                        //Inicialización de la piscina
                        final ForkJoinPool forkJoinPool =
                                new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);
                        MSrecursivoParalelo task =
                                new MSrecursivoParalelo(array, new int[array.length], 0, array.length-1);

                        //Benchmark
                        long start = System.nanoTime();
                        testMethod(forkJoinPool, task);
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

    @Benchmark
    @Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.NANOSECONDS)
    @Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.NANOSECONDS)
    @Fork(1)
    public void testMethod(ForkJoinPool forkJoinPool, MSrecursivoParalelo task) throws IOException {
        forkJoinPool.invoke(task);
    }

    public static void writeResults() throws IOException {
        try (FileWriter writer = new FileWriter("results.csv")) {
            // Escribir encabezados writer.append("Threshold\\Size");
            for (int size = 10; size <= 1_000_000_000; size *= 10) {
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

    public int[] generateArray(int size) {
        int[] array = new int[size];
        long seed = 6180339887L;
        SplittableRandom random = new SplittableRandom(seed);

        // Todos los arrays constan de una misma secuencia
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000); // De 0 a 999
        }
        return array;
    }
}

