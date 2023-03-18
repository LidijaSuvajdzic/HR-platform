package com.example.HRplatform.repository;

import com.example.HRplatform.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Skill save(Skill skill);

    Optional<Skill> findByName(String name);

    Optional<Skill> findById(Long id);
}
