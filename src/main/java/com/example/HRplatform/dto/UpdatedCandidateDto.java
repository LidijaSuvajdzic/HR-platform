package com.example.HRplatform.dto;

import java.util.Date;
import java.util.List;

public class UpdatedCandidateDto {
    private String oldFirstname;
    private String oldLastname;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Date date;
    private List<String> skills;

    public UpdatedCandidateDto(String oldFirstname, String oldLastname, String firstname, String lastname, String email, String phoneNumber, Date date, List<String> skills) {
        this.oldFirstname = oldFirstname;
        this.oldLastname = oldLastname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.skills = skills;
    }

    public String getOldFirstname() {
        return oldFirstname;
    }

    public void setOldFirstname(String oldFirstname) {
        this.oldFirstname = oldFirstname;
    }

    public String getOldLastname() {
        return oldLastname;
    }

    public void setOldLastname(String oldLastname) {
        this.oldLastname = oldLastname;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
