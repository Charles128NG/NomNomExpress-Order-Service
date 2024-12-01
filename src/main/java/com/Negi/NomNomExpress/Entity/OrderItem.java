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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_order_id", nullable = true)
    private Order order;

    @Column(nullable = false)
    private Long dishId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;
    
    public OrderItem(CartItem cartItem, Order order) {
    	this.order = order;
    	this.dishId = cartItem.getCartItemId();
    	this.quantity = cartItem.getQuantity();
    	this.price = cartItem.getPrice();
    }
}
