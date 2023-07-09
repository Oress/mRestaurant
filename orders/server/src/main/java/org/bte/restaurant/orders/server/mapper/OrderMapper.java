package org.bte.restaurant.orders.server.mapper;

import org.bte.restaurant.inventory.connector.model.OrderDTO;
import org.bte.restaurant.orders.server.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderDTO toDto(Order order);

    Order toModel(OrderDTO orderDto);
}
