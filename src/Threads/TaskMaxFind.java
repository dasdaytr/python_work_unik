package Threads;

import java.util.concurrent.RecursiveTask;

public class TaskMaxFind  extends RecursiveTask <Integer> {
    private int l;
    private int r;
    private int[] arr;

    public TaskMaxFind(int l, int r, int[] arr) {
        this.l = l;
        this.r = r;
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if( r - l < 1000){
            try {
                return getMaxElement(arr, l, r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int mid = (r + l) / 2;

        RecursiveTask<Integer> left = new TaskMaxFind(l, mid, arr);
        RecursiveTask<Integer> right = new TaskMaxFind(mid, r, arr);

        left.fork();
        right.fork();

        return Math.max(left.join(),right.join());
    }


    public  static int getMaxElement(int [] array, int left, int right) throws InterruptedException {
        int max = Integer.MIN_VALUE;
        for(int i =left; i < right; i++ ){
            if(array[i] > max){
                max = array[i];
                Thread.sleep(1);
            }

        }

        return max;
    }
}
