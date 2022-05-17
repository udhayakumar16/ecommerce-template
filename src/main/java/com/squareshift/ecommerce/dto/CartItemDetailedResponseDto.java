package com.squareshift.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartItemDetailedResponseDto {
    private String status;
    private String message;
    private List<CartItemDto> items;
}
