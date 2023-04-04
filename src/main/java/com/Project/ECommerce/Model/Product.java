package com.Project.ECommerce.Model;

import com.Project.ECommerce.Enum.ProductCategory;
import com.Project.ECommerce.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Name;

    private int price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;


    @Enumerated(EnumType.STRING)
    ProductCategory productCategory;

    @ManyToOne
    @JoinColumn
    Seller seller;


    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    Item item;

}