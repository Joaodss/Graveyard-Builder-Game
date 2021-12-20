package com.ironhack.signinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SigninServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigninServiceApplication.class, args);
    }

}
