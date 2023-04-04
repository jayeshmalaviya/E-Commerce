package com.Project.ECommerce.Service;

import com.Project.ECommerce.Enum.ProductCategory;
import com.Project.ECommerce.RequestDTO.ProductRequestDto;
import com.Project.ECommerce.ResponseDTO.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws Exception;

    List<ProductResponseDto> getProductByCategory(ProductCategory productCategory);

}
