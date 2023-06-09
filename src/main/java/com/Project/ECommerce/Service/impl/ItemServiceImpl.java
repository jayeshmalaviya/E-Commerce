package com.Project.ECommerce.Service.impl;

import com.Project.ECommerce.Enum.ItemStatus;
import com.Project.ECommerce.Exception.ProductNotFoundException;
import com.Project.ECommerce.Model.Item;
import com.Project.ECommerce.Model.Product;
import com.Project.ECommerce.Repository.ItemRepository;
import com.Project.ECommerce.Repository.ProductRepository;
import com.Project.ECommerce.ResponseDTO.ItemResponseDto;
import com.Project.ECommerce.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ItemResponseDto viewItem(int productId) throws ProductNotFoundException {
        Product product;
        try {
            product = productRepository.findById(productId).get();
        } catch (Exception e) {
            throw new ProductNotFoundException("Invalid product id");
        }

        //product should be listed as items
        Item item =new Item();

        item.setProduct(product);

        // set the item in product
        product.setItem(item); //map the product to the items, since now this product has become an items

        item.setItemStatus(ItemStatus.VIEWED);


        //make the responseDto
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .productName(product.getName())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .productStatus(product.getProductStatus())
                .build();

        //saving both item and product
        productRepository.save(product);

        return itemResponseDto;
    }

}
