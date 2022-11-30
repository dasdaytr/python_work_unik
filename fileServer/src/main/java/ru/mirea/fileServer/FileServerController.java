package ru.mirea.fileServer;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.fileServer.Model.ServerInfo;
import ru.mirea.fileServer.Model.StorageFile;
import ru.mirea.fileServer.repository.StorageRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RestController
public class FileServerController {


    private final Path fileStorageLocation;
    private final ServerInfo serverInfo;
    private final StorageRepository storageRepository;
    public FileServerController(ServerInfo serverInfo, StorageRepository storageRepository) {
        this.serverInfo = serverInfo;
        this.storageRepository = storageRepository;
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + File.separator +"Files").toAbsolutePath().normalize() ;
    }

    @GetMapping("download")
    public ResponseEntity<?> download(String fileName) throws URISyntaxException, FileNotFoundException {

        File file = new File(System.getProperty("user.dir") + File.separator +"Files" + File.separator + fileName);
        if(!file.exists()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Файл не был найден на сервере -->" + serverInfo.getAddress());
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("upload")
    public  UploadFileResponse upload(@RequestParam("file")MultipartFile file) throws URISyntaxException, IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        StorageFile storage = new StorageFile(serverInfo.getUuid(),fileName);
        storageRepository.save(storage);
        return new UploadFileResponse(fileName, "download", serverInfo.getAddress(),
                file.getContentType(), file.getSize());
    }

    @GetMapping("info")
    public ServerInfo GetInfo(){

        return serverInfo;
    }
}
