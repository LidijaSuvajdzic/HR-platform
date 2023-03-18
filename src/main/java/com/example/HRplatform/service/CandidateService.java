package com.example.HRplatform.service;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.dto.NewCandidateDto;
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


    public boolean save(NewCandidateDto newCandidateDto) {
        if(candidateRepository.findByFirstnameAndLastname(newCandidateDto.getFirstname(), newCandidateDto.getLastname()) != null) {
            return true;
        }else {
            Candidate candidate = new Candidate(newCandidateDto.getFirstname(), newCandidateDto.getLastname(), newCandidateDto.getEmail(), newCandidateDto.getPhoneNumber(), newCandidateDto.getDate());
            candidateRepository.save(candidate);
            for (String skillName : newCandidateDto.getSkills()) {
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

    public void updateCandidate(CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findByFirstnameAndLastname(candidateDto.getOldFirstname(),candidateDto.getOldLastname());
        candidate.setFirstname(candidateDto.getFirstname());
        candidate.setLastname(candidate.getLastname());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setDateOfBirth(candidateDto.getDate());
        candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        candidateRepository.save(candidate);
        List<CandidateSkill> oldCandidateSkills = candidateSkillService.findByCandidateId(candidate.getId());
        List<Skill> oldSkills = new ArrayList<>();
        for(CandidateSkill candidateSkill: oldCandidateSkills) {
            oldSkills.add(skillService.findById(candidateSkill.getSkillId()));
        }
        List<String> newSkillsToString = candidateDto.getSkills();
        List<Skill> newSkills = new ArrayList<>();
        for(String s: newSkillsToString) {
            if(skillService.findByName(s) == null) {
                Skill newSkill = new Skill(s);
                skillService.save(newSkill);
                newSkills.add(skillService.findByName(s));
            }else {
                newSkills.add(skillService.findByName(s));
            }
        }
        for(Skill oldSkill: oldSkills) {
            boolean isExists = false;
            for(Skill newSkill: newSkills) {
                if(oldSkill.getName().equals(newSkill.getName())) {
                    isExists=true;
                }
            }
            if(!isExists) {
                candidateSkillService.delete(candidate.getId(), oldSkill.getId());
            }
        }
        for(Skill newSkill: newSkills) {
                if(skillService.findByName(newSkill.getName()) == null) {
                    skillService.save(newSkill);
                    CandidateSkill candidateSkill = new CandidateSkill(candidate.getId(),newSkill.getId());
                    candidateSkillService.save(candidateSkill);
                }else {
                    if (candidateSkillService.findByIds(candidate.getId(), newSkill.getId()) == null) {
                        CandidateSkill candidateSkill = new CandidateSkill(candidate.getId(), newSkill.getId());
                        candidateSkillService.save(candidateSkill);
                    }
                }
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

    public List<NewCandidateDto> findByFirstname(String firstname) {
        List<Candidate> candidates = candidateRepository.findByFirstname(firstname);
        List<NewCandidateDto> candidatesDto = new ArrayList<>();
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
            candidatesDto.add(new NewCandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getPhoneNumber(), candidate.getDateOfBirth(), candidateSkills));
        }
        return candidatesDto;
    }

    public NewCandidateDto findByFirstnameAndLastname(String firstname, String lastname) {
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
        NewCandidateDto newCandidateDto = new NewCandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getPhoneNumber(), candidate.getDateOfBirth(), candidateSkills);
        return newCandidateDto;
    }

        public List<NewCandidateDto> findBySkillName(String skillName) {
        List<NewCandidateDto> candidatesDto = new ArrayList<>();
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
            candidatesDto.add(new NewCandidateDto(candidate.getFirstname(),candidate.getLastname(),candidate.getEmail(),candidate.getPhoneNumber(),candidate.getDateOfBirth(),skillsToString));
        }
        return candidatesDto;
    }
}