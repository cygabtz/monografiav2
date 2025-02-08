package Implementaciones;

import java.util.concurrent.RecursiveAction;

public class MSrecursivoParalelo extends RecursiveAction {

    //Atributos constantes
    private final int[] arr, aux;
    public static int THRESHOLD = 32768;
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
}
