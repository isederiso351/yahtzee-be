package org.example.yahtzee_be.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "\"user\"")
public class User {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keycloackID;

    @Email
    @NotBlank
    @Column(unique=true, nullable=false)
    private String email;

    @Min(0)
    @Column(nullable=false)
    private double credit;
}
