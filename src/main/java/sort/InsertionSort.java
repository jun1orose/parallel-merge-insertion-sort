package sort;

public class InsertionSort implements Runnable {

    private int[] arr;

    public InsertionSort(int[] input) {
        this.arr = input;
    }
    
    public void run() {
        for (int i = 1; i < arr.length; i++) {
            int x = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > x) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }

            arr[j + 1] = x;
        }
    }
}
