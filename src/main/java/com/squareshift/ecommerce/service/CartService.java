package com.squareshift.ecommerce.service;

import com.squareshift.ecommerce.dto.CartItemDto;

import java.util.List;

public interface CartService {
    String addToCart(CartItemDto cartItemDto) throws Exception;

    List<CartItemDto> getAllItems() throws Exception;

    String emptyCart();

    Long calculateTotalCartValue(Long shipping_postal_code) throws Exception;
}
