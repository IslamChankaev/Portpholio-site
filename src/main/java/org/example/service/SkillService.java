package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.entity.Skill;
import org.example.repository.SkillRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAllByOrderByLevelDesc();
    }

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    public List<Skill> getSkillsByType(Skill.SkillType type) {
        return skillRepository.findByType(type);
    }
}