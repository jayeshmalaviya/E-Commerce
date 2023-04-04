package com.Project.ECommerce.Service.impl;

import com.Project.ECommerce.Enum.ProductStatus;
import com.Project.ECommerce.Exception.CustomerNotFoundException;
import com.Project.ECommerce.Exception.ProductNotFoundException;
import com.Project.ECommerce.Model.*;
import com.Project.ECommerce.Repository.CustomerRepository;
import com.Project.ECommerce.Repository.ProductRepository;
import com.Project.ECommerce.RequestDTO.OrderRequestDto;
import com.Project.ECommerce.ResponseDTO.OrderResponseDto;
import com.Project.ECommerce.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    JavaMailSender emailSender;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ItemServiceImpl itemService;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientResourcesException {

        //check customer is available
        Customer customer;
        try {
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        } catch (Exception e) {
            throw new CustomerNotFoundException("Invalid customer id");
        }

        //check product is available
        Product product;
        try {
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        } catch (Exception e) {
            throw new ProductNotFoundException("Invalid product id");
        }

        //check the quantity of product
        if (product.getQuantity() < orderRequestDto.getRequiredQuantity()) {
            throw new InsufficientResourcesException("Insufficient quantity sorry...");
        }


        // make the order
        Ordered ordered = new Ordered();
        int totalCost = orderRequestDto.getRequiredQuantity() * product.getPrice();
        int deliveryCharge = 0;
        if(totalCost < 500)
        {
            deliveryCharge = 50;
            totalCost += deliveryCharge;
        }
        ordered.setTotalCost(totalCost);
        ordered.setDeliveryCharge(deliveryCharge);


        // fetch the card details from customer so that I can add it in the ordered class
        Card card = customer.getCardList().get(0);

        String cardNo = "";
        // it will set the cardNo with 'X'
        for(int i=0; i<card.getCardNo().length()-4; i++)
        {
            cardNo += 'X';
        }
        // it will set the last 4 digit of cardNo.
        cardNo += card.getCardNo().substring(card.getCardNo().length()-4);


        // set the card used for payment in ordered class
        ordered.setCardUsedForPayment(cardNo);


        // make the new item
        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        // the below two lines will affect in the table it fills the orderedId and productId
        item.setProduct(product);
        item.setOrdered(ordered);


        // set the item and customer in ordered class
        ordered.getItemList().add(item);
        ordered.setCustomer(customer);


        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        if(leftQuantity <= 0)
        {
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
        product.setQuantity(leftQuantity);


        // set the order in the customer class
        customer.getOrderedList().add(ordered);
        Customer savedCustomer = customerRepository.save(customer);


        Ordered savedOrder = savedCustomer.getOrderedList().get(savedCustomer.getOrderedList().size()-1);


        // make the responseDto
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .productName(product.getName())
                .orderDate(savedOrder.getOrderDate())
                .quantityOrdered(orderRequestDto.getRequiredQuantity())
                .cardUsedForPayment(cardNo)
                .itemPrice(product.getPrice())
                .totalCost(ordered.getTotalCost())
                .deliveryCharge(deliveryCharge)
                .build();


        // email
        // these are the steps to send the email.
        String text = "Congrats !!. your order with total value "+ ordered.getTotalCost() + " has been placed." ;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mukesh1029mishra@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order placed directly");
        message.setText(text);
        emailSender.send(message);

        return orderResponseDto;
    }

}
