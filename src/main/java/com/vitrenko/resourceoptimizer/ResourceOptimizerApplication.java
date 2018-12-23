package com.vitrenko.resourceoptimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ResourceOptimizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceOptimizerApplication.class, args);
    }
}
