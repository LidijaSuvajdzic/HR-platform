package com.example.HRplatform.service;
import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public boolean save(SkillDto skillDto) {
        if(skillRepository.findByName(skillDto.getName()) == null) {
            skillRepository.save(new Skill(skillDto.getName()));
            return false;
        }else {
            return true;
        }
    }

    public void save(Skill skill) {
        skillRepository.save(skill);
    }

    public Skill findByName(String name) {
        return skillRepository.findByName(name);
    }

    public void save(String skillName) {
        skillRepository.save(new Skill(skillName));
    }
}
