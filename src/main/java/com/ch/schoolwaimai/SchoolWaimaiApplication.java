package com.ch.schoolwaimai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories

public class SchoolWaimaiApplication {

    public static void main(String[] args) {

        SpringApplication.run(SchoolWaimaiApplication.class, args);
    }

}
