package com.example.HRplatform.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name="skills")
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private UUID id;

    @Column(name="name", nullable=false)
    private String name;
}
