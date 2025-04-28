package com.example.stocktw50;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockTw50Application {
    public static void main(String[] args) {
        SpringApplication.run(StockTw50Application.class, args);
    }
}
