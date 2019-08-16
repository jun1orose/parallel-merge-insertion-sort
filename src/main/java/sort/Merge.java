package sort;

import threadpool.Task;
import threadpool.TaskCounter;

public class Merge extends Task {

    private int[] arr;
    private int[] leftArr;
    private int[] rightArr;

    Merge(int priority, TaskCounter countOfRelatedTasks,
          TaskCounter childTaskCounter, int[] input, int[] leftArr, int[] rightArr) {

        super(priority, countOfRelatedTasks, childTaskCounter);
        this.arr = input;
        this.leftArr = leftArr;
        this.rightArr = rightArr;
    }

    public void solve() {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        while (i < leftArr.length) {
            arr[k++] = leftArr[i++];
        }

        while (j < rightArr.length) {
            arr[k++] = rightArr[j++];
        }

    }
}
