package Algoritmos.Tests;

import Algoritmos.MergeSortIterativoParalelo;

public class MSIPtest {
    public static void main(String[] args) {
        int[] arr = MainTester.generateArray(100);

        System.out.println("Array desordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
        MergeSortIterativoParalelo.sort(arr);


        System.out.println("Array ordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
    }
}
