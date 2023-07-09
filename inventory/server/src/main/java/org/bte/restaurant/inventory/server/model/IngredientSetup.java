package org.bte.restaurant.inventory.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ingredients")
@Setter
@Getter
public class IngredientSetup {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
}


