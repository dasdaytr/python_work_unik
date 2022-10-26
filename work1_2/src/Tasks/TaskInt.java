package Tasks;

import java.util.concurrent.Callable;

public class TaskInt implements Callable<Integer> {
    private int number;

    public TaskInt(int number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(5000);
        return number * number;
    }
}
