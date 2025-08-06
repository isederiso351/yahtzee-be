package org.example.yahtzee_be.config;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix="app.game")
@Validated
@Getter
public class GameProperties {

    @Min(1)
    private long maxPlayers=99;
}
