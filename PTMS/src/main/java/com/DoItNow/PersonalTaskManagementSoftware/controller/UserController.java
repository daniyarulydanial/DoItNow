package com.DoItNow.PersonalTaskManagementSoftware.controller;

import com.DoItNow.PersonalTaskManagementSoftware.model.CustomUserDetails;
import com.DoItNow.PersonalTaskManagementSoftware.model.NewTask;
import com.DoItNow.PersonalTaskManagementSoftware.model.Task;
import com.DoItNow.PersonalTaskManagementSoftware.model.UserModel;
import com.DoItNow.PersonalTaskManagementSoftware.repository.TaskRepository;
import com.DoItNow.PersonalTaskManagementSoftware.repository.UserRepository;
import com.DoItNow.PersonalTaskManagementSoftware.service.TaskService;
import com.DoItNow.PersonalTaskManagementSoftware.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @GetMapping("/login")
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login_page";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showSignUpPage(Model model) {
        model.addAttribute("user", new UserModel());
        return "register_page";
    }

    @PostMapping("/process_register")
    public String processRegister(UserModel user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = ((CustomUserDetails) principal).getUser();
        Task task = new Task();
        model.addAttribute("username", user.getLogin());
        model.addAttribute("reqTask", task);
        model.addAttribute("allTask", taskService.findByUserIdStatus(user.getId(), Status.ACTIVE.getValue()));
        model.addAttribute("allPassiveTask", taskService.findByUserIdStatus(user.getId(), Status.PASSIVE.getValue()));
        return "personal_page";
    }

    @PostMapping("/task/saveTask")
    public String saveTodo(@ModelAttribute("reqTask") Task reqTask, final RedirectAttributes redirectAttributes) {
        try {
            reqTask.setStatus(Status.ACTIVE.getValue());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserModel user = ((CustomUserDetails) principal).getUser();
            reqTask.setUserId(user.getId());
            taskService.save(reqTask);
            redirectAttributes.addFlashAttribute("msg", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
        }

        return "redirect:/list_users";
    }

    @PostMapping("/task/editTask")
    public String editTodo(@ModelAttribute("editTask") Task editTask, Model model) {
        try {
            Task task = taskService.findById(editTask.getId());
            if (!task.equals(editTask)) {
                taskService.update(editTask);
                model.addAttribute("msg", "success");
            } else {
                model.addAttribute("msg", "same");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "fail");
        }
        model.addAttribute("editTodo", editTask);
        return "edit";
    }

    @GetMapping(value = "/task/{operation}/{id}")
    public String todoOperation(@PathVariable("operation") String operation,
                                @PathVariable("id") int id, final RedirectAttributes redirectAttributes,
                                Model model) {

        if (operation.equals("delete")) {
            if (taskService.delete(id)) {
                redirectAttributes.addFlashAttribute("msg", "del");
                redirectAttributes.addFlashAttribute("msgText", " Task deleted permanently");
            } else {
                redirectAttributes.addFlashAttribute("msg", "del_fail");
                redirectAttributes.addFlashAttribute("msgText", " Task could not deleted. Please try later");
            }
        } else if (operation.equals("edit")) {
            Task editTask = taskService.findById(id);
            if (editTask != null) {
                model.addAttribute("editTask", editTask);
                return "edit";
            } else {
                redirectAttributes.addFlashAttribute("msg", "notfound");
            }
        }
        return "redirect:/list_users";
    }

    @GetMapping("/calendarView")
    public String calendarView(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = ((CustomUserDetails) principal).getUser();
        model.addAttribute("allTasks", taskRepository.findAll(user.getId()));
        List<NewTask> list = taskRepository.findAll(user.getId());
        List<String> titles = new ArrayList<>();
        List<LocalDate> startDates = new ArrayList<>();
        List<LocalDate> endDates = new ArrayList<>();
        for (NewTask newTask : list) {
            titles.add(newTask.getTitle());
            startDates.add(newTask.getStart());
            endDates.add(newTask.getEnd());
        }
        model.addAttribute("titles", titles);
        model.addAttribute("starts", startDates);
        model.addAttribute("ends", endDates);
        return "calendar_page";
    }

    @GetMapping("/subscribe")
    public String getSubscribePage(){
        return "subscribe_page";
    }
    @GetMapping("/store")
    public String viewStorePage(){
        return "store_page";
    }
}
