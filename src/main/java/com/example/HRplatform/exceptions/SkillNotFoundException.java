package com.example.HRplatform.exceptions;

import com.example.HRplatform.model.Skill;

public class SkillNotFoundException extends EntityNotFoundException {

    public SkillNotFoundException(Long id) {
        super(id, Skill.class);
    }

    public SkillNotFoundException(String name) {
        super(name, "name", Skill.class);
    }

}
