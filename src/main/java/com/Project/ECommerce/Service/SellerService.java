package com.Project.ECommerce.Service;

import com.Project.ECommerce.RequestDTO.SellerRequestDto;
import com.Project.ECommerce.ResponseDTO.SellerResponseDto;

import java.util.List;

public interface SellerService {

    String addSeller(SellerRequestDto sellerRequestDto);

    List<SellerResponseDto> viewAllSeller();

    SellerResponseDto getSellerByPan(String panCard);

    List<SellerResponseDto> getAllSellerByAge(int startAge, int endAge);

    String deleteSeller(int sellerId);

    String deleteAllSeller();

    List<SellerResponseDto> getSellerByAge(int age);
}
