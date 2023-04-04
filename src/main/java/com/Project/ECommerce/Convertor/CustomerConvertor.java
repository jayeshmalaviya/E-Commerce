package com.Project.ECommerce.Convertor;

import com.Project.ECommerce.Model.Customer;
import com.Project.ECommerce.RequestDTO.CustomerRequestDto;
import com.Project.ECommerce.ResponseDTO.CustomerResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass// it can not be instantiated and all functions are static so that it can be accessed by class name directly
public class CustomerConvertor {

    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = Customer.builder()
                .name(customerRequestDto.getName())
                .age(customerRequestDto.getAge())
                .email(customerRequestDto.getEmail())
                .mobile(customerRequestDto.getMobNo())
                .build();

        return customer;
    }

    public static CustomerResponseDto customerToCustomerResponseDto(Customer customer) {
        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .age(customer.getAge())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .name(customer.getName())
                .build();

        return customerResponseDto;
    }
}
