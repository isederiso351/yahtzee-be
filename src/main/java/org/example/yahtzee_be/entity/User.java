package org.example.yahtzee_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "\"user\"")
public class User {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keycloackID;

    @Column(nullable = false, name = "last_bonus_credit")
    private LocalDateTime lastBonusCredit;

    @Column(nullable = false)
    private boolean active=false;

    @Email
    @NotBlank
    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable = false, unique=true)
    private String name;

    @Column(nullable=false)
    private @Min(0) long credit;
}
