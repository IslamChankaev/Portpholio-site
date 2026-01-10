package org.example;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entity.Project;
import org.example.entity.Skill;
import org.example.entity.User;
import org.example.repository.ProjectRepository;
import org.example.repository.SkillRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .password("admin123") // Открытый пароль
                    .role("ADMIN")
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }

        // Добавляем тестовые навыки если их нет
        if (skillRepository.count() == 0) {
            Skill springBoot = Skill.builder()
                    .name("Spring Boot")
                    .level(90)
                    .iconUrl("https://spring.io/img/spring.svg")
                    .type(Skill.SkillType.FRAMEWORK)
                    .build();

            Skill java = Skill.builder()
                    .name("Java")
                    .level(95)
                    .iconUrl("https://cdn-icons-png.flaticon.com/512/226/226777.png")
                    .type(Skill.SkillType.PROGRAMMING_LANGUAGE)
                    .build();

            Skill mysql = Skill.builder()
                    .name("MySQL")
                    .level(85)
                    .iconUrl("https://www.mysql.com/common/logos/logo-mysql-170x115.png")
                    .type(Skill.SkillType.DATABASE)
                    .build();

            skillRepository.saveAll(Arrays.asList(springBoot, java, mysql));
        }

        // Добавляем тестовые проекты если их нет
        if (projectRepository.count() == 0) {
            Project project1 = Project.builder()
                    .title("Интернет-магазин на Spring Boot")
                    .description("Полнофункциональный интернет-магазин с корзиной, оплатой и админ-панелью")
                    .imageUrl("https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")
                    .projectUrl("https://demo-shop.example.com")
                    .githubUrl("https://github.com/yourusername/spring-boot-shop")
                    .technologies(Arrays.asList("Java", "Spring Boot", "MySQL", "Bootstrap", "Thymeleaf"))
                    .category(Project.ProjectCategory.WEB_DEVELOPMENT)
                    .createdDate(LocalDate.now())
                    .build();

            projectRepository.save(project1);
        }
    }
}