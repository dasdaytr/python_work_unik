package unik.rsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;
import unik.rsocket.model.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerRSocket {

    private  List<Message> messages = new ArrayList<>();
    {
        messages.add(new Message("test1"));

        messages.add(new Message("test2"));

        messages.add(new Message("test3"));
    }
    @MessageMapping("currentMarketData")
    public Mono<String> currentMarketData(String request) {
        System.out.println(request);
        return Mono.just(request);
    }
}
