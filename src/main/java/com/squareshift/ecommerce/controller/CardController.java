package com.squareshift.ecommerce.controller;

import com.squareshift.ecommerce.dto.CartItemDetailedResponseDto;
import com.squareshift.ecommerce.dto.CartItemDto;
import com.squareshift.ecommerce.dto.CartItemResponseDto;
import com.squareshift.ecommerce.dto.ProductDto;
import com.squareshift.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cart/")
public class CardController {

    @Autowired
    CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "item")
    public ResponseEntity<CartItemResponseDto> addItem(@RequestBody CartItemDto cartItemDto) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        try {
            String response = cartService.addToCart(cartItemDto);
            cartItemResponseDto.setStatus(response);
            if (response.equals("success")) {
                cartItemResponseDto.setMessage("Item has been added to cart");
                return new ResponseEntity(cartItemResponseDto, HttpStatus.OK);
            } else {
                cartItemResponseDto.setMessage("Invalid product id,Valid product id range is 100 to 110");
                return new ResponseEntity(cartItemResponseDto, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            System.out.println("Error in add cart");
            // TODO : logger will be added
            cartItemResponseDto.setStatus("error");
            cartItemResponseDto.setMessage("Invalid product id,Valid product id range is 100 to 110");
            return new ResponseEntity(cartItemResponseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "items")
    public ResponseEntity<CartItemDetailedResponseDto> allItemsInCard() {
        CartItemDetailedResponseDto cartItemDetailedResponseDto = new CartItemDetailedResponseDto();
        try {
            List<CartItemDto> allItems = cartService.getAllItems();
            if (allItems.size() > 0) {
                cartItemDetailedResponseDto.setItems(allItems);
                cartItemDetailedResponseDto.setStatus("success");
                cartItemDetailedResponseDto.setMessage("Item available in the cart");
                return new ResponseEntity<>(cartItemDetailedResponseDto, HttpStatus.OK);
            } else {
                cartItemDetailedResponseDto.setMessage("Cart is empty.");
                return new ResponseEntity(cartItemDetailedResponseDto, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // TODO : logger will be added
            System.out.println("Error in list cart");
            cartItemDetailedResponseDto.setMessage("Cart is empty.");
            return new ResponseEntity(cartItemDetailedResponseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "items")
    public ResponseEntity<String> emptyCart() {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        try {
            String response = cartService.emptyCart();
            cartItemResponseDto.setStatus(response);
            if (response.equals("success")) {
                cartItemResponseDto.setMessage("All items have been removed from the cart !");
                return new ResponseEntity(cartItemResponseDto, HttpStatus.OK);
            }
            cartItemResponseDto.setMessage("Error state. Bad request.");
            return new ResponseEntity(cartItemResponseDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            System.out.println("Error in empty cart");
            // TODO : logger will be added
            cartItemResponseDto.setStatus("error");
            cartItemResponseDto.setMessage("Invalid product id,Valid product id range is 100 to 110");
            return new ResponseEntity(cartItemResponseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "checkout-value")
    public ResponseEntity<Long> calculateTotalCartValue(@RequestParam("shipping_postal_code") Long shipping_postal_code) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        try {
            Long allItemsPrice = cartService.calculateTotalCartValue(shipping_postal_code);
            cartItemResponseDto.setStatus("success");
            cartItemResponseDto.setMessage("Total value of your shopping cart is - $"+allItemsPrice);
            return new ResponseEntity(cartItemResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            // TODO : logger will be added
            cartItemResponseDto.setStatus("error");
            cartItemResponseDto.setMessage("Invalid product id,Valid product id range is 100 to 110");
            return new ResponseEntity(cartItemResponseDto, HttpStatus.BAD_REQUEST);
        }

    }
}
