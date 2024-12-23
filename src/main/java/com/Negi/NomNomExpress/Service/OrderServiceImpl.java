package com.Negi.NomNomExpress.Service;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.Negi.NomNomExpress.DTO.CancelOrderDTO;
import com.Negi.NomNomExpress.DTO.CartDTO;
import com.Negi.NomNomExpress.DTO.CartItemDTO;
import com.Negi.NomNomExpress.DTO.OrderDTO;
import com.Negi.NomNomExpress.DTO.PlaceOrderDTO;
import com.Negi.NomNomExpress.Entity.*;
import com.Negi.NomNomExpress.Exception.RESTException;
import com.Negi.NomNomExpress.Repository.*;
import com.Negi.NomNomExpress.kafka.KafkaProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private KafkaProducerService kafkaService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
 	public Cart addToCart(@Valid CartItemDTO cartItemDTO) throws RESTException{
		try {
			Optional<UserEntity> user = userRepo.findById(cartItemDTO.getUserId());
			
			if(!user.isPresent()) {
				throw new RESTException("User not found", HttpStatus.BAD_REQUEST);
			}
			
			Optional<Cart> cartOptional = cartRepo.findByUser(user.get());
			if(!cartOptional.isPresent()) {
				//create new cart 
				Cart cart = new Cart();
				cart.setUser(user.get());
				cart.setCartItems(null);
				cart.setCartTotal(0.0);
				cart = cartRepo.save(cart);
				
				//and add items to it
				List<CartItem> cartItems = new ArrayList<>();
				cartItems.add(cartItemRepo.save(new CartItem(cartItemDTO, 1, cart)));
				cart.setCartTotal(cartItemDTO.getPrice());
				cart.setCartItems(cartItems);
				return cartRepo.save(cart);
			}else {
				Cart cart = cartOptional.get();
				List<CartItem> cartItems= cart.getCartItems();
				
				//if cartITems is null, make new with current Item
				if(cartItems == null) {
					cartItems = new ArrayList<>();
					cartItems.add(cartItemRepo.save(new CartItem(cartItemDTO, 1, cart)));
					cart.setCartTotal(cartItemDTO.getPrice());
					cart.setCartItems(cartItems);
					return cartRepo.save(cart);
				}
				else {
					Optional<CartItem> matchingCartItem = cartItems.stream()
						    .filter(obj -> obj.getMenuItemId().equals(cartItemDTO.getMenuItemId()))
						    .findFirst();
					if (matchingCartItem.isPresent()) {
						cartItems.remove(matchingCartItem.get());
						matchingCartItem.get().setQuantity(matchingCartItem.get().getQuantity()+1);
						cartItems.add(matchingCartItem.get());
						cart.setCartItems(cartItems);
						cart.setCartTotal(cart.getCartTotal() + cartItemDTO.getPrice());
						return cartRepo.save(cart);
					}
					else {
						cartItems.add(cartItemRepo.save(new CartItem(cartItemDTO, 1, cart)));
						cart.setCartItems(cartItems);
						cart.setCartTotal(cart.getCartTotal() + cartItemDTO.getPrice());
						return cartRepo.save(cart);
					}
				}
			}
		}catch(RESTException e) {
			throw e;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RESTException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Cart removeFromCart(@Valid CartItemDTO cartItemDTO) throws RESTException{
		try {
			Optional<UserEntity> user = userRepo.findById(cartItemDTO.getUserId());
			
			if(!user.isPresent()) {
				throw new RESTException("User not found", HttpStatus.BAD_REQUEST);
			}
			
			Optional<Cart> cartOptional = cartRepo.findByUser(user.get());
			if(!cartOptional.isPresent()) {
				throw new RESTException("Cart Not Created for user!!",HttpStatus.BAD_REQUEST);
			}else {
				Cart cart = cartOptional.get();
				List<CartItem> cartItems= cart.getCartItems();
				
				//if cartITems is null, say cart not made
				if(cartItems == null) {
					throw new RESTException("Cart Not Created for user!!",HttpStatus.BAD_REQUEST);
				}
				else {
					Optional<CartItem> matchingCartItem = cartItems.stream()
						    .filter(obj -> obj.getMenuItemId().equals(cartItemDTO.getMenuItemId()))
						    .findFirst();
					if (matchingCartItem.isPresent()) {
						cartItems.remove(matchingCartItem.get());
						
						matchingCartItem.get().setQuantity(matchingCartItem.get().getQuantity()-1);
						
						if(matchingCartItem.get().getQuantity()>=1) {
							cartItems.add(matchingCartItem.get());
						}
						else {
							cartItemRepo.delete(matchingCartItem.get());
						}
						cart.setCartItems(cartItems);
						cart.setCartTotal(cart.getCartTotal() - cartItemDTO.getPrice());
						
						return cartRepo.save(cart);
					}
					else {
						throw new RESTException("Item not in cart", HttpStatus.BAD_REQUEST);
					}
				}
			}
		}catch(RESTException e) {
			throw e;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RESTException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Cart showCart(@Valid CartDTO cartDTO) throws RESTException{
		try {
			Optional<UserEntity> user = userRepo.findById(cartDTO.getUserId());
			
			if(!user.isPresent()) {
				throw new RESTException("User not found", HttpStatus.BAD_REQUEST);
			}
			Optional<Cart> cartOptional = cartRepo.findById(cartDTO.getCartId());
			if(!cartOptional.isPresent()) {
				throw new RESTException("Cart Not Created for user!!",HttpStatus.BAD_REQUEST);
			}
			else {
				return cartOptional.get();
			}
		}catch(RESTException e) {
			throw e;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RESTException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Cart emptyCart(@Valid CartDTO cartDTO) throws RESTException{
		
		try {
			Optional<UserEntity> user = userRepo.findById(cartDTO.getUserId());

			if (!user.isPresent()) {
				throw new RESTException("User not found", HttpStatus.BAD_REQUEST);
			}
			Optional<Cart> cartOptional = cartRepo.findById(cartDTO.getCartId());
			if(!cartOptional.isPresent()) {
				throw new RESTException("Cart Not Created for user!!",HttpStatus.BAD_REQUEST);
			}
			else {
				cartOptional.get().setCartTotal(0.0);
				cartOptional.get().getCartItems().clear();
				return cartRepo.save(cartOptional.get());
			}
		}catch(RESTException e) {
			throw e;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RESTException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Order placeOrder(@Valid PlaceOrderDTO placeOrderDTO) throws RESTException {
		
		try {
			Optional<UserEntity> user = userRepo.findById(placeOrderDTO.getUserId());
			if(!user.isPresent()) {
				throw new RESTException("User not found", HttpStatus.BAD_REQUEST);
			}
			
			Optional<Cart> cartOptional = cartRepo.findById(placeOrderDTO.getCartId());
			if(!cartOptional.isPresent()) {
				throw new RESTException("invalid cart id", HttpStatus.BAD_REQUEST);
			}
			else {
				Cart cart = cartOptional.get();
				Order order = new Order();
				order.setUser(user.get());
				order.setDeliveryAddress(placeOrderDTO.getDeliveryAddress());
				order.setOrderStatus("Order Placed");
				order.setTotalPrice(cart.getCartTotal());
				Order savedOrder = orderRepo.save(order);
				
				List<OrderItem> orderItems = cart.getCartItems().stream()
						.map(cartItem -> new OrderItem(cartItem, savedOrder)).collect(Collectors.toList());
				
				savedOrder.setItems(orderItems);
				Order finalOrder = orderRepo.save(savedOrder);
				kafkaService.sendMessage(objectMapper.writeValueAsString(new OrderDTO(finalOrder)));
				return finalOrder;
			}
		}catch(RESTException e) {
			throw e;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RESTException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Order cancleOrder(@Valid CancelOrderDTO cancelOrderDTO)throws RESTException {
		try {
			Optional<Order> orderOptional = orderRepo.findById(cancelOrderDTO.getOrderId());
			if(orderOptional.isPresent()) {
				orderOptional.get().setOrderStatus("Order Canceled");
				orderOptional.get().setUpdatedAt(LocalDateTime.now());
				return orderRepo.save(orderOptional.get());
			}else {
				throw new RESTException("Order not found",HttpStatus.BAD_REQUEST);
			}
		}catch(RESTException e) {
			throw e;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RESTException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
