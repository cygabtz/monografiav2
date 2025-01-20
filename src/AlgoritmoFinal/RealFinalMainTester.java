package AlgoritmoFinal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class RealFinalMainTester {

    private static final int[] arraySizes = generateSizes();
    private static final int numtrials = 20;
    private static final SplittableRandom RANDOM = new SplittableRandom();
    private static long[] averages, minValues, maxValues;
    private static final String csvName = "testMSiterativoParalelo.csv";
    private static final int parallismLevel = 10;

    public static void main(String[] args) throws IOException {
        averages = new long[arraySizes.length];
        minValues = new long[arraySizes.length];
        maxValues = new long[arraySizes.length];

        for (int i = 0; i< arraySizes.length; i++) {
            int size = arraySizes[i];
            int[] originalArray = generateArray(size);

            long[] times = new long[numtrials];
            long totalTime = 0;

            for (int trial = 0; trial < numtrials; trial++) {
                //Copia del array ya que es paso por referencia
                int[] arrayCopy = originalArray.clone();

                //Array auxiliar
                int[] aux = new int[size];

                ExecutorService executor = Executors.newFixedThreadPool(parallismLevel);

                //Llamada al Garbage Collector
                System.gc();

                //Benchmark
                long startTime = System.nanoTime();
                MSiterativoParalelo.sort(arrayCopy, aux, executor);
                long endTime = System.nanoTime();

                long time = endTime - startTime;
                times[trial] = time;

                totalTime += time;

                executor.shutdown();
            }

            //Encontrar máximo y mínimo
            long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
            for (long l : times) if (l > max) max = l;
            for (long p : times) if (p < min) min = p;

            //Calcular el promedio
            long average = (totalTime - min - max) / (numtrials - 2);
            averages[i] = average;
            minValues[i] = min;
            maxValues[i] = max;

            System.out.println("Size: "+size+" \t\t Time: "+average);
        }

        writeResults();
    }

    public static void writeResults() throws IOException {
        try (FileWriter writer = new FileWriter(csvName)) {
            for (int i = 0; i<arraySizes.length; i++) {
                //Escribir etiqueta
                writer.append(";").append(String.valueOf(arraySizes[i]));
                //Escribir datos
                writer.append(";").append(String.valueOf(averages[i]));
                writer.append(";").append(String.valueOf(minValues[i]));
                writer.append(";").append(String.valueOf(maxValues[i]));

                writer.append("\n");
            }
        }
    }

    private static int[] generateArray(int size) {
        return RANDOM.ints(size, 0, Integer.MAX_VALUE).toArray();
    }

    private static int[] generateSizes() {
        return new int[]
                {10, 45, 100, 450, 1_000, 4_500, 10_000, 45_000, 100_000,
                450_000, 1_000_000, 4_500_000, 10_000_000, 45_000_000, 100_000_000};
    }

}

