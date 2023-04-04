package com.Project.ECommerce.Convertor;

import com.Project.ECommerce.Model.Seller;
import com.Project.ECommerce.RequestDTO.SellerRequestDto;
import com.Project.ECommerce.ResponseDTO.SellerResponseDto;

public class SellerConvertor {

    public static Seller sellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){
        Seller seller = Seller.builder()
                .name(sellerRequestDto.getName())
                .email(sellerRequestDto.getEmail())
                .age(sellerRequestDto.getAge())
                .mobile(sellerRequestDto.getMobile())
                .panCard(sellerRequestDto.getPanCard())
                .build();

        return seller;
    }


    public static SellerResponseDto sellerToSellerResponseDto(Seller seller){
        SellerResponseDto sellerResponseDto = SellerResponseDto.builder()
                .name(seller.getName())
                .email(seller.getEmail())
                .age(seller.getAge())
                .mobile(seller.getMobile())
                .panCard(seller.getPanCard())
                .build();

        return sellerResponseDto;
    }
}
