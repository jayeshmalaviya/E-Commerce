package com.Project.ECommerce.ResponseDTO;

import com.Project.ECommerce.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private String cardNo;
    private CardType cardType;
}
