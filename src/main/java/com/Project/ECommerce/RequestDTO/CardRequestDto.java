package com.Project.ECommerce.RequestDTO;

import com.Project.ECommerce.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequestDto {
    private int customerId;
    private String cardNo;
    private int cvv;
    private CardType cardType;
}