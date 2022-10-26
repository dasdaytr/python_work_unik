package Random;

import java.lang.reflect.Array;
import java.util.Random;


public class ArrayRandomizer <T extends Number> {
    private  T[] arrayT;
    private  int[] arrayI;
    public ArrayRandomizer(Class<T[]> clazz,int length){
        this.arrayT  = clazz.cast(Array.newInstance(clazz.getComponentType(),length));
    }

    public ArrayRandomizer(int length){
        this.arrayI  = new int[length];
    }
    public T[] getRandomArray(){
        return  arrayT;
    }
    public int[] getRandomArrayInt(){
        Random random = new Random();
        for(int i = 0; i < arrayI.length; i++){
            arrayI[i] = random.nextInt(100000);
        }
        return  arrayI;
    }
}
