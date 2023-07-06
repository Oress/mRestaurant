package org.bte.restaurant.inventory.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryMain {
    public static void main(String[] args) {
        SpringApplication.run(InventoryMain.class, args);
    }
}