package com.Project.ECommerce.Service;

import com.Project.ECommerce.ResponseDTO.ItemResponseDto;

public interface ItemService {

    ItemResponseDto viewItem(int productId) throws Exception;

}
