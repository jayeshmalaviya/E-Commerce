package com.Project.ECommerce.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

    private String customerName;

    private List<CardDto> cardDtoList;
}
