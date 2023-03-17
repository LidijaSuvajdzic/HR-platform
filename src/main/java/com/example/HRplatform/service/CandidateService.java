package com.example.HRplatform.service;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    SkillService skillService;

    @Autowired
    CandidateSkillService candidateSkillService;


    public boolean save(CandidateDto candidateDto) {
        if(candidateRepository.findByFirstNameAndLastName(candidateDto.getFirstname(),candidateDto.getLastname()) != null) {
            return true;
        }else {
            Candidate candidate = new Candidate(candidateDto.getFirstname(), candidateDto.getLastname(), candidateDto.getEmail(), candidateDto.getPhoneNumber(), candidateDto.getDate());
            candidateRepository.save(candidate);
            for (String skillName : candidateDto.getSkills()) {
                if (skillService.findByName(skillName) == null) {
                    Skill newSkill = new Skill(skillName);
                    skillService.save(newSkill);
                    CandidateSkill candidateSkill = new CandidateSkill(candidate.getId(), newSkill.getId());
                    candidateSkillService.save(candidateSkill);
                } else {
                    Skill newSkill = skillService.findByName(skillName);
                    skillService.save(newSkill);
                    CandidateSkill candidateSkill = new CandidateSkill(candidate.getId(), newSkill.getId());
                    candidateSkillService.save(candidateSkill);
                }
            }
            return false;
        }
    }

    public boolean isExists(String firstname, String lastname) {
        if(candidateRepository.findByFirstNameAndLastName(firstname,lastname) != null) {
            return true;
        }else {
            return false;
        }
    }

    public void delete(String firstname, String lastname) {
        Candidate candidate = candidateRepository.findByFirstNameAndLastName(firstname, lastname);
        candidateRepository.delete(candidate);
        List<CandidateSkill> candidateSkillServiceList = candidateSkillService.findByCandidateId(candidate.getId());
        for (CandidateSkill candidateSkill : candidateSkillServiceList) {
            candidateSkillService.delete(candidateSkill);
        }
    }
}