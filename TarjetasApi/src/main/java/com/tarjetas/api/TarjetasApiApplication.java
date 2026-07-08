package com.tarjetas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TarjetasApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TarjetasApiApplication.class, args);
    }
}
