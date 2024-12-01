package com.Negi.NomNomExpress.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Negi.NomNomExpress.Entity.Cart;
import com.Negi.NomNomExpress.Entity.UserEntity;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Optional<Cart> findByUser(UserEntity user);
}
