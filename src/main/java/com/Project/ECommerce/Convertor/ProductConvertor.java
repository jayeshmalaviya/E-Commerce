package com.Project.ECommerce.Convertor;

import com.Project.ECommerce.Enum.ProductStatus;
import com.Project.ECommerce.Model.Product;
import com.Project.ECommerce.RequestDTO.ProductRequestDto;
import com.Project.ECommerce.ResponseDTO.ProductResponseDto;

public class ProductConvertor {

    public static Product productRequestDtoToProduct(ProductRequestDto productRequestDto)
    {
        Product product = Product.builder()
                .Name(productRequestDto.getName())
                .productCategory(productRequestDto.getProductCategory())
                .price(productRequestDto.getPrice())
                .quantity(productRequestDto.getQuantity())
                .productStatus(ProductStatus.AVAILABLE)
                .build();

        return product;
    }

    public static ProductResponseDto ProductToProductResponseDto(Product product)
    {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .build();

        return productResponseDto;
    }


}
