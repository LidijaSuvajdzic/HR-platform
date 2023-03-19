package com.example.HRplatform.dto;

public class RemoveSkillRequestDto {

    private String candidateEmail;

    private String skillName;

    public RemoveSkillRequestDto(String candidateEmail, String skillName) {
        this.candidateEmail = candidateEmail;
        this.skillName = skillName;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
