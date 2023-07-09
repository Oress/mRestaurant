package org.bte.restaurant.inventory.server.mappers;

import org.bte.restaurant.inventory.connector.model.MenuItemDTO;
import org.bte.restaurant.inventory.server.model.MenuItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MenuItemMapper {
    List<MenuItemDTO> toDto(List<MenuItem> menuItems);

    List<MenuItem> toModel(List<MenuItemDTO> menuItems);
}
