package com.Negi.NomNomExpress.Service;

import javax.validation.Valid;

import com.Negi.NomNomExpress.DTO.CancelOrderDTO;
import com.Negi.NomNomExpress.DTO.CartDTO;
import com.Negi.NomNomExpress.DTO.CartItemDTO;
import com.Negi.NomNomExpress.DTO.PlaceOrderDTO;
import com.Negi.NomNomExpress.Entity.Cart;
import com.Negi.NomNomExpress.Entity.Order;

public interface OrderService {

	Cart addToCart(@Valid CartItemDTO cartItemDTO);

	Cart removeFromCart(@Valid CartItemDTO cartItemDTO);

	Cart showCart(@Valid CartDTO cartDTO);

	Cart emptyCart(@Valid CartDTO cartDTO);

	Order placeOrder(@Valid PlaceOrderDTO placeOrderDTO);

	Order cancleOrder(@Valid CancelOrderDTO cancelOrderDTO);

}
