package com.beerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BeerserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerserviceApplication.class, args);
    }

}
