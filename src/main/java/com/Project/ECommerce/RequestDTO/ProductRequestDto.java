package com.Project.ECommerce.RequestDTO;
import com.Project.ECommerce.Enum.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String name;

    private int price;

    private int quantity;

    private ProductCategory productCategory;

    private int sellerID;

}
