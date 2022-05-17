package com.squareshift.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long product_id;
    private Long quantity;
    private String description;
}
