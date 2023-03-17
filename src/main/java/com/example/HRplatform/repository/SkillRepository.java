package com.example.HRplatform.repository;

import com.example.HRplatform.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Skill save(Skill skill);

    Skill findByName(String name);

    @Query("SELECT s FROM Skill s WHERE s.id = ?1")
    Skill findBySkillId(Long id);
}
