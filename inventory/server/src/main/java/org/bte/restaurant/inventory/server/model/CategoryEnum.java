package org.bte.restaurant.inventory.server.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Enumerated;
import org.bte.restaurant.inventory.connector.model.MenuItemDTO;

public enum CategoryEnum {
    BREAKFAST("breakfast"),

    LUNCH("lunch"),

    DRINKS("drinks"),

    DINNER("dinner");

    private String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static MenuItemDTO.CategoryEnum fromValue(String value) {
        for (MenuItemDTO.CategoryEnum b : MenuItemDTO.CategoryEnum.values()) {
            if (b.getValue().equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
