package ru.mirea.practic_6.practic_6;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.mirea.practic_6.practic_6.Model.Server;
import ru.mirea.practic_6.practic_6.Model.StorageFile;
import ru.mirea.practic_6.practic_6.repository.ServerRepository;
import ru.mirea.practic_6.practic_6.repository.StorageRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

@RestController
public class TestController {

    private final ServerRepository serverRepository;
    private final StorageRepository storageRepository;

    public TestController(ServerRepository serverRepository, StorageRepository storageRepository) {
        this.serverRepository = serverRepository;
        this.storageRepository = storageRepository;
    }

    @RequestMapping("/proxy/**")
    public ResponseEntity<?> getTest(@RequestBody(required = false) String body,
                                          HttpMethod method, HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws URISyntaxException {

        String requestUrl = request.getRequestURI();
        requestUrl = requestUrl.substring(6);

        List<Server> list = serverRepository.findByServerNameAndIsLive("fileServer",true);

        if(list.isEmpty()){
            System.out.println("Нет доступных серверов");
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
        Server server = list.get(new Random().nextInt(list.size()));
        HttpHeaders headers = new HttpHeaders();
        LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        URI uri = getUri(server,request,requestUrl);
        if(requestUrl.equals("/download")){
            String fileName = request.getParameter("fileName");
            StorageFile storage = storageRepository.findByFileName(fileName);
            if(storage == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не был найден на сервере");
            }
            server = serverRepository.findByUuidAndIsLive(storage.getUuid(),true);
            if(server == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Сервер не найден или недоступен");
            };

            uri = getUri(server,request,requestUrl);
        }
        if(requestUrl.equals("/upload")){
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            Resource invoicesResource = file.getResource();
            parts.add("file", invoicesResource);
        }



        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.set(headerName, request.getHeader(headerName));
        }

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(uri, method, httpEntity, Resource.class);
        } catch(HttpStatusCodeException e) {

            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }

    public URI getUri(Server server, HttpServletRequest request, String path){

        return UriComponentsBuilder.fromHttpUrl("http://"+ server.getServerUrl() + ":" + server.getServerPort())
                .path(path)
                //.queryParam(request.getp)
                .query(request.getQueryString())
                .build(true).toUri();
    }

    @RequestMapping("test")
    public String getTes1t() throws URISyntaxException {


        return "test";
    }
}
