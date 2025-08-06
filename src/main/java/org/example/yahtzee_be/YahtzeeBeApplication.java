package org.example.yahtzee_be;

import org.example.yahtzee_be.config.GameProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GameProperties.class)
public class YahtzeeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YahtzeeBeApplication.class, args);
    }

}
