package com.Negi.NomNomExpress.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.Negi.NomNomExpress.Entity.Order;
import com.Negi.NomNomExpress.Entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	@NotNull(message="Order Id can't be null")
	private Long orderId;
	private String user;
    private List<OrderItemDTO> items;
    private Double totalPrice;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String orderStatus;
    
    public OrderDTO(Order order) {
    	this.orderId = order.getOrderId();
    	this.user = order.getUser().getUserName();
    	this.items = order.getItems().stream().map(OrderItemDTO::new).collect(Collectors.toList());
    	this.totalPrice = order.getTotalPrice();
    	this.deliveryAddress = order.getDeliveryAddress();
    	this.createdAt = order.getCreatedAt();
    	this.updatedAt = order.getUpdatedAt();
    	this.orderStatus = order.getOrderStatus();
    }
}
