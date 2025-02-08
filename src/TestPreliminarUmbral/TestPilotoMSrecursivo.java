package TestPreliminarUmbral;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SplittableRandom;

public class TestPilotoMSrecursivo {
    private static long[] results;
    private static final int maxSize = 10_000_000;
    private static final int numTrials = 50;
    private static final String csvName = "testPilotoMSrecursivo.csv";

    private static final int parallelismLevel = 10;

    public static void main(String[] args) throws IOException {
        results = new long[40];
        for (int size = 10, i = 0; size <= maxSize; size *= 10, i++) {
            int[] array = generateArray(size);

            long[] times = new long[numTrials];
            long totalTime = 0;

            for (int trial = 0; trial < numTrials; trial++) {

                //Arreglo auxiliar
                int[] aux = new int[size];
                int[] arrayCopy = array.clone();

                //Llamada al Garbage Collector
                System.gc();

                //Para el paralelo
                //final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelismLevel);
                //final MSrecursivoParaleloSinUmbral task = new MSrecursivoParaleloSinUmbral(arrayCopy, aux, 0, size-1);

                //Benchmark
                long start = System.nanoTime();

                //En el caso del serial
                //MSrecursivoSerial.sort(array, aux, 0, size-1);

                //En el caso del paralelo
                //forkJoinPool.invoke(task);

                long end = System.nanoTime();

                long time = end - start;
                times[trial] = time;

                totalTime += time;
            }

            //Encontrar máximo y mínimo
            long max = Long.MIN_VALUE, min = Long.MAX_VALUE;
            for (long l : times) if (l > max) max = l;
            for (long p : times) if (p < min) min = p;

            //Calcular el promedio
            long average = (totalTime - min - max) / (numTrials - 2);
            results[i] = average;

            System.out.println("Size: "+size+" \t\t Time: "+average);

        }
        writeResults();
    }

    public static void writeResults() throws IOException {
        try (FileWriter writer = new FileWriter(csvName)) {
            for (int size = 10, i=0; size <= maxSize; size *= 10, i++) {
                //Escribir etiqueta
                writer.append(",").append(String.valueOf(size));
                //Escribir dato
                writer.append(",").append(String.valueOf(results[i]));
                writer.append("\n");
            }
        }
    }

    public static int[] generateArray(int size) {
        int[] array = new int[size];
        long seed = 6180339887L;
        SplittableRandom random = new SplittableRandom(seed);

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000); //De 0 a 999
        }
        return array;
    }

}
