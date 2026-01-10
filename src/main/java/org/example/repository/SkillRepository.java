package org.example.repository;

import org.example.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByOrderByLevelDesc();
    List<Skill> findByType(Skill.SkillType type);
}