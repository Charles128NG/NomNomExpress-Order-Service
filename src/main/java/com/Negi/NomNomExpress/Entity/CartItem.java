package com.Negi.NomNomExpress.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.Negi.NomNomExpress.DTO.CartItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @Column(nullable = false, name = "restaurant_id")
    private Long restaurantId;

    @Column(nullable = false, name = "menuItemId")
    private Long menuItemId;

    @Column(nullable = false, name = "quantity")
    private Integer quantity;

    @Column(nullable = false)
    private Double price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_cart_id", nullable = false)
    private Cart cart;
    
    public CartItem(CartItemDTO cartItemDTO, Integer quantity, Cart cart) {
    	this.cart = cart;
    	this.quantity = quantity;
    	this.restaurantId = cartItemDTO.getRestaurantId();
    	this.menuItemId = cartItemDTO.getMenuItemId();
    	this.price = cartItemDTO.getPrice();
    }
}

