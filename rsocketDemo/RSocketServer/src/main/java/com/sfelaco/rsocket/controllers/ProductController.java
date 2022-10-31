package com.sfelaco.rsocket.controllers;


import com.sfelaco.rsocket.pojos.Notification;
import com.sfelaco.rsocket.pojos.Product;
import com.sfelaco.rsocket.pojos.ProductResponse;
import com.sfelaco.rsocket.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Controller
@Slf4j
public class ProductController
{
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @MessageMapping("product")
    public Mono<Product> getNumAccesses(int id) {

        return productService.getProductById(id)
                .defaultIfEmpty(new Product());

    }

    @MessageMapping("product/post")
    public Mono<Product> getNumAccessesPost(ProductResponse productResponse) {
        Product p  = new Product();
        p.setDescription(productResponse.getDescription());
        p.setPrice(productResponse.getPrice());
        return productService.createProduct(p)
                .defaultIfEmpty(new Product());

    }

    @MessageMapping("product/fireAndForget")
    public Mono<Void> addProductFireAndForget(ProductResponse productResponse){
        Product p  = new Product();
        p.setDescription(productResponse.getDescription());
        p.setPrice(productResponse.getPrice());
        productService.createProduct(p);
        System.out.println("---> Продукт был добавлен --->");
        return Mono.empty();
    }

    @MessageMapping("feedMarketData")
    public Flux<Product> feedMarketData(String marketDataRequest) {
        return productService.getAllProducts();
    }

    @MessageMapping("product/channel")
    public Flux<Long> channel(Flux<Notification> notifications) {
        final AtomicLong notificationCount = new AtomicLong(0);
        return notifications
                .doOnNext(notification -> {
                    logger.info("Received notification for channel: " + notification);
                    notificationCount.incrementAndGet();
                })
                .switchMap(notification ->
                        Flux.interval(Duration.ofSeconds(1)).map(new Object() {
                            private Function<Long, Long> numberOfMessages(AtomicLong notificationCount) {
                                long count = notificationCount.get();
                                logger.info("Return flux with count: " + count);
                                return i -> count;
                            }
                        }.numberOfMessages(notificationCount))).log();
    }
}
