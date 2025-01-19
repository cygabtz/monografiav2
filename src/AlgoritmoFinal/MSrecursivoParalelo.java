package AlgoritmoFinal;
import Algoritmos.MergeSortRecursivoParalelo_V0;
import Algoritmos.MergeSortRecursivoParalelo_V1;
import Algoritmos.Tests.MainTester;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MSrecursivoParalelo extends RecursiveAction {

    //Atributos constantes
    private final int[] arr, aux;
    public static int THRESHOLD = 1;
    //Atributos dinámicos
    private final int right, left;

    public MSrecursivoParalelo(int[] arr, int[] aux, int left, int right){
        ///Paso por referencia: constantes
        this.arr = arr;
        this.aux = aux;

        //Dinámicos
        this.left = left;
        this.right = right;
    }
    @Override
    protected void compute() {
        int length = (right + 1 - left);
        if (length <= THRESHOLD) {
            MSrecursivoSerial.sort(arr, aux, left, right);
            //System.out.println("Serial sort executed. Th: "+THRESHOLD + "\t\tSize: "+ length);
        } else {
            int mid = left + (right - left) / 2;

            //Instanciación de nuevas tareas
            final MSrecursivoParalelo Left = new MSrecursivoParalelo(arr, aux, left, mid);
            final MSrecursivoParalelo Right = new MSrecursivoParalelo(arr, aux, mid+1, right);

            invokeAll(Left, Right);

            merge(arr, aux, left, mid, right);
        }
    }

    private static void merge(int[] arr, int[] aux, int left, int mid, int right) {
        // Copiar los elementos al arreglo auxiliar
        for (int i = left; i <= right; i++) aux[i] = arr[i];

        int i = left;       // Índice del subarreglo izquierdo
        int j = mid + 1;    // Índice del subarreglo derecho
        int k = left;       // Índice del arreglo original

        // Mezclar los dos subarreglos
        while (i <= mid && j <= right) arr[k++] = (aux[i] <= aux[j])? aux[i++] : aux[j++];

        // Copiar elementos restantes del subarreglo izquierdo (si los hay)
        while (i <= mid) arr[k++] = aux[i++];

        // Los elementos restantes del subarreglo derecho ya están en su lugar
    }

    public static void main(String[] args) {

        //Comparación entre el MSrecursivoParalelo con merge() unificado y sin unificar

        int[] array = MainTester.generateArray(1000);
        System.out.println("Array original:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
        int[] array2 = array.clone();

        System.out.print("\n\nTesting MSrecursivoParalelo unified");

        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);
        MSrecursivoParalelo task = new MSrecursivoParalelo(array, new int[array.length], 0, array.length-1);

        long startTime = System.nanoTime();
        forkJoinPool.invoke(task);
        long endTime = System.nanoTime();

        System.out.println("\n\nArray ordenado:");
        for (int num : array) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println("Time taken: " + (endTime-startTime) );


        System.out.print("\n\nTesting MSrecursivoParalelo non-unified");

        final ForkJoinPool forkJoinPool2 = new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);
        MergeSortRecursivoParalelo_V1 task2 = new MergeSortRecursivoParalelo_V1(array2, array2.length);

        startTime = System.nanoTime();
        forkJoinPool2.invoke(task2);
        endTime = System.nanoTime();
        System.out.println("\n\nArray ordenado:");
        for (int num : array2) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println("Time taken: " + (endTime-startTime) );
    }
}
