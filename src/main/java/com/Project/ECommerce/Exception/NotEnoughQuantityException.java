package com.Project.ECommerce.Exception;

public class NotEnoughQuantityException extends  Exception{
    public NotEnoughQuantityException(){
        super("Required Quantity of the product not available");
    }
}
