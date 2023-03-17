package com.example.HRplatform.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="skills")
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable=false, unique = true)
    private Long id;

    @Column(name="name", nullable=false)
    private String name;

    @ManyToMany(mappedBy = "skills")
    List<Candidate> candidates;

    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
