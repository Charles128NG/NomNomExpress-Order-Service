package com.Negi.NomNomExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Negi.NomNomExpress.Entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
