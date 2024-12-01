package com.Negi.NomNomExpress.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDTO {
	
	@NotNull(message="Cart Id can't be null")
	private Long cartId;
	@NotNull(message="delivery address can't be null")
	private String deliveryAddress;
	
	private Long userId;
}
