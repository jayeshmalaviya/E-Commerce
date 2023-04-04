package com.Project.ECommerce.Service;

import com.Project.ECommerce.RequestDTO.OrderRequestDto;
import com.Project.ECommerce.ResponseDTO.OrderResponseDto;

import java.util.List;

public interface CartService {

    String addToCart(OrderRequestDto orderRequestDto) throws Exception;

    List<OrderResponseDto> checkoutCart(int customerId) throws Exception;



}
