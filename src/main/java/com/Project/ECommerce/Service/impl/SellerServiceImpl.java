package com.Project.ECommerce.Service.impl;

import com.Project.ECommerce.Convertor.SellerConvertor;
import com.Project.ECommerce.Model.Seller;
import com.Project.ECommerce.Repository.SellerRepository;
import com.Project.ECommerce.RequestDTO.SellerRequestDto;
import com.Project.ECommerce.ResponseDTO.SellerResponseDto;
import com.Project.ECommerce.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public String addSeller(SellerRequestDto sellerRequestDto) {

        // convert requestDto to Seller with the help of convertor
        Seller seller = SellerConvertor.sellerRequestDtoToSeller(sellerRequestDto);

        sellerRepository.save(seller);

        return "Congrats!! Now you can sell on our site :)..";
    }

    @Override
    public List<SellerResponseDto> viewAllSeller() {

        List<Seller> sellers = sellerRepository.findAll();

        List<SellerResponseDto> sellerResponseDtos = new ArrayList<>();
        for (Seller s : sellers) {
            SellerResponseDto sellerResponseDto = SellerConvertor.sellerToSellerResponseDto(s);

            sellerResponseDtos.add(sellerResponseDto);
        }

        return sellerResponseDtos;
    }

    @Override
    public SellerResponseDto getSellerByPan(String panCard) {
        Seller seller = sellerRepository.findByPanCard(panCard);

        SellerResponseDto sellerResponseDto = SellerConvertor.sellerToSellerResponseDto(seller);

        return sellerResponseDto;
    }

    @Override
    public List<SellerResponseDto> getSellerByAge(int age) {
        List<Seller> sellerList = sellerRepository.findByAge(age);

        List<SellerResponseDto> sellerResponseDtos = new ArrayList<>();
        for (Seller seller : sellerList) {
            SellerResponseDto sellerResponseDto = SellerConvertor.sellerToSellerResponseDto(seller);

            sellerResponseDtos.add(sellerResponseDto);
        }
        return sellerResponseDtos;
    }

    @Override
    public List<SellerResponseDto> getAllSellerByAge(int startAge, int endAge) {
        List<Seller> sellerList = sellerRepository.findAllAge(startAge, endAge);

        List<SellerResponseDto> sellerResponseDtos = new ArrayList<>();

        for (Seller seller : sellerList) {
            SellerResponseDto sellerResponseDto = SellerConvertor.sellerToSellerResponseDto(seller);

            sellerResponseDtos.add(sellerResponseDto);
        }

        return sellerResponseDtos;
    }

    @Override
    public String deleteSeller(int sellerId) {
        sellerRepository.deleteById(sellerId);

        return "Seller has been deleted Successfully.";
    }

    @Override
    public String deleteAllSeller() {
        sellerRepository.deleteAll();
        return "All Sellers data have been deleted";
    }


}
