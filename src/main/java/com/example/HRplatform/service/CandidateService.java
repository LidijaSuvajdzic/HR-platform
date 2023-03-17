package com.example.HRplatform.service;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    SkillService skillService;

    @Transactional
    public boolean save(CandidateDto candidateDto) {
        List<Candidate> possibleCandidates = new ArrayList<>();
        if(candidateRepository.findByFirstNameAndLastName(candidateDto.getFirstname(),candidateDto.getLastname()) != null) {
            return true;
        }else {
            Candidate candidate = new Candidate(candidateDto.getFirstname(), candidateDto.getLastname(), candidateDto.getEmail(), candidateDto.getPhoneNumber(), candidateDto.getDate());
            List<Skill> candidateSkills = new ArrayList<>();
            for (String skillName : candidateDto.getSkills()) {
                if (skillService.findByName(skillName) == null) {
                    Skill newSkill = new Skill(skillName);
                    candidateSkills.add(newSkill);
                } else {
                    Skill newSkill = skillService.findByName(skillName);
                    candidateSkills.add(newSkill);
                }
            }
            candidate.setSkills(candidateSkills);
            candidateRepository.save(candidate);
            return false;
        }
    }
}