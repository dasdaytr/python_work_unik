package Practics;

import Constats.SystemKeys;
import Enums.CopyType;
import Model.FileInfo;
import Service.SuperFIle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Practic2 {
    private  static final String PathToDirFiles = File.separator + "src" + File.separator +"files" + File.separator;
    private  static final String PathMain = System.getProperty(SystemKeys.CURRENT_DIR_KEY);
    private  static HashMap<String, FileInfo> files;
    private  static  HashMap<Integer,String> updateLines = new HashMap<>();
    private  static HashMap<Integer,String> deleteLines = new HashMap<>();




    public void start(){

    }

    private static void startPractice2Exercise1() throws IOException {
        String pathMain = System.getProperty(SystemKeys.CURRENT_DIR_KEY);
        Path path = Paths.get(pathMain + PathToDirFiles + "test.txt");
        try(SeekableByteChannel sbc = Files.newByteChannel(path)) {
            final int BUFFER_CAPACITY = 10;
            ByteBuffer buf = ByteBuffer.allocate(BUFFER_CAPACITY);
            String encoding = System.getProperty(SystemKeys.FILE_ENCODING_KEY);
            while (sbc.read(buf) > 0) {
                buf.flip();
                System.out.print(Charset.forName(encoding).decode(buf));
                buf.clear();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startPractice2Exercise2() throws IOException {
        File file = new File(PathMain + PathToDirFiles + "100file.txt");
        File fileTo = new File(PathMain + PathToDirFiles + "cop.txt");
        File testFileClass = new File(PathMain + PathToDirFiles + "file_class.txt");
        if(!file.exists()) writeToFile(file,1024 * 1024 * 10);
        if(fileTo.exists()) fileTo.delete();
        //Копироавние файла с помощью FileInputStream/FileOutputStream

        SuperFIle superFIle = new SuperFIle();
        long start = System.currentTimeMillis();
        superFIle.copy(CopyType.DEFAULT,file,fileTo);
        long finis = System.currentTimeMillis();

        System.out.println("Прошло времени, мс: DEFAULT --> "+ (finis - start));
        fileTo.delete();
        start = System.currentTimeMillis();
        superFIle.copy(CopyType.FILE_CHANEL,file,fileTo);
        finis = System.currentTimeMillis();
        System.out.println("Прошло времени, мс: FILE_CHANEL --> "+ (finis - start));

        start = System.currentTimeMillis();
        superFIle.copy(CopyType.FILE_CLASS,file,testFileClass);
        finis = System.currentTimeMillis();
        System.out.println("Прошло времени, мс: FILE_CLASS --> "+ (finis - start));

        start = System.currentTimeMillis();
        superFIle.copy(CopyType.APACHE_IO,file,testFileClass);
        finis = System.currentTimeMillis();
        System.out.println("Прошло времени, мс: APACHE_IO --> "+ (finis - start));
    }
    private static void startPractice2Exercise4() throws ClassNotFoundException {
        files = new HashMap<>();
        String pathToDirFiles = PathMain + PathToDirFiles;
        File[] filesDir = new File(pathToDirFiles).listFiles(x->!x.isDirectory());

        for(File file: Objects.requireNonNull(filesDir)){
            try {
                byte[] bytesFile = Files.readAllBytes(file.toPath());
                files.put(file.getName(),new FileInfo(file.getName(),bytesFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(PathMain + PathToDirFiles);
            path.register(watchService,StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            List<String> list = new ArrayList<>();
            while ((key = watchService.take()) != null){
                for(WatchEvent<?> event: key.pollEvents()){
                    System.out.println(event.context().toString() + "  " +files.containsKey(event.context().toString()) );
                    if(files.containsKey(event.context().toString())){
                        FileInfo fileInfo = files.get(event.context().toString());
                        byte[] bytesFile =fileInfo.getFileByte();

                        String strOld = new String(bytesFile);

                        Path pathF = Paths.get(PathMain + PathToDirFiles + fileInfo.getFileName());
                        byte[] updateByte = Files.readAllBytes(pathF);
                        String strNew = new String(updateByte);
                        String[] strLinesOld = strOld.split(System.lineSeparator());
                        String[] strLinesNew = strNew.split(System.lineSeparator());



                        fileInfo.setFileByte(updateByte);

                        files.replace(fileInfo.getFileName(),fileInfo);
                        findInsertLines(strLinesOld,strLinesNew);
                        System.out.println("Новые строки-->>>" + updateLines);
                        System.out.println("Удаленные строки-->>>" + deleteLines);
                        updateLines.clear();
                        deleteLines.clear();
                    }
                    System.out.println("Event type " + event.kind() + " File " + event.context());
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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
        return String.valueOf((char) 120).repeat(Math.max(0, size));
    }
    private static void findInsertLines(String[] oldStrLines, String[] newStrLines){
        int step = 0;
        for(int i = 0; i < oldStrLines.length; i++){
            if(i + step >= newStrLines.length) {
                continue;
            }
            if((i + step) >= oldStrLines.length) continue;
            if(!oldStrLines[i + step].equals(newStrLines[i])){
                int  findLeft = findLineLeft(i,oldStrLines[i + step],newStrLines);
                int  findRight = findLineRight(i,oldStrLines[i + step],newStrLines);
                if(findLeft != -1){
                    updateLines.put(i,newStrLines[i]);
                    step = findLeft;
                    continue;
                }
                if(findRight != -1){
                    step = findRight;
                    updateLines.put(i,newStrLines[i]);
                    continue;
                }
                deleteLines.put(i,oldStrLines[i]);
            }
        }
    }
    private static int  findLineRight(int positionStart, String strFind, String[] newStrLines){

        int step = 0;
        for(int i = positionStart; i < newStrLines.length; i++){
            if(strFind.equals(newStrLines[i])) return step;
            step++;
        }
        return -1;
    }
    private static int  findLineLeft(int positionStart, String strFind, String[] newStrLines){
        int step = 0;
        for(int i = positionStart; i >=0; i--){
            if(strFind.equals(newStrLines[i]))return step;
            step++;
        }
        return -1;
    }
    static ArrayList<Integer> findDifference(int[] a, int[] b) {
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        Arrays.stream(a).forEach(e -> list1.add(e));
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        Arrays.stream(b).forEach(e -> list2.add(e));

        ArrayList<Integer> list1Copy = new ArrayList<Integer>();
        ArrayList<Integer> list2Copy = new ArrayList<Integer>();
        list1Copy.addAll(list1);
        list2Copy.addAll(list2);

        list1.forEach(e -> list2Copy.remove(e));
        list2.forEach(e -> list1Copy.remove(e));
        list1Copy.addAll(list2Copy);
        return list1Copy;
    }

}
