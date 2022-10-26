package Tasks;

import java.util.List;
import java.util.concurrent.Callable;

public class TaskFindMax implements Callable<Void> {

    private int left;
    private int right;
    private int[] arr;
    private List<Integer> maxList;

    public TaskFindMax(int left, int right, int[] arr, List<Integer> maxList ) {
        this.left = left;
        this.right = right;
        this.arr = arr;
        this.maxList = maxList;
    }

    @Override
    public Void call() throws InterruptedException {
        maxList.add(getMaxElement(arr,left,right));
        return null;
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
