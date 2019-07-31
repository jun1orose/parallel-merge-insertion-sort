
public class InsertionSort implements Runnable {

    private int[] array;

    public InsertionSort(int[] input) {
        this.array = input;
    }
    
    public void run() {
        for (int i = 1; i < array.length; i++) {
            int x = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > x) {
                array[j + 1] = array[j];
                j = j - 1;
            }

            array[j+1] = x;
        }
    }
}
