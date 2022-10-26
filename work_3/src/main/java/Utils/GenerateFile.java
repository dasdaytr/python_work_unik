package Utils;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

import java.io.File;

public class GenerateFile implements Function<Long, ObservableSource<? extends Pair<File, Integer>>> {
    @Override
    public ObservableSource<? extends Pair<File, Integer>> apply(Long aLong) throws Throwable {
        return null;
    }
}
