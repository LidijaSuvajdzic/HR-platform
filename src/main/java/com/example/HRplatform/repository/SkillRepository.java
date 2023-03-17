package com.example.HRplatform.repository;

import com.example.HRplatform.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Skill save(Skill skill);

    Skill findByName(String name);
}
