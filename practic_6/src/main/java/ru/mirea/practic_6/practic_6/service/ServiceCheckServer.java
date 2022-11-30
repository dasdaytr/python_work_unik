package ru.mirea.practic_6.practic_6.service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import ru.mirea.practic_6.practic_6.Model.Server;
import ru.mirea.practic_6.practic_6.repository.ServerRepository;

import java.io.IOException;
import java.util.List;
@Service
public class ServiceCheckServer implements ServiceCheckServerI{

    private ServerRepository serverRepository;

    public ServiceCheckServer(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void checkServer() {
        List<Server> listServer = serverRepository.findAll();
        if(listServer.isEmpty()){
            System.out.println("Нет доступных серверов");
        }
        listServer.forEach(x->{
            OkHttpClient client = new OkHttpClient();
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host(x.getServerUrl())
                    .port(x.getServerPort())
                    .addPathSegment("info")
                    .build();
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .method("GET",null)
                    .build();

            try {
                Response httpResponse = client.newCall(request).execute();
                if(httpResponse.code() >= 500){
                    System.out.println("Сервер недоступен -->port=" + x.getServerPort());
                    serverRepository.delete(x);
                }else
                    System.out.println("Сервер доступен -->port=" + x.getServerPort());
            } catch (IOException e) {
                serverRepository.delete(x);
                System.out.println("Не получилось отправить запроса -- ошибка=" + e.getMessage());
            }
        });
    }
}
