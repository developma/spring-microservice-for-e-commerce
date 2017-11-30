package com.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableDiscoveryClient
@SpringBootApplication
@EnableWebMvc
public class InventoryApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(InventoryApplication.class, args);
        DispatcherServlet dispatcherServlet = ((DispatcherServlet) context.getBean("dispatcherServlet"));
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}
