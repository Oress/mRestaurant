package org.bte.restaurant.orders.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Integer id;

//    @ManyToOne
//    private Order order;

    @Column(name = "menu_item_id")
    private UUID menuItemId;

    private int quantity = 1;

    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;

    @Column(name = "price_total")
    private BigDecimal priceTotal;

    public static OrderItem createOrderItem(UUID productId, Integer quantity, BigDecimal pricePerUnit) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(productId);
        orderItem.setPricePerUnit(pricePerUnit);
        orderItem.setPriceTotal(pricePerUnit.multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }
}
