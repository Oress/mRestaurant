package org.bte.restaurant.orders.server.mapper;

import org.bte.restaurant.inventory.connector.model.OrderDTO;
import org.bte.restaurant.orders.server.model.Order;
import org.mapstruct.Mapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    OrderDTO toDto(Order order);

    Order toModel(OrderDTO orderDto);

    default Instant map(Timestamp value) {
        return value == null ? null : value.toInstant();
    }

    default Timestamp map(Instant value) {
        return value == null ? null : Timestamp.from(value);
    }
}
