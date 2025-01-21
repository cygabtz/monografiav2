package DetUmbralFinal;
import AlgoritmoFinal.MSrecursivoSerial;
import Algoritmos.MergeSortRecursivoParalelo_V1;
import Algoritmos.Tests.MainTester;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MSrecursivoParaleloCustomThresh extends RecursiveAction {

    //Atributos constantes
    private final int[] arr, aux;
    public int THRESHOLD = 8192;
    //Atributos dinámicos
    private final int right, left;

    public MSrecursivoParaleloCustomThresh(int[] arr, int[] aux, int left, int right){
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
        if (length < THRESHOLD) {
            MSrecursivoSerial.sort(arr, aux, left, right);
        } else {
            int mid = left + (right - left) / 2;

            //Instanciación de nuevas tareas
            final MSrecursivoParaleloCustomThresh Left = new MSrecursivoParaleloCustomThresh(arr, aux, left, mid);
            final MSrecursivoParaleloCustomThresh Right = new MSrecursivoParaleloCustomThresh(arr, aux, mid+1, right);

            invokeAll(Left, Right);

            merge(arr, aux, left, mid, right);
        }
    }

    private static void merge(int[] arr, int[] aux, int left, int mid, int right) {
        for (int i = left; i <= right; i++) aux[i] = arr[i];

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) arr[k++] = (aux[i] <= aux[j])? aux[i++] : aux[j++];

        while (i <= mid) arr[k++] = aux[i++];
    }

    public void setThresholdAt(int threshold){
        this.THRESHOLD = threshold;
    }
}
