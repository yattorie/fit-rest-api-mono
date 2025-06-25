package com.orlovandrei.fit_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class FitRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitRestApplication.class, args);
    }

}
