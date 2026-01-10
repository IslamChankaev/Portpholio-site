package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.ContactMessage;
import org.example.entity.Project;
import org.example.entity.Skill;
import org.example.service.ContactService;
import org.example.service.ProjectService;
import org.example.service.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    private final ProjectService projectService;
    private final SkillService skillService;
    private final ContactService contactService;

    @GetMapping("/")
    public String home(Model model) {
        List<Project> projects = projectService.getAllProjects();
        List<Skill> skills = skillService.getAllSkills();

        model.addAttribute("projects", projects);
        model.addAttribute("skills", skills);
        return "index";
    }

    @PostMapping("/contact")
    public String sendMessage(@ModelAttribute ContactMessage message, Model model) {
        contactService.saveMessage(message);
        model.addAttribute("success", "Сообщение успешно отправлено!");

        List<Project> projects = projectService.getAllProjects();
        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("projects", projects);
        model.addAttribute("skills", skills);

        return "index";
    }
}