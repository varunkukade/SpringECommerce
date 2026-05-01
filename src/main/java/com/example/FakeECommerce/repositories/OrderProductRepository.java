package com.example.FakeECommerce.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.FakeECommerce.schema.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT DISTINCT op FROM OrderProduct op JOIN FETCH op.product p JOIN FETCH p.category WHERE op.order.id IN :orderIds")
    List<OrderProduct> findAllByOrderIdInWithProductAndCategory(@Param("orderIds") Collection<Long> orderIds);

    Optional<OrderProduct> findByOrder_IdAndProduct_Id(Long orderId, Long productId);

    List<OrderProduct> findAllByOrder_Id(Long orderId);
}
