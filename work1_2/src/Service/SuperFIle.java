package Service;

import Enums.CopyType;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class SuperFIle {

    public void copy(CopyType type, File file, File fileTo){

        switch (type){
            case DEFAULT -> defaultCopy(file, fileTo);
            case APACHE_IO -> fileCopyApacheIO(file,fileTo);
            case FILE_CLASS -> fileCopyUseFiles(file,fileTo);
            case FILE_CHANEL -> fileChanelCopy(file,fileTo);
        }
    }

    private void defaultCopy(File file, File fileTo){
        try(FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(fileTo))
        {
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = fis.read(buffer)) > 0){
                fos.write(buffer,0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileChanelCopy(File file, File fileTo){
        try(FileChannel mainChannel = new FileInputStream(file).getChannel();
            FileChannel copyChannel = new FileOutputStream(fileTo).getChannel();
        ) {

            copyChannel.transferFrom(mainChannel,0,mainChannel.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileCopyUseFiles(File file, File fileTo) {
        try {
            Files.copy(file.toPath(),fileTo.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileCopyApacheIO(File file, File fileTo){
        try {
            FileUtils.copyFile(file,fileTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
