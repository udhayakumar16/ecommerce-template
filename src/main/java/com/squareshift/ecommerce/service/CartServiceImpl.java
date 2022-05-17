package com.squareshift.ecommerce.service;

import com.squareshift.ecommerce.dto.CartItemDto;
import com.squareshift.ecommerce.dto.ProductDto;
import com.squareshift.ecommerce.dto.ProductResponseDto;
import com.squareshift.ecommerce.proxy.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductService productService;
    @Autowired
    Proxy proxy;
    private HashMap<Long, Long> cart = new HashMap<>();

    @Override
    public String addToCart(CartItemDto cartItemDto) throws Exception {
        ProductResponseDto productById = proxy.getProductById(cartItemDto.getProduct_id());
        if (!productById.getStatus().equals("error")) {
            if (cart.containsKey(cartItemDto.getProduct_id())) {
                cart.put(cartItemDto.getProduct_id(), cart.get(cartItemDto.getProduct_id()) + cartItemDto.getQuantity());
            } else {
                cart.put(cartItemDto.getProduct_id(), cartItemDto.getQuantity());
            }
            return "success";
        } else {
            return "error";
        }
    }

    @Override
    public List<CartItemDto> getAllItems() throws Exception {
        List<CartItemDto> itemsInCart = new ArrayList<>();
        for (Long key : cart.keySet()) {
            CartItemDto cartItemDto = new CartItemDto();
            ProductDto productDto = productService.getProductById(key);
            //BeanUtils.copyProperties(productDto,cartItemDto);
            cartItemDto.setProduct_id(key);
            cartItemDto.setQuantity(cart.get(key));
            cartItemDto.setDescription(productDto.getDescription());
            itemsInCart.add(cartItemDto);
        }
        System.out.println(itemsInCart);
        return itemsInCart;
    }

    @Override
    public String emptyCart() {
        try {
            cart.clear();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }

    }

    @Override
    public Long calculateTotalCartValue(Long shipping_postal_code) throws Exception {
        Long totalprice = 0L;
        Long distance = proxy.getWareHouseDistanceByPostalCode(shipping_postal_code).getDistance_in_kilometers();
        for (Long key : cart.keySet()) {
            ProductDto productDto = productService.getProductById(key);
            Long weight = productDto.getWeight_in_grams() * cart.get(key);
            Long productPrice = caculatePricePerProduct(distance, weight);
            // TODO: Arithmetic Exception to be handled here
            totalprice += productPrice - (productPrice * (productDto.getDiscount_percentage() / 100L));
        }
        return totalprice;
    }

    private Long caculatePricePerProduct(Long distance, Long weight) {
        weight = weight / 1000;
        Long price = 0L;
        // TODO : it needs to be handled in better manner either db or someother thing
        if (distance <= 5) {
            if (weight < 2) {
                return 12L;
            } else if ( weight > 2 && weight <= 5) {
                return 14L;
            }
            else if ( weight > 5 && weight <= 20) {
                return 16L;
            }
            else if ( weight > 20) {
                return 21L;
            }
        } else if (distance > 5 && distance <= 20) {
            if (weight < 2) {
                return 15L;
            } else if ( weight > 2 && weight <= 5) {
                return 18L;
            }
            else if ( weight > 5 && weight <= 20) {
                return 25L;
            }
            else if ( weight > 20) {
                return 35L;
            }

        } else if (distance > 20 && distance <= 50) {
            if (weight < 2) {
                return 20L;
            } else if ( weight > 2 && weight <= 5) {
                return 24L;
            }
            else if ( weight > 5 && weight <= 20) {
                return 30L;
            }
            else if ( weight > 20) {
                return 50L;
            }

        } else if (distance > 50 && distance <= 500) {
            if (weight < 2) {
                return 50L;
            } else if ( weight > 2 && weight <= 5) {
                return 55L;
            }
            else if ( weight > 5 && weight <= 20) {
                return 80L;
            }
            else if ( weight > 20) {
                return 90L;
            }

        } else if (distance > 500 && distance <= 800) {
            if (weight < 2) {
                return 100L;
            } else if ( weight > 2 && weight <= 5) {
                return 110L;
            }
            else if ( weight > 5 && weight <= 20) {
                return 130L;
            }
            else if ( weight > 20) {
                return 150L;
            }

        } else if (distance > 800) {
            if (weight < 2) {
                return 220L;
            } else if ( weight > 2 && weight <= 5) {
                return 250L;
            }
            else if ( weight > 5 && weight <= 20) {
                return 270L;
            }
            else if ( weight > 20) {
                return 300L;
            }
        }
        return price;
    }
}