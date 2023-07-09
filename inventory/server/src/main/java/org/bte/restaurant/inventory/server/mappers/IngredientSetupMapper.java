package org.bte.restaurant.inventory.server.mappers;

import org.bte.restaurant.inventory.connector.model.IngredientSetupDTO;
import org.bte.restaurant.inventory.connector.model.MenuItemDTO;
import org.bte.restaurant.inventory.server.model.IngredientSetup;
import org.bte.restaurant.inventory.server.model.MenuItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface IngredientSetupMapper {
    List<IngredientSetupDTO> toDto(List<IngredientSetup> menuItems);

    List<IngredientSetup> toModel(List<IngredientSetupDTO> menuItems);
}
