package com.example.iot.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "activities")
@Data
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime timestamp;

    // Constructores
    public Activity() {
    }

    public Activity(String description, LocalDateTime timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    // Getters y Setters

}
