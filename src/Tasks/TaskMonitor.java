package Tasks;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class TaskMonitor implements Callable<Void> {
    private Future<Integer> taskMonitor;

    public TaskMonitor(Future<Integer> taskMonitor) {
        this.taskMonitor = taskMonitor;
    }

    @Override
    public Void call() throws Exception {
        System.out.println("Finis="+taskMonitor.get());
        return null;
    }
}
