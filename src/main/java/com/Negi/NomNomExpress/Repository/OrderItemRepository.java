package com.Negi.NomNomExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Negi.NomNomExpress.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
