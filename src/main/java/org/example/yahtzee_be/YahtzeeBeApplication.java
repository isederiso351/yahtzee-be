package org.example.yahtzee_be;

import org.example.yahtzee_be.config.GameProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties(GameProperties.class)
@EnableAsync
public class YahtzeeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YahtzeeBeApplication.class, args);
    }

}
