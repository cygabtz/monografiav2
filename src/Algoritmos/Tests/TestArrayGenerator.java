package Algoritmos.Tests;

import static Algoritmos.MergeSortRecursivoSerial_V0.*;
import static Algoritmos.Tests.MSRStest.*;

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
