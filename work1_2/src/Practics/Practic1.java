package Practics;

import Random.ArrayRandomizer;
import Tasks.TaskFindMax;
import Tasks.TaskInt;
import Tasks.TaskMonitor;
import Threads.ConsumerFile;
import Threads.ProducerGenerateFile;
import Threads.TaskMaxFind;
import Threads.ThreadMaxFind;
import Utils.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Practic1 {

    private  static ArrayBlockingQueue<Pair<File,Integer>> queue = new ArrayBlockingQueue<>(5);



    public void start() throws InterruptedException {
        startPractice1Exercise1();
    }


    private void startPractice1Exercise1() throws InterruptedException {
        ArrayRandomizer randomizer = new ArrayRandomizer(10000000);

        int[] arr = randomizer.getRandomArrayInt();
        long start = System.currentTimeMillis();
        int resMax = getMaxElement(arr);
        long finis = System.currentTimeMillis();
        System.out.println("Max element consistent ---> " + resMax + " time ---> " + (finis - start));

        start = System.currentTimeMillis();
        RecursiveTask<Integer> recursiveTask = new TaskMaxFind(0, arr.length,arr);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        resMax = forkJoinPool.invoke(recursiveTask);
        finis = System.currentTimeMillis();
        System.out.println("Max element forkJoin ---> " + resMax + " time ---> " + (finis - start));

        List<Integer> list = new ArrayList<>();
        int left = 0;
        int k = arr.length / 10;
        int right = k;
        start = System.currentTimeMillis();
        for(int i = 0; i < 10; i++){
            ThreadMaxFind threadMaxFind = new ThreadMaxFind(left,right,arr,list);
            left = right;
            right += k;
            threadMaxFind.start();
            threadMaxFind.join();
            System.out.println("Поток -->" + threadMaxFind.getId());

        }
        finis = System.currentTimeMillis();
        System.out.println("Max element thread ---> " + Collections.max(list) + " time ---> " + (finis - start));

        left = 0;
        k = arr.length / 10;
        right = k;
        ExecutorService executorService = Executors.newCachedThreadPool();
        list.clear();
        start = System.currentTimeMillis();
        for(int i = 0; i < 10; i++){
            TaskFindMax taskFindMax = new TaskFindMax(left,right,arr,list);
            left = right;
            right += k;
            executorService.submit(taskFindMax);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finis = System.currentTimeMillis();
        System.out.println("Max element future ---> " + Collections.max(list) + " time ---> " + (finis - start));

        System.out.println("Max element stream ---> " + Arrays.stream(arr).max().getAsInt());
    }

    private void startPractice1Exercise2(){
        Scanner scanner = new Scanner(System.in);
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (scanner.hasNextInt()){
            int n = scanner.nextInt();
            TaskInt task = new TaskInt(n);
            Future<Integer> f =  executorService.submit(task);
            executorService.submit(new TaskMonitor(f));
        }
    }

    private  void startPractice1Exercise3() throws IOException {
        ProducerGenerateFile producerGenerateFile = new ProducerGenerateFile(queue);
        ConsumerFile consumerFile = new ConsumerFile(queue);
        producerGenerateFile.start();
        consumerFile.start();
    }

    public  int getMaxElement(int [] array) throws InterruptedException {
        int max = Integer.MIN_VALUE;
        for (int element: array) {
            if(element > max){
                max = element;
                Thread.sleep(1);
            }

        }
        return max;
    }
    public int getMinElement(int [] array){
        int min = Integer.MAX_VALUE;
        for (int element: array) {
            if(element < min)
                min = element;
        }
        return min;
    }
}
