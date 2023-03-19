package com.example.HRplatform.service;
import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.exceptions.SkillExistsException;
import com.example.HRplatform.exceptions.SkillNotFoundException;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class  SkillService {

    private final SkillRepository skillRepository;

    public void save(SkillDto skillDto) {
        Skill skill = new Skill(skillDto.getName());
        if(skillRepository.findByName(skill.getName()).isPresent()) {
            throw new SkillExistsException(skill.getName());
        }
        skillRepository.save(skill);
    }

    public void save(Skill skill) {
        skillRepository.save(skill);
    }

    public Skill findByName(String name) {
        return skillRepository.findByName(name).orElseThrow(() -> new SkillNotFoundException(name));
    }
    public Skill getSkillByName(String name) {
        return skillRepository.findByName(name).orElse(null);
    }

    public void save(String skillName) {
        skillRepository.save(new Skill(skillName));
    }

    public Skill findById(Long skillId) {
        return skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
    }

}
