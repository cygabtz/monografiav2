package Algoritmos.Tests;

import Algoritmos.MergeSortRecursivoParalelo_V0;

import java.util.concurrent.ForkJoinPool;

public class MSRPtest {
    public static void main(String[] args) {
        int [] arr = MainTester.generateArray(100);

        System.out.println("Array desordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
        System.out.println();

        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() -1);

        MergeSortRecursivoParalelo_V0 task = new MergeSortRecursivoParalelo_V0(arr, arr.length);

        forkJoinPool.invoke(task);

        System.out.println("Array ordenado: ");
        for(int i = 0; i<arr.length; i++) System.out.print(arr[i] + " ");
    }
}
