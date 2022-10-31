package com.sfelaco.rsocket.pojos;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Table // optional
public class Product {

    @Id
    private Integer id;
    private String description;
    private Double price;

}
