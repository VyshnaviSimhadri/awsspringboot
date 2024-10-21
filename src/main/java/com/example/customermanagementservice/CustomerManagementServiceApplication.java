package com.example.customermanagementservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableEurekaClient
//@EnableJpaRepositories(basePackages = {"com.example.customermanagementservice.repository.CustomerRepository"})
public class CustomerManagementServiceApplication {

    @Value("${server.port}")
    private int serverPort;

    public static void main(String[] args) {
        SpringApplication.run(CustomerManagementServiceApplication.class, args);
    }

    @Bean
    public void printServerPort() {
        System.out.println("Server running on port: " + serverPort);
    }
}
