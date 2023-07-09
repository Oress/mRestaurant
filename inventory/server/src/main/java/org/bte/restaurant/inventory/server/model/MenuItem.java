package org.bte.restaurant.inventory.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menu_items")
@Setter
@Getter
public class MenuItem {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private List<String> ingredients = null;

    private BigDecimal price;

    private String image;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
}


