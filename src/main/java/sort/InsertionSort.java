package sort;

import threadpool.Task;
import threadpool.TaskCounter;

public class InsertionSort extends Task {

    private int[] arr;

    InsertionSort(int priority, TaskCounter countOfRelatedTasks,
                  TaskCounter childTaskCounter, int[] input) {

        super(priority, countOfRelatedTasks, childTaskCounter);
        this.arr = input;
    }

    public void solve() {
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
