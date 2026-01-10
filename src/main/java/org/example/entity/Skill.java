package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer level; // 1-100

    @Column(length = 500)
    private String iconUrl;

    @Enumerated(EnumType.STRING)
    private SkillType type;

    public enum SkillType {
        PROGRAMMING_LANGUAGE, FRAMEWORK, DATABASE,
        TOOL, LIBRARY, OTHER
    }
}