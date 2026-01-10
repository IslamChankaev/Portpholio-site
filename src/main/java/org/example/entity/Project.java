package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 500)
    private String projectUrl;

    @Column(length = 500)
    private String githubUrl;

    @ElementCollection
    @CollectionTable(name = "project_technologies")
    private List<String> technologies;

    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    private ProjectCategory category;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDate.now();
    }

    public enum ProjectCategory {
        WEB_DEVELOPMENT, MOBILE_APP, DESKTOP_APP,
        MACHINE_LEARNING, OTHER
    }
}