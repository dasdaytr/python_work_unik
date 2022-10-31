package com.sfelaco.rsocket.controllers;

import com.sfelaco.rsocket.pojos.Notification;
import com.sfelaco.rsocket.pojos.Product;
import com.sfelaco.rsocket.pojos.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@Slf4j
public class ProductController {
    private static final String CLIENT = "Client";
    private static final String SERVER = "Server";

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private RSocketRequester rSocketRequester;

    @GetMapping("/product/{id}")
    public Mono<Product> getNumAccesses(@PathVariable Integer id){
         return rSocketRequester.route("product")
                .data(id).retrieveMono(Product.class);
    }

    @GetMapping("/product/post")
    public Mono<Product> getNumAccessesPost(){

        ProductResponse p = new ProductResponse(1,"test",5d);
        return rSocketRequester.route("product/post")
                .data(p).retrieveMono(Product.class);
    }
    @GetMapping("/product/fireAndForget")
    public Mono<Void> addProductFireAndForget(ProductResponse productResponse){
        ProductResponse p = new ProductResponse(1,"test",5d);
        return rSocketRequester.route("product/fireAndForget")
                .data(p).send();
    }

    @GetMapping(value = "/product/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<ProductResponse> feed() {
        return rSocketRequester
                .route("feedMarketData")
                .data("stock")
                .retrieveFlux(ProductResponse.class);
    }

    @GetMapping("/channel")
    public void  channel() {

        Mono<Notification> notification0 = Mono.just(new Notification(CLIENT, SERVER, "Test the Channel interaction model"));

        Mono<Notification> notification2 = Mono.just(new Notification(CLIENT, SERVER, "Test the Channel interaction model")).delayElement(Duration.ofSeconds(2));

        Mono<Notification> notification5 = Mono.just(new Notification(CLIENT, SERVER, "Test the Channel interaction model")).delayElement(Duration.ofSeconds(5));



        Flux<Notification> notifications = Flux.concat(notification0, notification5, notification0, notification2, notification2, notification2)

                .doOnNext(d -> logger.info("Send notification for my-channel"));



         this.rSocketRequester

                .route("product/channel")
                .data(notifications)
                .retrieveFlux(Long.class).subscribe(System.out::println);



    }
}
