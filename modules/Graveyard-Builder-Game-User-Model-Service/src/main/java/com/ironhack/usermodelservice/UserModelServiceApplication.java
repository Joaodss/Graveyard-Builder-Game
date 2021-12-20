package com.ironhack.usermodelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserModelServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserModelServiceApplication.class, args);
    }

}
