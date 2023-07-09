package org.bte.restaurant.orders.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NONE;

    @Column(name = "status_details")
    private String statusDetails;

    @Column(name = "created_by_id")
    private UUID createdById;

    @OneToMany
    List<OrderItem> items = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "last_modified")
    private Date lastModified;

    public static Order createNewOrder(UUID createdBy, Collection<OrderItem> items) {
        Order order = new Order();
        order.setCreatedById(createdBy);
        order.setStatus(OrderStatus.CREATED);
        order.setItems(new ArrayList<>(items));
        return order;
    }

    public void cancelOrder() {
        if (this.status == OrderStatus.CREATED) {
            this.status = OrderStatus.CANCELLED;
            this.statusDetails = "Order cancelled";
        } else {
            throw new IllegalStateException("Order can be cancelled only in CREATED state");
        }
    }

    public void rejectOrder() {
        if (this.status == OrderStatus.CREATED) {
            this.status = OrderStatus.PAYMENT_REJECTED;
            this.statusDetails = "Order rejected";
        } else {
            throw new IllegalStateException("Order can be rejected only in CREATED state");
        }
    }

    public void startOrder() {
        if (this.status == OrderStatus.CREATED) {
            this.status = OrderStatus.IN_PROGRESS;
            this.statusDetails = "Order started";
        } else {
            throw new IllegalStateException("Order can be started only in CREATED state");
        }
    }

    public void completeOrder() {
        if (this.status == OrderStatus.IN_PROGRESS) {
            this.status = OrderStatus.COMPLETED;
            this.statusDetails = "Order completed";
        } else {
            throw new IllegalStateException("Order can be completed only in IN_PROGRESS state");
        }
    }
}

