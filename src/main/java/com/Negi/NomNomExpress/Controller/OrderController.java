package com.Negi.NomNomExpress.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import com.Negi.NomNomExpress.Service.OrderService;
import com.Negi.NomNomExpress.DTO.*;
import com.Negi.NomNomExpress.Entity.*;
import com.Negi.NomNomExpress.Exception.RESTException;

@RestController
public class OrderController {
	
	@Autowired 
	OrderService service;
	
	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart(@Valid @RequestBody CartItemDTO cartItemDTO) throws RESTException{
		try {
			return new ResponseEntity<>(new CartDTO((Cart) service.addToCart(cartItemDTO)), HttpStatus.OK);
		}catch(RESTException e) {
			throw e;
		}
	}
	@PostMapping("/removeFromCart")
	public ResponseEntity<?> removeFromCart(@Valid @RequestBody CartItemDTO cartItemDTO){
		try {
			return new ResponseEntity<>(new CartDTO((Cart) service.removeFromCart(cartItemDTO)), HttpStatus.OK);
		}catch(RESTException e) {
			throw e;
		}
	}
	@PostMapping("/showCartStatus")
	public ResponseEntity<?> showCartStatus(@Valid @RequestBody CartDTO cartDTO){
		try {
			return new ResponseEntity<>(new CartDTO(service.showCart(cartDTO)), HttpStatus.OK);
		}catch(RESTException e) {
			throw e;
		}
	}
	
	@PostMapping("/emptyCart")
	public ResponseEntity<?> emptyCart(@Valid @RequestBody CartDTO cartDTO){
		try {
			return new ResponseEntity<>(new CartDTO(service.emptyCart(cartDTO)), HttpStatus.OK);
		}catch(RESTException e) {
			throw e;
		}
	}
	@PostMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(@Valid @RequestBody PlaceOrderDTO placeOrderDTO){
		try {
			return new ResponseEntity<>(new OrderDTO(service.placeOrder(placeOrderDTO)), HttpStatus.OK);
		}catch(RESTException e) {
			throw e;
		}
	}
	@PostMapping("/cancelOrder")
	public ResponseEntity<?> cancelOrder(@Valid @RequestBody CancelOrderDTO cancelOrderDTO){
		try {
			OrderDTO resp = new OrderDTO(service.cancleOrder(cancelOrderDTO));
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}catch(RESTException e) {
			throw e;
		}
	}
}
