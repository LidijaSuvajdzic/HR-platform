package com.example.HRplatform.mappers;

import com.example.HRplatform.dto.SkillDto;
import com.example.HRplatform.model.Skill;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SkillMapper {

    public Skill map(final SkillDto skillDto) {
            return Skill.builder()
                    .name(skillDto.getName().toUpperCase())
                    .build();
        }

    public SkillDto map(final Skill skill) {
        return SkillDto.builder()
                .name(skill.getName())
                .build();
    }

    public List<SkillDto> map(final List<Skill> skills) {
        List<SkillDto> skillDtos = new ArrayList<>();
        skills.forEach(skill -> {
            skillDtos.add(map(skill));
        });
        return skillDtos;
    }
}
