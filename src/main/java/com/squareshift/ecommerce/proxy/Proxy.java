package com.squareshift.ecommerce.proxy;

import com.squareshift.ecommerce.dto.ProductDto;
import com.squareshift.ecommerce.dto.ProductResponseDto;
import com.squareshift.ecommerce.dto.WarehouseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@FeignClient(value = "client", url = "${external.system-service.url}")
public interface Proxy {

    @GetMapping(value = "/product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponseDto getProductById(@PathVariable Long id) throws Exception;

    @GetMapping(value = "/warehouse/distance", consumes = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseResponseDto getWareHouseDistanceByPostalCode(@RequestParam Long postal_code) throws Exception;
}
