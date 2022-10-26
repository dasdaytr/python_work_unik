import Constats.SystemKeys;
import Enums.TypeFile;
import Model.UserFriend;
import Observers.CO2Observer;
import Observers.ConsumerObserver;
import Observers.GenerateObserverble;
import Observers.TemperatureObserver;
import Utils.Pair;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    private  static ArrayBlockingQueue<Pair<File,Integer>> queue = new ArrayBlockingQueue<>(5);
    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> observableTemperature = Observable.interval(1, TimeUnit.SECONDS)
                .flatMap(getF(15,30));

        Observable<Integer> observableCo2 = Observable.interval(1, TimeUnit.SECONDS)
                .flatMap(getF(30,100));


        TemperatureObserver temperatureObserver = new TemperatureObserver();
        CO2Observer co2Observer = new CO2Observer();

        observableTemperature.subscribe(temperatureObserver);
        observableCo2.subscribe(co2Observer);
        /////1---задание



        ///2.1.2<-----
        Observable.range(0,1000)
                        .map(x->ThreadLocalRandom.current().nextInt(0,1001))
                        .filter(x->x > 500)
                        .toList()
                        .subscribe(System.out::println);

        //2.1.2---->

        //2.2.1<------
        Random r = new Random();
        Observable.range(0,1000).map(x->(char)(r.nextInt('Z' - 'A') + 'A'))
                        .zipWith(Observable.range(0,1000).map(x->ThreadLocalRandom.current().nextInt(0,1001)), (x,y) -> x.toString() + y)
                        .toList()
                        .subscribe(System.out::println);
        //2.2.1------>


        //2.3.1<-----

        Observable.range(0,10).map(x->ThreadLocalRandom.current().nextInt(0,1001))
                .skip(3)
                        .toList()
                                .subscribe(System.out::println);

        //2.3.1----->

        ///3

        Integer[] arrUserId = new Integer[10];
        for(int i = 0; i < arrUserId.length; i++)
           arrUserId[i] = ThreadLocalRandom.current().nextInt(1,100);


       Observable<Integer> userIdObservable = Observable.fromArray(arrUserId);

       Observable<Observable<UserFriend>> test = userIdObservable.map(Main::getFriends);

       test.subscribe(x->x.forEach(System.out::println));



        //4


        Observable<Pair<File,Integer>> observableGenerate = Observable
                .create(new GenerateObserverble(queue))
                .observeOn(Schedulers.newThread());


        ConsumerObserver consumerObserver = new ConsumerObserver(queue);
        observableGenerate.subscribe(consumerObserver);


        Thread.sleep(1000000);
    }

    private static Function<Long, ObservableSource<? extends Integer>> getF(int min, int max){
        return new Function<Long, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(Long aLong) throws Throwable {
                return Observable.just(ThreadLocalRandom.current().nextInt(min,max + 1));
            }
        };
    }

    private static Observable<UserFriend> getFriends(int userId){
        UserFriend[] arrFriends = new UserFriend[1000];
        for (int i = 0; i< arrFriends.length; i++ )
            arrFriends[i] = new UserFriend(
                    ThreadLocalRandom.current().nextInt(1,100),
                    ThreadLocalRandom.current().nextInt(1,100)
            );

        Observable<UserFriend> observableArrUserId =  Observable.fromArray(arrFriends);
        return observableArrUserId.filter(x-> x.getUserId() == userId);
   }

}
