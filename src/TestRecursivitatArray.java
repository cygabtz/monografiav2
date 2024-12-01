public class TestRecursivitatArray {

    public static void main(String[] args) {

        int[] numeros = {2, 5, 1, 8, 9};

        recursiva(numeros, 0);
    }

    public static void recursiva(int[] nums, int i){
        if(i==nums.length/2){

        }
        else{
            int temp = nums[i];
            nums[i] = nums[nums.length - i -1];
            nums[nums.length - i - 1] = temp;
            System.out.println("I: "+i);
            printArray(nums);
            recursiva(nums, i+1);
        }
    }


    public static void printArray(int[] array){
        for(int i=0; i<array.length; i++){
            System.out.print(array[i]+", ");
        }
        System.out.println();
    }
}
