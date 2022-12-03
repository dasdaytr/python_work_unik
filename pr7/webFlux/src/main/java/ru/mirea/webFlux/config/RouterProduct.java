package ru.mirea.webFlux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.*;
import ru.mirea.webFlux.handler.ProductHandler;

@Configuration
public class RouterProduct {

    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler productHandler){

        RequestPredicate routeGetAllProducts = RequestPredicates
                .GET("/all");
        RequestPredicate routePostAddProduct = RequestPredicates
                .POST("/product")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));
        RequestPredicate routeDeleteProduct = RequestPredicates
                .DELETE("/product/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));
        RequestPredicate routePutUpdateProduct = RequestPredicates
                .PUT("/product/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));
        RequestPredicate routeGetProductsName = RequestPredicates
                .GET("/product/names");

        return RouterFunctions
                .route(routeGetAllProducts, productHandler::getAllProducts)
                .andRoute(routePostAddProduct, productHandler::addProduct)
                .andRoute(routeDeleteProduct,productHandler::deleteProduct)
                .andRoute(routePutUpdateProduct,productHandler::updateProduct)
                .andRoute(routeGetProductsName,productHandler::getProductsNames);

    }
}
