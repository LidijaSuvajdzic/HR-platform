package com.example.HRplatform.dto;

public class RemoveSkillRequestDto {

    private String candidateFirstname;

    private String candidateLastname;

    private String skillName;

    public RemoveSkillRequestDto(String candidateFirstname, String candidateLastname, String skillName) {
        this.candidateFirstname = candidateFirstname;
        this.candidateLastname = candidateLastname;
        this.skillName = skillName;
    }

    public String getCandidateFirstname() {
        return candidateFirstname;
    }

    public void setCandidateFirstname(String candidateFirstname) {
        this.candidateFirstname = candidateFirstname;
    }

    public String getCandidateLastname() {
        return candidateLastname;
    }

    public void setCandidateLastname(String candidateLastname) {
        this.candidateLastname = candidateLastname;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
