package com.Negi.NomNomExpress.DTO;

import javax.validation.constraints.NotNull;

import com.Negi.NomNomExpress.Entity.CartItem;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
	
	@NotNull(message="User Id can't be null")
	private Long userId;
	@NotNull(message="Restaurant Id can't be null")
	private Long restaurantId;
	@NotNull(message="menuItemId can't be null")
	private Long menuItemId;
	@NotNull(message="price can't be null")
	private Double price;
	
	private Integer quantity;
	
	public CartItemDTO(CartItem cartItem) {
		this.userId = cartItem.getCart().getUser().getUserId();
		this.restaurantId = cartItem.getRestaurantId();
		this.menuItemId = cartItem.getMenuItemId();
		this.price = cartItem.getPrice();
		this.quantity = cartItem.getQuantity();
	}
}
