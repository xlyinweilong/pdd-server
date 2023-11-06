package com.yin.pddserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PddServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PddServerApplication.class, args);
    }

}
