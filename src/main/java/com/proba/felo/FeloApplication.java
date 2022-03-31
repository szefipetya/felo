package com.proba.felo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FeloApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeloApplication.class, args);
    }

}
