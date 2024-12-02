package Algoritmos;

import java.util.SplittableRandom;
import java.io.FileWriter;
import java.io.IOException;

import static Algoritmos.MergeSortRecursivoSerial.*;

public class MSRStest {

    public static void main(String[] args) throws IOException{
        //Generar el array de longitudes. Un total de 9.
        int[] sizes = new int[8];
        for (int i = 0; i<sizes.length; i++){
            sizes[i] = (int) Math.pow(10, i+1);
        }
        System.out.println("SIZES SUCCESSFUL");

        int reps = 5;
        long[][] results = new long[sizes.length][reps];

        //Benchmark
        for (int i = 0; i<sizes.length; i++){
            for (int j = 0; j<reps; j++){
                int [] array = generateArray(sizes[i]);

                long startTime = System.nanoTime();

                try{
                    MSRS(array, 0, array.length-1);
                } catch (OutOfMemoryError e){
                    e.printStackTrace();
                }

                long endTime = System.nanoTime();

                results [i][j] = endTime - startTime;

                System.out.println("Array with size " + sizes[i] + " successfully sorted in " + results[i][j]);
            }
        }

        //Calcular promedios
        long [] averages = new long[sizes.length];
        for (int i = 0; i<sizes.length; i++){
            for (int j = 0; j<reps; j++){
                averages[i] = results[i][j];
            }
            averages[i] /= reps;
        }

        System.out.println("AVERAGES SUCCESSFULL");

        writeResultsToCSV(sizes, averages);
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

    public static void writeResultsToCSV(int[] sizes, long[] averages) throws IOException{
        try (FileWriter writer = new FileWriter("MSRS_Benchmark.csv")){
            writer.append("Longitud colección,Promedio ejecución (ns)\n");
            for (int i = 0; i<sizes.length; i++){
                writer.append(sizes[i] + "," + averages[i] + "\n");
            }
        }
    }
}
