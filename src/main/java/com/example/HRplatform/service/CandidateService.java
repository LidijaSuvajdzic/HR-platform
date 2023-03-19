package com.example.HRplatform.service;
import com.example.HRplatform.dto.CandidateDto;
import com.example.HRplatform.dto.RemoveSkillRequestDto;
import com.example.HRplatform.dto.SkillsDto;
import com.example.HRplatform.dto.UpdatedCandidateDto;
import com.example.HRplatform.exceptions.CandidateExistsException;
import com.example.HRplatform.exceptions.CandidateNotFoundException;
import com.example.HRplatform.model.Candidate;
import com.example.HRplatform.model.CandidateSkill;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final SkillService skillService;
    private final CandidateSkillService candidateSkillService;

    @Transactional
    public void create(CandidateDto candidateDto) {
        Candidate candidate = new Candidate(candidateDto.getFirstname(), candidateDto.getLastname(), candidateDto.getEmail(), candidateDto.getPhoneNumber(), candidateDto.getDate());
        if (candidateRepository.findByEmail(candidate.getEmail()).isPresent()) {
            throw new CandidateExistsException(candidate.getEmail());
        }
        candidateRepository.save(candidate);
        List<CandidateSkill> candidateSkills = getCandidateSkills(candidate, candidateDto.getSkills());
        candidate.setSkills(candidateSkills);
        candidateRepository.save(candidate);
    }

    private List<CandidateSkill> getCandidateSkills(Candidate candidate, List<String> skillsToString) {
        List<CandidateSkill> candidateSkills = new ArrayList<>();
        for (String skillName : skillsToString) {
            Skill newSkill = new Skill();
            if (skillService.getSkillByName(skillName) == null) {
                newSkill.setName(skillName);
                skillService.save(newSkill);
            } else {
                newSkill = skillService.findByName(skillName);
            }
            CandidateSkill candidateSkill = new CandidateSkill(candidate.getId(), newSkill.getId());
            candidateSkillService.save(candidateSkill);
            candidateSkills.add(candidateSkill);
        }
        return candidateSkills;
    }

    @Transactional
    public void delete(String email) {
        if (!candidateRepository.existsByEmail(email)) {
            throw new CandidateNotFoundException(email);
        }
        Optional<Candidate> candidate = candidateRepository.findByEmail(email);
        candidate.ifPresent(candidateRepository::delete);
        List<CandidateSkill> candidateSkillServiceList = candidateSkillService.findByCandidateId(candidate.map(Candidate::getId).orElse(null));
        for (CandidateSkill candidateSkill : candidateSkillServiceList) {
            candidateSkillService.delete(candidateSkill);
        }
    }

    @Transactional
    public void updateCandidate(UpdatedCandidateDto updatedCandidateDto) {
        Candidate candidate = candidateRepository.findByFirstnameAndLastname(updatedCandidateDto.getOldFirstname(),updatedCandidateDto.getOldLastname()).orElseThrow(() -> new CandidateNotFoundException(updatedCandidateDto.getOldFirstname(),updatedCandidateDto.getOldLastname()));
        List<CandidateSkill> oldCandidateSkills = candidateSkillService.findByCandidateId(candidate.getId());
        List<Skill> oldSkills = new ArrayList<>();
        for(CandidateSkill candidateSkill: oldCandidateSkills) {
            oldSkills.add(skillService.findById(candidateSkill.getSkillId()));
        }
        List<String> newSkillsToString = updatedCandidateDto.getSkills();
        List<Skill> newSkills = getNewSkills(newSkillsToString);
        removeOldSkills(oldSkills,newSkills,candidate.getId());
        List<CandidateSkill> candidateSkills = addNewCandidateSkills(newSkills,candidate.getId());
        setUpdatedCandidate(updatedCandidateDto,candidateSkills,candidate);
    }

    private void setUpdatedCandidate(UpdatedCandidateDto updatedCandidateDto, List<CandidateSkill> candidateSkills,Candidate candidate) {
        candidate.setFirstname(updatedCandidateDto.getFirstname());
        candidate.setLastname(updatedCandidateDto.getLastname());
        candidate.setEmail(updatedCandidateDto.getEmail());
        candidate.setDateOfBirth(updatedCandidateDto.getDate());
        candidate.setPhoneNumber(updatedCandidateDto.getPhoneNumber());
        candidate.setSkills(candidateSkills);
        candidateRepository.save(candidate);
    }

    private List<CandidateSkill> addNewCandidateSkills(List<Skill> newSkills, Long id) {
        List<CandidateSkill> candidateSkills = new ArrayList<>();
        for(Skill newSkill: newSkills) {
            if(skillService.getSkillByName(newSkill.getName()) == null) {
                skillService.save(newSkill);
                CandidateSkill candidateSkill = new CandidateSkill(id,newSkill.getId());
                candidateSkillService.save(candidateSkill);
                candidateSkills.add(candidateSkill);
            }else {
                if (candidateSkillService.findByIds(id, newSkill.getId()) == null) {
                    CandidateSkill candidateSkill = new CandidateSkill(id, newSkill.getId());
                    candidateSkillService.save(candidateSkill);
                    candidateSkills.add(candidateSkill);
                }
            }
        }
        return candidateSkills;
    }

    private List<Skill> getNewSkills(List<String> newSkillsToString) {
        List<Skill> newSkills = new ArrayList<>();
        for(String s: newSkillsToString) {
            if(skillService.getSkillByName(s) == null) {
                Skill newSkill = new Skill(s);
                skillService.save(newSkill);
            }
            newSkills.add(skillService.findByName(s));
        }
        return newSkills;
    }

    private void removeOldSkills(List<Skill> oldSkills,List<Skill> newSkills, Long id) {
        for(Skill oldSkill: oldSkills) {
            boolean isExists = false;
            for(Skill newSkill: newSkills) {
                if(oldSkill.getName().equals(newSkill.getName())) {
                    isExists=true;
                }
            }
            if(!isExists) {
                candidateSkillService.delete(id, oldSkill.getId());
            }
        }
    }

    @Transactional
    public void removeSkillFromCandidate(RemoveSkillRequestDto removeSkillRequestDto) {
        Optional<Candidate> candidate = candidateRepository.findByEmail(removeSkillRequestDto.getCandidateEmail());
        Skill skill = skillService.findByName(removeSkillRequestDto.getSkillName());
        List<CandidateSkill> candidateSkillServiceList = candidateSkillService.findByCandidateId(candidate.map(Candidate::getId).orElse(null));
        for (CandidateSkill candidateSkill : candidateSkillServiceList) {
            if (skill.getId() == candidateSkill.getSkillId() && candidateSkill.getCandidateId() == candidate.map(Candidate::getId).orElse(null)) {
                candidateSkillService.delete(candidateSkill);
            }
        }
    }

    @Transactional
    public List<CandidateDto> findByFirstname(String firstname) {
        List<Candidate> candidates = candidateRepository.findByFirstname(firstname).orElseThrow(() -> new CandidateNotFoundException(firstname, " "));
        List<CandidateDto> candidatesDto = new ArrayList<>();

        for (Candidate candidate : candidates) {
            List<CandidateSkill> candidateSkillServiceList = candidate.getSkills();
            List<String> candidateSkillsToString = getCandidateSkills(candidateSkillServiceList);
            candidatesDto.add(new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getPhoneNumber(), candidate.getDateOfBirth(), candidateSkillsToString));
        }
        return candidatesDto;
    }

    @Transactional
    public CandidateDto findByFirstnameAndLastname(String firstname, String lastname) {
        Candidate candidate = candidateRepository.findByFirstnameAndLastname(firstname, lastname).orElseThrow(() -> new CandidateNotFoundException(firstname, lastname));
        List<CandidateSkill> candidateSkills = candidate.getSkills();
        List<String> candidateSkillsToString = getCandidateSkills(candidateSkills);
        CandidateDto CandidateDto = new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getPhoneNumber(), candidate.getDateOfBirth(), candidateSkillsToString);
        return CandidateDto;
    }

    private List<String> getCandidateSkills(List<CandidateSkill> candidateSkills) {
        List<String> candidateSkillsToString = new ArrayList<>();
        for (CandidateSkill candidateSkill : candidateSkills) {
            Skill skill = skillService.findById(candidateSkill.getSkillId());
            candidateSkillsToString.add(skill.getName());
        }
        return candidateSkillsToString;
    }

    @Transactional
    public List<CandidateDto> findBySkillsName(SkillsDto skillsDto) {
        List<CandidateDto> candidatesDto = new ArrayList<>();
        List<Skill> skills = convertSkillsDtoToSkills(skillsDto);
        for (Skill s : skills) {
            List<CandidateSkill> cs = candidateSkillService.findBySkillId(s.getId());
            List<Candidate> candidates = new ArrayList<>();
            for (CandidateSkill candidateSkill : cs) {
                Candidate candidate = candidateRepository.findById(candidateSkill.getCandidateId()).orElse(null);
                candidates.add(candidate);
            }
            for (Candidate candidate : candidates) {
                List<CandidateSkill> cSkills = candidate.getSkills();
                List<String> cSkillsToString = getCandidateSkills(cSkills);
                candidatesDto.add(new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getPhoneNumber(), candidate.getDateOfBirth(), cSkillsToString));
            }
        }
            return candidatesDto;
    }

    private List<Skill> convertSkillsDtoToSkills(SkillsDto skillsDto) {
        List<Skill> skills = new ArrayList<>();
        for(String skillName: skillsDto.getSkills()) {
            Skill skill = skillService.findByName(skillName);
            skills.add(skill);
        }
        return skills;
    }
}