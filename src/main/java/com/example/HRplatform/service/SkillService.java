package com.example.HRplatform.service;
import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.model.Skill;
import com.example.HRplatform.repository.SkillRepository;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    private SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void save(SkillDto skillDto) {
        skillRepository.save(new Skill(skillDto.getName()));
    }
}
