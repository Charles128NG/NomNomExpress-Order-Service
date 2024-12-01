package com.Negi.NomNomExpress.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderDTO {
	@NotNull(message="Order Id can't be null")
	private Long orderId;
}
