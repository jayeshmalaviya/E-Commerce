package com.Project.ECommerce.Repository;

import com.Project.ECommerce.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

    // Inbuilt Custom
    Seller findByPanCard(String panCard);

    List<Seller> findByAge(int age);


    // Custom Query
    @Query(value = "Select * from Seller s where s.age>=:startAge and s.age<=:endAge", nativeQuery = true)
    List<Seller> findAllAge(int startAge, int endAge);
}