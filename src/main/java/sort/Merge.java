package sort;

import java.util.List;

public class Merge implements Runnable {

    private int[] arr;
    private int[] leftArr;
    private int[] rightArr;

    public Merge(int[] input, int[] leftArr, int[] rightArr) {
        this.arr = input;
        this.leftArr = leftArr;
        this.rightArr = rightArr;
    }

    public void run() {
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
