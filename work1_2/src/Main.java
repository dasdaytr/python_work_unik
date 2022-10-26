import Constats.SystemKeys;
import Enums.CopyType;
import Enums.TypeFile;
import Model.FileInfo;
import Practics.Practic1;
import Random.ArrayRandomizer;
import Service.SuperFIle;
import Tasks.*;
import Threads.ConsumerFile;
import Threads.ProducerGenerateFile;
import Threads.TaskMaxFind;
import Threads.ThreadMaxFind;
import Utils.Pair;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Practic1 practic1 = new Practic1();
        practic1.start();
    }

}

