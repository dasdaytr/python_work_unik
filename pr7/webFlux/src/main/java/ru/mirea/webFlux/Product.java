package ru.mirea.webFlux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private long id;

    private String name;

    private int count;

    private int price;

}
