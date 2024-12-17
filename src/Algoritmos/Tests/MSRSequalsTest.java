package Algoritmos.Tests;

import Algoritmos.MergeSortRecursivoSerial_V1;

public class MSRSequalsTest {
    public static void main(String[] args) {
        int [] actual = MSRStest.generateArray(100);
        System.out.println("Actual array: ");
        printArray(actual);

        MergeSortRecursivoSerial_V1.sort(actual, actual.length);

        System.out.println("Sorted array: ");
        printArray(actual);

    }

    public static void printArray(int[] arr){
        for (int i : arr) System.out.print(i + " ");
        System.out.println();
    }

    public boolean areEquals(int[] arrA, int[] arrB){
        boolean equals = true;
        if (arrA.length == arrB.length){
            for (int i = 0; i<arrA.length; i++){
                equals = arrA[i] == arrB[i];
            }
            return equals;
        }
        else {return false;}
    }
}
