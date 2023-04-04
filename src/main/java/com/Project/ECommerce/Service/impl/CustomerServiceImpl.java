package com.Project.ECommerce.Service.impl;

import com.Project.ECommerce.Convertor.CustomerConvertor;
import com.Project.ECommerce.Model.Cart;
import com.Project.ECommerce.Model.Customer;
import com.Project.ECommerce.Repository.CustomerRepository;
import com.Project.ECommerce.RequestDTO.CustomerRequestDto;
import com.Project.ECommerce.ResponseDTO.CustomerResponseDto;
import com.Project.ECommerce.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public String addCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = CustomerConvertor.customerRequestDtoToCustomer(customerRequestDto);

        // allocate the cart to customer
        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(customer);

        //set the cart in customer
        customer.setCart(cart);

        customerRepository.save(customer);

        return "Congrats!! Now you can shop anything :) ..";

    }

    public CustomerResponseDto getById(int customerId) {

        Customer customer = customerRepository.findById(customerId).get();

        CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

        return customerResponseDto;
    }

    @Override
    public List<CustomerResponseDto> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

            customerResponseDtos.add(customerResponseDto);
        }

        return customerResponseDtos;
    }

    @Override
    public List<CustomerResponseDto> getByAge(int age) {
        List<Customer> customerList = customerRepository.findByAge(age);

        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

            customerResponseDtos.add(customerResponseDto);
        }
        return customerResponseDtos;
    }

    @Override
    public List<CustomerResponseDto> getByAge(int startAge, int endAge) {

        List<Customer> customerList = customerRepository.findAllAge(startAge, endAge);

        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

            customerResponseDtos.add(customerResponseDto);
        }

        return customerResponseDtos;
    }

    @Override
    public String deleteById(int id) {
        customerRepository.deleteById(id);
        return "Customer Deleted Successfully";
    }

    @Override
    public CustomerResponseDto getByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);

        CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

        return customerResponseDto;
    }

    @Override
    public CustomerResponseDto updateName(String name, int id) {
        Customer customer = customerRepository.findById(id).get();

        customer.setName(name);

        customerRepository.save(customer);

        CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

        return customerResponseDto;
    }

    @Override
    public CustomerResponseDto updateAge(int age, int id) {
        Customer customer = customerRepository.findById(id).get();

        customer.setAge(age);

        customerRepository.save(customer);

        CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

        return customerResponseDto;
    }

    @Override
    public CustomerResponseDto updateEmail(String email, int id) {
        Customer customer = customerRepository.findById(id).get();

        customer.setEmail(email);

        customerRepository.save(customer);

        CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

        return customerResponseDto;
    }

    @Override
    public CustomerResponseDto updateMobile(String mobile, int id) {
        Customer customer = customerRepository.findById(id).get();

        customer.setMobile(mobile);

        customerRepository.save(customer);

        CustomerResponseDto customerResponseDto = CustomerConvertor.customerToCustomerResponseDto(customer);

        return customerResponseDto;
    }
}
