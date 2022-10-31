package com.sfelaco.rsocket.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String description;
    private Double price;
}
