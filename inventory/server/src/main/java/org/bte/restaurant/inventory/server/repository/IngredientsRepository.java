package org.bte.restaurant.inventory.server.repository;

import org.bte.restaurant.inventory.server.model.IngredientSetup;
import org.bte.restaurant.inventory.server.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientsRepository extends JpaRepository<IngredientSetup, Integer> {
}
