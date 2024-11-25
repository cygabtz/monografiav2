package Concurrency.MergeSort;

public class MergeSortMain {
    public static void main(String[] args) {
        int[] array = {8, 2, 5, 3, 4, 7, 6, 1};

        mergeSort(array);

    }

    private static void mergeSort(int[] array){
        int lenght = array.length;
        if(lenght<=1) return; //???

        int middle = lenght/2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[lenght - middle];

        int i = 0; //left array
        int j = 0; //right array

        //Copying from original to sub-arrays
        for(; i< lenght; i++){
            if(i < middle){
                leftArray[i] = array[i];
            }
            else{
                rightArray[j] = array[i];
                j++;
            }
        }

        mergeSort(leftArray);
        mergeSort(rightArray);
        merge(leftArray, rightArray, array);

    }

    private static void merge(int[] leftArray, int[] rightArray, int[] array){
        int leftSize = array.length / 2;
        int rightSize = array.length - leftSize;

        int i = 0, l = 0, r = 0;

        //check for merging
        while(l < leftSize && r < rightSize){

            if(leftArray[l] < rightArray[r]){
                array[i] = leftArray[l];
                i++;
                l++;
            }
            else{
                array[i] = rightArray[r];
                i++;
                r++;
            }
        }
        while(l < leftSize){
            array[i] = leftArray[l];
            i++;
            l++;
        }
        while(r < rightSize){
            array[i] = rightArray[r];
            i++;
            r++;
        }
    }
}
