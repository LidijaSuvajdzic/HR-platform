package com.example.HRplatform.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotBlank
    private String firstname;

    @Column
    @NotBlank
    private String lastname;

    @Column
    @NotBlank
    private String email;

    @Column
    @NotBlank
    private String phoneNumber;

    @Column
    private Date dateOfBirth;

    @OneToMany(mappedBy = "candidateId")
    private List<CandidateSkill> skills;

    public Candidate(String firstname, String lastname, String email, String phoneNumber, Date dateOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}
