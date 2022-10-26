package Threads;

import Utils.Pair;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerFile extends Thread{
    private ArrayBlockingQueue<Pair<File,Integer>> queue ;
    public ConsumerFile(ArrayBlockingQueue<Pair<File,Integer>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                Pair<File,Integer> result = queue.take();
                File file = result.key;
                Thread.sleep(7L * result.value);
                System.out.println("Обработка файла заверешена: " + file.getName() + "--->Время обработки=" + 7L * result.value );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
