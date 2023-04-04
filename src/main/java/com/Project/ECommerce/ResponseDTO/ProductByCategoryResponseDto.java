package com.Project.ECommerce.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductByCategoryResponseDto {

    private String name;

    private int price;

    private int quantity;

    private String sellerName;
}
