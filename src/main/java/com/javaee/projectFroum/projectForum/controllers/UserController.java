package com.javaee.projectFroum.projectForum.controllers;

import com.javaee.projectFroum.projectForum.dto.PostDto;
import com.javaee.projectFroum.projectForum.models.User;
import com.javaee.projectFroum.projectForum.security.services.SecurityService;
import com.javaee.projectFroum.projectForum.services.interfaces.UserService;
import com.javaee.projectFroum.projectForum.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        log.info("Registration page returned.");
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Error while registartion.");
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        log.info("Registration successful.");
        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null){
            model.addAttribute("error", "Your username and password is invalid.");
            log.error("Your username and password is invalid.");
        }

        if (logout != null){
            model.addAttribute("message", "You have been logged out successfully.");
            log.error("You have been logged out successfully.");
        }
        log.info("Login page returned.");
        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        log.info("Welcome page returned.");
        return "welcome";
    }

    @GetMapping(path = "/follow/{id}")
    public String followTopic(@PathVariable("id") long id) {
        userService.addTopicToFollowed(id);
        log.info("Topic added to followed.");
        return "redirect:/topic";
    }
    @GetMapping(path = "/unfollow/{id}")
    public String unfollowTopic(@PathVariable("id") long id) {
        userService.deleteTopicFromFollowed(id);
        log.info("Topic unfollowed.");
        return "redirect:/topic";
    }

}
