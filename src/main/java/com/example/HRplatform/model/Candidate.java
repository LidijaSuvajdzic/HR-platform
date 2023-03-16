package com.example.HRplatform.model;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private UUID id;

    @Column(name="firstname", nullable=false)
    private String firstname;

    @Column(name="lastname", nullable=false)
    private String lastname;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="phoneNumber", nullable=false)
    private String phoneNumber;

    @Column(name="dateOfBirth", nullable=false)
    private Date dateOfBirth;

    @ManyToMany
    @JoinTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> skills;
}
