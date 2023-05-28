package com.Project.ECommerce.Service.impl;

import com.Project.ECommerce.Enum.ItemStatus;
import com.Project.ECommerce.Enum.ProductStatus;
import com.Project.ECommerce.Exception.CustomerNotFoundException;
import com.Project.ECommerce.Exception.NotEnoughQuantityException;
import com.Project.ECommerce.Exception.ProductNotFoundException;
import com.Project.ECommerce.Exception.ProductOutOfStockException;
import com.Project.ECommerce.Model.*;
import com.Project.ECommerce.Repository.CustomerRepository;
import com.Project.ECommerce.Repository.OrderedRepository;
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

    @Autowired
    OrderedRepository orderedRepository;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientResourcesException, ProductOutOfStockException, NotEnoughQuantityException {

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

        if(product.getProductStatus()== ProductStatus.OUT_OF_STOCK){
            throw new ProductOutOfStockException();
        }

        //check the quantity of product
        if (product.getQuantity() < orderRequestDto.getRequiredQuantity()) {
            throw new InsufficientResourcesException("Insufficient quantity sorry...");
        }

        //calculate total cost
        int cost =  product.getPrice() * orderRequestDto.getRequiredQuantity();

        //lets assign a delivery charge
        int deliveryCharge = 0;
        if(cost>=5000){
            deliveryCharge = 20;
        }
        else if (cost>=2000){
            deliveryCharge = 40;
        }
        else if(cost>=500){
            deliveryCharge = 60;
        }
        else
            deliveryCharge = 100;

        //total cost
        int totalCost = cost + deliveryCharge;

        //make masked card
        String cardNo = "";
        Card card = customer.getCardList().get(0);
        for (int i = 0; i < card.getCardNo().length() - 4; i++) {
            cardNo += 'X';
        }

        //deduct the requested quantity from current quantity of the prodct
        product.setQuantity(product.getQuantity() - orderRequestDto.getRequiredQuantity());

        //make productStatus = OUT_OF_STOCK if productquantity = 0
        if(product.getQuantity()==0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }

        //prepare the Ordered object and set its attributes
        Ordered newOrder = new Ordered();

        newOrder.setTotalCost(totalCost);
        newOrder.setDeliveryCharge(deliveryCharge);
        newOrder.setCardUsedForPayment(cardNo);
        newOrder.setCustomer(customer);

        customer.getOrderedList().add(newOrder);

        //list the product as an items
        //Create an Item and set its attributes
        Item item = new Item();

        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        item.setProduct(product);
        item.setOrdered(newOrder);
        item.setItemStatus(ItemStatus.ORDERED);

        newOrder.getItemList().add(item);

        customerRepository.save(customer);

         // email
        // these are the steps to send the email.
        String text = "Congrats !!. your order with total value "+ newOrder.getTotalCost() + " has been placed." ;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mukesh1029mishra@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Order placed directly");
        message.setText(text);
        emailSender.send(message);

        //make the responseDto
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .productName(product.getName())
                .orderDate(orderedRepository.findLastOrderDate())
                .quantityOrdered(orderRequestDto.getRequiredQuantity())
                .cardUsedForPayment(cardNo)
                .itemPrice(product.getPrice())
                .totalCost(newOrder.getTotalCost())
                .deliveryCharge(deliveryCharge)
                .build();

        return orderResponseDto;






//        // make the order
//        Ordered ordered = new Ordered();
//        int totalCost = orderRequestDto.getRequiredQuantity() * product.getPrice();
//        int deliveryCharge = 0;
//        if(totalCost < 500)
//        {
//            deliveryCharge = 50;
//            totalCost += deliveryCharge;
//        }
//        ordered.setTotalCost(totalCost);
//        ordered.setDeliveryCharge(deliveryCharge);
//
//
//        // fetch the card details from customer so that I can add it in the ordered class
//        Card card = customer.getCardList().get(0);
//
//        String cardNo = "";
//        // it will set the cardNo with 'X'
//        for(int i=0; i<card.getCardNo().length()-4; i++)
//        {
//            cardNo += 'X';
//        }
//        // it will set the last 4 digit of cardNo.
//        cardNo += card.getCardNo().substring(card.getCardNo().length()-4);
//
//
//        // set the card used for payment in ordered class
//        ordered.setCardUsedForPayment(cardNo);
//
//
//        // make the new item
//        Item item = new Item();
//        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
//        // the below two lines will affect in the table it fills the orderedId and productId
//        item.setProduct(product);
//        item.setOrdered(ordered);
//
//
//        // set the item and customer in ordered class
//        ordered.getItemList().add(item);
//        ordered.setCustomer(customer);
//
//
//        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
//        if(leftQuantity <= 0)
//        {
//            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
//        }
//        product.setQuantity(leftQuantity);
//
//
//        // set the order in the customer class
//        customer.getOrderedList().add(ordered);
//        Customer savedCustomer = customerRepository.save(customer);
//
//
//        Ordered savedOrder = savedCustomer.getOrderedList().get(savedCustomer.getOrderedList().size()-1);
//
//
//        // make the responseDto
//        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
//                .productName(product.getName())
//                .orderDate(savedOrder.getOrderDate())
//                .quantityOrdered(orderRequestDto.getRequiredQuantity())
//                .cardUsedForPayment(cardNo)
//                .itemPrice(product.getPrice())
//                .totalCost(ordered.getTotalCost())
//                .deliveryCharge(deliveryCharge)
//                .build();
//
//
//        // email
//        // these are the steps to send the email.
//        String text = "Congrats !!. your order with total value "+ ordered.getTotalCost() + " has been placed." ;
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("mukesh1029mishra@gmail.com");
//        message.setTo(customer.getEmail());
//        message.setSubject("Order placed directly");
//        message.setText(text);
//        emailSender.send(message);
//
//        return orderResponseDto;
    }

}
