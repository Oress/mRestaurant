package org.bte.restaurant.orders.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = {
        org.bte.auth.client.feign.api.MenuApiClient.class
})
public class OrdersMain {
    public static void main(String[] args) {
        SpringApplication.run(OrdersMain.class, args);
    }
}
