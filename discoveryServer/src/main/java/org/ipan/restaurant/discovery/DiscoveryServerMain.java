package org.ipan.restaurant.discovery;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerMain {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryServerMain.class).web(WebApplicationType.SERVLET).run(args);
    }
}
