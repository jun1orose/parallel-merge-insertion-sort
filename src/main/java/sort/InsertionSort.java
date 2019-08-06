import java.util.ArrayList;
import java.util.List;

public class InsertionSort implements Runnable {

    private List<Integer> array;

    public InsertionSort(ArrayList<Integer> input) {
        this.array = input;
    }
    
    public void run() {
        for (int i = 1; i <= array.size(); i++) {
            int x = array.get(i);
            int j = i - 1;

            while (j >= 0 && array.get(j) > x) {
                array.set(j + 1, array.get(j));
                j = j - 1;
            }

            array.set(j + 1, x);
        }
    }
}
