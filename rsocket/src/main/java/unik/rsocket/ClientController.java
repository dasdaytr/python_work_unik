package unik.rsocket;


import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import unik.rsocket.model.Message;

@RestController
public class ClientController {

    @Autowired
    private Mono<RSocketRequester> rSocketRequester;



    @GetMapping(value = "/current/{stock}")
    public Mono<Message> current(@PathVariable("stock") String stock) {
        return rSocketRequester
                .map(r->r.route("currentMarketData").data(new Message(stock)))
                .flatMap(r->r.retrieveMono(Message.class))
                .doOnNext(r->System.out.println(r.getText()));
    }
}
