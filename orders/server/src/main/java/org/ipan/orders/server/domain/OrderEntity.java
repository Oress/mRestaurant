package org.bte.orders.server.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "order_number")
//    private String orderNumber;
    private Integer orderNumber;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Digits(integer = 9, fraction = 2)
    private BigDecimal total;

    @Digits(integer = 9, fraction = 2)
    private BigDecimal weight;

    private String status;

    @Column(name = "status_details")
    private String statusDetails;

    private Boolean completed;

    @Embedded
    @AttributeOverride(name = "firstName", column = @Column(name = "first_name"))
    @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
    private ContactInfo contactInfo;

    @Lob
//    @Basic(fetch = FetchType.LAZY)
    private String notes;

    @UpdateTimestamp
    @Column(name = "last_modified")
    private Date lastModified;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

}
