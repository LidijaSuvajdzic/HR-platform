package com.example.HRplatform.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable=false,  unique = true)
    private Long id;

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

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @JsonIgnore
    private List<Skill> skills;

    public Candidate() {

    }

    public Candidate(String firstname, String lastname, String email, String phoneNumber, Date dateOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
