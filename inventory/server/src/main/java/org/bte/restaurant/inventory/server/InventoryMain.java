package org.bte.restaurant.inventory.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryMain {
    public static void main(String[] args) {
//        SpringApplication.run(InventoryMain.class, args);
        new InventoryMain().test(new double[Integer.MAX_VALUE / 2]);
    }

    public void test(double[] doubles) {
        test(new double[Integer.MAX_VALUE / 2]);
    }
}