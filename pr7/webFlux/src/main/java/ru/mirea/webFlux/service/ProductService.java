package ru.mirea.webFlux.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mirea.webFlux.Product;
import ru.mirea.webFlux.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> listProduct(){
        return productRepository.findAll();
    }

    public Mono<Product> addProduct(Product product){
        return productRepository.save(product);
    }

    public Mono<Void> deleteProduct(long id ){
        return productRepository.deleteById(id);
    }

    public Mono<Product> updateProduct(Product product){
        return productRepository.save(product);
    }
    public Mono<Product> productFindById(long id){
        return productRepository.findById(id);
    }
    public Flux<String> getNamesProduct(){
        return productRepository
                .findAll()
                .map(Product::getName);
    }
}
