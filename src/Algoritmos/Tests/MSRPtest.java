package Algoritmos.Tests;

import Algoritmos.MergeSortParaleloRecursivo;

import java.util.concurrent.ForkJoinPool;

public class MSRPtest {
    public static void main(String[] args) {
        int [] arr = MainTester.generateArray(100);

        System.out.println("Array desordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
        System.out.println();

        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);

        MergeSortParaleloRecursivo task = new MergeSortParaleloRecursivo(arr, arr.length);

        forkJoinPool.invoke(task);

        System.out.println("Array ordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
    }
}
