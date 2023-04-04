package com.Project.ECommerce.ResponseDTO;

import com.Project.ECommerce.Enum.ProductCategory;
import com.Project.ECommerce.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDto {

    private String productName;
    private int price;
    private ProductStatus productStatus;
    private ProductCategory productCategory;

}