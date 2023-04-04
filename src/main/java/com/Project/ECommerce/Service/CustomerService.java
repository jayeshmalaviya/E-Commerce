package com.Project.ECommerce.Service;

import com.Project.ECommerce.RequestDTO.CustomerRequestDto;
import com.Project.ECommerce.ResponseDTO.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    String addCustomer(CustomerRequestDto customerRequestDto);

    CustomerResponseDto getById(int customerId);

    List<CustomerResponseDto> getAllCustomer();

    List<CustomerResponseDto> getByAge(int age);

    List<CustomerResponseDto> getByAge(int startAge, int endAge);

    String deleteById(int id);

    CustomerResponseDto getByEmail(String email);

    CustomerResponseDto updateName(String name, int id);

    CustomerResponseDto updateAge(int age, int id);

    CustomerResponseDto updateEmail(String email, int id);

    CustomerResponseDto updateMobile(String mobile, int id);
}