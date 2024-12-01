package com.Negi.NomNomExpress.DTO;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.Negi.NomNomExpress.Entity.Cart;
import com.Negi.NomNomExpress.Entity.CartItem;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
	 
	 @NotNull(message="Cart Id can't be null")
	 private Long cartId;
	 @NotNull(message="User Id can't be null")
	 private Long userId;
	 
	 private List<CartItemDTO> cartItems;
	 private Double cartTotal;
	 
	 public CartDTO(Cart cart) {
		 this.cartId = cart.getCartId();
		 this.userId = cart.getUser().getUserId();
		 this.cartItems = cart.getCartItems().stream().map(CartItemDTO::new).collect(Collectors.toList());
		 this.cartTotal = cart.getCartTotal();
	 }
}
