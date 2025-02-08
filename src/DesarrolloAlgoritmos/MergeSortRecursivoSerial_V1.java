package DesarrolloAlgoritmos;

public class MergeSortRecursivoSerial_V1 {
    //Retrieved from: https://www.baeldung.com/java-merge-sort
    public static void sort(int[] A, int length) {
        //Caso base
        if (length <=1) return;

        //Calcular mitad y dividir en dos sub-arrays
        int mid = length / 2;
        int[] L = new int[mid];
        int[] R = new int[length - mid];

        //Copiar al primer array
        for (int i = 0; i < mid; i++)   L[i] = A[i];

        //Copiar al segundo array
        for (int i = mid; i < length; i++)  R[i - mid] = A[i];

        //Llamada recursiva
        sort(L, mid);
        sort(R, length - mid);

        //Unir las dos partes
        merge(A, L, R, mid, length - mid);
    }

    public static void merge(int[] A, int[] L, int[] R, int left, int right) {
        //Índices de L, R, A
        int i = 0, j = 0, k = 0;

        //Hasta recorrer todo un sub-array: llenar en A[k] el menor elemento entre L[i] y R[j]
        while (i < left && j < right)   A[k++] = (L[i] <= R[j])? L[i++] : R[j++];

        //Llenar en A[k] los elementos restantes: aquellos que no han entrado en el bucle anterior
        //Si un sub-array se recorre por completo se copia el sub-array restante a A[]
        while (i < left)    A[k++] = L[i++];
        while (j < right)   A[k++] = R[j++];
    }

    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("Array original:");
        for (int num : array) {
            System.out.print(num + " ");
        }

        sort(array, array.length);

        System.out.println("\n\nArray ordenado:");
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
