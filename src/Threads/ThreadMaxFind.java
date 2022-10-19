package Threads;

import java.util.List;

public class ThreadMaxFind extends Thread{

    private int left;
    private int right;
    private int[] arr;
    private List<Integer> maxList;

    public ThreadMaxFind(int left, int right, int[] arr, List<Integer> maxList ) {
        this.left = left;
        this.right = right;
        this.arr = arr;
        this.maxList = maxList;
    }

    @Override
    public void run() {
        try {
            maxList.add(getMaxElement(arr,left,right));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
