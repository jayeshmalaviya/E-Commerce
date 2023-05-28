package com.Project.ECommerce.Service.impl;

import com.Project.ECommerce.Enum.ItemStatus;
import com.Project.ECommerce.Enum.ProductStatus;
import com.Project.ECommerce.Exception.CustomerNotFoundException;
import com.Project.ECommerce.Exception.NotEnoughQuantityException;
import com.Project.ECommerce.Exception.ProductNotFoundException;
import com.Project.ECommerce.Exception.ProductOutOfStockException;
import com.Project.ECommerce.Model.*;
import com.Project.ECommerce.Repository.CustomerRepository;
import com.Project.ECommerce.Repository.ProductRepository;
import com.Project.ECommerce.RequestDTO.OrderRequestDto;
import com.Project.ECommerce.ResponseDTO.OrderResponseDto;
import com.Project.ECommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JavaMailSender emailSender;

    @Override
    public String addToCart(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientResourcesException {
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

        // we are not making new cart because each customer is having only one cart
        Cart cart = customer.getCart();

        int newCost = cart.getCartTotal() + (orderRequestDto.getRequiredQuantity() * product.getPrice());
        cart.setCartTotal(newCost);


        // ** firstly make the item and set the cart in it because child serve first by their parent
        // add item to current cart
        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());

        // we are adding cartId and productId in item table.
        item.setProduct(product);
        item.setCart(cart);
        item.setItemStatus(ItemStatus.ADDED_TO_CART);


        // ** after child being served not parent can add the item in its database
        // set the item to cart because it is parent
        cart.getItemList().add(item);

        customerRepository.save(customer);


        // return the string
        return "Item has been added to the cart";
    }

    @Override
    public List<OrderResponseDto> checkoutCart(int customerId) throws CustomerNotFoundException, ProductOutOfStockException {
        // check the customer
        Customer customer;
        try {
            customer = customerRepository.findById(customerId).get();
        } catch (Exception e) {
            throw new CustomerNotFoundException("Invalid customer Id");
        }

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        Cart cart = customer.getCart();

        for (Item item : cart.getItemList()) {
            //check item.reqd quantity >=product.quantity
            if (item.getProduct().getQuantity() < item.getRequiredQuantity()) {
                throw new ProductOutOfStockException();
            }
            //if product is outofstock
            else if (item.getProduct().getProductStatus() == ProductStatus.OUT_OF_STOCK) {
                throw new ProductOutOfStockException();
            }
        }

        //make masked card
        String cardNo = "";
        Card card = customer.getCardList().get(0);
        for (int i = 0; i < card.getCardNo().length() - 4; i++) {
            cardNo += 'X';
        }

        //at this point if we arrive , it means all the items in the cart are okay and can be proceeded for checkout

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        //place order for every item one by one
        int size = cart.getItemList().size();

        int count = 0;

        int cartTotal = cart.getCartTotal();

        int deliveryCharge;
        //lets assign a delivery charge
        if (cartTotal >= 5000) {
            deliveryCharge = 20;
        } else if (cartTotal >= 2000) {
            deliveryCharge = 40;
        } else if (cartTotal >= 500) {
            deliveryCharge = 60;
        } else {
            deliveryCharge = 100;
        }

        for (Item item : cart.getItemList()) {

            //place order
            //calculate total cost
            int cost = item.getProduct().getPrice() * item.getRequiredQuantity();

            //total cost
            int totalCost = cost + deliveryCharge;

            //deduct the requested quantity from current quantity of the prodct
            item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getRequiredQuantity());

            //make productStatus = OUT_OF_STOCK if productquantity = 0
            if (item.getProduct().getQuantity() == 0) {
                item.getProduct().setProductStatus(ProductStatus.OUT_OF_STOCK);
            }

            //prepare the Ordered object and set its attributes
            Ordered newOrder = new Ordered();

            newOrder.setTotalCost(totalCost);
            newOrder.setDeliveryCharge(deliveryCharge);
            newOrder.setCardUsedForPayment(cardNo);
            newOrder.setCustomer(customer);

            customer.getOrderedList().add(newOrder);

            item.setOrdered(newOrder);
            item.setCart(null);
            item.setItemStatus(ItemStatus.ORDERED);

            newOrder.getItemList().add(item);

//            customerRepository.save(customer);
            count++;

            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .productName(item.getProduct().getName())
                    .orderDate(item.getOrdered().getOrderDate())
                    .quantityOrdered(item.getRequiredQuantity())
                    .cardUsedForPayment(cardNo)
                    .itemPrice(item.getProduct().getPrice())
                    .totalCost(newOrder.getTotalCost())
                    .deliveryCharge(deliveryCharge)
                    .build();

            orderResponseDtoList.add(orderResponseDto);

            String text = "Congrats !!. your order with total value " + totalCost + " has been placed.";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mukesh1029mishra@gmail.com");
            message.setTo(customer.getEmail());
            message.setSubject("Order placed via cart");
            message.setText(text);
            emailSender.send(message);
        }

        //empty the cart
        cart.setCartTotal(0);
        cart.getItemList().clear();
        customerRepository.save(customer);

        return orderResponseDtoList;
    }
}

//        int totalCost = cart.getCartTotal();
//        int deliveryCharge = 0;
//        if(cart.getCartTotal() < 499) {
//            deliveryCharge = 50;
//        }
//        for (Item item : cart.getItemList()) {
//            Ordered ordered = new Ordered();
//            ordered.setTotalCost(item.getRequiredQuantity() * item.getProduct().getPrice());
//            ordered.setDeliveryCharge(deliveryCharge);
//            ordered.setCustomer(customer);
//            ordered.getItemList().add(item);
//            String cardNo = "";
//            Card card = customer.getCardList().get(0);
//            for (int i = 0; i < card.getCardNo().length() - 4; i++) {
//                cardNo += 'X';
//            }
//            cardNo += card.getCardNo().substring(card.getCardNo().length() - 4);
//            ordered.setCardUsedForPayment(cardNo);
//
//            int leftQuantity = item.getProduct().getQuantity() - item.getRequiredQuantity();
//            if(leftQuantity <= 0)
//            {
//                item.getProduct().setProductStatus(ProductStatus.OUT_OF_STOCK);
//            }
//            item.getProduct().setQuantity(leftQuantity);
//
//            customer.getOrderedList().add(ordered);
//            item.setOrdered(ordered);
//
//            // prepare the response Dto
//            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
//                    .productName(item.getProduct().getName())
//                    .orderDate(item.getOrdered().getOrderDate())
//                    .quantityOrdered(item.getRequiredQuantity())
//                    .cardUsedForPayment(cardNo)
//                    .itemPrice(item.getProduct().getPrice())
//                    .totalCost(ordered.getTotalCost())
//                    .deliveryCharge(deliveryCharge)
//                    .build();
//
//
//            orderResponseDtos.add(orderResponseDto);
//        }
//
//        cart.setItemList(new ArrayList<>());
//        cart.setCartTotal(0);
//
//        customerRepository.save(customer);
//
//        String text = "Congrats !!. your order with total value "+ totalCost + " has been placed." ;
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("mukesh1029mishra@gmail.com");
//        message.setTo(customer.getEmail());
//        message.setSubject("Order placed via cart");
//        message.setText(text);
//        emailSender.send(message);
//        return orderResponseDtos;
//    }
//}
