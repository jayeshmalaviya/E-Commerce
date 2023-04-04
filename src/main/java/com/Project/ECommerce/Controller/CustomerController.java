package com.Project.ECommerce.Controller;

import com.Project.ECommerce.RequestDTO.CustomerRequestDto;
import com.Project.ECommerce.ResponseDTO.CustomerResponseDto;
import com.Project.ECommerce.Service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @PostMapping("/add")
    public String addCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.addCustomer(customerRequestDto);
    }

    @GetMapping("/get")
    public CustomerResponseDto getById(@RequestParam("id") int customerId) {
        return customerService.getById(customerId);
    }

    @GetMapping("/getAllCustomer")
    public List<CustomerResponseDto> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/get/{age}")
    public List<CustomerResponseDto> getByAge(@PathVariable("age") int age) {
        return customerService.getByAge(age);
    }

    @GetMapping("/getByAgeBetween")
    public List<CustomerResponseDto> getByAge(@RequestParam int startAge, @RequestParam int endAge) {
        return customerService.getByAge(startAge, endAge);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") int id) {
        return customerService.deleteById(id);
    }

    @GetMapping("/getByEmail")
    public CustomerResponseDto getByEmail(@RequestParam("email") String email) {
        return customerService.getByEmail(email);
    }

    @PutMapping("/updateName")
    public CustomerResponseDto updateName(@RequestParam("name") String name, @RequestParam("id") int id) {
        return customerService.updateName(name, id);
    }

    @PutMapping("/updateAge")
    public CustomerResponseDto updateAge(@RequestParam("age") int age, @RequestParam("id") int id) {
        return customerService.updateAge(age, id);
    }

    @PutMapping("/updateEmail")
    public CustomerResponseDto updateEmail(@RequestParam("email") String email, @RequestParam("id") int id) {
        return customerService.updateEmail(email, id);
    }

    @PutMapping("/updateMobile")
    public CustomerResponseDto updateMobile(@RequestParam("mobile") String mobile, @RequestParam("id") int id) {
        return customerService.updateMobile(mobile, id);
    }
}