package DesarrolloAlgoritmos.Tests;

import DesarrolloAlgoritmos.MergeSortIterativoParalelo_V1;

public class MSIPtest {
    public static void main(String[] args) {
        int[] arr = MainTester.generateArray(100_000);

        System.out.println("Array desordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
        MergeSortIterativoParalelo_V1.sort(arr);
        System.out.println();


        System.out.println("Array ordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
    }
}
