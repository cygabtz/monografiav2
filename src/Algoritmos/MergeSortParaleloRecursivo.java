package Algoritmos;

import java.util.concurrent.RecursiveAction;

public class MergeSortParaleloRecursivo extends RecursiveAction {

    private final int[] A;
    private final int length;
    public MergeSortParaleloRecursivo(int[] A, int length){
        this.A = A;
        this.length = length;
    }
    @Override
    protected void compute() {
        if (length <=1) return;

        final int mid = length / 2;
        int[] L = new int[mid];
        int[] R = new int[length - mid];

        for (int i = 0; i < mid; i++)   L[i] = A[i];
        for (int i = mid; i < length; i++)  R[i - mid] = A[i];

        final MergeSortParaleloRecursivo Left = new MergeSortParaleloRecursivo(L, mid);
        final MergeSortParaleloRecursivo Right = new MergeSortParaleloRecursivo(R, length - mid);

        invokeAll(Left, Right);

        merge(A, L, R, mid, length - mid);
    }

    public static void merge(int[] A, int[] L, int[] R, int left, int right) {
        int i = 0, j = 0, k = 0;

        while (i < left && j < right)   A[k++] = (L[i] <= R[j])? L[i++] : R[j++];

        while (i < left)    A[k++] = L[i++];
        while (j < right)   A[k++] = R[j++];
    }
}
