package com.Negi.NomNomExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Negi.NomNomExpress.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
