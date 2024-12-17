package Algoritmos;

public class MergeSortRecursivoSerial_V0 {
    //https://www.geeksforgeeks.org/merge-sort/
    public static void sort(int[] arr, int left, int right){
        if(left < right){
            //Encontrar el punto medio del array
            //Cálculo seguro con l+(r-l)/2 para evitar desbordamiento
            int mid = left + (right - left) / 2;

            //Ordenar cada mitad
            sort(arr, left, mid);
            sort(arr, mid+1, right);

            //Unir las mitades
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        //Crear arrays temporales y definir su longitud
        int L[] = new int[mid - left + 1];
        int R[] = new int[right - mid];

        //Copiar datos a los arrays
        for (int i = 0; i < L.length; i++){
            L[i] = arr[left + i];
        }
        for (int j = 0; j < R.length; j++){
            R[j] = arr[mid + 1 + j];
        }

        //Índices de los subarrays L y R
        int i = 0, j = 0;

        //Índice del array unión
        int k = left;

        //Unión de los subarrays
        while (i < L.length && j < R.length) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copia de los elementos restantes de L[] si hay
        while (i < L.length) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copia de los elementos restantes de L[] si hay
        while (j < R.length) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void printArray(int[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    public static void main(String [] args){
        int array[] = { 12, 11, 13, 5, 6, 7 };
        
        System.out.println("Array desordenado");
        printArray(array);

        //Paso por referencia
        sort(array, 0, array.length - 1);

        System.out.println("\nArray ordenado");
        printArray(array);
    }
}
