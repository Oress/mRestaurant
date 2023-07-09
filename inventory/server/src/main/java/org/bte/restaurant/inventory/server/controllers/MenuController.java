package org.bte.restaurant.inventory.server.controllers;

import lombok.AllArgsConstructor;
import org.bte.restaurant.inventory.connector.api.MenuApi;
import org.bte.restaurant.inventory.connector.model.BaseResponseDTO;
import org.bte.restaurant.inventory.connector.model.MenuItemDTO;
import org.bte.restaurant.inventory.server.mappers.MenuItemMapper;
import org.bte.restaurant.inventory.server.model.MenuItem;
import org.bte.restaurant.inventory.server.model.MenuItem_;
import org.bte.restaurant.inventory.server.repository.MenuItemsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class MenuController implements MenuApi {

    private final MenuItemsRepository repository;
    private final MenuItemMapper mapper;
    private final Sort menuItemsSortBy = Sort.by(Sort.Order.desc(MenuItem_.CATEGORY),
            Sort.Order.desc(MenuItem_.TITLE),
            Sort.Order.desc(MenuItem_.ID));

    @Override
    public ResponseEntity<List<MenuItemDTO>> getMenu(Integer offset, Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? 0 : limit;

        Pageable pageable = PageRequest.of(offset, limit, menuItemsSortBy);
        Page<MenuItem> items =  repository.findAll(pageable);

        return ResponseEntity.ok(mapper.toDto(items.toList()));
    }

    @Override
    public ResponseEntity<List<MenuItemDTO>> updateMenuItems(List<MenuItemDTO> menuItemDTO) {
        List<MenuItem> items =  mapper.toModel(menuItemDTO);
        List<MenuItem> updatedItems = repository.saveAll(items);
        return ResponseEntity.ok(mapper.toDto(updatedItems));
    }

    @Override
    public ResponseEntity<BaseResponseDTO> deleteMenuItems(List<MenuItemDTO> menuItemDTO) {
        BaseResponseDTO responseDTO = new BaseResponseDTO();

        List<UUID> items =  menuItemDTO.stream().map(MenuItemDTO::getId).toList();
        try {
            repository.deleteAllById(items);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        responseDTO.setSuccess(true);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<List<MenuItemDTO>> getItemsByIds(List<UUID> menuItemsIds) {
        return ResponseEntity.ok(mapper.toDto(repository.findAllById(menuItemsIds)));
    }
}
