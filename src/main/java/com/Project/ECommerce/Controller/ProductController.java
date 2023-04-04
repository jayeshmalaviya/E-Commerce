package com.Project.ECommerce.Controller;

import com.Project.ECommerce.Enum.ProductCategory;
import com.Project.ECommerce.RequestDTO.ProductRequestDto;
import com.Project.ECommerce.ResponseDTO.ProductResponseDto;
import com.Project.ECommerce.Service.ProductService;
import com.Project.ECommerce.Service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto) throws Exception {
        ProductResponseDto productResponseDto;
        try {
            productResponseDto = productService.addProduct(productRequestDto);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(productResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/category/{productCategory}")
    public List<ProductResponseDto> getProductByCategory(@PathVariable("productCategory")ProductCategory productCategory)
    {
        return productService.getProductByCategory(productCategory);
    }
}