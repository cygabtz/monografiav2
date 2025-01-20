package AlgoritmoFinal;

public class MSrecursivoSerial {
    //Basado inicialment en: https://www.baeldung.com/java-merge-sort
    public static void sort(int[] arr, int[] aux, int left, int right) {
        //Caso base
        if (left >= right) return;

        //Calcular la mitad
        int mid = left + (right - left) / 2; //Evitar desbordamiento de Integer.MAX_VALUE

        //Llamada recursiva
        sort(arr, aux, left, mid);
        sort(arr, aux,mid+1, right);

        //Unir las dos partes
        merge(arr, aux, left, mid, right);
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


    //Método de pruba
    public static void main(String[] args) {

        //Comparación entre el MSrecursivoSerial con merge() unificado y sin unificar

        int[] array = Algoritmos.Tests.MainTester.generateArray(1000);
        System.out.println("Array original:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
        int[] array2 = array.clone();


        System.out.print("\n\nTesting MSrecursivoSerial unified");
        long startTime = System.nanoTime();
        sort(array, new int[array.length], 0, array.length-1);
        long endTime = System.nanoTime();

        System.out.println("\n\nArray ordenado:");
        for (int num : array) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println("Time taken: " + (endTime-startTime) );


        System.out.print("\n\nTesting MSrecursivoSerial non-unified");

        startTime = System.nanoTime();
        Algoritmos.MergeSortRecursivoSerial_V1.sort(array2, array2.length);
        endTime = System.nanoTime();
        System.out.println("\n\nArray ordenado:");
        for (int num : array2) {
            System.out.print(num + " ");
        }

        System.out.println();
        System.out.println("Time taken: " + (endTime-startTime) );

        //El unificado es el doble de rápido
    }
}
