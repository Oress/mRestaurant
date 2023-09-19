package org.bte.restaurant.inventory.server.controllers;

import org.assertj.core.util.Lists;
import org.bte.restaurant.inventory.connector.model.MenuItemDTO;
import org.bte.restaurant.inventory.server.InventoryMain;
import org.bte.restaurant.inventory.server.model.CategoryEnum;
import org.bte.restaurant.inventory.server.model.MenuItem;
import org.bte.restaurant.inventory.server.repository.MenuItemsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class MenuControllerTest {
    private final WebApplicationContext wac;
    private final MenuController menuController;
    private final MenuItemsRepository itemsRepository;
    private MenuItem item1;
    private MenuItem item2;

    @Autowired
    public MenuControllerTest(WebApplicationContext wac) {
        this.wac = wac;
        this.menuController = this.wac.getBean(MenuController.class);
        this.itemsRepository = this.wac.getBean(MenuItemsRepository.class);
    }

    @BeforeEach
    public void setup() {
        item1 = new MenuItem();
        item1.setTitle("Title Item 1");
        item1.setPrice(BigDecimal.valueOf(2.0));

        item2 = new MenuItem();
        item1.setTitle("Title Item 2");
        item1.setPrice(BigDecimal.valueOf(1.0));

        itemsRepository.saveAll(Lists.list(item1, item2));
    }

    @Test
    public void test_getItemsByIds() {
        ResponseEntity<List<MenuItemDTO>> responseEntity = menuController.getItemsByIds(Lists.list(item1.getId(), item2.getId());
        List<MenuItemDTO> body = responseEntity.getBody();

        Assertions.assertEquals(body.size(), 2);

    }
}