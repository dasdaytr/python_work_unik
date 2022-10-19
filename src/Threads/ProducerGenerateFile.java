package Threads;
import Constats.SystemKeys;
import Enums.TypeFile;
import Utils.Pair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerGenerateFile extends Thread{
    private ArrayBlockingQueue<Pair<File,Integer>> queue ;
    private  static final String PathToDirFiles = File.separator + "src" + File.separator +"files" + File.separator;
    private  static final TypeFile[] values = {TypeFile.JSON,TypeFile.XLS, TypeFile.XML};
    private  static final List<TypeFile> listTypes = List.of(values);
    private  static final int SIZE = listTypes.size();
    private Stack<Pair<File,Integer>> stackFiles = new Stack<>();
    private  static final Random random = new Random();
    public ProducerGenerateFile(ArrayBlockingQueue<Pair<File,Integer>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String pathFile = System.getProperty(SystemKeys.CURRENT_DIR_KEY);
        while (true){
            int timeToSleep = ThreadLocalRandom.current().nextInt(100, 1000 + 1);
            try {
                Thread.sleep(timeToSleep);
                String fileName = UUID.randomUUID().toString();
                TypeFile typeFile = getFileType();
                File file = new File(pathFile + PathToDirFiles + fileName + "."+ typeFile.getType());
                int fileSize = ThreadLocalRandom.current().nextInt(10, 100 + 1);
                writeToFile(file,fileSize);
                Pair<File,Integer> pair = new Pair<>(file,timeToSleep);
                if(queue.size() == 5){
                    System.out.println("Очередь переполнена---->ожидание");
                    stackFiles.push(pair);
                    continue;
                }
                stackFiles.push(pair);
                queue.put(stackFiles.pop());
                System.out.println("Файл создан:" + file.getName() + "--->" + "Время генирации=" + timeToSleep);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeToFile(File file, int size) throws IOException {
        byte[] dataByte = generateData(size).getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(dataByte);
        FileOutputStream f = new FileOutputStream(file);
        baos.writeTo(f);
    }
    private static String generateData(int size){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < size; i++){
            s.append((char) i);
        }
        return s.toString();
    }
    private static TypeFile getFileType(){
        return listTypes.get(random.nextInt(SIZE));
    }
}
