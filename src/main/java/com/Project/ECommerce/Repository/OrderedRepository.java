package com.Project.ECommerce.Repository;

import com.Project.ECommerce.Model.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderedRepository extends JpaRepository<Ordered, Integer> {
    @Query(value = "SELECT o.order_date FROM orders o ORDER BY o.order_date DESC limit 1",nativeQuery = true)
    Date findLastOrderDate();
}