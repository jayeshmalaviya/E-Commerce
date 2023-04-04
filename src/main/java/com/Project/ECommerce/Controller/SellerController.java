package com.Project.ECommerce.Controller;

import com.Project.ECommerce.RequestDTO.SellerRequestDto;
import com.Project.ECommerce.ResponseDTO.SellerResponseDto;
import com.Project.ECommerce.Service.impl.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerServiceImpl sellerService;

    @PostMapping("/add")
    public String addSeller(@RequestBody SellerRequestDto sellerRequestDto) {
        return sellerService.addSeller(sellerRequestDto);
    }

    @GetMapping("/getAllSellers")
    public List<SellerResponseDto> viewAllSeller() {
        return sellerService.viewAllSeller();
    }  // extra


    @GetMapping("/getsellerByPan/{pancard}")
    public SellerResponseDto getSellerByPan(@PathVariable("pancard") String panCard) {  // extra
        return sellerService.getSellerByPan(panCard);
    }


    @GetMapping("/getsellerByAge/{age}")
    public List<SellerResponseDto> getSellerByAge(@PathVariable("age") int age) {
        return sellerService.getSellerByAge(age);
    } // extra


    @GetMapping("/getAllSellerByAge")
    public List<SellerResponseDto> getAllSellerByAge(@RequestParam int startAge, @RequestParam int endAge) {
        return sellerService.getAllSellerByAge(startAge, endAge);  // extra
    }


    @DeleteMapping("/delete/{sellerId}")
    public String deleteSeller(@PathVariable("sellerId") int sellerId) {
        return sellerService.deleteSeller(sellerId);
    } // extra


    @DeleteMapping("/deleteAll")
    public String deleteAllSeller()
    {
        return sellerService.deleteAllSeller();
    } // extra
}
