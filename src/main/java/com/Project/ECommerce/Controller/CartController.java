package com.Project.ECommerce.Controller;

import com.Project.ECommerce.RequestDTO.OrderRequestDto;
import com.Project.ECommerce.ResponseDTO.OrderResponseDto;
import com.Project.ECommerce.Service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartServiceImpl cartService;

    //make responseDto
    @PostMapping("/add")
    public String addToCart(@RequestBody OrderRequestDto orderRequestDto) {
        String response = "";
        try {
            response = cartService.addToCart(orderRequestDto);
        } catch (Exception e) {
            return e.getMessage();
        }
        return response;
    }


    @PostMapping("/checkout/{customerId}")
    public ResponseEntity checkoutCart(@PathVariable("customerId") int customerId) {
        List<OrderResponseDto> orderResponseDtos;
        try
        {
            orderResponseDtos = cartService.checkoutCart(customerId);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.ACCEPTED);
    }
}
