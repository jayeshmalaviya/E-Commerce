package com.Project.ECommerce.Controller;

import com.Project.ECommerce.Exception.CustomerNotFoundException;
import com.Project.ECommerce.RequestDTO.CardRequestDto;
import com.Project.ECommerce.ResponseDTO.CardResponseDto;
import com.Project.ECommerce.Service.impl.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardServiceImpl cardService;

    @PostMapping("/add")
    public ResponseEntity addCard(@RequestBody CardRequestDto cardRequestDto) throws CustomerNotFoundException {
        CardResponseDto cardResponseDto;
        try {
            cardResponseDto = cardService.addCard(cardRequestDto);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cardResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCard(@PathVariable("id") int id) {
        return cardService.deleteCard(id);
    }

    @DeleteMapping("/deleteByCardNo")
    public String deleteCard(@RequestParam("card") String cardNo) {
        return cardService.deleteCard(cardNo);
    }

    @DeleteMapping("/deleteById")
    public String deleteByCustomerId(@RequestParam("id") int id) {
        return cardService.deleteByCustomerId(id);
    }
}
