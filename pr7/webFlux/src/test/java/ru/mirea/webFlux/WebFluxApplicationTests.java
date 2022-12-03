package ru.mirea.webFlux;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebFluxApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@MockBean
	ProductRepository productRepository;


	@Test
	void testGetProducts() {
		Flux<Product> productFlux = Flux.just(
				new Product(1,"test1",5,10),
				new Product(2,"test2",5,10)
		);

		Mockito.when(productRepository.findAll()).thenReturn(productFlux);
		webTestClient.get()
				.uri("/all")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$[0].id").isEqualTo(1)
				.jsonPath("$[1].id").isEqualTo(2);
	}

	@Test
	void testAddProduct() {
		Product product = new Product(1, "add", 6, 10);

		Mono<Product> productMono = Mono.just(product);

		Mockito.when(productRepository.save(product)).thenReturn(productMono);


		webTestClient.post()
				.uri("/product")
				.contentType(MediaType.APPLICATION_JSON)
				.body(productMono, Product.class)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Product.class).isEqualTo(product);
	}
}
