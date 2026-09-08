package com.gamebasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.gamebasic.game.repository")
public class GameBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameBasicApplication.class, args);
    }

}
