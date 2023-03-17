package com.example.HRplatform.service;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.dto.RemoveSkillRequestDto;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(candidateRepository.findByFirstnameAndLastname(candidateDto.getFirstname(),candidateDto.getLastname()) != null) {
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
        if(candidateRepository.findByFirstnameAndLastname(firstname,lastname) != null) {
            return true;
        }else {
            return false;
        }
    }

    public void delete(String firstname, String lastname) {
        Candidate candidate = candidateRepository.findByFirstnameAndLastname(firstname, lastname);
        candidateRepository.delete(candidate);
        List<CandidateSkill> candidateSkillServiceList = candidateSkillService.findByCandidateId(candidate.getId());
        for (CandidateSkill candidateSkill : candidateSkillServiceList) {
            candidateSkillService.delete(candidateSkill);
        }
    }

    public void removeSkillFromCandidate(RemoveSkillRequestDto removeSkillRequestDto) {
        Candidate candidate = candidateRepository.findByFirstnameAndLastname(removeSkillRequestDto.getCandidateFirstname(), removeSkillRequestDto.getCandidateLastname());
        Skill skill = skillService.findByName(removeSkillRequestDto.getSkillName());
        List<CandidateSkill> candidateSkillServiceList = candidateSkillService.findByCandidateId(candidate.getId());
        for (CandidateSkill candidateSkill : candidateSkillServiceList) {
           if(skill.getId() == candidateSkill.getSkillId() && candidateSkill.getCandidateId() == candidate.getId()) {
               candidateSkillService.delete(candidateSkill);
           }
        }
    }

    public List<CandidateDto> findByFirstname(String firstname) {
        List<Candidate> candidates = candidateRepository.findByFirstname(firstname);
        List<CandidateDto> candidatesDto = new ArrayList<>();
        for (Candidate candidate : candidates) {
            List<CandidateSkill> candidateSkillServiceList = candidateSkillService.findByCandidateId(candidate.getId());
            List<Skill> skills = new ArrayList<>();
            for(CandidateSkill candidateSkill: candidateSkillServiceList) {
                skills.add(skillService.findById(candidateSkill.getSkillId()));
            }
            List<String> candidateSkills = new ArrayList<>();
            for (Skill skill: skills) {
                candidateSkills.add(skill.getName());
            }
            candidatesDto.add(new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getPhoneNumber(), candidate.getDateOfBirth(), candidateSkills));
        }
        return candidatesDto;
    }

    public CandidateDto findByFirstnameAndLastname(String firstname, String lastname) {
        Candidate candidate = candidateRepository.findByFirstnameAndLastname(firstname,lastname);
        List<CandidateSkill> candidateSkillServiceList = candidateSkillService.findByCandidateId(candidate.getId());
        List<Skill> skills = new ArrayList<>();
        for(CandidateSkill candidateSkill: candidateSkillServiceList) {
            skills.add(skillService.findById(candidateSkill.getSkillId()));
        }
        List<String> candidateSkills = new ArrayList<>();
        for (Skill skill: skills) {
            candidateSkills.add(skill.getName());
        }
        CandidateDto candidateDto = new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getPhoneNumber(), candidate.getDateOfBirth(), candidateSkills);
        return candidateDto;
    }

        public List<CandidateDto> findBySkillName(String skillName) {
        List<CandidateDto> candidatesDto = new ArrayList<>();
        Skill skill = skillService.findByName(skillName);
        if(skill == null) {
           return candidatesDto;
        }
        List<CandidateSkill> candidateSkills = candidateSkillService.findBySkillId(skill.getId());
        List<Candidate> candidates = new ArrayList<>();
        for (CandidateSkill candidateSkill: candidateSkills) {
            candidates.add(candidateRepository.findByCandidateId(candidateSkill.getCandidateId()));
        }
        for(Candidate candidate: candidates) {
            List<CandidateSkill> cSkills = candidateSkillService.findByCandidateId(candidate.getId());
            List<Skill> skills = new ArrayList<>();
            for(CandidateSkill candidateSkill: cSkills) {
                skills.add(skillService.findById(candidateSkill.getSkillId()));
            }
            List<String> skillsToString = new ArrayList<>();
            for(Skill s: skills) {
                skillsToString.add(s.getName());
            }
            candidatesDto.add(new CandidateDto(candidate.getFirstname(),candidate.getLastname(),candidate.getEmail(),candidate.getPhoneNumber(),candidate.getDateOfBirth(),skillsToString));
        }
        return candidatesDto;
    }
}