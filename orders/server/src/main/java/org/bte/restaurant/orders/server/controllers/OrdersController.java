package org.bte.restaurant.orders.server.controllers;

import lombok.AllArgsConstructor;
import org.bte.auth.client.feign.api.MenuApi;
import org.bte.auth.client.feign.api.MenuApiClient;
import org.bte.auth.client.feign.model.MenuItemDTO;
import org.bte.restaurant.inventory.connector.api.OrdersApi;
import org.bte.restaurant.inventory.connector.model.BaseResponseDTO;
import org.bte.restaurant.inventory.connector.model.OrderDTO;
import org.bte.restaurant.inventory.connector.model.OrderItemDTO;
import org.bte.restaurant.orders.server.mapper.OrderMapper;
import org.bte.restaurant.orders.server.model.Order;
import org.bte.restaurant.orders.server.model.OrderItem;
import org.bte.restaurant.orders.server.repository.OrderRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
public class OrdersController implements OrdersApi {
    private final MenuApi menuApi;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final Environment environment;

    @Override
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO) {
        // TODO: validate orderDTO
        UUID userId = orderDTO.getCreatedById();
        List<UUID> collect = orderDTO.getOrderItems().stream().map(OrderItemDTO::getMenuItemId).collect(Collectors.toList());

        // sync call to the menu service to fetch the actual menu items
        List<MenuItemDTO> items = menuApi.getItemsByIds(collect).getBody();

        List<OrderItem> orderItems = items.stream().map(item -> OrderItem.createOrderItem(item.getId(), 1, BigDecimal.valueOf(item.getPrice()))).toList();

        Order newOrder= Order.createNewOrder(orderDTO.getCreatedById(), orderItems);
        newOrder = repository.save(newOrder);

        return ResponseEntity.ok(mapper.toDto(newOrder));
    }

    @Override
    public ResponseEntity<OrderDTO> getOrders(UUID userId, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public ResponseEntity<BaseResponseDTO> updateOrder(OrderDTO orderDTO) {
        return null;
    }
}
