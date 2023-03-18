package com.example.HRplatform.exceptions;

import com.example.HRplatform.model.Skill;

public class SkillExistsException extends EntityExistsException {

        public SkillExistsException(String username) {
            super(username, "name", Skill.class);
        }

    }

