package ru.mirea.webFlux.handler;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.mirea.webFlux.Product;
import ru.mirea.webFlux.service.ProductService;

import java.util.ArrayList;

@Component
public class ProductHandler {

    private final ProductService productService;

    public ProductHandler(ProductService productService) {
        this.productService = productService;
    }
    public Mono<ServerResponse> getAllProducts(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.listProduct(), Product.class);
    }

    public Mono<ServerResponse> addProduct(ServerRequest request){
        Mono<Product> product = request.bodyToMono(Product.class);

        return product.flatMap(x-> ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.addProduct(x),Product.class));
    }


    public Mono<ServerResponse> deleteProduct(ServerRequest request){
        long productId = Long.parseLong(request.pathVariable("id"));

        return productService.deleteProduct(productId)
                .flatMap(x->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(productId));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request){
        long productId = Long.parseLong(request.pathVariable("id"));
        Mono<Product> productUpdate = request.bodyToMono(Product.class);

        return productService.productFindById(productId)
                .flatMap(x-> productUpdate.flatMap(update->{
                    x.setName(update.getName());
                    return Mono.just(x);
                }))
                .flatMap(y->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
                        productService.updateProduct(y).switchIfEmpty(Mono.empty()),Product.class
                ))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProductsNames(ServerRequest request){

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getNamesProduct().collectList(), ArrayList.class)
                .onErrorResume(e->Mono.error(new Exception(e)));
    }
}
