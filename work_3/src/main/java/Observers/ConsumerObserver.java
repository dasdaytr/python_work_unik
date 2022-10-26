package Observers;

import Utils.Pair;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerObserver implements Observer<Pair<File,Integer>> {
    private  ArrayBlockingQueue<Pair<File, Integer>> queue;

    public ConsumerObserver(ArrayBlockingQueue<Pair<File, Integer>> queue){
        this.queue = queue;
    }
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        System.out.println("onSubscribe");

    }

    @Override
    public void onNext(@NonNull Pair<File, Integer> fileIntegerPair) {
        try {
            File file =fileIntegerPair.key;
            queue.take();
            Thread.sleep(7L * fileIntegerPair.value);
            System.out.println("Обработка файла заверешена: " + file.getName() + "--->Время обработки=" + 7L * fileIntegerPair.value );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
