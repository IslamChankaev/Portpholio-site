package org.example.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.entity.Project;
import org.example.entity.Skill;
import org.example.entity.User;
import org.example.service.ContactService;
import org.example.service.ProjectService;
import org.example.service.SkillService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProjectService projectService;
    private final SkillService skillService;
    private final ContactService contactService;
    private final UserService userService;

    // Страница входа
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Обработка входа
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        // Проверяем учетные данные
        User user = userService.findByUsername(username).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            // Сохраняем пользователя в сессии
            session.setAttribute("admin", user);
            return "redirect:/admin/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Неверное имя пользователя или пароль");
            return "redirect:/admin/login";
        }
    }

    // Выход
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/";
    }

    // Проверка аутентификации
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("admin") != null;
    }

    // Главная страница админки
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        long unreadMessages = contactService.getUnreadCount();
        long messagesCount = contactService.getAllMessages().size();
        long projectsCount = projectService.getAllProjects().size();
        long skillsCount = skillService.getAllSkills().size();

        model.addAttribute("unreadMessages", unreadMessages);
        model.addAttribute("messagesCount", messagesCount);
        model.addAttribute("projectsCount", projectsCount);
        model.addAttribute("skillsCount", skillsCount);
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        return "admin/dashboard";
    }

    // Управление проектами
    @GetMapping("/projects")
    public String manageProjects(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "admin/projects";
    }

    @PostMapping("/projects")
    public String createProject(@ModelAttribute Project project,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        projectService.saveProject(project);
        redirectAttributes.addFlashAttribute("success", "Проект успешно добавлен!");
        return "redirect:/admin/projects";
    }

    @PostMapping("/projects/{id}/edit")
    public String updateProject(@PathVariable Long id,
                                @ModelAttribute Project project,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        project.setId(id);
        projectService.saveProject(project);
        redirectAttributes.addFlashAttribute("success", "Проект успешно обновлен!");
        return "redirect:/admin/projects";
    }

    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable Long id,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("success", "Проект успешно удален!");
        return "redirect:/admin/projects";
    }

    // Управление навыками
    @GetMapping("/skills")
    public String manageSkills(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "admin/skills";
    }

    @PostMapping("/skills")
    public String createSkill(@ModelAttribute Skill skill,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        skillService.saveSkill(skill);
        redirectAttributes.addFlashAttribute("success", "Навык успешно добавлен!");
        return "redirect:/admin/skills";
    }

    @PostMapping("/skills/{id}/edit")
    public String updateSkill(@PathVariable Long id,
                              @ModelAttribute Skill skill,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        skill.setId(id);
        skillService.saveSkill(skill);
        redirectAttributes.addFlashAttribute("success", "Навык успешно обновлен!");
        return "redirect:/admin/skills";
    }

    @PostMapping("/skills/{id}/delete")
    public String deleteSkill(@PathVariable Long id,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        skillService.deleteSkill(id);
        redirectAttributes.addFlashAttribute("success", "Навык успешно удален!");
        return "redirect:/admin/skills";
    }

    // Управление сообщениями
    @GetMapping("/messages")
    public String manageMessages(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        List<org.example.entity.ContactMessage> messages = contactService.getAllMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("unreadMessages", contactService.getUnreadCount());
        return "admin/messages";
    }

    @PostMapping("/messages/{id}/read")
    public String markAsRead(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        contactService.markAsRead(id);
        redirectAttributes.addFlashAttribute("success", "Сообщение отмечено как прочитанное!");
        return "redirect:/admin/messages";
    }

    @PostMapping("/messages/{id}/delete")
    public String deleteMessage(@PathVariable Long id,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/admin/login";
        }

        contactService.deleteMessage(id);
        redirectAttributes.addFlashAttribute("success", "Сообщение успешно удалено!");
        return "redirect:/admin/messages";
    }
}