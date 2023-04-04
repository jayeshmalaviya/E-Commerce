package com.Project.ECommerce.Service;

import com.Project.ECommerce.RequestDTO.CardRequestDto;
import com.Project.ECommerce.ResponseDTO.CardResponseDto;

public interface CardService {

    CardResponseDto addCard(CardRequestDto cardRequestDto) throws Exception;

    String deleteCard(int id);

    String deleteCard(String cardNo);

    String deleteByCustomerId(int id);
}