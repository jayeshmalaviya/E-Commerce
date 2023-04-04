package com.Project.ECommerce.Repository;

import com.Project.ECommerce.Enum.ProductCategory;
import com.Project.ECommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // custom query
//    @Query(value = "select * from product p where p.category=:category", nativeQuery = true)
//    List<Product> getListOfProductsViaCategory(String category);

    //using findAllByProductCategory
    //ORM is written the whole code for us.
    List<Product> findAllByProductCategory(ProductCategory productCategory);
}