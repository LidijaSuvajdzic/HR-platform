package com.example.HRplatform.service;
import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.exceptions.SkillExistsException;
import com.example.HRplatform.exceptions.SkillNotFoundException;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class  SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public void save(SkillDto skillDto) {
        Skill skill = new Skill(skillDto.getName());
        validateCreate(skill);
        skillRepository.save(skill);
    }

    public void validateCreate(Skill skill) {
        Objects.requireNonNull(skill);
        if(skillRepository.findByName(skill.getName()).isPresent()) {
            throw new SkillExistsException(skill.getName());
        }
    }

    public void save(Skill skill) {
        skillRepository.save(skill);
    }

    public Skill findByName(String name) {
        return skillRepository.findByName(name).orElseThrow(() -> new SkillNotFoundException(name));
    }

    public void save(String skillName) {
        skillRepository.save(new Skill(skillName));
    }

    public Skill findById(Long skillId) {
        return skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
    }
}
