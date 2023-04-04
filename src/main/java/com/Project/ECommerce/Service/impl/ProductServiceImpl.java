package com.Project.ECommerce.Service.impl;

import com.Project.ECommerce.Convertor.ProductConvertor;
import com.Project.ECommerce.Enum.ProductCategory;
import com.Project.ECommerce.Exception.SellerNotPresentException;
import com.Project.ECommerce.Model.Product;
import com.Project.ECommerce.Model.Seller;
import com.Project.ECommerce.Repository.ProductRepository;
import com.Project.ECommerce.Repository.SellerRepository;
import com.Project.ECommerce.RequestDTO.ProductRequestDto;
import com.Project.ECommerce.ResponseDTO.ProductResponseDto;
import com.Project.ECommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotPresentException {

        Seller seller;
        try
        {
            seller = sellerRepository.findById(productRequestDto.getSellerID()).get();
        }
        catch (Exception e)
        {
            throw new SellerNotPresentException("Invaild seller id");
        }

        // make the Product object using builder and convertor
        Product product = ProductConvertor.productRequestDtoToProduct(productRequestDto);

        // set seller in product
        product.setSeller(seller);

        //add product in seller
        seller.getProductList().add(product);

        // parent can save the child also because or cascade
        sellerRepository.save(seller);

        // convert the seller to responseDto
        ProductResponseDto productResponseDto = ProductConvertor.ProductToProductResponseDto(product);

        return productResponseDto;
    }


    @Override
    public List<ProductResponseDto> getProductByCategory(ProductCategory productCategory){
        List<Product> products = productRepository.findAllByProductCategory(productCategory);

        // prepare the list of responseDto
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product p : products)
        {
            ProductResponseDto productResponseDto = ProductConvertor.ProductToProductResponseDto(p);

            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }
}
