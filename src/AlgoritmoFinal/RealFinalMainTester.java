package AlgoritmoFinal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RealFinalMainTester {

    private static final int[] arraySizes = generateSizes();
    private static final int numtrials = 50;
    private static long[] averages, minValues, maxValues;
    private static long[][] rawTrials;
    private static final String csvName = "testDefinitivoMSiterativoSerial.csv";
    private static final int parallelismLevel = 10;

    public static void main(String[] args) throws IOException {
        averages = new long[arraySizes.length];
        rawTrials = new long[arraySizes.length][numtrials];
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
                //Llamada al Garbage Collector
                System.gc();

                //Benchmark
                long startTime = System.nanoTime();
                MSiterativoSerial.sort(arrayCopy, aux);
                long endTime = System.nanoTime();

                long time = endTime - startTime;
                times[trial] = time;

                totalTime += time;

                rawTrials[i][trial] = time;
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

                for (int j = 0; j<numtrials; j++){
                    writer.append(";").append(String.valueOf(rawTrials[i][j]));
                }

                writer.append("\n");
            }
        }
    }

    public static int[] generateArray(int size) {
        int[] array = new int[size];
        long seed = 6180339887L;
        SplittableRandom random = new SplittableRandom(seed);

        //Todos los arrays constan de una misma secuencia
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000); //De 0 a 999
        }
        return array;
    }

    private static int[] generateSizes() {
        return new int[]
                {10, 45, 100, 450, 1_000, 4_500, 10_000, 45_000, 100_000,
                450_000, 1_000_000, 4_500_000, 10_000_000, 45_000_000};
    }

}

