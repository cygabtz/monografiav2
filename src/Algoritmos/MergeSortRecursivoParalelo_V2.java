package Algoritmos;

import java.util.concurrent.RecursiveAction;

public class MergeSortRecursivoParalelo_V2 extends RecursiveAction {

    private final int[] A;
    private final int length;
    private final int max = 8192; //threshold

    public MergeSortRecursivoParalelo_V2(int[] A, int length){
        this.A = A;
        this.length = length;
    }
    @Override
    protected void compute() {
        if (length <= max) {
            MergeSortRecursivoSerial_V2.sort(A, length); System.out.println("MSRS run");
        } else {
            final int mid = length / 2;
            int[] L = new int[mid];
            int[] R = new int[length - mid];

            for (int i = 0; i < mid; i++)   L[i] = A[i];
            for (int i = mid; i < length; i++)  R[i - mid] = A[i];

            final MergeSortRecursivoParalelo_V2 Left = new MergeSortRecursivoParalelo_V2(L, mid);
            final MergeSortRecursivoParalelo_V2 Right = new MergeSortRecursivoParalelo_V2(R, length - mid);

            invokeAll(Left, Right);

            merge(A, L, R, mid, length - mid);
        }
    }

    public static void merge(int[] A, int[] L, int[] R, int left, int right) {
        int i = 0, j = 0, k = 0;

        while (i < left && j < right)   A[k++] = (L[i] <= R[j])? L[i++] : R[j++];

        while (i < left)    A[k++] = L[i++];
        while (j < right)   A[k++] = R[j++];
    }
}
