package TestPreliminarUmbral;

import java.io.FileWriter;
import java.io.IOException;
import java.util.SplittableRandom;

public class TestPilotoMSiterativo {
    private static long[] results;
    private static final int maxSize = (int) Math.pow(2, 20);
    private static final int factor = 2;
    private static final int numTrials = 50;
    private static final String csvName = "testPilotoMSiterativo.csv";
    private static final int parallismLevel = 10;

    public static void main(String[] args) throws IOException {
        results = new long[40];
        for (int size = factor, i = 0; size <= maxSize; size *= factor, i++) {
            int[] array = generateArray(size);

            long[] times = new long[numTrials];
            long totalTime = 0;

            for (int trial = 0; trial < numTrials; trial++) {
                int[] aux = new int[size];
                int[] arrayCopy = array.clone();

                //En el caso del paralelo
                //ExecutorService executorService = Executors.newFixedThreadPool(parallismLevel);

                //Llamada al Garbage Collector
                System.gc();

                //Benchmark
                long start = System.nanoTime();

                //En el caso del serial
                //MSiterativoSerial.sort(arrayCopy, aux);

                //En el caso del paralelo
                //MSiterativoParaleloSinUmbral.sort(arrayCopy, aux, executorService);

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
            long average = (totalTime - min - max) / (numTrials-2);
            results[i] = average;

            System.out.println("Size: "+size+" \t\t Time: "+average);

        }
        writeResults();
    }

    public static void writeResults() throws IOException {
        try (FileWriter writer = new FileWriter(csvName)) {
            for (int size = factor, i=0; size <= maxSize; size *= factor, i++) {
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

        //Todos los arrays constan de una misma secuencia
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1000); //De 0 a 999
        }
        return array;
    }

}
