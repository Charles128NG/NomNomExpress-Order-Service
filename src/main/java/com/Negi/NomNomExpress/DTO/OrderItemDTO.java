package com.Negi.NomNomExpress.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.Negi.NomNomExpress.Entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
	private Long itemId;
	private Long dishId;
	private Integer quantity;
	private Double price;
	
	public OrderItemDTO(OrderItem orderItem) {
		this.itemId = orderItem.getItemId();
		this.dishId = orderItem.getDishId();
		this.quantity = orderItem.getQuantity();
		this.price = orderItem.getPrice();
	}
}
