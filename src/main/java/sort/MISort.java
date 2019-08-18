package sort;

import threadpool.CustomThreadPool;
import threadpool.Task;
import threadpool.TaskCounter;

import java.util.Random;

public class MISort {

    private static int INSERTION_SORT_BOUND = 100;
    private static CustomThreadPool threadPool;

    public static void main(String[] args) throws InterruptedException {
        int[] sortingArray = new Random()
                .ints(10000000)
                .toArray();

        threadPool = new CustomThreadPool(8);
        mergeInsertionSort(sortingArray, sortingArray.length, 1, new TaskCounter(2));

        threadPool.terminate();
        threadPool.awaitTerminating();
    }

    private static void mergeInsertionSort(int[] input, int length, int recursionDepth, TaskCounter prevTaskCounter) {
        if (length <= INSERTION_SORT_BOUND) {
            Task insertionSort = new InsertionSort(recursionDepth, prevTaskCounter, new TaskCounter(0), input);
            threadPool.addTask(insertionSort);
            return;
        }

        int mid = length / 2;
        int[] left = new int[mid];
        int[] right = new int[length - mid];

        System.arraycopy(input, 0, left, 0, mid);
        System.arraycopy(input, mid, right, 0, length - mid);

        TaskCounter currTaskCounter = new TaskCounter(2);

        mergeInsertionSort(left, mid, recursionDepth + 1, currTaskCounter);
        mergeInsertionSort(right, length - mid, recursionDepth + 1, currTaskCounter);

        Task mergeResults = new Merge(recursionDepth, prevTaskCounter, currTaskCounter, input, left, right);
        threadPool.addTask(mergeResults);
    }

}
