package com.Project.ECommerce.Exception;

public class ProductOutOfStockException extends Exception{
    public ProductOutOfStockException()
    {
        super("Product out of stock!");
    }
}
