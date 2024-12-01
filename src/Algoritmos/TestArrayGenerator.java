package Algoritmos;

import static Algoritmos.MergeSortRecursivoSerial.*;
import static Algoritmos.MSRStest.*;

public class TestArrayGenerator {
    public static void main(String[] args) {
        int [] arrA = generateArray(100);
        int [] arrB = generateArray(1000);
        System.out.println("Array A: ");
        printArray(arrA);

        System.out.println("Array B: ");
        printArray(arrB);
    }
}
